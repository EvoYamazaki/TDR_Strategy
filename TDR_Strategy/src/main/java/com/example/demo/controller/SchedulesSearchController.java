package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.Bookmarks;
import com.example.domain.Schedules;
import com.example.domain.UseScenes;
import com.example.mybatis.mapper.BookmarksMapper;
import com.example.mybatis.mapper.RelationshipsMapper;
import com.example.mybatis.mapper.SchedulesMapper;
import com.example.mybatis.mapper.UseScenesMapper;
import com.example.mybatis.mapper.UsersMapper;

@Controller
@RequestMapping("")
public class SchedulesSearchController {
    @Autowired
    private SchedulesMapper schedulesMapper;
    @Autowired
    private UseScenesMapper useScenesMapper;
    @Autowired
    private UsersMapper usersMapper;
	@Autowired
    private RelationshipsMapper RelationshipsMapper;
	@Autowired
    private BookmarksMapper bookmarksMapper;

    // スケジュール検索画面を表示する
    @GetMapping("/schedule/search")
    public String searchScheduleForm(Model model) {
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
    	
        // 全ての使用シーンを取得
        List<UseScenes> allScenes = useScenesMapper.selectByExample(null);
        model.addAttribute("allScenes", allScenes);
        return "/schedule/search";
    }

    // 検索条件の受け取り、検索結果の表示
	@PostMapping("/schedule/search")
    public String searchSchedule(Model model,
    	@RequestParam(value = "park", required = false) Integer park,
    	@RequestParam(value = "keyword", required = false) String keyword,
    	@RequestParam(value = "scenes", required = false) List<Integer> scenes,
    	@RequestParam(value = "holiday", required = false) Boolean holiday,
    	@RequestParam(value = "numOfScenes", required = false) Integer numOfScenes) {
		
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
		
		//useScenesの入力を確認、変換
		StringBuilder sb = new StringBuilder();
		String selectUseScenes = null;
		Integer useScenesCount = 0;
		if(scenes != null) {
			for (int i = 0; i < scenes.size(); i++) {
				sb.append(scenes.get(i));
				if(i != scenes.size() -1) {
					sb.append(", ");
				}
				useScenesCount ++;
			}
			selectUseScenes = sb.toString();
		}
//		System.out.println(park);
//		System.out.println(keyword);
//		System.out.println(selectUseScenes);
//		System.out.println(holiday);
//		System.out.println(useScenesCount);
		
		//検索メソッドの呼び出し
		List<Schedules> searchResults = new ArrayList<>();
		if(selectUseScenes != null && park != null && holiday != null) {
			//すべての項目で検索
			System.out.println(1);
			searchResults = schedulesMapper.searchSchedulesAll(selectUseScenes, keyword, park, holiday, useScenesCount);
		} else if (selectUseScenes != null && park != null && holiday == null) {
			//holidayが未入力
			System.out.println(2);
			searchResults = schedulesMapper.searchSchedulesUP(selectUseScenes, keyword, park, useScenesCount);
		} else if (selectUseScenes != null && park == null && holiday != null) {
			//parkが未入力
			System.out.println(3);
			searchResults = schedulesMapper.searchSchedulesUH(selectUseScenes, keyword, holiday, useScenesCount);
		} else if (selectUseScenes == null && park != null && holiday != null) {
			//useSceneが未選択
			System.out.println(4);
			searchResults = schedulesMapper.searchSchedulesPH(keyword, park, holiday);
		} else if (selectUseScenes != null && park == null && holiday == null) {
			//holidayとparkが未入力
			System.out.println(5);
			searchResults = schedulesMapper.searchSchedulesU(selectUseScenes, keyword, useScenesCount);
		} else if (selectUseScenes == null && park != null && holiday == null) {
			//holidayとuseSceneが未入力
			System.out.println(6);
			searchResults = schedulesMapper.searchSchedulesP(keyword, park);
		} else if (selectUseScenes == null && park == null && holiday != null) {
			//parkとuseSceneが未入力
			System.out.println(7);
			searchResults = schedulesMapper.searchSchedulesH(keyword, holiday);
		} else if (selectUseScenes == null && park == null && holiday == null) {
			//検索ワードのみ（検索ワードが未入力の場合は全検索）
			System.out.println(8);
			searchResults = schedulesMapper.searchSchedulesW(keyword);
		}
		
		
		//表示用のデータリスト
		List<List<String>> schedulesView = new ArrayList<>();
		//表示件数
		int scheduleCount = 0;
		
		//要素を１つずつ取り出し、表示用のリストへ格納していく
		for(Schedules schedule: searchResults) {
			List<String> scheduleList = new ArrayList<>();
			System.out.println("ユーザーID" + schedulesMapper.selectUserIdById(schedule.getId()));
			System.out.println("スケID" + schedule.getId());
			//投稿者の名前を格納
			scheduleList.add(usersMapper.selectNameById(schedulesMapper.selectUserIdById(schedule.getId())));
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
			List<UseScenes> useScenesList = RelationshipsMapper.selectByUseSeenes(schedule.getId());
//			System.out.println(useScenesList);
			//１つずつ取り出し、String型に変換（#○○  #○○）
			StringBuilder sb2 = new StringBuilder();
			for (int i = 0; i < useScenesList.size(); i++) {
				sb2.append("#");
			    sb2.append(useScenesList.get(i));
			    if (i != useScenesList.size() - 1) {
			        sb2.append("  ");
			    }
			}
			String useScenes = sb2.toString();
//			System.out.println(useScenes);
			scheduleList.add(useScenes);
			//ブクマ数を格納
			List<Bookmarks> bookmarksList = bookmarksMapper.selectBySchedule_id(schedule.getId());
			scheduleList.add(String.valueOf(bookmarksList.size()));
			
			//useridを取得（ユーザー画面遷移用）
			scheduleList.add(schedulesMapper.selectUserIdById(schedule.getId()).toString());
			
			//idを取得（投稿詳細画面遷移用）
			scheduleList.add(schedule.getId().toString());
					
			//表示用のデータリストに格納
			schedulesView.add(scheduleList);
			
			//表示件数をカウント
			scheduleCount++;
		}
		
		model.addAttribute("schedulesView", schedulesView);
		model.addAttribute("scheduleCount", scheduleCount);

		return "schedule/results";
    }
	
    @GetMapping("/schedule/results")
    public String resultsSchedule(Model model) {
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
//	    
//	    //検索結果の取得
//	    List<Schedules> schedulesView = (List<Schedules>) model.getAttribute("schedulesView");
//	    //結果件数の取得
//	  	int scheduleCount = (int) model.getAttribute("scheduleCount");
//	  	
//		model.addAttribute("schedulesView", schedulesView);
//		model.addAttribute("scheduleCount", scheduleCount);
    	
    	return "schedule/results";
    }
}
