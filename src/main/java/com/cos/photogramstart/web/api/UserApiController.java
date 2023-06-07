package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {
	
	private final UserService userService;
	
	// 회원정보 수정
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(@Valid UserUpdateDto userUpdateDto, 	// 넘어온 데이터들 dto 에 담아
												BindingResult bindingResult,     // 꼭 @Valid 가 적혀있는 다음 파라미터에 적어야 됨.
												@PathVariable int id, 
												@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("==========================");
				System.out.println(error.getDefaultMessage());
				System.out.println("==========================");
			}
			// 유효성 검사 시 , 에러가 있으면 (bindingResult에) -> exception 날림
			throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
		}else {
			// 유효성 검사시, 에러가 없으면
			User userEntity = userService.회원수정(id, userUpdateDto.toEntity());  //userUpdateDto.toEntity() -> User 타입
			// 세션정보도 강제로 변경 
			principalDetails.setUser(userEntity);
			return new CMRespDto<>(1, "회원정보 수정완료", userEntity);  // 응답시에 userEntity의 모든 getter 함수가 호출되고, JSON으로 파싱하여 응답한다. => getImages 하면 또 getUser -> 무한참조
		}
		}
		

}
