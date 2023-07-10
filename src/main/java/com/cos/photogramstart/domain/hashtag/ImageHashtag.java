package com.cos.photogramstart.domain.hashtag;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cos.photogramstart.domain.image.Image;

import antlr.collections.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ImageHashtag extends BaseEntity {

		@JoinColumn(name = "imageId")
		@ManyToOne
		private Image image;  // 게시글 번호
		
		@JoinColumn(name = "tagId")
		@ManyToOne
		private Hashtag hashtag; // 태그아이디 리스트
}
