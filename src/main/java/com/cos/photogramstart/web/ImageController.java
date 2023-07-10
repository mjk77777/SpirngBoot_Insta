package com.cos.photogramstart.web;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageHashtagService;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {
	
	private final ImageService imageService;
	private final ImageHashtagService imageHashtagService;

	@GetMapping("/image/story")
	public String story() {
		return "image/story";
	}
	
	// API 구현하는 이유 - (브라우저가 요청하는 게 아니라, 안드로이드/ios 가 요청)
	@GetMapping({"/", "/image/popular"})
	public String popular(Model model) {
		// api 는 데이터를 return하는 서버 !! -> ajax 로 통신할 거 아니니까 그냥 Controller
		List<Image> images = imageService.인기게시물();
		
		model.addAttribute("images", images);
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
	
	
	// 해시태그에 해당하는 스토리 불러오기
//	@GetMapping("/api/image/{hashtag}")
//	public String hashtagStory(@AuthenticationPrincipal PrincipalDetails principalDetails,
//			@PageableDefault(size = 3) Pageable pageable,   //org.springframework.data.domain.Pageable
//			 String hashtag, Model model){ 
//		System.out.println("===============g해시태그="+hashtag);
//		
//		
//			 Page<Image> images = imageHashtagService.스토리가져오기(hashtag ,principalDetails.getUser().getId(), pageable);
//			 model.addAttribute("images", images);
//		return "image/story";
//	}
	
}
