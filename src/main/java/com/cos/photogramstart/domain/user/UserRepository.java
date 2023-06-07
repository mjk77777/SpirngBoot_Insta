package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

// 어노테이션이 없어도 JPARepository를 상속하면 ->  IoC 등록이 자동으로 된다. 
public interface UserRepository extends JpaRepository<User, Integer> {  // object , pk 키 type
 
	// JPA query methods
	User findByUsername(String username);  // username으로 찾아서 User 오브젝트 리턴
	
	
	
}