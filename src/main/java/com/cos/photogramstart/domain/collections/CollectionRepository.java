package com.cos.photogramstart.domain.collections;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CollectionRepository extends JpaRepository<Collections, Integer> {

	@Query(value = "SELECT COUNT(*) FROM collections WHERE userId = :principalId AND imageId = :imageId", nativeQuery = true)
	int mSearchBookmark(int principalId, int imageId);
	
	
    // 북마크 등록
	@Modifying
	@Query(value = "INSERT INTO collections(imageId, userId, createDate) VALUES(:imageId, :principalId, now())", nativeQuery = true)
	void mBookmark(int imageId, int principalId);
	
	// 북마크 취소
	@Modifying
	@Query(value = "DELETE FROM collections WHERE imageId = :imageId AND userId = :principalId", nativeQuery = true)
	void mCancelBookmark(int imageId, int principalId);

	/*
	 * collectionName 중복 검사 중복 : true // 중복 x : false 데이터가 DB에 존재하는지 확인할 때
	 * 'existsBy' 키워드 사용
	 */
	//boolean existsByCollectionName(String collectionName);

}
