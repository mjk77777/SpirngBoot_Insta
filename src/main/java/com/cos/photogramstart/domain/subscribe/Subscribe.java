package com.cos.photogramstart.domain.subscribe;

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
@Table(
		uniqueConstraints = {
				@UniqueConstraint(
							name = "subscribe_uk",
							columnNames = {"fromUserId", "toUserId"}    // 실제 테이블의 컬럼명을 써야 돼 // ex. 1 번 유저가 2번 유저 구독하는데, 구독을 중복할 수 없잖아.
						)
		}
		)
public class Subscribe {  // 중간테이블
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JoinColumn(name = "fromUserId") // 카멜 표기법 // joinColumn(name = 이렇게 컬럼명 만들어!)
	@ManyToOne
	private User fromUser;  // 구독하는 유저
	
	@JoinColumn(name = "toUserId")
	@ManyToOne
	private User toUser;  // 구독받는 유저
	
	private LocalDateTime createDate;
	
	@PrePersist // db 에 insert 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
	

}
