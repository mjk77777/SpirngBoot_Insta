package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JPA - Java Persistance API ( 자바로 데이터를 영구적으로 저장(DB)할 수 있는 API 를 제공)

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity  // DB에 테이블 생성
public class User {
	
	@Id     //PK키
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //번호 증가 전략 -> DB 를 따라간다.  ex. mysql) auto-increment , oracle)sequence 전략
	private int id;    //사용자 많아지면 Long 타입으로 
	
	@Column(length = 20, unique = true)  //중복되면 안돼
	private String username;
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String name;
	private String website; //웹 사이트
	private String bio;  //자기소개
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl;
	private String role; //권한
	
	// mappedBy (연관관계의 주인 - Image 테이블의 user 컬럼)
	// 나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼 만들지마!!
	// User를 Select 할 때, 해당 User id로 등록된 image 들을 다 가져와.
	// LAZY = User 를 select 할 때, 해당 User id로 등록된 image들 가져오지마. + getImages() 함수가 호출될때 가져옴
	// EAGER = User 를 Select 할때, 해당 User id로 등록된 image 들을 전부 Join 해서 가져와
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER) 
	@JsonIgnoreProperties("user") // json으로 파싱할 때, Image 클래스의 user 는 무시하고 파싱해줘 (user의 getter 호출되면 무한참조 일어나)
	private List<Image> images;  //양방향 매핑
	
	private LocalDateTime createDate; //자동으로 들어감
	
	@PrePersist  // DB에 Insert 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", website="
				+ website + ", bio=" + bio + ", email=" + email + ", phone=" + phone + ", gender=" + gender
				+ ", profileImageUrl=" + profileImageUrl + ", role=" + role +" , createDate="
				+ createDate + "]";
	}
	
	

}
