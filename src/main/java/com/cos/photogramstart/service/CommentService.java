package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;

	@Transactional
	public Comment 댓글쓰기(String content, int imageId, int userId) {
		 //imageRepository.findById() -> 해서 그 오브젝트를 넣어야 되는데 귀찮아 => 그냥 쿼리 만들어
		// but nativeQuery 만들지 못할 때 
		
		//TIP! (객체를 만들어서 id값만 담아서 insert 하기) -> 어차피 db 에 들어가는 값은 FK 이기때문
		// 단, return 시에 image 객체와 user 객체는 id 값만 가지고 있는 빈 객체를 리턴 받는다.
		
		Image image = new Image();
		image.setId(imageId);

		// 댓글 정보 가져갈 때, user의 username 도 필요하기 때문에 db 에서 findById 해서 가져옴
		User userEntity = userRepository.findById(userId).orElseThrow(()->{
			throw new CustomApiException("해당 유저는 없습니다");
		});
		
		Comment comment = new Comment();
		
		comment.setContent(content);
		comment.setImage(image);
		comment.setUser(userEntity);
		
		return commentRepository.save(comment);  // Comment 객체 return
	}
	
	@Transactional
	public void 댓글삭제(int id) {
		try {
			commentRepository.deleteById(id);
		}catch(Exception e) {
			throw new CustomApiException(e.getMessage());
		}
	}
}
