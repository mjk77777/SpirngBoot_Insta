package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.likes.LikesRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikesService {
	private final LikesRepository likesRepository;
	
	@Transactional
	public void 좋아요(int imageId, int principalId) {
// 추천 안해 -> LikesRepository 에서 바로 pk 키 넣어주기
//		Likes like = new Likes();
//		like.setUser(null);  -> user 객체 가져와서 넣어야 돼
		likesRepository.mLikes(imageId, principalId);
	}

	@Transactional
	public void 좋아요취소(int imageId, int principalId) {
		likesRepository.mUnLikes(imageId, principalId);
	}
}
