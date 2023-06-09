package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubscribeDto {

	// 모달에서 확인할 유저의 ID
	private int id;
	// 모달에서 확인할 유저의 이름
	private String username;
	// 모달에서 확인할 유저의 프로필이미지
	private String profileImageUrl;
	// 로그인한 유저가 모달에서 확인할 유저를 구독했는지 여부
	private Integer subscribeState;                                  // MariaDB 는 int로 하면 int로써, true, false 값을 못 받음 
	// 로그인한 유저가 모달에서 확인한 유저가 본인 스스로인지에 대한 여부
	private Integer equalUserState;
	
	public SubscribeDto(Object[] object) {
		this.id = (int)object[0];
		this.username = (String)object[1];
		this.profileImageUrl = (String)object[2];
		this.subscribeState = Integer.valueOf(String.valueOf(object[3]));
		this.equalUserState = Integer.valueOf(String.valueOf(object[4]));
	}
	
}
