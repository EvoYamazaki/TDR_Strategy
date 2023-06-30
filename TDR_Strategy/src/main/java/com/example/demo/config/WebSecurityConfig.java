package com.example.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.example.domain.UsersExample;

@Configuration
@MapperScan("com.example.mybatis.mapper")
@EnableWebSecurity
public class WebSecurityConfig {
	
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setUseReferer(false); // リクエスト元のページに戻る場合はtrueに設定
        handler.setDefaultTargetUrl("/home"); // ログイン後にリダイレクトするURL
        return handler;
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers(
						"/"
						, "/home"
						, "/about"
						,"/user/add"
						,"/user/create"
						,"/user/userpage/{id}"
						,"/user/bookmarks/{id}"
						,"/schedule"
						,"/schedule/search"
						,"/results"
						,"/schedule/results"
						,"/schedule/{id}"
						,"/images/TDR_Strategy.png").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.successHandler(successHandler()) // 追加
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());

		return http.build();
	}

//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user =
//			 User.withDefaultPasswordEncoder()
//				.username("user")
//				.password("pass")
//				.roles("USER")
//				.build();
//
//		return new InMemoryUserDetailsManager(user);
//	}
	
	//追加
    //パスワードのアルゴリズムをBCryptに設定
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public UsersExample usersExample() {
        return new UsersExample();
    }
}
