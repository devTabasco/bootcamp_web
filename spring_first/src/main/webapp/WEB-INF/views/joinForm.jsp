<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member Join :: Group Step 1</title>
<script src="resources/js/common.js"></script>
<script src="resources/js/beforeAuth.js"></script>
<link rel="stylesheet" href="resources/css/common.css" />
<link rel="stylesheet" href="resources/css/main.css" />
</head>
<body onLoad="pageInit('${param.message}', '')">

	<div id="header">
		<div id="logo">훈</div>
		<div id="project">HoonZzang's Lecture Notes</div>
		<div id="currentPage">[ Join ]<span class="nav small">▶▶▶</span><span class="nav big">[ Group Step 1 ]</span></div>
	</div>
	<div id="content"></div>
	<div id="footer">
		<div class="btn double on" onClick="nextJoinStep(1)">NEXT</div>
		<div class="btn double off" onClick="movePage('Index')">PREVIOUS</div>
		<div class="btn tripple etc" style="display:none" onClick="movePage('Index')">MAIN</div>
	</div>
	<!-- 사용자 입력 Box -->
	<div class="communicationBox single">
		<!-- Step1. 그룹명 입력 -->
		<div id="inputZone" style="display:block">
			<input type="text" name="groupName" placeholder="GROUP NAME" class="box big" />
		</div>
		<!-- Step2. 그룹일반정보 입력 -->
		<div id="inputZone" style="display:none">
			<input type="text" name="groupName" class="box big" readOnly />
			<input type="text" name="groupCeo" placeholder="CEO NAME"  class="box big" />
			<input type="password" name="groupPin" placeholder="ACCESS PIN"  class="box big" />
		</div>
		<!-- Step3. 매장 정보 입력 -->
		<div id="inputZone" style="display:none">
			<input type="text" name="storeCode" placeholder="사업자 등록 번호" class="box big" />
			<input type="text" name="storeName" placeholder="상점 상호"  class="box big" />
			<input type="text" name="storePhone" placeholder="대표 연락처"  class="box big" />
		</div>
		<!-- Step4. 직원 정보 입력 -->
		<div id="inputZone" style="display:none">
			<!--  ${levelInfo } -->
			<input type="text" name="empCode" placeholder="직원코드"  class="box big" />
			<input type="text" name="empName" placeholder="직원이름"  class="box big" />
			<input type="password" name="empPin" placeholder="핀번호"  class="box big" />
		</div>
	</div>
	<!-- div button -->
	<div class="vButton right" style="display:none" onClick="moveCommunicationBox(true)">▶</div>
	<!-- div button -->
	<div class="vButton left" style="display:none" onClick="moveCommunicationBox(false)">◀</div>
	<div class="communicationBox tripple" style="display:none">
		<div id="inputZone">
			<input type="text" name="storeZip" placeholder="우편 번호" class="box big" onClick="kakaoAPI('communicationBox')" />
			<input type="text" name="storeAddr" placeholder="상점 주소" class="box big"  readOnly />
			<input type="text" name="storeAddrDetail" placeholder="상점 상세 주소" class="box big" />
			<input type="hidden" name="groupCode" value="${groupCode }" />
			<input type="hidden" name="groupName" value="${groupName }" />
		</div>
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
<script>
const jsonData = '';
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
	
	form.submit();
		
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
</html>