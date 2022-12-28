/**
 * Smart POS - Common Function
 */
 /* keyEvent 새로고침 */
document.onkeydown = function(event){
	const key = event.keyCode;
	if(key == 116 || (event.ctrlKey && key == 82) || (event.altKey && key == 37)){
		event.preventDefault();
	}
};

 /* PUBLIC IP 수집 */
let publicIp;
function getPublicIp(){
	/* ajax:: get >>  https://api.ipify.org/format=json */ 
	return "106.243.194.229";
}
 
 /* 서버로 전송할 데이터 길이의 유효성 판단 */
 function lengthCheck(obj){
	const dataLength =[["storeCode",10,10], ["empCode", 3, 3], ["empPin", 6, 6], ["groupName",2,20], ["groupCeo",2,5],["groupPin",6,6],["levName", 2, 20]];
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

/* DIV 생성 */
function createDIV(objId, className, funcName){
	const div = document.createElement("div");
	if(objId != '') div.setAttribute("id", objId);
	if(className != '') div.setAttribute("class", className);
	if(funcName != '') div.setAttribute("onClick", funcName);
	
	return div;
}

function createDiv(objId, className, funcName, innerText){
	const div = document.createElement("div");
	if(objId != '') div.setAttribute("id", objId);
	if(className != '') div.setAttribute("class", className);
	if(funcName != '') div.setAttribute("onClick", funcName);
	if(innerText != '') div.innerText = innerText;
	return div;
}
/* Input Box 생성*/
function createInputBox(type, name, value, placeholder){
	const input = document.createElement("input");
	input.setAttribute("type", type);
	input.setAttribute("name", name);
	if(value != "") input.setAttribute("value", value);
	if(placeholder != "") input.setAttribute("placeholder", placeholder);
	return input;
}
/* DatePicker 생성 */
function createDatePicker(name, minDate, maxDate){
	const date = document.createElement("input");
	date.setAttribute("type", "date");
	date.setAttribute("name", name);
	if(minDate != "") date.setAttribute("min", minDate);
	if(maxDate != "") date.setAttribute("max", maxDate);
	return date;	
} 

function createSelect(name, options, className){
	const select = document.createElement("select");
	for(idx=0; idx<options.length; idx++){
		const option = document.createElement("option");
		option.setAttribute("value", options[idx].levCode);
		option.innerText = options[idx].levName;
		select.appendChild(option);
	}
	select.setAttribute("class", className);
	return select;
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
function pageInit(message, accessInfo){
	publicIp = getPublicIp();
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
	if(accessInfo != ''){
		document.getElementById("accessInfo").innerText =	"로그아웃(Access Time : " + accessInfo.substr(8,2) + ":" + accessInfo.substr(10, 2) + ":" + accessInfo.substr(12, 2) + ")";
	}

	if(jsonString != ''){
		mgrInit();
	}
	
}

function disableMessage(){
	document.getElementById("messageTitle").innerText = "";
	document.getElementById("messageContent").innerText = "";
	document.getElementById("background").style.display = "none";
}

function accessOut(){
	const form = createForm("", "AccessOut", "post");
	document.body.appendChild(form);
	form.submit();
}