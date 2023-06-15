package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.Users;
import com.example.mybatis.mapper.UsersMapper;

/**
* ユーザー情報 Controller
*/
@RequestMapping("")
@Controller
public class UsersController {

	/**
	* ユーザー新規登録画面を表示
	* @param model Model
	* @return ユーザー情報一覧画面
	*/
	@GetMapping("/user/add")
	public String displayAdd(Model model) {
	// 入力フォームで取り扱うオブジェクトを設定
	model.addAttribute("users", new Users());
	// 表示するHTMLを指定
	return "user/add";
	}


	/**
	* ユーザー新規登録
	*/
	@Autowired
	private UsersMapper usersMapper;
	
	@PostMapping("/user/create")
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
		return "hello";
	}
}