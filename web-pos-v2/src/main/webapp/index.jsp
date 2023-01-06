<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>:: SMART POS ::</title>
<script src="resources/js/common.js"></script>
<link rel="stylesheet" href="resources/css/common.css" />
<link rel="stylesheet" href="resources/css/main.css" />
</head>
<body onLoad="pageInit('${param.message}', '')">
	<div id="header">
		<div id="logo">훈</div>
		<div id="project">HoonZzang's Lecture Notes</div>
		<div id="currentPage"><span>[ Main ]</span></div>
	</div>
	<div id="content"></div>
	<div id="footer">
		<div class="btn double on" onClick="access()">매장입장</div>
		<div class="btn double off" onClick="serverCall()">회원가입</div>
	</div>
	<!-- 로그인 Box -->
	<div class="communicationBox tripple">
		<div id="inputZone">
			<input type="text" name="storeCode" placeholder="매장코드" class="box big" />
			<input type="text" name="empCode" placeholder="직원코드"  class="box big" />
			<input type="password" name="empPin" placeholder="핀번호"  class="box big" />
		</div>
	</div>
	<!-- MessageBox -->
	<div id="background" style="display: none">
		<div id="lightBox">
			<div id="messageTitle">Message Title</div>
			<div id="messageZone">
				<div id="messageContent">Server Message</div>
			</div>
			<div id="messageAction">
				<div class="button solo" onClick="disableMessage()">확인</div>
			</div>
		</div>
	</div>
	<!-- JavaScript -->
<script>
const jsonString = '';
function serverCall(){
	const form = createForm("", "Join", "get");
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
</script>
</body>
</html>




