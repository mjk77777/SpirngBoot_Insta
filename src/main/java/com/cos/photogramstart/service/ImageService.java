package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import com.sun.mail.handlers.image_gif;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	
	@Value("${file.path}")  // application.yml 에 적은 설정값 가져올 수 있음
	private String uploadFolder;
	
	@Transactional(readOnly = true) // 영속성 컨텍스트 변경 감지를 해서 , 더티체킹 , flush(반영) X -> 성능 좋아짐
	public Page<Image> 이미지스토리(int principalId, Pageable pageable){
		Page<Image> images = imageRepository.mStory(principalId, pageable); 
		
		// images 에 '좋아요' 상태 담기
		images.forEach((image) -> { // login 한 사용자가 구독하고 있는 images 들 가져와
			
			image.setLikeCount(image.getLikes().size());
			
			image.getLikes().forEach((like) ->{ // 해당 이미지를 '좋아요' 하고 있는 애들 가져와
				if(like.getUser().getId() == principalId) {  // 해당 이미지를 좋아요하고 있는 애들의 아이디와 로그인한 아이디가 같다면
					// '내'가 좋아요 함
					image.setLikeState(true);
				}
			});
		});
		
		return images;
	}
	
	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID();  //UUID - 유일성 보장하는 식별자
		String imageFileName = uuid + "_"+ imageUploadDto.getFile().getOriginalFilename(); // 1.jpg
		System.out.println("이미지 파일 이름 " + imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);  // 실제 경로 저장 (application.yml 설정파일에 설정함)
		
		// 통신, I/O -> 예외 발생할 수 있어.
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes()); // upload 폴더에 업로드
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		// image 테이블에 저장 (db)
		// imageUploadDto -> Image 객체에 넣어야 돼. (dto 에서 만들기)
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		Image imageEntity = imageRepository.save(image);
		
		//System.out.println(imageEntity.toString());  
	}
}
