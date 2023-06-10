package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.comment.CommentDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
	
	private final CommentService commentService;
	
	@PostMapping("/api/comment")           // 기본디폴트가 x-www-urlencoded 기 때문에 -> @RequestBody 붙여줘야 json 데이터 받을 수 있음
	public ResponseEntity<?> commentSave(@Valid @RequestBody CommentDto commentDto,
			BindingResult bindingResult,
			@AuthenticationPrincipal PrincipalDetails principalDetails){
		
		Comment comment = commentService.댓글쓰기(commentDto.getContent(),commentDto.getImageId(), principalDetails.getUser().getId()); // content, imageId, userId
		
		return new ResponseEntity<>(new CMRespDto<>(1, "댓글 등록 성공", comment), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/api/comment/{id}")
	public ResponseEntity<?> commentDelete(@PathVariable int id){
		
		commentService.댓글삭제(id);
		
		return new ResponseEntity<>(new CMRespDto<>(1, "댓글 삭제 성공", null), HttpStatus.OK);
	}
}