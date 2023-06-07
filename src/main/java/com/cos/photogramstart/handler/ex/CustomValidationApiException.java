package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationApiException extends RuntimeException{   //Exception 이 되려면 RuntimeException 상속하면 돼

	// 객체 구분할 때 쓰는 것 (우리한테 중요한 건 x, JVM)
	private static final long serialVersionUID = 1L; 
	
	private String message;
	private Map<String, String> errorMap;
	
	public CustomValidationApiException(String message) {
		super(message);  // 부모한테 던지면 message 출력해줌
	}
	
	public CustomValidationApiException(String message, Map<String, String> errorMap) {
		super(message);  // 부모한테 던지면 message 출력해줌
		this.errorMap = errorMap;
	}
	
	
	public Map<String, String> getErrorMap(){
		return errorMap;
	}

}
