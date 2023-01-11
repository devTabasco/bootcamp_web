<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member Join :: Group Step 1</title>
<script src="resources/js/common.js"></script>
<script src="resources/js/beforeAuth_json.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
function kakaoPostCodeAPI(obj) {
    new daum.Postcode({
        oncomplete: function(data) {
           obj.children[0].value = data.zonecode;
           obj.children[1].value =  data.roadAddress
           obj.children[2].focus();
        }
    }).open();
}
</script>
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
		<div class="btn double off" onClick="movePage('Index')">HOME</div>
		<div class="btn double on" onClick="nextJoinStep(1)">Next Step</div>
	</div>
	<!-- 사용자 입력 Box -->
	<div class="communicationBox single">
		<!-- Step1. 그룹명 입력 -->
		<div class="inputZone" style="display:block">
			<input type="text" name="groupName" placeholder="GROUP NAME" class="box big" />
		</div>
		<!-- Step2. 그룹일반정보 입력 -->
		<div class="inputZone" style="display:none">
			<input type="text" name="groupName" class="box big" readOnly />
			<input type="text" name="groupCeo" placeholder="CEO NAME"  class="box big" />
			<input type="password" name="groupPin" placeholder="ACCESS PIN"  class="box big" />
		</div>
		<!-- step3. 상점정보1 입력 -->
		<div class="inputZone" style="display:none">
			<input type="text" name="storeCode" placeholder="사업자 등록 번호" class="box big" />
			<input type="text" name="storeName" placeholder="상점 상호"  class="box big" />
			<input type="text" name="storePhone" placeholder="대표 연락처"  class="box big" />
		</div>
		<!-- step4. 상점정보2 입력 -->
		<div class="inputZone" style="display:none">
			<input type="text" name="storeZip" placeholder="우편 번호" class="box big" onClick="kakaoPostCodeAPI(this.parentElement)" readOnly />
			<input type="text" name="storeAddr" placeholder="상점 주소" class="box big"  readOnly />
			<input type="text" name="storeAddrDetail" placeholder="상점 상세 주소" class="box big" />
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
</html>