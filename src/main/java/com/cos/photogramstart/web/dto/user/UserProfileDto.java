package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
	
	private boolean pageOwnerState;
	private int imageCount;  // view 페이지에서 연산하는 건 좋은 방법 아니야.
	private User user;
	
	private boolean subscribeState; //구독한 상태인지
	private int subscribeCount; //구독자수

}
