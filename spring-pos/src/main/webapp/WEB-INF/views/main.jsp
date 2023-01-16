<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>:: SMART POS ::</title>
<script src="resources/js/common.js"></script>
<script src="resources/js/beforeAuth_json_230112.js"></script>
<link rel="stylesheet" href="resources/css/common.css" />
<link rel="stylesheet" href="resources/css/main.css" />
</head>
<body onLoad="pageInit('${param.message}', '')">
<div id="header">
		<div id="logo">훈</div>
		<div id="project">HoonZzang's Lecture Notes</div>
		<div id="currentPage"><span>[ Main ]</span></div>
	</div>
	<div id="content">${AccessInfo}</div>
	<div id="footer">
		<div class="btn double on" onClick="serverCallByRequest('goMgr', 'get', '')">매장관리</div>
		<div class="btn double on" onClick="serverCallByRequest('goSales', 'get', '')">판매</div>
	</div>
	<!-- MessageBox -->
	<div id="background" style="display: none">
		<div id="lightBox">
			<div id="messageTitle">Message Title</div>
			<div id="messageZone">
				<div id="messageContent">Server Message</div>
			</div>
			<div id="messageAction">
				<div class="button solo" onClick="messageController(false,'')">확인</div>
			</div>
		</div>
	</div>
</body>
</html>