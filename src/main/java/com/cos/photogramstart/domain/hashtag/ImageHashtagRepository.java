package com.cos.photogramstart.domain.hashtag;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.photogramstart.domain.image.Image;

public interface ImageHashtagRepository extends JpaRepository<ImageHashtag, Integer>{

	List<ImageHashtag> findAllByImage(Image image);
	
	

}
