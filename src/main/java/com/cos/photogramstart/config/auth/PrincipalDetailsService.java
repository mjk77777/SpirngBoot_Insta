package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService { // IoC 에선 UserDetailsService 는 PrincipalDetailsService로 갈아끼워짐 (부모 타입 같아서)

	private final UserRepository userRepository;  //DI
	
	// 1. password 는 알아서 비교해주니까 신경 쓸 필요 없다.
	// 2. 리턴이 잘되면 자동으로 UserDetails 타입을 세션으로 만들어줌
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  // 로그인 요청시 body에 담아온 username 과 password
		
		// Username 이 DB 에 있는지
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {
			return null;
			
		}else {
			return new PrincipalDetails(userEntity);  // 세션에 저장
		}
		
	}

}
