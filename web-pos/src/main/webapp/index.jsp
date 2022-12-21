<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Web-Pos Main</title>
<script src="resources/js/common.js"></script>
<link rel="stylesheet" href="resources/css/main.css">
<link rel="stylesheet" href="resources/css/common.css" />
</head>
<body>
	<div class="nav">
		<h1>CodeHunt</h1>
		<p>정현우 :: 임창용 :: 박건호 :: 정영준</p>
	</div>
	<div class="main">
		<div class="contents">
			<h1 class="logo">CodeHunt</h1>
			<div class="container" id="joinDataLayer">
				<input type="text" placeholder="stcode" id="id" name="storeCode" class="account">
				<input type="text" placeholder="secode" name="empCode" id="id" class="account">
				<input type="password" placeholder="pin" name="empPin" id="password"
					class="account">
				<div class="selectLine">
					<button id="login" class="account" onClick="registration()">로그인</button>
					<a href="group-step1.jsp"><button id="login" class="account">회원가입</button></a>
				</div>
				${param.message }
			</div>
		</div>
	</div>
	<div class="footer"></div>
</body>
<script type="text/javascript">  
	function registration() {
		const form = createForm("", "Access", "post");
		const joinDataLayer = document.getElementById("joinDataLayer");
		let group = [];
		let submitResult = false;

		group.push(document.getElementsByName("storeCode")[0]);
		group.push(document.getElementsByName("empCode")[0]);
		group.push(document.getElementsByName("empPin")[0]);

		for (let idx = 0; idx < group.length; idx++) {
			submitResult = accessLengthCheck(group[idx]);
			if (submitResult)
				form.appendChild(group[idx]);
			else
				break;
		}
		
		const hidden = createInputBox("hidden","accessPublicIp",publicIp,"");
		form.appendChild(hidden);
		if (submitResult) {
			joinDataLayer.appendChild(form);
			form.submit();
		}
	}
</script>
</html>