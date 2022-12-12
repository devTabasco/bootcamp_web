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
		const form = createForm("", "RegEmp", "get");
		const joinDataLayer = document.getElementById("joinDataLayer");
		let group = [];
		let submitResult = false;

		group.push(document.getElementsByName("employeeGrade")[0]);
		group.push(document.getElementsByName("employeeName")[0]);
		group.push(document.getElementsByName("employeePin")[0]);

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
					${storeCode}
					<input type="text" name="employeeGrade" id="id" class="account" value="${employeeGrade}" placeholder="employee Grade" readOnly /> 
					<input type="text" name="employeeName" id="id" class="account" placeholder="Employee Name" /> 
					<input type="password" name="employeePin" id="password" class="account" placeholder="ACCESS Employee PIN NUMBER" />
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