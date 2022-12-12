<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MEMBER JOIN</title>
<link rel="stylesheet" href="resources/css/main.css">
<script type="text/javascript" src="resources/js/common.js"></script>
<script>
	function serverCall() {
		const form = createForm("", "GroupDupCheck", "get");
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
</script>
</head>
<body>
	<div class="nav">
		<h1>코드헌트</h1>
		<p>정현우 :: 임창용 :: 박건호 :: 정영준</p>
	</div>
	<div class="main">
		<div class="contents">
			<h1 class="logo">CodeHunt</h1>
			<div class="container">
				<div id="joinDataLayer">
					<input type="text" name="groupName" id="id" class="account" placeholder="GROUP NAME" />
					<div id="messageZone">${param.message}</div>
				</div>
				<div>
					<div id="joinEventLayer">
						<input id="login" class="account" type="button" value="NEXT" onClick="serverCall()" /> 
						<input id="login" class="account" type="button" value="MAIN" onclick="movePrePage()" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="footer"></div>
</body>
</html>