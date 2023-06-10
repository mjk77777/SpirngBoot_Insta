package com.cos.photogramstart.domain.image;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer>{

	@Query(value = "SELECT * FROM image WHERE userId in (SELECT toUserId FROM subscribe WHERE fromUserId = :principalId) ORDER BY id DESC", nativeQuery = true)
	Page<Image> mStory(int principalId, Pageable pageable); // 스토리를 가져올 때, paging 이 자동으로 돼서 던져짐
	
	@Query(value = "SELECT i.* FROM image i INNER JOIN (SELECT imageId, COUNT(imageId) likeCount FROM likes GROUP BY imageId) l ON i.id = l.imageId ORDER BY likeCount desc ", nativeQuery = true)
	List<Image> mPopular();
}
