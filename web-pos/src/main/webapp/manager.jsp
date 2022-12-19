<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CSS example</title>
<link rel="stylesheet" href="resources/css/serve.css">
<script src="resources/js/common.js"></script>
</head>
<body>
	<div id="header">
		<h2 id="menu-name">종합분석</h2>
		<div id="logout">
			<button onClick="accessOut()">로그아웃</button>
		</div>
	</div>
	<div id="content1">
		<div id="storeName">
			<p>${AccessInfo.groupName }</p>
			<p>${AccessInfo.groupCode }</p>
			<p>${AccessInfo.storeInfoList[0].storeCode }</p>
			<p>${AccessInfo.storeInfoList[0].empList[0].empCode }</p>
		</div>
		<div id="menu">
			<div class="middle-menu" onClick="showMenu(0)">매출분석</div>
			<ul>
				<li><span class="sub-menu">종합분석</span></li>
				<li><span onClick="alertMenu(0)">일자별분석</span></li>
				<li><span onClick="alertMenu(1)">상품별분석</span></li>
				<li><span onClick="alertMenu(2)">결재수단별분석</span></li>
				<li><span onClick="alertMenu(3)">고객별분석</span></li>
			</ul>
			<div class="middle-menu" onClick="showMenu(1)">상품</div>
			<ul>
				<li><span onClick="alertMenu(4)">상품관리</span></li>
				<li><span onClick="alertMenu(5)">가격관리</span></li>
				<li><span onClick="alertMenu(6)">분류관리</span></li>
			</ul>
			<div class="middle-menu" onClick="showMenu(2)">매장</div>
			<ul>
				<li><span onClick="alertMenu(7))">판매구역관리</span></li>
				<li><span onClick="alertMenu(8)">테이블등록</span></li>
				<li><span onClick="alertMenu(9)">테이블배치</span></li>
				<li><span onClick="alertMenu(10)">주문상태관리</span></li>
				<li><span onClick="alertMenu(11)">결제상태관리</span></li>
			</ul>
			<div class="middle-menu" onClick="alertMenu(12)">직원관리</div>
			<div class="middle-menu" onClick="alertMenu(13)">고객관리</div>
		</div>
	</div>

	<div id="content2"></div>
	<div id="footer"></div>
</body>
<script type="text/javascript">
	function accessOut() {
		const form = createForm("", "AccessOut", "post");

		const hidden = createInputBox("hidden", "accessPublicIp", publicIp, "");
		form.appendChild(hidden);
		document.body.appendChild(form);
		form.submit();
	}

	function showMenu(i) {
		const menu = document.getElementsByTagName('ul');

		if (menu[i].style.display === "none") {
			menu[i].style.display = "block";
		} else {
			menu[i].style.display = "none";
		}

		for (index = 0; index < 3; index++) {
			if (i != index)
				menu[index].style.display = "none";
		}
	}
	
	function alertMenu(i){
		
		let name = ['일자별분석', '상품별분석', '결제수단별분석', '고객별분석', '상품관리', '가격관리', '분류관리', '판매구역관리', '테이블등록', '테이블배치', '주문상태관리', '결제상태관리', '직원관리', '고객관리']
		alert(name[i]+'을(를) 클릭하셨습니다.');
	}
</script>
</html>