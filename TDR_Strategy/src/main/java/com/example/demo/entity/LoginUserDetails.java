package com.example.demo.entity;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.example.domain.Users;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
//equals()とhashCode()を生成するが親クラスのメソッドは呼び出さない
@EqualsAndHashCode(callSuper=false)
public class LoginUserDetails extends User {
    //Usersテーブルから取得したオブジェクトを格納
    private final Users users;

    //認証処理
    public LoginUserDetails(Users user, String role) {
        //Usersテーブルの名前とパスワードでログイン認証を行う
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(role));
        this.users = user;
    }
}