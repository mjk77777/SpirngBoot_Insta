package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String postImageUrl; // 사진을 전송 받아서 그 사진을 서버의 특정 폴더에 저장 - DB 엔 그 경로를 INSERT
	
	private String caption; // 설명
	
	// 이미지 좋아요
	
	// 댓글
	
	@JoinColumn(name = "userId")  //오브젝트 자체를 db 에 저장 못하니 fk 키를 저장 (fk 키의 이름을 우리가 정해줌)
	@ManyToOne // 한 명의 유저는 여러 이미지 만들수있어
	private User user;
	
	private LocalDateTime createDate;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	//오브젝트를 콘솔에 출력할 때, 무한참조가 일어날수 있어서 -> User 부분은 출력되지 않게 함 .
//	@Override
//	public String toString() {
//		return "Image [id=" + id + ", postImageUrl=" + postImageUrl + ", caption=" + caption
//				+ ", createDate=" + createDate + "]";
//	}
	
	

}
