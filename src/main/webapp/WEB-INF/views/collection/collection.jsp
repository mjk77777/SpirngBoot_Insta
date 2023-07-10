<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<section class="collection">
	<div class="collectionContainer">
		
		</div>
	</div>
</section>
<!--게시물컨섹션-->
<section id="tab-content">
	<!--게시물컨컨테이너-->
	<div class="profileContainer">
		<!--그냥 감싸는 div (지우면이미지커짐)-->
		<div id="tab-1-content" class="tab-content-item show">
			<!--게시물컨 그리드배열-->
			<div class="tab-1-content-inner" id="collection-list">

				<!--아이템들-->
				

				<!--아이템들end-->


			</div>
		</div>
	</div>
</section>

<!-- 컬렉션 생성 모달 -->
<div class="modal" id="collect">

	<div class="modal-content">
		<div class="modal-header">
			<h5 class="modal-title">컬렉션 이름을 지정하세요!</h5>
			<button onclick="modalClose()">
				<i class="fas fa-times"></i>
			</button>
		</div>
		<div class="modal-body">
				<input type="text" id="collectionName" name="collectionName" style="width: 260px;"> <br> <br> <span>커버 이미지를 꾸며보세요!</span> 
			<form id="collectionPostForm" >
				<input type="file" id="coverImageUrl"
					name="coverImageUrl" >
			</form>
		</div>
		<div class="modal-footer" style="height: 38px;">
			<button type="button" class="badge text-bg-primary" onclick="postCollection();">생성</button>
		</div>
	</div>
</div>




<script src="/js/collection.js"></script>

<%@ include file="../layout/footer.jsp"%>