/**
 * Smart POS - Common Function
 */
 
 /* 서버로 전송할 데이터 길이의 유효성 판단 */
 function lengthCheck(obj){
	const dataLength =[["groupName",2,20],["groupCeo",2,5],["groupPin",6,6]];
	let result = null;
	for(let recordIdx=0; recordIdx<dataLength.length; recordIdx++){
		if(obj.getAttribute("name") == dataLength[recordIdx][0]){
			result = (obj.value.length >= dataLength[recordIdx][1] && 
			          obj.value.length <= dataLength[recordIdx][2])?true:false;
			break;
		} 	
	}
	
	return result;
}

/* FORM 생성 */
function createForm(name, action, method){
	const form = document.createElement("form");
	if(name != "") form.setAttribute("name", name);
	form.setAttribute("action", action);
	form.setAttribute("method", method);
	return form;
}

/* Input Box 생성*/
function createInputBox(type, name, value, placeholder){
	const input = document.createElement("input");
	input.setAttribute("type", type);
	input.setAttribute("name", name);
	if(value != "") input.setAttribute("value", value);
	if(placeholder != "") input.setAttribute("placeholde", placeholder);
	return input;
}
 
/* 페이지 이동 */
function movePage(targetPage){
	const form = createForm("", "MovePage", "get");
	
	const target = createInputBox("hidden", "target", targetPage, "");
	if(targetPage == "grStep1.jsp"){
		form.appendChild(createInputBox("hidden", "groupName", document.getElementsByName("groupName")[0].value, ""));	
	}else if(targetPage == "storeStep.jsp"){
		form.appendChild(createInputBox("hidden", "groupName", document.getElementsByName("storeCode")[0].value, ""));
	}
	
	form.appendChild(target);
	document.body.appendChild(form);
	
	form.submit();	
}

/* Page Initialize */
function pageInit(message){
	let serverMessage;
	if(message != ''){
		serverMessage = message.split(":"); 
		let content = document.getElementById("messageContent");
		document.getElementById("messageTitle").innerText = serverMessage[0];
		content.innerText = serverMessage[1];
		document.getElementById("background").style.display = "block";
		
		if(serverMessage[2] == "1") content.style.lineHeight = "calc(37.5vh*0.54)";
		if(serverMessage[2] == "2") content.style.lineHeight = "calc(37.5vh*0.54/2)";
		if(serverMessage[2] == "3") content.style.lineHeight = "calc(37.5vh*0.54/3)";
		if(serverMessage[2] == "4") content.style.lineHeight = "calc(37.5vh*0.54/4)";
		 
	}
}

function disableMessage(){
	document.getElementById("messageTitle").innerText = "";
	document.getElementById("messageContent").innerText = "";
	document.getElementById("background").style.display = "none";
}