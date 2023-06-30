package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.Bookmarks;
import com.example.domain.Schedules;
import com.example.domain.UseScenes;
import com.example.domain.Users;
import com.example.mybatis.mapper.BookmarksMapper;
import com.example.mybatis.mapper.RelationshipsMapper;
import com.example.mybatis.mapper.SchedulesMapper;
import com.example.mybatis.mapper.UsersMapper;

/**
* ユーザー情報 Controller
*/
@RequestMapping("/user")
@Controller
public class UsersController {
	@Autowired
    private SchedulesMapper schedulesMapper;
	@Autowired
    private RelationshipsMapper RelationshipsMapper;
	@Autowired
    private BookmarksMapper bookmarksMapper;
	@Autowired
	private UsersMapper usersMapper;
	
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
	
	//ログイン済みユーザーIDの取得
    public Integer getUserId(@AuthenticationPrincipal UserDetails userDetails) {
        // ログイン済みユーザーのIDを取得する
        String userEmail = userDetails.getUsername();
        Users users = usersMapper.selectByEmail(userEmail);
        Integer userId = users.getId();
        System.out.println(userId);
        return userId;
    }

	/**
	* ユーザー新規登録画面を表示
	* @param model Model
	* @return ユーザー情報一覧画面
	*/
	@GetMapping("/add")
	public String displayAdd(Model model) {
		//ログイン情報の取得
		loginCheck(model);
	    
		// 入力フォームで取り扱うオブジェクトを設定
		model.addAttribute("users", new Users());
		// 表示するHTMLを指定
		return "user/add";
	}


	/**
	* ユーザー新規登録
	*/

	
	@PostMapping("/create")
	public String submitNewUser(
		@RequestParam("name") String name,
		@RequestParam("email") String email,
		@RequestParam("password") String password,
		Authentication authentication,
		Model model) {
		String pass = new BCryptPasswordEncoder().encode(password);
		// userRequestに入力フォームの内容が格納されている
//		System.out.println(name);
//		System.out.println(email);
//		System.out.println(pass);
		usersMapper.userInsert(name, email, pass);
		return "login";
	}
	

	//ユーザーページ
	@GetMapping("/userpage/{id}")
	public String userPage(
		@PathVariable Integer id,
		Model model,
		@AuthenticationPrincipal UserDetails userDetails) {
		//ログイン情報の取得
	    boolean isLoggedIn = loginCheck(model);
	    
	    //アクセスしたページとログインIDが一致するか確認
	    if(isLoggedIn) {
	    	Integer loginUserId = getUserId(userDetails);
		    boolean isMypage = false;
		    if(loginUserId == id) {
		    	isMypage = true;
		    }
		    model.addAttribute("isMypage", isMypage);
	    }
	    
//		Integer loginUserId = getUserId(userDetails);
		//Userの取得
		List<Users> userList = usersMapper.selectById(id);
		//表示用のデータリスト
		List<String> userData = new ArrayList<>();
		//要素を１つずつ取り出し、表示用のリストへ格納していく
		for(Users user: userList) {
			//ユーザー名の格納
			userData.add(user.getName());
			//年齢の格納
			if(user.getAge() != null) {
				userData.add(user.getAge().toString());
			} else {
				userData.add("非公開");
			}
			//紹介文の格納
			if(user.getIntroduction() != null) {
				userData.add(user.getIntroduction());
			} else {
				userData.add(" ");
			}
		}
		model.addAttribute("userData", userData);
		
		
		//Scheduleの取得
		List<Schedules> userSchedules = schedulesMapper.selectAllByUserId(id);
		//表示用のデータリスト
		List<List<String>> schedulesView = new ArrayList<>();
		
		//要素を１つずつ取り出し、表示用のリストへ格納していく
		for(Schedules schedule: userSchedules) {
			List<String> scheduleList = new ArrayList<String>();
			//投稿者の名前を格納
			scheduleList.add(usersMapper.selectNameById(id));
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
			
			//idを取得（投稿詳細画面遷移用）
			scheduleList.add(schedule.getId().toString());
			
			//表示用のデータリストに格納
			schedulesView.add(scheduleList);
		}
		model.addAttribute("schedulesView", schedulesView);
		
		//user_idを渡す（ブックマーク表示用）
		model.addAttribute("id", id);
		return "user/userpage";
	}
	
