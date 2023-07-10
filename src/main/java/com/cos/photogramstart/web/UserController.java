package com.cos.photogramstart.web;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	

	@GetMapping("/user/{pageUserId}")
	public String profile(@PathVariable int pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		UserProfileDto dto = userService.회원프로필(pageUserId, principalDetails.getUser().getId());
		model.addAttribute("dto", dto);
		return "user/profile";
	}
	
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		// 1. 추천 (@AuthenticationPrincipal PrincipalDetails 어노테이션 사용)
		//System.out.println("세션 정보  : " + principalDetails.getUser());
		
		// 2. 극혐 
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//PrincipalDetails mprincipalDetails = (PrincipalDetails)auth.getPrincipal();
		//System.out.println("직접 찾은 세션 정보 : " + mprincipalDetails.getUser());
		
		return "user/update";
	}
}
