package com.cos.photogramstart.web.dto.image;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class ImageUploadDto {

	@NotBlank(message = "이미지 첨부는 필수입니다!")
	private MultipartFile file;
	
	@Size(max = 255, message = "캡션의 최대길이를 초과하였습니다!")
	private String caption;
	
	private List<String> hashtags;
	
	// ImageUploadDto -> Image 객체에 넣기
	public Image toEntity(String postImageUrl, User user) {
		return Image.builder()
				.postImageUrl(postImageUrl)
				.caption(caption)
				.user(user)
				.build();
	}

}
