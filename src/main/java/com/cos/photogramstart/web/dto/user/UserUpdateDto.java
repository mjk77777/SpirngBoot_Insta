package com.cos.photogramstart.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	
	//회원수정시 -> username, email 은 안 바꿈
	@NotBlank
	private String name;  // (필수)
	@NotBlank
	private String password;  // (필수)
	private String website;
	private String bio;
	private String phone;
	private String gender;
	
	// 조금 위험함. 코드 수정이 필요할 예정  ; name, password 말고는 꼭 받지 않아도 되니.
	public User toEntity() {
		return User.builder()
				.name(name) // 이름을 기재 안하면 문제!! Validation 체크
				.password(password)  // 패스워드를 기재 안했으면 null 이 들어가 문제!! Validation 체크
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
	
	

}
