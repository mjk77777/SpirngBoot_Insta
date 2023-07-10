package com.cos.photogramstart.domain.hashtag;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {

	Optional<Hashtag> findByTagName(String hashtag);
}
