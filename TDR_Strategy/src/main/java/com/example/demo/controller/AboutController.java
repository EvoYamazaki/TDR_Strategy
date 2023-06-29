package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Users;
import com.example.mybatis.mapper.UsersMapper;

@RequestMapping("")
@Controller
public class AboutController {
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
	
	@GetMapping("/about")
	public String showAbout(Model model){
		
		loginCheck(model);
	    
		return "about";
	}

}
