/**
 * 인증 전 사용할 자바스크립트
 */
const jsonString = '';

/* HttpRequest를 이용한 서버 요청
		clientData format : [['name', 'value'], ...]
 */
function serverCallByRequest(jobCode, methodType, clientData){
	const form = createForm("", jobCode, methodType);
	if(clientData != ''){
		for(let idx=0;idx<clientData.length;idx++){
			form.appendChild(createInputBox('hidden', clentData[idx][0], clentData[idx][1], ''));
		}
	}
	document.body.appendChild(form);
	form.submit();
}

function serverCallByXHRAjax(formData, jobCode, methodType, callBackFunc){
	const ajax =  new XMLHttpRequest();
	
	ajax.onreadystatechange = function(){
		if(ajax.readyState == 4 && ajax.status == 200){
			window[callBackFunc](JSON.parse(ajax.responseText));
		}else{
			console.log(ajax);
		}
	};
	
	//connection을 여는 것
	ajax.open(methodType, jobCode);

	
	/* Post 방식일 때 */
	//URLEncoded 방식으로 넘길 시 RequestHeader를 수정
	//ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	
	//Json Type으로 데이터 넘길 시 변경
	//ajax.setRequestHeader("Content-Type", "application/json");
	
	//FormData는 변경 없음.(기본값)
	
	ajax.send(formData);	
}

function serverCallByFetchAjax(formData, jobCode, methodType, callBackFunc){
	fetch(jobCode, {
  	method: methodType,
  	/*
    headers: {
    	'Content-Type': 'application/x-www-form-urlencoded'
    },
    body: new URLSearchParams(formData)
    */
    body:formData
  })
  	.then((response) => response.json())
  	.then((data) => {
			window[callBackFunc](data);
  	});
  
}

function nextJoinStep(step){
	let formData = null;
	switch(step){
		case 1:
			const groupName = document.getElementsByName('groupName')[0];
			if(lengthCheck(groupName)){
				formData = new FormData();
				formData.append(groupName.name, groupName.value);
				// append(name,[blob ,] [value | fileName]) set(name,[blob ,] [value | fileName]) --> overwrite
				console.log(groupName.value);
				console.log(formData);
				serverCallByXHRAjax(formData, 'GroupDupChk', 'post', 'moveJoinStep2');
				//serverCallByFetchAjax(formData, 'GroupDupChk', 'post', 'moveJoinStep2');   
			}else{
				messageController(true, ' 그룹명 입력 오류:사용할 그룹명을 문자로 시작하는 두 글자 이상으로 입력하세요');
			}		
		break;
	}
	
}

function moveJoinStep2(jsonData){
	console.log(jsonData);
	const box = document.getElementsByClassName('communicationBox single')[0];
	if(jsonData.message == null){
		box.children[1].style.display = 'block';
		box.children[1].children[0].value = box.children[0].children[0].value;
		box.children[0].style.display = 'none';
		box.children[0].children[0].value = "";
		box.className = 'communicationBox tripple';	
	}else{
		messageController(true, jsonData.message);
		box.children[0].children[0].value = "";
	}
	
}

/* Authentication 이전 Get 방식의 페이지 요청 */
function movePage(target){
	const form = createForm("", target, "get");
	document.body.appendChild(form);
	form.submit();	
}	

function access(){
	const form = createForm("", "Access", "post");
	const inputZone = document.getElementById("inputZone");
	const communicationBox = document.getElementsByClassName("communicationBox")[0];
	const message = ["매장코드 형식을 확인하세요.", "직원코드 형식을 확인하세요.", "핀번호 형식을 확인하세요"];
	let isSubmit;
		
	for(let idx=0; idx<inputZone.children.length; idx++){
		isSubmit = lengthCheck(inputZone.children[idx])
		if(!isSubmit)	{
			inputZone.children[idx].value = "";
			inputZone.children[idx].setAttribute("placeholder", message[idx]);
			inputZone.children[idx].focus();
			break;
		}
	}
	
	const hidden = createInputBox("hidden", "accessPublicIp", publicIp, "");
	form.appendChild(inputZone);
	form.appendChild(hidden);
	communicationBox.appendChild(form);
	form.submit();
}

/* Password Validation */
function isCharCheck(text, type){
	let result;
	
	const largeChar = /[A-Z]/;
	const smallChar = /[a-z]/;
	const num = /[0-9]/;
	const specialChar = /[!@#$%^&*]/;
	
	let typeCount = 0;
	
	if(largeChar.test(text)) typeCount++;
	if(smallChar.test(text)) typeCount++;
	if(num.test(text)) typeCount++;
	if(specialChar.test(text)) typeCount++;
	
	if(type){
		result = typeCount >= 3? true:false;
	}else{
		result = typeCount == 0? true:false;
	}
	
	return result;
}