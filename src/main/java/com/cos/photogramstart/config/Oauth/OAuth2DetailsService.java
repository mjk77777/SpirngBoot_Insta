package com.cos.photogramstart.config.Oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService{
	
	// facebook 에서 '회원정보 ' 바로 여기로 줌
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("oauth2 탐");
		//userRequest 안에 '회원정보' 담아서 줌
		OAuth2User oAuth2User = super.loadUser(userRequest); //userRequest 파싱해서 줌
		System.out.println(oAuth2User.getAttributes());
		return null;
	}

}
