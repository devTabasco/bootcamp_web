<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member Join :: STEP 1</title>
<script src="resources/js/common.js"></script>
<link rel="stylesheet" href="resources/css/common.css" />
<link rel="stylesheet" href="resources/css/main.css" />
</head>
<body onLoad="pageInit('${param.message}')">

	<div id="header">
		<div id="logo">훈</div>
		<div id="project">HoonZzang's Lecture Notes</div>
		<div id="currentPage">[ Join ]<span class="nav small">▶▶▶</span><span class="nav big">[ Group Step 1 ]</span></div>
	</div>
	<div id="content"></div>
	<div id="footer">
		<div class="btn double on" onClick="serverCall()">NEXT</div>
		<div class="btn double off" onClick="movePage('index.jsp')">PREVIOUS</div>
	</div>
	<!-- 로그인 Box -->
	<div id="communicationBox" class="single">
		<div id="inputZone">
			<input type="text" name="groupName" placeholder="GROUP NAME" class="box big" />
		</div>
	</div>
	<!-- MessageBox -->
	<div id="lightBox" style="display:none">
		<div id="messageTitle">Message Title</div>
		<div id="messageZone">
			<div id="messageContent">Server Message</div>
		</div>
		<div id="messageAction">
			<div class="button solo" onClick="disableMessage()">확인</div>
		</div>
	</div>
	
	<!-- JavaScript -->
<script>
function serverCall(){
	const form = createForm("", "GroupDupCheck", "get");
	const communicationBox = document.getElementById("communicationBox");
	let group = [];
	let submitResult = false;
	
	group.push(document.getElementsByName("groupName")[0]);
	
	for(let idx=0; idx<group.length; idx++){
		submitResult = lengthCheck(group[idx]);
		if(!submitResult) break;
	}
	
	if(submitResult){
		form.appendChild(communicationBox);
		document.body.appendChild(form);
		form.submit();
	}
}
</script>
</body>
</html>