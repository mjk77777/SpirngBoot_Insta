package com.cos.photogramstart.config.Oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService{  // 타입이 DefaultOauth2 타입이 되어야 함.
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private final UserRepository userRepository;
	
	// 구글 로그인이 완료된 후 후처리(loadUser)를 여기서 해줌.
	// 구글로부터 받은 userRequest 데이터에 대한 후처리 함수
	//userRequest 안에 '회원정보' 담아서 줌
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("getClientRegistration :"+ userRequest.getClientRegistration());
		System.out.println("getAccessToken :" +userRequest.getAccessToken());
		
		// 구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 -> code 를 리턴(OAuth-Client 라이브러리) -> AccessToken 요청
		// userRequest 정보 -> loadUser 함수 호출 -> 구글로부터 회원 프로필 받아줌.
		OAuth2User oAuth2User = super.loadUser(userRequest);
		System.out.println("getAttributes :" +oAuth2User.getAttributes());
		
		// 강제 회원가입 진행
		String provider = userRequest.getClientRegistration().getClientName(); //google
		String providerId = oAuth2User.getAttribute("sub"); 
		String username = provider + "_" + providerId;
		String name = oAuth2User.getAttribute("name");
		String password = bCryptPasswordEncoder.encode("겟인데어"); // oaut2로 로그인 하기에 큰 의미 없음
		String role = "ROLE_USER";
		String email = oAuth2User.getAttribute("email");
		
		// 이미 회원가입 되어있는지 확인
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {
			// 회원가입 안되어 있음
			userEntity = User.builder()
					.username(username)
					.password(password)
					.name(name)
					.email(email)
					.role(role)
					.provider(provider)
					.providerId(providerId)
					.build();
			userRepository.save(userEntity);   // 회원가입
		}
		
		return new PrincipalDetails(userEntity, oAuth2User.getAttributes()); // -> Authentication 객체에 들어감. 세션 저장
	}

}
