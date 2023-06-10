package com.cos.photogramstart.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;

@Component // 메모리에 띄우기 (RestController , Service 와 같은 어노테이션들은 Component 를 상속해서 만들어짐)
@Aspect
public class ValidationAdvice {  // advice : 공통기능 , Around : 함수 전후로 감시
	
	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")  // 해당 패키지 안의 Controller 로 끝나는 + 모든 함수
	public Object apiAdivce(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {  //ProceedingJoinPoint proceedingJoinPoint : 함수의 모든 정보에 접근할 수 잇는
		
		System.out.println("web api 컨트롤러 ======================");
		
		Object[] args = proceedingJoinPoint.getArgs();
		for(Object arg : args) {
			if(arg instanceof BindingResult) {        // object instanceof ClassName
				System.out.println("유효성 검사를 하는 함수입니다.");
				BindingResult bindingResult = (BindingResult) arg; // BindingResult 로 다운캐스팅
				
				if(bindingResult.hasErrors()) {
					// DTO 유효성 검사했을 때 -> 에러 있으면
					Map<String, String> errorMap = new HashMap<>();
					
					for(FieldError error :  bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());  // errorMap EX) content : "공백일수없습니다"
					}
					throw new CustomValidationApiException("유효성 검사 실패함", errorMap);  // throw 를 날리는 순간 밑에 코드는 다 무효화 됨.
				}
				
			}
		}
		//proceedingJoinPoint => profile 함수의 모든 곳에 접근할 수 있는 변수 (함수의 매개변수까지)
		// profile 함수보다 먼저 실행
		
		return proceedingJoinPoint.proceed();  // profile 함수가 실행됨.
	}
	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
	public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
		System.out.println("web 컨트롤러 =========================");
		
		Object[] args = proceedingJoinPoint.getArgs();
		for(Object arg : args) {
			System.out.println(arg);
			if(arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;
				//valdation 체크해서 -> 문제가 생기면 BindingReusult 클래스의 getFieldErrors 란 list 에 다 모아줌
				if(bindingResult.hasErrors()) {  //ex. max(20) 넘어갔다든지, blank 가 생겼다든지
					Map<String, String> errorMap = new HashMap<>();
					
					for(FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
						System.out.println(error.getDefaultMessage());  // '20이하여야 합니다'
					}
					throw new CustomValidationException("유효성 검사 실패함", errorMap);   //Exception 을 강제로 발동시킬 거임 -> Exception 가로채기 (ControllerExceptionHandler)
				}
			}
		}
		
		return proceedingJoinPoint.proceed();
	}

}
