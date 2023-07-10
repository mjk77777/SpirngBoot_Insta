
function getCollectionItem(image) {
	let item =
	
	 `<div class="img-box" id="collection-${image.id}">
				 <img src="/upload/${image.postImageUrl}" onerror="this.src='/images/noimage.png' " />
					
				</div>`;
				
	 return item;			
}

//컬렉션 전체 가져오기(조회)
function collectionLoad() {
	$.ajax({
		url: "/collection/list",
		dataType: "json"
	}).done(res => {
		console.log("컬렉션 가져오기 성공", res);
		res.data.forEach((image) => {
			let item = getCollectionItem(image);
			$('#collection-list').append(item);
		});
	}).fail(error => {
		console.log("컬렉션 가져오기 실패", error);
	});
}
collectionLoad();


// 컬렉션 등록
/*function postCollection() {

	let collectionPostForm = $('#collectionPostForm')[0];
	//console.log(collectionPostForm);
	
	let formData = new FormData(collectionPostForm);
	formData.append("collectionName", $('#collectionName').val());
	
	$.ajax({
		type: "post",
		url: "/collection/post",
		data: formData,
		contentType : false,
		processData : false,
		enctype: "multipart/form-data",
		dataType: "json"
	}).done(res => {
		console.log("컬렉션 등록 성공", res);
		$('#collect').css("display", "none");
		location.reload();
	}).fail(error => {
		console.log("컬렉션 등록 실패", error);
		alert(JSON.stringify(error.responseJSON.message));
	});
}*/
