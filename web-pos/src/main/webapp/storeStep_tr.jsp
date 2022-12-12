<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member Join :: Store Step</title>
<script src="resources/js/common.js"></script>
<link rel="stylesheet" href="resources/css/common.css" />
<link rel="stylesheet" href="resources/css/main.css" />
</head>
<body onLoad="pageInit('${message}')">
	<div id="header">
		<div id="logo">훈</div>
		<div id="project">HoonZzang's Lecture Notes</div>
		<div id="currentPage">[ Join ]<span class="nav small">▶▶▶</span>[ Group Step ]<span class="nav small">▶▶▶</span><span class="nav big">[ ${groupName} - Store Step ]</span></div>
	</div>
	<div id="content"></div>
	<div id="footer">
		<div class="btn tripple on" onClick="regStore()">NEXT</div>
		<div class="btn tripple off" onClick="movePage('grStep1.jsp')">PREVIOUS</div>
		<div class="btn tripple etc" onClick="movePage('index.jsp')">MAIN</div>
	</div>
	<!-- Communication Box -->
	<div class="communicationBox tripple" style="display:block">
		<div id="inputZone">
			<input type="text" name="storeCode" placeholder="사업자 등록 번호" class="box big" />
			<input type="text" name="storeName" placeholder="상점 상호"  class="box big" />
			<input type="text" name="storePhone" placeholder="대표 연락처"  class="box big" />
		</div>
	</div>
	<!-- div button -->
	<div class="vButton right" style="display:block" onClick="moveCommunicationBox(true)">▶</div>
	<!-- div button -->
	<div class="vButton left" style="display:none" onClick="moveCommunicationBox(false)">◀</div>
	<div class="communicationBox tripple" style="display:none">
		<div id="inputZone">
			<input type="text" name="storeCode" placeholder="우편 번호" class="box big" onClick="kakaoAPI('communicationBox')" />
			<input type="text" name="storeName" placeholder="상점 주소" class="box big"  readOnly />
			<input type="text" name="storePhone" placeholder="상점 상세 주소" class="box big" />
			<input type="hidden" name="groupCode" value="${groupCode }" />
			<input type="hidden" name="groupName" value="${groupName }" />
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
				<div class="button solo">확인</div>
			</div>
		</div>
	</div>
<script>
function kakaoAPI(className){
	const address = document.getElementsByClassName(className)[1].querySelector("#inputZone");
	address.children[0].setAttribute("value", "22223");	
	address.children[1].setAttribute("value", "인천광역시 미추홀구 매소홀로488번길");
	address.children[2].setAttribute("value", "6-3");
}
function regStore(){
	const form = createForm("", "RegStore", "post");
	const communicationBox = document.getElementsByClassName("communicationBox");

	form.appendChild(communicationBox[0]);
	form.appendChild(communicationBox[0]);
	document.body.appendChild(form);
	
	//form.submit();
		
}
function moveCommunicationBox(direction){
	const communicationBox = document.getElementsByClassName("communicationBox");
	const moveButton = document.getElementsByClassName("vButton");
	if(direction){
		communicationBox[0].style.display = "none";
		moveButton[0].style.display = "none";
		communicationBox[1].style.display = "block";
		moveButton[1].style.display = "block";
	}else{
		communicationBox[0].style.display = "block";
		moveButton[0].style.display = "block";
		communicationBox[1].style.display = "none";
		moveButton[1].style.display = "none";
	}
}
</script>
</body>
</html>