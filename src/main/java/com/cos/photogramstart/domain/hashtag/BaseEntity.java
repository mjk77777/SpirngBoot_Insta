package com.cos.photogramstart.domain.hashtag;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@Getter
@SuperBuilder // 상속된 필더들까지 builder 패턴으로 만들수 있음
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 하위클래스에서만 생성자에 접근할 수 있다. (접근제한자 : protected)
@EntityListeners(AuditingEntityListener.class) //AuditingEntityListener 클래스는 엔티티의 생성 및 수정시간을 자동으로 추적함
@MappedSuperclass // 슈퍼클래스의 속성들이 하위 클래스에 상속되도록 지정

// abstract class : 다른 클래스들에게 공통적인 특성을 제공함
public abstract class BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column  // 엔티티 클래스의 필드와 db 컬럼을 매핑할 때 사용
	private int id;
	
	@CreatedDate   // 엔티티가 처음 생성될 때 자동으로 설정
	private LocalDateTime createdAt;
	@LastModifiedDate // 엔티티 마지막 수정일 자동으로 갱신
	private LocalDateTime updatedAt;
	
}
