package com.cos.photogramstart.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class ImageUploadDto {

	private MultipartFile file;
	private String caption;
	
	// ImageUploadDto -> Image 객체에 넣기
	public Image toEntity(String postImageUrl, User user) {
		return Image.builder()
				.postImageUrl(postImageUrl)
				.caption(caption)
				.user(user)
				.build();
	}
}
