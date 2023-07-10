package com.cos.photogramstart.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@SpringBootTest
public class HashtagTest {
	
	private final ImageService imageService;
	
	@Test
	void addHashtag() {
		// given
		List<String> list = Arrays.asList("#JAVA", "#C++");
		ImageUploadDto dto = new ImageUploadDto("캠핑1.png",  "캡션입니다.", list);
		PrincipalDetails principal =  () -> "미미미누";
		
		//when
		imageService.사진업로드(dto, principal);
		
		
	}
	

}
