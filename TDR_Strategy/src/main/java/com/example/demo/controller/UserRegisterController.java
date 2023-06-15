//package com.example.demo.controller;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.mybatis.mapper.UsersMapper;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@RestController
//public class UserRegisterController {
//	  private final UsersMapper usersMapper;
//
//	  @GetMapping("/register")
//	  public String sample() {
//	    final var count = usersMapper.userInsert("yamada", "aaa@123", new BCryptPasswordEncoder().encode("abcd1234"));
//	    System.out.println(count); // insert件数
//	    return "register";
//	  }
//
//}
