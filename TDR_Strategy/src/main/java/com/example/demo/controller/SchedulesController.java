package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Bookmarks;
import com.example.domain.Schedules;
import com.example.domain.UseScenes;
import com.example.mybatis.mapper.BookmarksMapper;
import com.example.mybatis.mapper.RelationshipsMapper;
import com.example.mybatis.mapper.SchedulesMapper;
import com.example.mybatis.mapper.UsersMapper;

@RequestMapping("")
@Controller
public class SchedulesController {
	@Autowired
    private SchedulesMapper schedulesMapper;
	@Autowired
    private RelationshipsMapper RelationshipsMapper;
	@Autowired
    private BookmarksMapper bookmarksMapper;
	@Autowired
	private UsersMapper usersMapper;
	
	//投稿詳細ページ
	@GetMapping("schedule/{id}")
	public String sucheduleDetailPage(
		@PathVariable Integer id,
		Model model,
		@AuthenticationPrincipal UserDetails userDetails){
//			Integer loginUserId = getUserId(userDetails);
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
			
			//本文の格納
			scheduleList.add(schedule.getSchedule());
			
			//useridを取得（ユーザー画面遷移用）
			scheduleList.add(schedule.getUserId().toString());
//		}
		model.addAttribute("scheduleList", scheduleList);
		
		return "schedule";
	}
}
