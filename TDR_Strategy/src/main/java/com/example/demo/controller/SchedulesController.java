package com.example.demo.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.Bookmarks;
import com.example.domain.Relationships;
import com.example.domain.Schedules;
import com.example.domain.UseScenes;
import com.example.domain.Users;
import com.example.mybatis.mapper.BookmarksMapper;
import com.example.mybatis.mapper.RelationshipsMapper;
import com.example.mybatis.mapper.SchedulesMapper;
import com.example.mybatis.mapper.UseScenesMapper;
import com.example.mybatis.mapper.UsersMapper;

@RequestMapping("")
@Controller
public class SchedulesController {
	@Autowired
    private SchedulesMapper schedulesMapper;
	@Autowired
    private RelationshipsMapper relationshipsMapper;
	@Autowired
    private BookmarksMapper bookmarksMapper;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private UseScenesMapper useScenesMapper;
	
	//新しいスケジュール投稿フォームを表示する
    @GetMapping("/new")
    public String showNewScheduleForm(Model model) {
    	//全ての使用シーンを取得
        List<UseScenes> allScenes = useScenesMapper.selectByExample(null);
        model.addAttribute("allScenes", allScenes);
        return "new-schedule";
    }
    
    //新しいスケジュールを提出する
    @PostMapping("/new")
    public String submitNewSchedule(
            @RequestParam("park") int park,
            @RequestParam("date") Date date,
            @RequestParam("scenes") List<Integer> scenes,
            @RequestParam("holiday") boolean isHoliday,
            @RequestParam("schedule") String schedule,
            @AuthenticationPrincipal UserDetails userDetails,
            Authentication authentication,
            Model model) {
//    	// ログインユーザーのIDを取得
    	String userEmail = userDetails.getUsername();
        Users users = usersMapper.selectByEmail(userEmail);
        Integer userId = users.getId();
        
    	//新しいスケジュールオブジェクトを作成し、パラメータを設定する
        Schedules newSchedule = new Schedules();
        
        newSchedule.setUserId(userId);
        newSchedule.setPark(park);
        newSchedule.setDate(date);
        newSchedule.setHoliday(isHoliday);
        newSchedule.setSchedule(schedule);
        
     // スケジュールをデータベースに挿入する
        schedulesMapper.insert(newSchedule);
        
     // データベースから最新のスケジュールIDを取得する
        int scheduleId = schedulesMapper.getLastInsertId();

        // scheduleIdの値を確認して、0より大きい場合のみ関連を挿入する
        if (scheduleId > 0) {
            // 選択されたシーンごとに使用シーンオブジェクトを作成し、データベースに挿入する
            for (Integer sceneId : scenes) {
                Relationships relationship = new Relationships();
                relationship.setScheduleId(scheduleId);
                relationship.setUseSceneId(sceneId);
                relationshipsMapper.insert(relationship);
            }
        }
        
        //成功フラグをモデルに追加し、新しいスケジュールフォームを表示する
        model.addAttribute("success", true);
        return "success";
    }

	
	//投稿詳細ページ
	@GetMapping("/schedule/{id}")
	public String sucheduleDetailPage(
		@PathVariable Integer id,
		Model model,
		@AuthenticationPrincipal UserDetails userDetails){
		
		//ログイン情報の取得
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String loggedInUsername = authentication.getName();
	    System.out.println(loggedInUsername);
//	    boolean isLoggedIn = authentication.isAuthenticated();
	    boolean isLoggedIn = true;
	    if(loggedInUsername == "anonymousUser") {
	    	isLoggedIn = false;
	    }
	    model.addAttribute("isLoggedIn", isLoggedIn);
	    
	    
		//Scheduleの取得
		Schedules schedule = schedulesMapper.selectByPrimaryKey(id);
		//表示用のデータリスト
		List<String> scheduleList = new ArrayList<>();
		
		//要素を１つずつ取り出し、表示用のリストへ格納していく
//		for(Schedules schedule: schedules) {
			//投稿者の名前を格納
			scheduleList.add(usersMapper.selectNameById(schedule.getUserId()));
			//パークを格納
			switch(schedule.getPark()) {
				case 0:
					scheduleList.add("東京ディズニーランド");
					break;
				case 1:
					scheduleList.add("東京ディズニーシー");
					break;
				default :
					scheduleList.add("その他");
					break;
			}
//			//来園日をフォーマット
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd (E)");
			String formattedDate = formatter.format(schedule.getDate());
			//来園日 + 休日フラグを格納
			if(schedule.getHoliday()) {
				scheduleList.add(formattedDate + "（土日祝）");
			} else {
				scheduleList.add(formattedDate + "（平日）");
			}
			//利用シーンを格納
			//スケジュールに紐づく利用シーンを取得
			List<UseScenes> useScenesList = relationshipsMapper.selectByUseSeenes(schedule.getId());
//			System.out.println(useScenesList);
			//１つずつ取り出し、String型に変換（#○○  #○○）
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < useScenesList.size(); i++) {
				sb.append("#");
			    sb.append(useScenesList.get(i));
			    if (i != useScenesList.size() - 1) {
			        sb.append("  ");
			    }
			}
			String useScenes = sb.toString();
//			System.out.println(useScenes);
			scheduleList.add(useScenes);
			//ブクマ数を格納
			List<Bookmarks> bookmarksList = bookmarksMapper.selectBySchedule_id(schedule.getId());
			scheduleList.add(String.valueOf(bookmarksList.size()));
			
