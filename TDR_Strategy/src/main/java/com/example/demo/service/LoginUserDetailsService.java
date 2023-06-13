package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.LoginUserDetails;
import com.example.domain.Users;
import com.example.domain.UsersExample;
import com.example.mybatis.mapper.UsersMapper;

/***
 * ログインイン時に認証ユーザーを「usersテーブル」から情報を取得するクラス
 */
@Service
public class LoginUserDetailsService implements UserDetailsService {
    @Autowired
    UsersExample usersExample;

    @Autowired
    UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //入力された名前をキーにusersテーブルのレコードを1件取得
        Users users = usersMapper.selectByEmail(email);

        //該当レコードが取得できなかった場合はエラーにする
        if  (users   ==  null)   {
            throw new UsernameNotFoundException("Wrong email or password");
        }

        //ログインユーザー権限を設定
        String role = "ROLE_ADMIN";

        return new LoginUserDetails(users, role);
    }
}