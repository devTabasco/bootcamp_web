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
			form.appendChild(createInputBox('hidden', clientData[idx][0], clientData[idx][1], ''));
		}
	}
	document.body.appendChild(form);
	form.submit();
}
/* ajax.readyState 
	0  request not initialize << new XMLHttpRequest()
	1	 server Connection established  << ajax.open() ajax.send()
	2  request recieved <<  client --> data --> server
	3	 processing request << server request processing
	4	 response ready
	
	ajax.status << data flow status
	200 << 'OK'
	400 403 << 'Forbidden'
	    404 << 'PageNotFound'
*/
function serverCallByXHRAjax(formData, jobCode, methodType, callBackFunc){
	const ajax =  new XMLHttpRequest();
	console.log(formData);
	ajax.onreadystatechange = function(){
		if(ajax.readyState == 4 && ajax.status == 200){
			alert(ajax.responseText);
			window[callBackFunc](JSON.parse(ajax.responseText));
		}
	};
	
	ajax.open(methodType, jobCode);
	//ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	//ajax.setRequestHeader("Content-Type", "application/json");

	ajax.send(formData);	
}

function serverCallByFetchAjax(formData, jobCode, methodType, callBackFunc){
	// fetch(jobCode, ajax-spec)
	fetch(jobCode, {
  	method: methodType,
  	/*
    headers: {
    	'Content-Type': 'application/x-www-form-urlencoded'
    },
    body: new URLSearchParams(formData)
    */
    
    body:formData
  }).then(response => response.json())
  	.then(data => window[callBackFunc](data));
}

function nextJoinStep(step){
	let formData = null;
	switch(step){
		case 1:
			const groupName = document.getElementsByName('groupName')[0];
			if(lengthCheck(groupName)){
				formData = new FormData();
				formData.append(groupName.name, groupName.value);
				//append(name,[blob ,] [value | fileName]) set(name,[blob ,] [value | fileName]) --> overwrite
				//console.log(formData);
				//serverCallByXHRAjax(formData, 'GroupDupChk', 'post', 'moveJoinStep2');
				serverCallByFetchAjax(formData, 'GroupDupChk', 'post', 'moveJoinStep2');   
			}else{
				messageController(true, ' 그룹명 입력 오류:사용할 그룹명을 문자로 시작하는 두 글자 이상으로 입력하세요');
			}		
		break;
		case 2:
			let checkData = [];
			let group = [];
			let check = false;
			checkData.push(document.getElementsByName('groupName')[1]);
			checkData.push(document.getElementsByName('groupCeo')[0]);
			checkData.push(document.getElementsByName('groupPin')[0]);
			checkData.push(document.getElementsByName('storeCode')[0]);
			formData = new FormData();
			
			for(let idx=0;idx<checkData.length-1;idx++){
				if(lengthCheck(checkData[idx])){
					formData.append(checkData[idx].name, checkData[idx].value);	
					console.log(checkData[idx].name);				
					check = true;
				}
			}
			
			if(lengthCheck(checkData[checkData.length-1])){
					formData.append('storeInfoList[0].'+checkData[checkData.length-1].name, checkData[checkData.length-1].value);									
			}
			
			group.push(document.getElementsByName('storeName')[0]);
			group.push(document.getElementsByName('storePhone')[0]);
			group.push(document.getElementsByName('storeZip')[0]);
			group.push(document.getElementsByName('storeAddr')[0]);
			group.push(document.getElementsByName('storeAddrDetail')[0]);
			
			for(let idx=0;idx<group.length;idx++){
				formData.append('storeInfoList[0].'+group[idx].name, group[idx].value);					
			}
			
			if(check){
				serverCallByFetchAjax(formData, 'MemberJoin', 'post', 'moveJoinStep3');   
			}else{
				messageController(true, '입력 오류: 사용자 정보를 다시 입력해주세요.');
			}		
		break;
	}
	
}

function moveJoinStep2(jsonData){
	console.log(jsonData);
	const box = document.getElementsByClassName('communicationBox single')[0];
	if(jsonData.message == null){
		itemStyleChange(1);
	}else{
		messageController(true, jsonData.message);
		box.children[0].children[0].value = "";
	}
	
}

function moveJoinStep3(jsonData){
	console.log(jsonData);
	serverCallByRequest("Index", "get", "");
	console.log("성공!");
}

/* 같은 페이지내 CommunicationBox와 페이지 하단 Command Button의 속성을 
   동적으로 적용하기 위한 전역 변수화 */
let itemIdx=0;
function itemStyleChange(idx){
	console.log(itemIdx);
	const currentIdx = itemIdx + idx;
	const box = document.getElementsByClassName('communicationBox')[0];
	box.children[currentIdx].style.display = 'block';
	box.children[itemIdx].style.display = 'none';
	console.log(currentIdx);
	if(currentIdx===3){
		document.getElementsByClassName("btn tripple on")[1].setAttribute("onclick","nextJoinStep(2)");
		document.getElementsByClassName("btn tripple on")[1].innerText="가입신청";
	}
	if(currentIdx >= 1)	box.className = 'communicationBox tripple'
	else box.className = 'communicationBox single';
	
	if(currentIdx == 1 && currentIdx>itemIdx){
		if(currentIdx>itemIdx) {
			box.children[currentIdx].children[0].value = box.children[itemIdx].children[0].value;
			box.children[itemIdx].children[0].value = "";
		}
		
		const parent = document.getElementById("footer");
		parent.firstElementChild.className = "btn tripple on";
		parent.lastElementChild.className = "btn tripple on";
		parent.lastElementChild.setAttribute("onclick", "itemStyleChange(1)");
		
		const child = createDiv('', 'btn tripple off', 'itemStyleChange(-1)', 'Previous Step');
		parent.insertBefore(child, parent.lastElementChild);
	}
	else if(currentIdx == 0){
		const parent = document.getElementById("footer");
		parent.firstElementChild.className = "btn double off";
		parent.lastElementChild.className = "btn double on";
		
		parent.removeChild(parent.lastElementChild.previousElementSibling);
	}
	itemIdx += idx;
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