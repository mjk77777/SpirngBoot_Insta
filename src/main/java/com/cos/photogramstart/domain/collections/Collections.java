package com.cos.photogramstart.domain.collections;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Collections  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; //pk
	

	@JsonIgnoreProperties({"user"})
	@OneToOne
	@JoinColumn(name = "imageId")
	private Image imageId;    // 북마크한 게시물

	@JsonIgnoreProperties({"collections" , "images"}) 
	@JoinColumn(name = "userId")
	@ManyToOne
	private User user;    // 해당 게시물 북마크한 유저
	
	
	private LocalDateTime createDate;

	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

//	@JsonIgnoreProperties("images")
//	@JoinColumn(name = "userId") // FK
//	@ManyToOne
//	private User user;
}
