package com.cos.photogramstart.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice // 모든 Exception 을 낚아채
public class ControllerExceptionHandler {
	
	// js 응답 (클라이언트에게)
	@ExceptionHandler(CustomValidationException.class)  // 모든 CustomValidationException 을 이 함수가 가로 챔 
	public String validationException(CustomValidationException e) {   
		// CMRespDto, Script 비교
		// 1. 클라이언트에게 응답할 때는 Script 가 좋음
		// 2. Ajax 통신 - CMRespDto (개발자가 응답 받음)
		// 3. android 통신 - CMRespDto (개발자가 응답 받음)
		if(e.getErrorMap() == null) {
			return Script.back(e.getMessage());
		}else {
			return Script.back(e.getErrorMap().toString());
		}
	}
	
	@ExceptionHandler(CustomException.class)  
	public String exception(CustomException e) {   
		return Script.back(e.getMessage());
	}
	
	// ajax 통신 - CMRespDto 로 응답 (data 응답) + HTTP 상태 코드 같이 던져줘야 done, fail 구분함
	@ExceptionHandler(CustomValidationApiException.class)  // 모든 CustomValidationApiException 을 이 함수가 가로 챔
	public ResponseEntity<CMRespDto<?>> validationApiException(CustomValidationApiException e) {   // BAD_REQUEST(400번)
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST );  // (body, 상태코드)
	}
	
	@ExceptionHandler(CustomApiException.class)  // 모든 CustomApiException 을 이 함수가 가로 챔
	public ResponseEntity<CMRespDto<?>> apiException(CustomApiException e) {  
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(),null), HttpStatus.BAD_REQUEST ); 
	}

}
