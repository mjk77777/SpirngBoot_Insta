package com.cos.photogramstart.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor  // final 필드에 대한 DI 
@Service // 1. IoC  2. 트랜잭션 관리
public class AuthService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional  // Insert, Update, Delete 할때 붙여주기
	public User 회원가입(User user) {
		
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);  // hash 로 암호화
		user.setPassword(encPassword);
		
		user.setRole("ROLE_USER");  // 관리자 : ROLE_ADMIN
		
		//회원가입 진행 (DB 에 들어간 오브젝트 리턴)
		User userEntity = userRepository.save(user);
		return userEntity;
	}
}
