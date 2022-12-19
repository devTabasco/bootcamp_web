<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member Join :: STEP 2</title>
<script src="resources/js/common.js"></script>
<link rel="stylesheet" href="resources/css/common.css" />
<link rel="stylesheet" href="resources/css/main.css" />
</head>
<body onLoad="pageInit('${message}')">
	<div id="header">
		<div id="logo">훈</div>
		<div id="project">HoonZzang's Lecture Notes</div>
		<div id="currentPage">[ Join ]<span class="nav small">▶▶▶</span><span class="nav big">[ Group Step 2 ]</span></div>
	</div>
	<div id="content"></div>
	<div id="footer">
		<div class="btn tripple on" onClick="registration()">NEXT</div>
		<div class="btn tripple off" onClick="movePage('${param.previous }')">PREVIOUS</div>
		<div class="btn tripple etc" onClick="movePage('index.jsp')">MAIN</div>
	</div>
	<!-- 로그인 Box -->
	<div id="communicationBox" class="tripple">
		<div id="inputZone">
			<input type="text" name="groupName" value="${groupName}" class="box big" />
			<input type="text" name="groupCeo" placeholder="CEO NAME"  class="box big" />
			<input type="password" name="groupPin" placeholder="ACCESS PIN"  class="box big" />
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
	<!-- JavaScript -->
<script>

	function registration() {
		const form = createForm("", "MemberJoin", "post");
		const communicationBox = document.getElementById("communicationBox");
		let group = [];
		let submitResult = false;

		group.push(document.getElementsByName("groupName")[0]);
		group.push(document.getElementsByName("groupCeo")[0]);
		group.push(document.getElementsByName("groupPin")[0]);

		for (let idx = 0; idx < group.length; idx++) {
			submitResult = lengthCheck(group[idx]);
			if (!submitResult)
				break;
		}

		if (submitResult) {
			form.appendChild(communicationBox);
			document.body.appendChild(form);
			form.submit();
		}
	}
</script>
</body>
</html>