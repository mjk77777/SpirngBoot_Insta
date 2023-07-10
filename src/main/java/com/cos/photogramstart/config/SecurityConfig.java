package com.cos.photogramstart.config;

// 1. 코드받기(인증). 2.AccessToken(권한)
// 3. 사용자 프로필 정보 가져오고 
// 4-1. 그 정보를 토대로 회원가입을 자동으로 진행시키기도 하고
// 4-2. 쇼핑몰 -> 구글에서 제공하는 이외의 정보(집주소) 필요 -> 더 받기도 함
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.cos.photogramstart.config.Oauth.OAuth2DetailsService;
import com.cos.photogramstart.config.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity //해당 파일로 시큐리티를 활성화
@Configuration //IoC
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final OAuth2DetailsService oAuth2DetailsService;  // DI
	
	/* 로그인 실패 핸들러 의존성 주입 */
	private final AuthenticationFailureHandler customFailureHandler;

	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super.configure(http);  // 기존 시큐리티가 가지고 있는 기능이 다 비활성화됨.
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**")
			.authenticated() // 해당 주소들은 -> 인증 필요해
			.anyRequest()
			.permitAll()  // 그외의 요청은 모두 허용하겠다.
			.and()
			.formLogin()  // 인증이 필요한 요청이 오면 
			.loginPage("/auth/signin") //GET-> 로그인페이지로 
			.loginProcessingUrl("/auth/signin") // POST -> 스프링 시큐리티가 로그인 프로세스 진행
			.failureHandler(customFailureHandler)  // 로그인 실패 핸들러 
			.defaultSuccessUrl("/") //로그인 성공하면 /로 
			.and()
			.oauth2Login()  //formLogin() 도 하는데, oauth2 로그인도 할거야!
			.userInfoEndpoint() // oauth2 로그인을 하면 최종응답인 '회원정보'를 바로 받을 수 있다.(code나 accessToken 안 받을거야)
			.userService(oAuth2DetailsService)
			// 구글 로그인이 완료되면 -> 코드X, (AccessToken + 사용자 프로필 정보 O)
			;
	}

}
