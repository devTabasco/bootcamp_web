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
		.then(jsonData => window[callBackFunc](jsonData))
}

//let formData = null;
let formData = null;
function nextJoinStep(step){
	let groupName = null;
	let userData = null;
	
	switch(step){
		case 0:
			formData = new FormData();
			formData.append('groupName', group.groupName);
			serverCallByFetchAjax(formData, 'DelTempGroupName', 'post', 'moveJoinStep');
			break;
		case 1:
			groupName = document.getElementsByName('groupName')[0];
			if(lengthCheck(groupName)){
				formData = new FormData();
				formData.append(groupName.name, groupName.value);
				//append(name,[blob ,] [value | fileName]) set(name,[blob ,] [value | fileName]) --> overwrite
				//console.log(formData);
				//serverCallByXHRAjax(formData, 'GroupDupChk', 'post', 'moveJoinStep');
				serverCallByFetchAjax(formData, 'GroupDupChk', 'post', 'moveJoinStep');   
			}else{
				messageController(true, ' 그룹명 입력 오류:사용할 그룹명을 문자로 시작하는 두 글자 이상으로 입력하세요');
			}		
			break;
		case 2:
			userData = document.getElementsByClassName('inputZone')[step-1];
			
			if(lengthCheck(userData.children[1])){
				//group.groupCeo = userData.children[1].value;
				formData.append("groupCeo",userData.children[1].value);
				if(lengthCheck(userData.children[2])){
					if(isPasswordCheck(userData.children[2].value)){
						//group.groupPin = userData.children[2].value; 
						formData.append("groupPin",userData.children[2].value);
						itemStyleChange(1);
					}else{
						userData.children[2].value = '';
						messageController(true, '패스워드 입력 오류:패스워드는 영문대문자, 영문소문자, 숫자, 특수기호 중 세가지 이상의 형태로 6자리로 입력하셔야 합니다.');
					}
				}else{
					userData.children[2].value = '';
					messageController(true, '패스워드 입력 오류:패스워드는 6개의 문자+숫자 조합으로 작성하여야 합니다.');
				}	
			}else{
				userData.children[1].value = '';
				messageController(true, '대표자명 입력 오류:대표자명을 2~5글자로 입력하셔야 합니다.');
			}
			console.log(formData);
			//console.log(group);
			break;
		case 3:
			userData = document.getElementsByClassName('inputZone')[step-1];
			if(lengthCheck(userData.children[0])){
				//group.storeInfoList[0].storeCode = userData.children[0].value;
				formData.append("storeInfoList[0].storeCode",userData.children[0].value);
				if(lengthCheck(userData.children[1])){
					//group.storeInfoList[0].storeName = userData.children[1].value;
					formData.append("storeInfoList[0].storeName", userData.children[1].value);
					if(lengthCheck(userData.children[2])){
						//group.storeInfoList[0].storePhone = userData.children[2].value;
						formData.append("storeInfoList[0].storePhone", userData.children[2].value);					
						itemStyleChange(1);		
					}else{ 
						userData.children[2].value = '';
						messageController(true, '대표 연락처 입력 오류:대표 연락처는 10~11자리의 숫자로 입력되어야 합니다.');
					}
				}else{
					userData.children[1].value = '';
						messageController(true, '상점명 입력 오류:상점명은 첫글자가 문자이면서 2~50자리로 입력되어야 합니다.');
				}
			}else{
				userData.children[0].value = '';
				messageController(true, '사업자등록번호 입력 오류:사업자등록번호는 10자리의 숫자로 입력되어야 합니다.');
			}
			console.log(group);
			break;
		case 4:
			userData = document.getElementsByClassName('inputZone')[step-1];
			if(userData.children[0].value == ''){
				kakaoPostCodeAPI(userData);
			}else{
				//group.storeInfoList[0].storeZip = userData.children[0].value;
				formData.append("storeInfoList[0].storeZip", userData.children[0].value);					
				//group.storeInfoList[0].storeAddr = userData.children[1].value;
				formData.append("storeInfoList[0].storeAddr", userData.children[1].value);					
				if(userData.children[2] != ''){
					//group.storeInfoList[0].storeAddrDetail = userData.children[2].value;
					formData.append("storeInfoList[0].storeAddrDetail", userData.children[2].value);					
					registration();
				}else{
					messageController(true, '상세주소 입력 오류:상세주소를 입력하셔야 합니다.');
				}
			}
			
			break;
	}
	
}

let group;
function moveJoinStep(jsonData){
	group = jsonData;
	console.log(group);
	const box = document.getElementsByClassName('communicationBox')[0];
	if(group.message == null){
		itemStyleChange(1);
	}else{
		messageController(true, jsonData.message);
		box.children[0].children[0].value = "";
		itemStyleChange(-1);
	}
	
}

/* 같은 페이지내 CommunicationBox와 페이지 하단 Command Button의 속성을 
   동적으로 적용하기 위한 전역 변수화 */
let itemIdx=0;
function itemStyleChange(idx){
	console.log(itemIdx);
	const currentIdx = itemIdx + idx;
	const box = document.getElementsByClassName('communicationBox')[0];
	const parent = document.getElementById("footer");
	
	box.children[currentIdx].style.display = 'block';
	box.children[itemIdx].style.display = 'none';
	console.log(currentIdx);
	if(currentIdx >= 1)	box.className = 'communicationBox tripple'
	else box.className = 'communicationBox single';
	
	if(currentIdx == 0){
		parent.firstElementChild.className = "btn double off";
		parent.lastElementChild.className = "btn double on";
		parent.lastElementChild.setAttribute('onclick', 'nextJoinStep(1)');
		
		parent.removeChild(parent.lastElementChild.previousElementSibling);
	}else if(currentIdx == 1){
		if(currentIdx>itemIdx) {
			box.children[currentIdx].children[0].value = box.children[itemIdx].children[0].value;
			box.children[itemIdx].children[0].value = '';
			const child = createDiv('', 'btn tripple off', 'nextJoinStep(0)', 'Previous Step');
			parent.insertBefore(child, parent.lastElementChild);
			
		}else {
			parent.lastElementChild.previousElementSibling.setAttribute('onclick', 'nextJoinStep(0)');
		}
			
		parent.firstElementChild.className = 'btn tripple on';
		parent.lastElementChild.className = 'btn tripple on';
		parent.lastElementChild.setAttribute('onclick', 'nextJoinStep(2)');		
	}else if(currentIdx == 2){
		parent.lastElementChild.previousElementSibling.setAttribute('onclick', 'itemStyleChange(-1)');
		parent.lastElementChild.setAttribute('onclick', 'nextJoinStep(3)');			
	}else if(currentIdx == 3){
		parent.lastElementChild.setAttribute('onclick', 'nextJoinStep(4)');
	}
	
	itemIdx += idx;
}

function registration(){
	alert("회원등록");
	serverCallByFetchAjax(formData, 'MemberJoin', 'post', 'PageReturn');
}

function PageReturn(jsonData){
	console.log(jsonData);
	serverCallByRequest("Index", "get", "");
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
