package com.cos.photogramstart.domain.image;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.collections.Collections;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.hashtag.ImageHashtag;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String postImageUrl; // 사진을 전송 받아서 그 사진을 서버의 특정 폴더에 저장 - DB 엔 그 경로를 INSERT

	private String caption; // 설명

	// 이미지 좋아요 (양방향 매핑)
	// OneToMany -> LAZY 전략 , getLikes() 호출하면, Image 가져올 때 likes 도 가져와
	@JsonIgnoreProperties({ "image" }) // 무한참조 방지
	@OneToMany(mappedBy = "image") // Likes 의 image 가 연관관계의 주인이다.(fk)
	private List<Likes> likes;

	@Transient // DB 에 컬럼이 만들어지지 않는다. -> JUST JSP 에서 좋아요 버튼 활성화할지만 관여하기 때문에
	private boolean likeState;

	@Transient
	private int likeCount;
	
	@Transient
	private int bookmarkState;
	
	@JsonIgnoreProperties({ "image" })
	@OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL)   // FK 는 ImageHashtag 의 hashtag 필드이다.
	private List<ImageHashtag> imageHashtags;  //select 만
	

	// 댓글 (양방향 매핑)
	@OrderBy("id DESC") // Image 에서 Comment 가져올 때 정렬해서 가져와
	@JsonIgnoreProperties({ "image" }) // 무한참조 방지
	@OneToMany(mappedBy = "image") // 연관관계의 주인(fk)은 Comment의 image 이다.
	private List<Comment> comments;


	@JsonIgnoreProperties({ "images" }) // User 클래스의 'images' 필드는 무시하고 딴 놈들만 가져와
	@JoinColumn(name = "userId") // 오브젝트 자체를 db 에 저장 못하니 fk 키를 저장 (fk 키의 이름을 우리가 정해줌)
	@ManyToOne // 한 명의 유저는 여러 이미지 만들수있어
	private User user;

	private LocalDateTime createDate;

	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	// 오브젝트를 콘솔에 출력할 때, 무한참조가 일어날수 있어서 -> User 부분은 출력되지 않게 함 .
//	@Override
//	public String toString() {
//		return "Image [id=" + id + ", postImageUrl=" + postImageUrl + ", caption=" + caption
//				+ ", createDate=" + createDate + "]";
//	}

}