	// ユーザープロフィール編集画面を表示
	@GetMapping("/edit")
	public String showProfileEdit(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		//ログイン情報の取得
		loginCheck(model);
		
		Integer userId = getUserId(userDetails);
		List<Users> usersList = usersMapper.selectById(userId);
		if(!usersList.isEmpty()) {
			Users user = usersList.get(0);
			model.addAttribute("user", user);
		}
		return "user/edit";
	}
	
	@PostMapping("/userpage/{id}")
	public String updateProfile(
	        @AuthenticationPrincipal UserDetails userDetails,
	        @ModelAttribute("user") Users updatedUser
	) {
	    String userEmail = userDetails.getUsername();
	    Users user = usersMapper.selectByEmail(userEmail);
	    user.setName(updatedUser.getName());
	    user.setIntroduction(updatedUser.getIntroduction());
	    user.setAge(updatedUser.getAge());
	    user.setEmail(updatedUser.getEmail());
	    if (!updatedUser.getPassword().isEmpty()) {
	        String encryptedPassword = new BCryptPasswordEncoder().encode(updatedUser.getPassword());
	        user.setPassword(encryptedPassword);
	    }
	    usersMapper.updateByPrimaryKeySelective(user);
	    return "redirect:/user/userpage/" + user.getId();
	}
	
	// 退会画面の表示
	@GetMapping("/withdraw")
	public String showWithdrawPage(Model model) {
		loginCheck(model);
		
		return "user/withdraw";
	}
	
	@PostMapping("/complete-withdraw")
	public String withdrawUser(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		Integer userId = getUserId(userDetails);
		usersMapper.deleteByPrimaryKey(userId);
//		SecurityContextHolder.clearContext();
		return "user/complete-withdraw";
	}

	
	//ユーザーページ(ブックマーク)
	@GetMapping("/bookmarks/{id}")
	public String userBookmarkPage(
		@PathVariable Integer id,
		Model model,
		@AuthenticationPrincipal UserDetails userDetails) {
		//ログイン情報の取得
	    boolean isLoggedIn = loginCheck(model);
	    
	    //アクセスしたページとログインIDが一致するか確認
	    if(isLoggedIn) {
	    	Integer loginUserId = getUserId(userDetails);
		    boolean isMypage = false;
		    if(loginUserId == id) {
		    	isMypage = true;
		    }
		    model.addAttribute("isMypage", isMypage);
	    }
	    
//		Integer loginUserId = getUserId(userDetails);
		//Userの取得
		List<Users> userList = usersMapper.selectById(id);
		//表示用のデータリスト
		List<String> userData = new ArrayList<>();
		//要素を１つずつ取り出し、表示用のリストへ格納していく
		for(Users user: userList) {
			//ユーザー名の格納
			userData.add(user.getName());
			//年齢の格納
			if(user.getAge() != null) {
				userData.add(user.getAge().toString());
			} else {
				userData.add("非公開");
			}
			//紹介文の格納
			if(user.getIntroduction() != null) {
				userData.add(user.getIntroduction());
			} else {
				userData.add(" ");
			}
		}
		model.addAttribute("userData", userData);
		
		
		//Scheduleの取得
		List<Schedules> userSchedules = schedulesMapper.selectBookmarksByUserId(id);
		//表示用のデータリスト
		List<List<String>> schedulesView = new ArrayList<>();
		
		//要素を１つずつ取り出し、表示用のリストへ格納していく
		for(Schedules schedule: userSchedules) {
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
			
			//idを取得（投稿詳細画面遷移用）
			scheduleList.add(schedule.getId().toString());
			
			//表示用のデータリストに格納
			schedulesView.add(scheduleList);
		}
		model.addAttribute("schedulesView", schedulesView);
		
		//user_idを渡す（ブックマーク表示用）
		model.addAttribute("id", id);
		
		return "user/bookmarks";
	}
}