package com.cos.photogramstart.domain.hashtag;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Hashtag extends BaseEntity {

	private String tagName;

	/*
	 * @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL) // FK 는
	 * ImageHashtag 의 hashtag 필드이다. private List<ImageHashtag> imageHashtags;
	 * //select 만
	 */	
	@Builder
	public Hashtag(String tagName) {
		this.tagName = tagName;
	}
}
