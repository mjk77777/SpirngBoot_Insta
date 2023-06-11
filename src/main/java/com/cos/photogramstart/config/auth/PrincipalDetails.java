package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
	// OAuth2User 로나 UserDetails(일반 로그인) 로 로그인하나
	// Authentication 객체에는 PrincipalDetails 타입으로 저장할 거임.
	// PrincipalDetails 저장할 때, attributes 정보도 같이 저장할 거임 (OAuth2User 로 로그인할 때)
	
	private User user;
	private Map<String, Object> attributes;
	
	// 일반 로그인 시 생성자
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	// OAuth2 로그인 시, 생성자 => PrincipalDetails 가 user 뿐만 아니라 attributes 정보도 가짐.
	// attributes 를 토대로 user 정보 만들거야.
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}

	//권한 : 한개가 아닐 수 있음
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collector = new ArrayList<>();  //arrayList 의 부모 Collection
		collector.add(() -> { return user.getRole();});  // 람다식 (매개변수로 함수를 넘기고 싶으면 인터페이스나 오브젝트 넘겨야 돼)
//		collector.add(new GrantedAuthority() {
//			
//			@Override
//			public String getAuthority() {
//				return user.getRole();
//			}
//		});
		return collector;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	//////////// OAuth2User////////////////

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return null;
	}
	

}
