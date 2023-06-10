package com.cos.photogramstart.web.dto.comment;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.Data;

// NotNull = Null 값 체크
// NotEmpty = 빈값이거나 null 체크
// NotBlank = 빈값이거나 null이거나 빈공백(스페이스)까지 체크

@Data
public class CommentDto {
	
	@NotBlank
	private String content;
	@NotNull
	private int imageId;
	
	// toEntity 가 필요없음

}
