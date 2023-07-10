package com.cos.photogramstart.web.dto.collection;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.collections.Collections;
import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CollectionPostDto {

	@NotNull
	private String collectionName;
	
	private MultipartFile file;
	

}
