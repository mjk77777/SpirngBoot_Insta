package com.cos.photogramstart.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.collections.CollectionRepository;
import com.cos.photogramstart.domain.hashtag.Hashtag;
import com.cos.photogramstart.domain.hashtag.ImageHashtag;
import com.cos.photogramstart.domain.hashtag.ImageHashtagRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class ImageHashtagService {
	
	private final HashtagService hashtagService;
	private final ImageHashtagRepository imageHashtagRepository;
	private final CollectionRepository collectionRepository;
	private final ImageRepository imageRepository;

	public void 해시태그저장(List<String> tags , Image image) {
		
		if(tags.size()==0) return;  // 태그가 없다면 즉시 종료!(호출한 곳으로 돌아간다)
		
		tags.stream()  // tags List 를 스트림 생성한다.
				.map(hashtag -> // map 메서드를 사용하여
					hashtagService.findByTagName(hashtag)  // findByHashtag 는 Optional 객체를 반환하며
								.orElseGet( () -> hashtagService.save(hashtag)))    // 태그 없는 경우, Optional.empty 반환
				.forEach(hashtag -> mapHashtagToImage(image, hashtag));		// 태그를 찾은 경우, Optional 에 값 반환
		}
	
	public int mapHashtagToImage(Image image, Hashtag hashtag) {
		 return imageHashtagRepository.save(new ImageHashtag(image,hashtag)).getId();
	}
	
	// 해당 게시물의 모든 태그들을 조회
	public List<ImageHashtag> findHashtagListByImage(Image image){
		return imageHashtagRepository.findAllByImage(image);
	}
	
	// 해당 해시태그가 포함된 스토리 다 가져오기
	public Page<Image> 스토리가져오기(String hashtag, int principalId, Pageable pageable) {
		 Page<Image> images = imageRepository.findAllByHashtag(hashtag, pageable);
		
			images.forEach((image) -> { // login 한 사용자가 구독하고 있는 images 들 가져와
			
			image.setLikeCount(image.getLikes().size());
			
			image.getLikes().forEach((like) ->{ // 해당 이미지를 '좋아요' 하고 있는 애들 가져와
				if(like.getUser().getId() == principalId) {  // 해당 이미지를 좋아요하고 있는 애들의 아이디와 로그인한 아이디가 같다면
					// '내'가 좋아요 함
					image.setLikeState(true);
				}
			});
			
			image.setBookmarkState(collectionRepository.mSearchBookmark(principalId, image.getId()));
			
			List<ImageHashtag> hashtags = findHashtagListByImage(image);
			image.setImageHashtags(hashtags);
		});
		return images;
	}
	
	
		
		
	}

