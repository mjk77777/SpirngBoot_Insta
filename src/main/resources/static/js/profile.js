/**
  1. 유저 프로파일 페이지
  (1) 유저 프로파일 페이지 구독하기, 구독취소
  (2) 구독자 정보 모달 보기
  (3) 구독자 정보 모달에서 구독하기, 구독취소
  (4) 유저 프로필 사진 변경
  (5) 사용자 정보 메뉴 열기 닫기
  (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달 
  (8) 구독자 정보 모달 닫기
 */

// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(obj, toUserId) {
	if ($(obj).text() === "구독취소") {  // click 이벤트가 발생할 때, 버튼의 내용이 '구독취소'라면
		// ajax 호출 
		$.ajax({
			type: "DELETE",
			url: "/api/subscribe/" + toUserId,
			dataType: "json" // 서버에서 받는 데이터 형식 -> javascript 개체로 변환
		}).done(res => {
			$(obj).text("구독하기");
			$(obj).toggleClass("blue");  /* .toggleClass() : 넣었다 뺏다*/
		}).fail(error => {
			console.log("구독취소 실패", error);
		});

	} else {
		$.ajax({
			type: "POST",
			url: "/api/subscribe/" + toUserId,
			dataType: "json"
		}).done(res => {
			$(obj).text("구독취소");
			$(obj).toggleClass("blue");
		}).fail(error => {
			console.log("구독하기 실패", error);
		});


	}
}

// (2) 구독자 정보  모달 보기
function subscribeInfoModalOpen(pageUserId) {
	$(".modal-subscribe").css("display", "flex"); // 화면에 보여주기 
	// ajax 통신으로 데이터 가져오기
	$.ajax({
		url: `/api/user/${pageUserId}/subscribe`,
		dataType: "json"
	}).done(res => {
		console.log(res.data);

		res.data.forEach((user) => {  //subscribeDto 돈다
			let item = getSubscribeModalItem(user);
			$("#subscribeModalList").append(item);
		});
	}).fail(error => {
		console.log("구독정보 불러오기 실패", error);
	});


}

function getSubscribeModalItem(user) {
	// 모달에 그림 그리는 함수

	let item = `<div class="subscribe__item" id="subscribeModalItem-${user.id}">
	<div class="subscribe__img">
		<img src="/upload/${user.profileImageUrl}" onerror="this.src='/images/person.jpeg'" />
	</div>
	<div class="subscribe__text">
		<h2>${user.username}</h2>
	</div>
	<div class="subscribe__btn">`;
	if (!user.equalUserState) { // 해당 pageUserId의 구독자가 로그인한 '나'라면 -> 구독 버튼 안 보이게
		if (user.subscribeState) { // 구독한 상태
			item += `<button class="cta blue" onclick="toggleSubscribe(this, ${user.id})">구독취소</button>`;
		} else { // 구독 안한 상태
			item += `<button class="cta" onclick="toggleSubscribe(this, ${user.id})">구독하기</button>`;
		}
	}
	item += `
	</div>
</div>`;

	return item;
}


// (3) 유저 프로파일 사진 변경 (완)
function profileImageUpload(pageUserId, principalId) {

	if (pageUserId != principalId) {
		alert("프로필 사진을 수정할 수 없는 유저입니다!");
		return; //아무런 동작 X
	}

	$("#userProfileImageInput").click();  // input type="file" 태그 강제 클릭 이벤트 발

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}

		//서버에 이미지를 전송
		let profileImageForm = $("#userProfileImageForm")[0];
		console.log(profileImageForm); /*form 태그 자체를 그대로 들고와*/

		// FormData 객체를 이용하면 form 태그의 필드와 값을 나타내는 일련의 key/value 쌍을 담을 수 있다.
		let formData = new FormData(profileImageForm); /*form 태그의 값들만 담김*/

		$.ajax({
			type: "PUT",
			url: `/api/user/${principalId}/profileImageUrl`,
			data: formData,
			contentType: false,  // (사진 전송할 때) 필수 : x-www-urlencoded 로 파싱되는 것을 방지
			processData: false, // 필수 : contentType을 false 로 줬을 때, QueryString 자동 설정됨 -> 해제
			enctype: "multipart/form-data",
			dataType: "json"
		}).done(res => {
			// 사진 전송 성공시 이미지 변경
			let reader = new FileReader();
			reader.onload = (e) => {
				$("#userProfileImage").attr("src", e.target.result);
			}
			reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.

		}).fail(error => {
			console.log("프로필 이미지 전송 실패",error)
		});



	});
}


// (4) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (5) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (6) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (7) 구독자 정보 모달 닫기
function modalClose() {
	$(".modal-subscribe").css("display", "none");
	location.reload();
}






