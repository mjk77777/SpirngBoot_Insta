package com.cos.photogramstart.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.hashtag.Hashtag;
import com.cos.photogramstart.domain.hashtag.HashtagRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class HashtagService {
	
	private final HashtagRepository hashtagRepository;
	
	// 태그 Hashtag 테이블에 있는지 조회
	public Optional<Hashtag> findByTagName(String hashtag) {
		
		return hashtagRepository.findByTagName(hashtag);
	}
	
	// 없으면 Hashtag 테이블에 저장
	public Hashtag save(String hashtag) {
		Hashtag tag = Hashtag.builder()
				.tagName(hashtag)
				.build();
		return hashtagRepository.save(tag);
	}

}
