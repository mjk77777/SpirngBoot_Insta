package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {
	
	private final UserService userService;
	private final SubscribeService subscribeService;
	
	@PutMapping("/api/user/{principalId}/profileImageUrl")   // profile.jsp 에서 input type="file" 의 정확한 name을 적어줘야 받음
	public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId, MultipartFile profileImageFile,
			@AuthenticationPrincipal PrincipalDetails principalDetails){  
		User userEntity = userService.회원프로필사진변경(principalId, profileImageFile);
		principalDetails.setUser(userEntity); //세션 강제 변경
		return new ResponseEntity<>(new CMRespDto<>(1, "프로필사진 변경 성공", null), HttpStatus.OK);
	}
	
	@GetMapping("/api/user/{pageUserId}/subscribe")  // 내가 이동한 페이지의 주인이 구독하고 있는 모든 정보
	public ResponseEntity<?> subscribeList( @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int pageUserId){
			List<SubscribeDto> subscribeDto = subscribeService.구독리스트(principalDetails.getUser().getId(), pageUserId);
			
			
			return new ResponseEntity<>(new CMRespDto<>(1, "구독자 정보 리스트 불러오기 성공", subscribeDto), HttpStatus.OK);
	}
	
	// 회원정보 수정
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(@Valid UserUpdateDto userUpdateDto, 	// 넘어온 데이터들 dto 에 담아
												BindingResult bindingResult,     // 꼭 @Valid 가 적혀있는 다음 파라미터에 적어야 됨.
												@PathVariable int id, 
												@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
			// 유효성 검사시, 에러가 없으면
			User userEntity = userService.회원수정(id, userUpdateDto.toEntity());  //userUpdateDto.toEntity() -> User 타입
			// 세션정보도 강제로 변경 
			principalDetails.setUser(userEntity);
			return new CMRespDto<>(1, "회원정보 수정완료", userEntity);  // 응답시에 userEntity의 모든 getter 함수가 호출되고, JSON으로 파싱하여 응답한다. => getImages 하면 또 getUser -> 무한참조
		}
		

}
