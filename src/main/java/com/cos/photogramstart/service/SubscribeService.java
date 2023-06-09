package com.cos.photogramstart.service;


import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em;  // 모든 Repository 는 EntityManager 를 구현해서 만들어진 구현체. 이번엔 Service 에서 직접 구현해볼거임.
	
	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalId, int pageUserId){
		// DB에서 Select 해서 SubscribeDto 형태로 담아오면 됨.
		
		// 쿼리준비
		StringBuffer sb = new StringBuffer(); // 쿼리를 만들기 위해 StringBuffer 사용
		// 끝에 꼭 한칸을 띄어주기 (아니면 오류 날 수 있음.) + 세미콜론(;) 첨부하면 안됨!
		sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
		sb.append("if((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState, ");
		sb.append("if((u.id = ?), 1, 0) equalUserState "); 
		sb.append("FROM user u INNER JOIN subscribe s ");
		sb.append("ON u.id = s.toUserId ");
		sb.append("WHERE s.fromUserId = ?"); 
		
		// 1번째 ? : principalId(로그인한 아이디)
		// 2번째 ? : principalId
		// 3번째 ? : pageUserId
		
		// 바인딩하기(쿼리완성) , java persistence Query 
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId);
		
		//JPA 쿼리 결과를  List<Object[]> 형태로 받아와서 각 레코드를 SubscribeDto 객체로 변환하여 리스트로 수집하는 과정
		List<Object[]> results = query.getResultList();
		List<SubscribeDto>subscribeDtos = results.stream()
													.map(o -> new SubscribeDto(o))
													.collect(Collectors.toList()); //스트림의 요소를 리스트로 수집
		
		System.out.println("쿼리 : ============" + query.getResultList().get(0));
		
		// 쿼리실행 (qlrm 라이브러리 필요 =DB 결과를  DTO에 매핑하기 위해서)
//		JpaResultMapper result = new JpaResultMapper();
//		List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);  // 한건만 리턴받을 거면 uniqueResult() 쓰면 돼. + SubscribeDto 로 리턴받겟다.
		return subscribeDtos; //구독 리스트 리턴
	}
	

	@Transactional
	public void 구독하기(int fromUserId, int toUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		}catch(Exception e) {
			throw new CustomApiException("이미 구독을 하였습니다");
		}
		
	}
	
	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
}
