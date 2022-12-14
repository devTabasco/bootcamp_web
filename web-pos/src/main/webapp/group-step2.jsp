<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MEMBER JOIN</title>
<script src="resources/js/common.js"></script>
<link rel="stylesheet" href="resources/css/main.css">
<script>
	function registration() {
		const form = createForm("", "MemberJoin", "post");
		const joinDataLayer = document.getElementById("joinDataLayer");
		let group = [];
		let submitResult = false;

		group.push(document.getElementsByName("groupName")[0]);
		group.push(document.getElementsByName("groupCeo")[0]);
		group.push(document.getElementsByName("groupPin")[0]);

		for (let idx = 0; idx < group.length; idx++) {
			submitResult = lengthCheck(group[idx]);
			if (submitResult)
				form.appendChild(group[idx]);
			else
				break;
		}

		if (submitResult) {
			joinDataLayer.appendChild(form);
			form.submit();
		}
	}

	/*
	 function serverCall() {
	 const form = createForm("", "DeleteTemp", "get");
	 const joinDataLayer = document.getElementById("joinDataLayer");

	 let group = [];
	 group.push(document.getElementsByName("groupName")[0]);

	 for (let i = 0; i < group.length; i++) {
	 submitResult = lengthCheck(group[i]);
	 if (submitResult) {
	 form.appendChild(group[i])
	 } else {
	 break;
	 }
	 }

	 if (submitResult) {
	 joinDataLayer.appendChild(form);
	 form.submit();
	 }
	 }
	 */
</script>

</head>
<body>
	<div class="nav">
		<h1>CodeHunt</h1>
		<p>정현우 :: 임창용 :: 박건호 :: 정영준</p>
	</div>
	<div class="main">
		<div class="contents">
			<h1 class="logo">CodeHunt</h1>
			<div class="container">
				<div id="joinDataLayer">
					<input type="text" name="groupName" id="id" class="account" value="${groupName}" placeholder="GROUP NAME" readOnly /> 
					<input type="text" name="groupCeo" id="id" class="account" placeholder="CEO NAME" /> 
					<input type="password" name="groupPin" id="password" class="account" placeholder="ACCESS GROUP PIN NUMBER" />
				</div>
				<div id="joinEventLayer">
					<input type="button" value="NEXT" id="login" class="account"
						onClick="registration()" /> <input type="button" value="PRE"
						id="login" class="account" onClick="movePage('${param.previous}')" />
					<input type="button" value="MAIN" id="login" class="account"
						onClick="movePage('index.jsp')" />
				</div>
			</div>
		</div>
	</div>
	<div class="footer"></div>
</body>
</html>