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
	const form = createForm("", "RegStore", "get");
	const joinDataLayer = document.getElementById("joinDataLayer");
	let group = [];
	let submitResult = false;

	group.push(document.getElementsByName("storeCode")[0]);
	group.push(document.getElementsByName("storeName")[0]);
	group.push(document.getElementsByName("storeZipCode")[0]);
	group.push(document.getElementsByName("storeAddr")[0]);
	group.push(document.getElementsByName("storeAddrDetails")[0]);
	group.push(document.getElementsByName("storePhone")[0]);
	group.push(document.getElementsByName("groupCode")[0]);	

	for (let idx = 0; idx < group.length; idx++) {
		form.appendChild(group[idx]);
	}

	joinDataLayer.appendChild(form);
	form.submit();
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
				${groupCode} ${groupName}
				<div id="joinDataLayer">
					<input type="text" name="storeCode" id="id" class="account" placeholder="STORE CODE" /> 
					<input type="text" name="storeName" id="id" class="account" placeholder="STORE NAME" /> 
					<input type="text" name="storeZipCode" id="password" class="account" placeholder="STORE ZIPCODE" /> 
					<input type="text" name="storeAddr" id="id" class="account" value="${groupName}" placeholder="STORE ADDR" readOnly /> 
					<input type="text" name="storeAddrDetails" id="id" class="account" placeholder="STORE ADDR DETAILS" /> 
					<input type="text" name="storePhone" id="password" class="account" placeholder="STORE PHONE" />
					<input type="hidden" value="${groupCode}" name="groupCode" id="id" class="account" />
				</div>
				<div id="joinEventLayer">
					<input type="button" value="NEXT" id="login" class="account" onClick="registration()" /> 
					<input type="button" value="PRE" id="login" class="account" onClick="movePage('${param.previous}')" />
					<input type="button" value="MAIN" id="login" class="account"
						onClick="movePage('index.jsp')" />
				</div>
			</div>
		</div>
	</div>
	<div class="footer"></div>
</body>
</html>