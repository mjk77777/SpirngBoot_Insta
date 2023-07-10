/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
	(6) 컬렉션모달
 */
//(0) 현재 로그인한 사용자 아이디
let principalId = $('#principalId').val();
//alert(principalId);

// (1) 스토리 로드하기

let page = 0;

function getStoryItem(image) {

	let item = `<div class="story-list__item id="list-item">
	<div class="sl__item__header">
		<div>
			<img class="profile-image" src="/upload/${image.user.profileImageUrl}"
				onerror="this.src='/images/person.jpeg'" />
		</div>
		<div>${image.user.username}</div>
	</div>

	<div class="sl__item__img">
		<img src="/upload/${image.postImageUrl}" />
	</div>

	<div class="sl__item__contents">
		<div class="sl__item__contents__icon">

			<button>`;

	if (image.likeState) {
		item += `<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
	} else {
		item += `<i class="far fa-heart" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
	}


	item += `</button><button>`;
	if (image.bookmarkState == 1) {
		item += `<i class="fas fa-bookmark red" onclick="toggleCollection(this,${image.id})"></i>`;
	} else {
		item += `<i class="far fa-bookmark" onclick="toggleCollection(this, ${image.id})" ></i>`;
	}

	item += `</button></div>

		<span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount} </b>likes</span>

		<div class="sl__item__contents__content">
			<p>${image.caption}</p>`;
			
			
			if(image.imageHashtags){
				for(let  data of  image.imageHashtags){
					item += `<span onclick="javascript:hashtagStoryLoad('${data.hashtag.tagName}')">#${data.hashtag.tagName}, </span>`;
					if(image.imageHashtags[image.imageHashtags.length -1] == data){   // 마지막 해시태그는 쉼표(,) 뺀다.
						item += `<span onclick="javascript:hashtagStoryLoad('${data.hashtag.tagName}') ">#${data.hashtag.tagName} </span>`;
					}
				}
			};
			
			
		item += `</div>
		</form>

		<div id="storyCommentList-${image.id}">`;

	image.comments.forEach((comment) => {
		item += `<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
				<p>
					<b>${comment.user.username} :</b> ${comment.content}
				</p>`;

		if (principalId == comment.user.id) {
			item += `
				<button onclick="deleteComment(${comment.id})">
					<i class="fas fa-times"></i>
				</button>`;
		}
		item += `</div>`;
	});

	item += `	
		</div>

		<div class="sl__item__input">
			<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
			<button type="button" onClick="addComment(${image.id})">게시</button>
		</div>

	</div>
</div>`;

	return item;
}

function storyLoad() {
	$.ajax({
		url: `/api/image?page=${page}`,
		dataType: "json"
	}).done(res => {
		console.log(res);
		if (res.data.empty == true) {
			$('#storyList').append("<h1>구독정보가 없습니다. 친구들을 팔로잉하세요~</h1>");
		}
		res.data.content.forEach((image) => {
			let item = getStoryItem(image);
			$("#storyList").append(item);
		});

	}).fail(error => {
		console.log(error);
	});
}
storyLoad(); // 호출

//해시태그 스토리 로드
function hashtagStoryLoad(hashtag){
	console.log("=====88888888888 "+ hashtag);
	$.ajax({
		url: `/api/image/${hashtag}?page=${page}`,
		dataType: "json"
	}).done(res => {
		console.log(res);
		$('#storyList').val("");
		if (res.data.empty == true) {
			$('#storyList').append("<h1>해당 해시태그에 해당하는 게시물이 없습니다!</h1>");
		}
		res.data.content.forEach((image) => {
			$("#storyList").val("");
			let item = getStoryItem(image);
			$("#storyList").append(item);
		});

	}).fail(error => {
		console.log(error);
	});
	
}



// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {  // window 의 scroll 이벤트
	// 문서의 높이 - 윈도우 높이 = 스크롤 다 내렸을 때 scrollTop
	//console.log(" 윈도우 scrollTop ",$(window).scrollTop()); 
	//console.log("문서의 높이", $(document).height()); -> (고정)
	//console.log("윈도우 높이", $(window).height()); -> 창의 높이

	let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());
	if (checkNum < 1 && checkNum > -1) {
		// 페이지 로드하기
		page++;  // 1 페이지 증가 시켜서 로드
		storyLoad();
	}
});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`);

	if (likeIcon.hasClass("far")) { // '좋아요' 안 눌렸을 때 -> '좋아요' 하겠다

		$.ajax({
			type: "POST",
			url: `/api/image/${imageId}/likes`,
			dataType: "json"
		}).done(res => {
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) + 1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);

			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		}).fail(error => {
			console.log("좋아요 실패", error);
		});

	} else {  // 좋아요 취소하겠따.
		$.ajax({
			type: "DELETE",
			url: `/api/image/${imageId}/likes`,
			dataType: "json"
		}).done(res => {
			console.log("좋아요 취소 성공", res);

			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) - 1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);

			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		}).fail(error => {
			console.log("좋아요 취소 실패", error);
		});

	}
}

// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
		imageId: imageId,
		content: commentInput.val()
	} //js 오브젝트

	console.log(data);
	console.log(JSON.stringify(data));

	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	$.ajax({
		type: "POST",
		url: `/api/comment`,
		data: JSON.stringify(data),  // 통신하기 위해 json 으로 바꿔서 던질거임
		contentType: "application/json; charset=utf-8;",
		dataType: "json"
	}).done(res => {
		console.log("성공", res);

		let comment = res.data;
		let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
			    <p>
			      <b>${comment.user.username} :</b>
			      ${comment.content}
			    </p>
			    <button onclick= "deleteComment(${comment.id})">
			    	<i class="fas fa-times"></i>
			    </button>
			  </div>
			`;
		commentList.prepend(content);  // prepend : 앞쪽에 넣는다(최신댓글이 위로)

	}).fail(error => {
		console.log("오류", error);
		alert(JSON.stringify(error.responseJSON.data));
	});

	commentInput.val(""); // 인풋 필드가 깨끗하게 비워진다 (오류가 나도.)
}

// (5) 댓글 삭제
function deleteComment(commentId) {
	$.ajax({
		type: "delete",
		url: `/api/comment/${commentId}`,
		dataType: "json"
	}).done(res => {
		console.log("댓글삭제성공", res);
		// 삭제 성공하면 바로 view 단에서 안 보이게 날려
		$(`#storyCommentItem-${commentId}`).remove();
	}).fail(error => {
		console.log("댓글삭제실패", error);
	});

}

// 북마크 추가 & 취소
function toggleCollection(obj, imageId) {

	if ($(obj).hasClass("fas")) {  // 북마크된 상태 -> 취소하기
	
		$.ajax({
			type : "delete",
			url : `/collection/${imageId}/delete`,
			dataType : "json"
		}).done(res=>{
			console.log("북마크 취소 성공", res);
			$(obj).removeClass("fas");
			$(obj).removeClass("red");
			$(obj).addClass("far");
		}).fail(error=>{
			console.log("북마크 취소 실패", error);
		});

	} else if ($(obj).hasClass("far")) {  // 북마크 안된 상태 -> 북마크

		$.ajax({
			type: "post",
			url: `/collection/${imageId}/post`,
			dataType: "json"
		}).done(res => {
			console.log("북마크 추가 성공", res);
			$(obj).removeClass("far");
			$(obj).addClass("fas");
			$(obj).addClass("red");
		}).fail(error => {
			console.log("북마크 추가실패", error);
		});
	}


}














