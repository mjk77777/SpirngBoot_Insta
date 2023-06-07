package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {
	
	private final ImageService imageService;

	@GetMapping({"/", "/image/story"})
	public String story() {
		return "image/story";
	}
	
	@GetMapping({"/image/popular"})
	public String popular() {
		return "image/popular";
	}
	
	@GetMapping({"/image/upload"})
	public String upload() {
		return "image/upload";
	}
	
	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
	
		if(imageUploadDto.getFile().isEmpty()) { // 사진 첨부되지 않았으면 Exception 발동시키고, db 엔 insert 되지 않게.
			throw new CustomValidationException("이미지가 첨부되지 않았습니다!!", null); // 페이지를 리턴해주니 apiException 아님
		}
		
		// 서비스 호출 (실제로직은 서비스에서)
		imageService.사진업로드(imageUploadDto, principalDetails);
		return "redirect:/user/" + principalDetails.getUser().getId(); // 주소 재분배 (/image 요청, /user/{id} 응답)
	}
}
