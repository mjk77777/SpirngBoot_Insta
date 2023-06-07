// (1) 회원정보 수정
function update(userId, event) {
	
	event.preventDefault();  // form 태그 action 을 막기 -> update()함수 잘 작동됨.
	
	let data = $("#profileUpdate").serialize(); // form 태그의 모든 input 값들이 (serialize)하면 담겨 (key=value) 
	
	console.log(data);
	
	$.ajax({
		type : "put",
		url : `/api/user/${userId}`,
		data : data,
		contentType: "application/x-www-form-urlencoded; charset=utf-8;",  //key=value
		dataType:"json" //json타입으로 응답 받음
		 //json -> javascript object로 파싱해서 res 가 받음
	}).done(res=>{  // HttpStatus 상태코드 200번대
		console.log("update 성공", res);
		location.href=`/user/${userId}`;
	}).fail(error=>{  // HttpStatus 상태코드 200번대가 아닐 때,
		if(error.responseJSON.data == null){
			//errorMap 을 보내지 않았을 때. 
			alert(JSON.stringify(error.responseJSON.message));
		}else{
			alert(JSON.stringify(error.responseJSON.data));  // JS object -> JSON 으로 변경
		}
	});
}