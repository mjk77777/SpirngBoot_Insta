package com.cos.photogramstart.handler;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				
				String errorMsg;
				if(exception instanceof BadCredentialsException) {
					errorMsg = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요!";
				}else if(exception instanceof UsernameNotFoundException) {
					errorMsg = "계정이 존재하지 않습니다. 회원가입 진행 후 로그인해 주세요!";
				} else if (exception instanceof InternalAuthenticationServiceException) {
					errorMsg = "내부적으로 발생한 시스템 문제로 요청을 처리할 수 없습니다. 관리자에게 문의하세요";
				}  else {
					errorMsg = "알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요";
				}
				
				// 에러메세지 한글 인코딩 처리
				errorMsg = URLEncoder.encode(errorMsg, "UTF-8");
				
				setDefaultFailureUrl("/auth/signin?error=true&exception="+errorMsg);
				
				super.onAuthenticationFailure(request, response, exception);
			}
}
