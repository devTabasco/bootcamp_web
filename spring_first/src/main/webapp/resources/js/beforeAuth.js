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
			//responseText : 서버에서 넘어온 데이터(json Type 으로)
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
	//fetch(jobCode, ajax-spec)
		fetch(jobCode, {
  		method: methodType,
	  	/*
	  	form-data가 아닌 json과 같은 방식은 header에 content-type을 적어주어야 한다.
	  	urlEncoded방식을 사용한다면, form data를 URLSearchParams에 담아서 body에 넣어주어야 한다.
	    headers: {
	    	'Content-Type': 'application/x-www-form-urlencoded'
	    },
	    body: new URLSearchParams(formData)
	    */
	    	body:formData
	 	 })
	  //callback function처리
	  	.then((response) => response.json())
	  	/* fuction(response){response.json()} */
	  	.then((data) => window[callBackFunc](data));
}

function nextJoinStep(step){
	let formData = null;
	let group = [];
	let submitResult = false;
	switch(step){
		case 1:
			const groupName = document.getElementsByName('groupName')[0];
			if(lengthCheck(groupName)){
				formData = new FormData();
				formData.append(groupName.name, groupName.value);
				// append(name,[blob ,] [value | fileName]) set(name,[blob ,] [value | fileName]) --> overwrite
				console.log(groupName.value);
				console.log(formData);
				//serverCallByXHRAjax(formData, 'GroupDupChk', 'post', 'moveJoinStep2');
				serverCallByFetchAjax(formData, 'GroupDupChk', 'post', 'moveJoinStep2');   
			}else{
				messageController(true, ' 그룹명 입력 오류:사용할 그룹명을 문자로 시작하는 두 글자 이상으로 입력하세요');
			}		
		break;
		case 2:
			group = [];
			submitResult = false;
			group.push(document.getElementsByName('groupName')[1]);
			group.push(document.getElementsByName('groupCeo')[0]);
			group.push(document.getElementsByName('groupPin')[0]);
			
			formData = new FormData();
			
			for (let idx = 0; idx < group.length; idx++) {
				submitResult = lengthCheck(group[idx]);
				if (submitResult){
					formData.append(group[idx].name, group[idx].value);
				}else{
					messageController(true, ' 그룹정보 입력 오류:그룹정보를 정확히 입력해주세요.');
					break;
				} 
			}
			if(submitResult){
				console.log(group);
				console.log(formData);
				serverCallByFetchAjax(formData, 'MemberJoin', 'post', 'moveJoinStep3');
			} 	
		break;
		case 3:
			group = [];
			submitResult = false;
			group.push(document.getElementsByName('storeCode')[0]);
			group.push(document.getElementsByName('storeName')[0]);
			group.push(document.getElementsByName('storePhone')[0]);
			group.push(document.getElementsByName('storeZip')[0]);
			group.push(document.getElementsByName('storeAddr')[0]);
			group.push(document.getElementsByName('storeAddrDetail')[0]);
			group.push(createInputBox("hidden", "groupCode", document.getElementsByClassName('communicationBox tripple')[0].children[1].children[0].value, ""));
			group.push(createInputBox("hidden", "groupCeo", document.getElementsByName('groupCeo')[0].value, ""));
			group.push(createInputBox("hidden", "groupPin", document.getElementsByName('groupPin')[0].value, ""));
			if(lengthCheck(group[0])){
				formData = new FormData();
				for (let idx = 0; idx < group.length-3; idx++) {
					formData.append('storeInfoList[0].'+group[idx].name, group[idx].value);
				}
				formData.append(group[group.length-1].name,group[group.length-1].value);
				formData.append(group[group.length-2].name,group[group.length-2].value);
				formData.append(group[group.length-3].name,group[group.length-3].value);
				serverCallByFetchAjax(formData, 'RegStore', 'post', 'moveJoinStep4');   
			}else{
				messageController(true, ' 그룹명 입력 오류:사용할 그룹명을 문자로 시작하는 두 글자 이상으로 입력하세요');
			}
			
			console.log(group);
			console.log(formData);	
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
		//box.children[0].children[0].value = "";
		box.className = 'communicationBox tripple';
		document.getElementsByClassName("btn double on")[0].setAttribute("onClick","nextJoinStep(2)");
		document.getElementsByClassName("btn double on")[0].setAttribute("class","btn tripple on");
		document.getElementsByClassName("btn double off")[0].setAttribute("class","btn tripple off");
		document.getElementsByClassName("btn tripple etc")[0].style.display = "block";
	}else{
		messageController(true, jsonData.message);
		box.children[0].children[0].value = "";
	}
}

function moveJoinStep3(jsonData){
	console.log(jsonData.groupCode+"groupCode");
	const box = document.getElementsByClassName('communicationBox tripple')[0];
	if(jsonData.message == null){
		box.children[2].style.display = 'block';
		box.children[1].children[0].value = jsonData.groupCode;
		box.children[1].style.display = 'none';
		//box.children[1].children[0].value = "";
		//box.className = 'communicationBox tripple';
		document.getElementsByClassName("vButton right")[0].style.display = "block";
		document.getElementsByClassName("btn tripple on")[0].setAttribute("onClick","nextJoinStep(3)");
	}else{
		messageController(true, jsonData.message);
		box.children[0].children[0].value = "";
	}
}

function moveJoinStep4(jsonData){
	console.log(jsonData);
	formData = new FormData();
	serverCallByFetchAjax(formData, 'PageReturn', 'post', 'moveJoinStep5'); 
	//const box = document.getElementsByClassName('communicationBox tripple')[0];
	//if(jsonData.message == null){
		//box.children[2].style.display = 'block';
		//box.children[2].children[0].value = box.children[0].children[0].value;
		//box.children[1].style.display = 'none';
		//box.children[1].children[0].value = "";
		//box.className = 'communicationBox tripple';
		//document.getElementsByClassName("vButton right")[0].style.display = "block";
		//document.getElementsByClassName("btn tripple on")[0].setAttribute("onClick","nextJoinStep(4)");
	//}else{
		//messageController(true, jsonData.message);
		//box.children[0].children[0].value = "";
	//}
	
}

function moveJoinStep5(jsonData){
	console.log("성공!");
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