package com.cos.photogramstart.web.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.service.CollectionService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CollectionApiController {

	private final CollectionService collectionService;

	// 북마크 리스트 조회
	@GetMapping("/collection/list")
	public ResponseEntity<?> collectionList(@AuthenticationPrincipal PrincipalDetails principalDetails){
		List<Image> lists = collectionService.컬렉션리스트(principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "컬렉션 가져오기 성공", lists), HttpStatus.OK);
	}
	
	// 북마크 등록
	@PostMapping("/collection/{imageId}/post")
	public ResponseEntity<?> collectionAdd(@AuthenticationPrincipal PrincipalDetails principalDetails,
			@PathVariable(value = "imageId") int imageId){
		collectionService.북마크등록(imageId, principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "북마크 등록 성공", null), HttpStatus.OK);
	}
	
	// 북마크 취소
		@DeleteMapping("/collection/{imageId}/delete")
		public ResponseEntity<?> collectionDelete(@AuthenticationPrincipal PrincipalDetails principalDetails,
				@PathVariable(value = "imageId") int imageId){
			collectionService.북마크취소(imageId, principalDetails.getUser().getId());
			return new ResponseEntity<>(new CMRespDto<>(1, "북마크 취소 성공", null), HttpStatus.OK);
		}
	

	 //컬렉션 등록
//	@PostMapping("/collection/post")
//	public ResponseEntity<?> postCollection(@RequestParam(name = "coverImageUrl", required = false) MultipartFile file,
//			@RequestParam(name = "collectionName") String collectionName,
//			@AuthenticationPrincipal PrincipalDetails principalDetails)  {
//		
//		 collectionService.컬렉션등록(file, collectionName , principalDetails);
//
//		return new ResponseEntity<>(new CMRespDto<>(1, "컬렉션 등록", null), HttpStatus.CREATED);
//	}
	
	/*
	 * // 컬렉션 취소 (수정)
	 * 
	 * @PutMapping("/api/collection/{collectionId}/{imageId}") public
	 * ResponseEntity<?> cancelCollection(@PathVariable int
	 * collectionId, @PathVariable int imageId){
	 * 
	 * collectionService.컬렉션취소(collectionId, imageId);
	 * 
	 * return new ResponseEntity<>(new CMRespDto<>(1, "컬렉션에서 삭제", null),
	 * HttpStatus.OK); }
	 */
}
