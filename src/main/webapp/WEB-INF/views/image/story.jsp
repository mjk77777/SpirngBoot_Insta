<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<main class="main">
	<section class="container">
		<!--전체 리스트 시작-->
		<article class="story-list" id="storyList">

			<!--전체 리스트 아이템-->
					<!-- 스토리들 append 됨. story.js 의 storyLoad() 함수 -->

		</article>
	</section>
</main>

<!-- 컬렉션(북마크) 모달 -->
<div class="modal-subscribe">
	<div class="subscribe">
		<div class="subscribe-header">
			<span>북마크(컬렉션)에 추가하기</span>
			<button onclick="modalClose()">
				<i class="fas fa-times"></i>
			</button>
		</div>
		<div class="subscribe-list" id="collectionModal">
		
		</div>
	</div>
</div>


<script src="/js/story.js"></script>
</body>
</html>
