package com.cos.photogramstart.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CollectionController {
	
	// 컬렉션 탭
	@GetMapping("/collection")
	public String collection() {
		return "collection/collection";
	}
	
	// 세부 컬렉션 창
	@GetMapping("/collection/collectionDetail")
	public String collectionDetail() {
		return "collection/collectionDetail";
	}
	
	
	
}
