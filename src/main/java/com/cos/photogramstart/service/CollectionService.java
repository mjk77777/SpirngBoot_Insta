package com.cos.photogramstart.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.collections.CollectionRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CollectionService {

	private final ImageRepository imageRepository;
	private final UserRepository userRepository;
	private final CollectionRepository collectionRepository;

	@Value("${file.collection}")
	private String uploadFolder;


	@Transactional(readOnly = true)
	public List<Image> 컬렉션리스트(int principalId) {
		
		List<Object[]> objs = imageRepository.mList(principalId);
		List<Image> lists = new ArrayList<>();
		
		
		for(Object[] obj : objs) {
			System.out.println("========================0"+obj[4]);
			Image image = new  Image();
			
			image.setId(  (int) obj[0]  );
			image.setCaption((String)obj[1]);
			
			Timestamp timestamp =  (Timestamp) obj[2];
			LocalDateTime localDateTime = timestamp.toLocalDateTime();
			
			image.setCreateDate(localDateTime);
			
			image.setPostImageUrl((String)obj[3]);
			// id -> User 타입
			User user = userRepository.findById((Integer) obj[4]).orElseThrow(()->
				new CustomApiException("해당 사용자가 없습니다")
			);
			image.setUser(user);
			
			lists.add(image);
		}

		return lists;
	}
	
	@Transactional
	public void 북마크등록(int imageId, int principalId) {
		collectionRepository.mBookmark(imageId, principalId);
	}
	
	@Transactional
	public void 북마크취소(int imageId, int principalId) {
		collectionRepository.mCancelBookmark(imageId, principalId);
	}
	
	 
	 

//	@Transactional
//	public int 컬렉션등록(MultipartFile file, String collectionName, PrincipalDetails principalDetails) {
//
//		// collectionName 중복 검사 : 중복이면 true 반환
//		boolean ok = cateRepository.existsByCollectionName(collectionName);
//		if (ok) {
//			throw new CustomApiException("중복된 이름입니다. 다른 이름을 입력해주세요");
//		}
//
//		UUID uuid = UUID.randomUUID();
//		String imageFileName = uuid + "_" + file.getOriginalFilename();
//
//		System.out.println("imageFilename================" + imageFileName);
//		Path imageFilePath = Paths.get(uploadFolder, imageFileName);
//
//		try {
//			Files.write(imageFilePath, file.getBytes());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return cateRepository.mSave(collectionName, imageFileName, principalDetails.getUser().getId());
//	}

}
