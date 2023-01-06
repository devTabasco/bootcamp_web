<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member Join :: Employee Step</title>
<script src="resources/js/common.js"></script>
<link rel="stylesheet" href="resources/css/common.css" />
<link rel="stylesheet" href="resources/css/main.css" />
</head>
<body onLoad="pageInit('${message}')">
	<div id="header">
		<div id="logo">훈</div>
		<div id="project">HoonZzang's Lecture Notes</div>
		<div id="currentPage">[ Join ]<span class="nav small">▶▶▶</span>[ Group Step ]<span class="nav small">▶▶▶</span>[ Store Step ]<span class="nav small">▶▶▶</span><span class="nav big">[ Employee Step ]</span></div>
	</div>
	<div id="content"></div>
	<div id="footer">
		<div class="btn tripple on" onClick="registration()">NEXT</div>
		<div class="btn tripple off" onClick="movePage('storeStep.jsp')">PREVIOUS</div>
		<div class="btn tripple etc" onClick="movePage('index.jsp')">MAIN</div>
	</div>
	<!-- 로그인 Box -->
	<div class="communicationBox tripple">
		<div id="inputZone">
			${levelInfo }
			<input type="text" name="empCode" placeholder="직원코드"  class="box big" />
			<input type="text" name="empName" placeholder="직원이름"  class="box big" />
			<input type="password" name="empPin" placeholder="핀번호"  class="box big" />
			<input type="hidden" name="storeCode" value="${storeCode}">
		</div>
	</div>
	<!-- MessageBox -->
	<div id="background" style="display: none">
		<div id="lightBox" >
			<div id="messageTitle">Message Title</div>
			<div id="messageZone">
				<div id="messageContent">Server Message</div>
			</div>
			<div id="messageAction">
				<div class="button solo" onClick="disableMessage()">확인</div>
			</div>
		</div>
	</div>

<script>
function registration() {
	const form = createForm("", "RegEmp", "post");
	const communicationBox = document.getElementsByClassName("communicationBox tripple")[0];
	
	let group = [];
	let submitResult = false;

	//group.push(document.getElementsByName("storeCode")[0]);
	//group.push(document.getElementsByName("empCode")[0]);
	//group.push(document.getElementsByName("levelInfo")[0]);
	//group.push(document.getElementsByName("empName")[0]);
	//group.push(document.getElementsByName("empPin")[0]);
	
	form.appendChild(communicationBox);
	document.body.appendChild(form);
	form.submit();
	
}
</script>
</body>
</html>