			//本文の格納
			scheduleList.add(schedule.getSchedule());
			
			//useridを取得（ユーザー画面遷移用）
			scheduleList.add(schedule.getUserId().toString());
			
			//sucheduleidを取得（ブックマーク用）
			scheduleList.add(schedule.getId().toString());
//		}
		model.addAttribute("scheduleList", scheduleList);
		
		if(isLoggedIn) {
			//ブックマーク登録の確認
			boolean BMCheck = bookmarkCheck(userDetails,id);
			model.addAttribute("BMCheck", BMCheck);
		}
		
		return "schedule";
	}
	
	//ブックマーク確認
	public boolean bookmarkCheck(
		@AuthenticationPrincipal UserDetails userDetails,
		Integer id) {
		boolean BMCheck = true;
		
		//ログイン中のユーザIDを取得
		String userEmail = userDetails.getUsername();
        Users users = usersMapper.selectByEmail(userEmail);
        Integer userId = users.getId();
		
		List<Bookmarks> bookmark = bookmarksMapper.selectBySchedule_idANDUser_id(userId, id);
		System.out.println(bookmark.size());
		if(bookmark.size() == 0) {
			BMCheck = false;
		}
		
		return BMCheck;
	}
	
	//ブックマーク登録
	@PostMapping("schedule/{id}/bookmarkInsert")
	public String bookmarkRegister(
		@PathVariable Integer id,
		Model model,
		@AuthenticationPrincipal UserDetails userDetails){

		//ログイン中のユーザIDを取得
		String userEmail = userDetails.getUsername();
        Users users = usersMapper.selectByEmail(userEmail);
        Integer userId = users.getId();
        
		bookmarksMapper.bookmarkInsert(userId, id);
		
		return "redirect:/schedule/" + id;
	}
	
	//ブックマーク解除
//	@DeleteMapping("schedule/{id}/bookmarkDelete")
	@RequestMapping(value = "/schedule/{id}/bookmarkDelete", method = RequestMethod.POST)
	public String bookmarkDelete(
		@PathVariable Integer id,
		Model model,
		@AuthenticationPrincipal UserDetails userDetails) {
		
		//ログイン中のユーザIDを取得
		String userEmail = userDetails.getUsername();
        Users users = usersMapper.selectByEmail(userEmail);
        Integer userId = users.getId();

	    bookmarksMapper.bookmarkDelete(userId, id);

	    return "redirect:/schedule/" + id;
	}
}
