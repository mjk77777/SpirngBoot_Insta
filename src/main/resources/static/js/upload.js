// (1) 스토리 이미지 업로드를 위한 사진 선택 로직
function imageChoose(obj) {
	let f = obj.files[0];

	if (!f.type.match("image.*")) {
		alert("이미지를 등록해야 합니다.");
		return;
	}

	let reader = new FileReader();
	reader.onload = (e) => {
		$("#imageUploadPreview").attr("src", e.target.result);
	}
	reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
}

// 해시태그

const hashtagInput = document.getElementById("hashtags");
const hashtagContainer = document.getElementById("hashtag-container");
const hiddenHashtagsInput = document.getElementById("hashtag-hidden");

let hashtags = [];

// hashtag-container 에 값 추가하기
function addHashtag(tag){
 	tag = tag.replace(/[\[\]]/g, '').trim();  // 대괄호[] 와 일치하는 것이 있으면 "" 로 대체하고 앞뒤 공백을 제거
 	
 	if(tag && !hashtags.includes(tag)){
		// 중복된 건 배제
		const span =  document.createElement("span");
		span.innerText = "#" + tag + " ";
		span.classList.add("hashtag");
		
		const removeButton = document.createElement("button");
		removeButton.innerText = 'x';
		removeButton.classList.add("removeButton");
		removeButton.addEventListener("click", () => {
			hashtagContainer.removeChild(span);
			hashtags = hashtags.filter( (hashtag) => hashtag != tag  );
			hiddenHashtagsInput.value =  hashtags.join(",");
		});
		
		span.appendChild(removeButton);
		hashtagContainer.appendChild(span);
		hashtags.push(tag);
		hiddenHashtagsInput.value =   hashtags.join(",");
	}	
	
}

hashtagInput.addEventListener("keydown", (event) => {
	if(event.key === 'Enter'){
		event.preventDefault();  // enter 쳐도 넘어가지 않음
		const tag = hashtagInput.value.trim();
		if(tag){
			addHashtag(tag);
			hashtagInput.value = "";
		}
	}
});