/**
 * 
 */
 
 /* PUBLIC IP 수집 */
 let publicIp;
 function getPublicIp(){
	//106.243.194.229
	/* https://api.ipify.org?format=json */ //AJAX의 GET 활용
	return "106.243.194.229";
}
 
 //<input type="text" name="groupName" placeholder="GROUP NAME" />
 function lengthCheck(obj) {
	//서버로 전송할 데이터 길이의 유효성 판단
	const data = [["groupName",2,20] , ["groupCeo",2,5] , ["groupPin",6,6]];
	let result = false;
	for(let i=0;i<data.length;i++){
		if(obj.getAttribute("name") == data[i][0]){
			result = (obj.value.length >= data[i][1] && 
			obj.value.length <= data[i][2])?true:false;
			
			break;
		}
	}
	return result;
}

function accessLengthCheck(obj) {
	//서버로 전송할 데이터 길이의 유효성 판단
	const data = [["storeCode",10] , ["empCode",3] , ["empPin",6]];
	let result = false;
	for(let i=0;i<data.length;i++){
		if(obj.getAttribute("name") == data[i][0]){
			result = (obj.value.length == data[i][1])?true:false;
			if(!result){
				alert(data[i][0]+"가 "+data[i][1]+"자리가 맞는지 확인하세요.")
			}
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
	div.setAttribute("class", className);
	if(funcName != '') div.setAttribute("onClick", funcName);
	
	return div;
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
	const input = createInputBox("hidden", "target", targetPage, "");
	if(targetPage == "grStep1.jsp"){
		const form = createForm("", "MovePage", "post");
		form.appendChild(createInputBox("hidden", "groupName", document.getElementsByName("groupName")[0].value, ""));
	}else if(targetPage == "store-stpe1.jsp"){
		const form = createForm("", "MovePageStore", "post");
		form.appendChild(createInputBox("hidden", "storeCode", document.getElementsByName("storeCode")[0].value, ""));
	}else if(targetPage == "MemberJoin"){
		const form = createForm("", "MovePageStore", "post");
		form.appendChild(createInputBox("hidden", "storeCode", document.getElementsByName("storeCode")[0].value, ""));
	}
	
	form.appendChild(input);
	document.body.appendChild(form);
	
	form.submit();	
}