package com.cos.photogramstart.domain.likes;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "likes_uk", columnNames = { "imageId", "userId" }) // 복합 unique key 설정 -> 1번 유저가 2번 이미지를 두번 좋아요 누를 수 없다.
																											
})
public class Likes { // 'Likes'인 이유 -> mySQL 과 MariaDB 는 like 가 키워드라서 테이블이 안 만들어짐

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// 어떤 이미지를 누가 좋아하는지 알면 돼.

	@JoinColumn(name="imageId")
	@ManyToOne
	private Image image;

	@JsonIgnoreProperties({"images"})  // User 의 images 는 가져오지 않는다.
	@JoinColumn(name="userId")
	@ManyToOne
	private User user;

	private LocalDateTime createDate;

	@PrePersist // db 에 insert 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

}
