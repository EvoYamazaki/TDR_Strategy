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
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Bookmarks;
import com.example.domain.Schedules;
import com.example.domain.UseScenes;
import com.example.domain.Users;
import com.example.mybatis.mapper.BookmarksMapper;
import com.example.mybatis.mapper.RelationshipsMapper;
import com.example.mybatis.mapper.SchedulesMapper;
import com.example.mybatis.mapper.UsersMapper;

@RequestMapping("")
@Controller
public class HomeController {
	@Autowired
    private SchedulesMapper schedulesMapper;
	@Autowired
    private UsersMapper usersMapper;
	@Autowired
    private RelationshipsMapper RelationshipsMapper;
	@Autowired
    private BookmarksMapper bookmarksMapper;
	
	//ログイン情報の取得
	public boolean loginCheck(Model model) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String loggedInUsername = authentication.getName();
	    System.out.println(loggedInUsername);
	    boolean isLoggedIn = false;
	    if(!loggedInUsername.equals("anonymousUser")) {
	    	isLoggedIn = true;
	    	Users loginUser = usersMapper.selectByEmail(loggedInUsername);
	    	Integer loginUserId = loginUser.getId();
	    	model.addAttribute("loginUserId", loginUserId);
	    }
	    model.addAttribute("isLoggedIn", isLoggedIn);
	    return isLoggedIn;
	}
	
	
	//投稿一覧の表示(一旦)
	@GetMapping("/home")
	public String indexSchedules(Model model) {
		//ログイン情報の取得
		loginCheck(model);
		
		
		//Schedulesの取得
		List<Schedules> allSchedules = schedulesMapper.selectAll();
		//表示用のデータリスト
		List<List<String>> schedulesView = new ArrayList<>();
		
		//要素を１つずつ取り出し、表示用のリストへ格納していく
		for(Schedules schedule: allSchedules) {
			List<String> scheduleList = new ArrayList<String>();
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
			
			//useridを取得（ユーザー画面遷移用）
			scheduleList.add(schedulesMapper.selectUserIdById(schedule.getId()).toString());
			
			//idを取得（投稿詳細画面遷移用）
			scheduleList.add(schedule.getId().toString());
					
			//表示用のデータリストに格納
			schedulesView.add(scheduleList);
		}
		model.addAttribute("schedulesView", schedulesView);
	    return "home";
	}
}
