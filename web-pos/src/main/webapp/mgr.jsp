<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sub Page</title>
<script src="resources/js/common.js"></script>
<link rel="stylesheet" href="resources/css/common.css" />
<link rel="stylesheet" href="resources/css/mgr.css" />
</head>
<body>
	<div id="wrap">
		<div id="side">
			<div>
				<span>${AccessInfo.storeInfoList[0].storeName }</span> <span>${StoreList }</span>
			</div>
			<div>${AccessInfo.storeInfoList[0].empList[0].empCode }</div>
			<div id="menu">
				<div class="middle-menu" onClick="showMenu(0)">매출분석</div>
					<ul>
						<li><span class="sub-menu">종합분석</span></li>
						<li><span>일자별분석</span></li>
						<li><span>상품별분석</span></li>
						<li><span>결재수단별분석</span></li>
						<li><span>고객별분석</span></li>
					</ul>
				<div class="middle-menu" onClick="showMenu(1)">상품</div>
					<ul>
						<li><span>상품관리</span></li>
						<li><span>가격관리</span></li>
						<li><span>분류관리</span></li>
					</ul>
				<div class="middle-menu" onClick="showMenu(2)">매장</div>
					<ul>
						<li><span>판매구역관리</span></li>
						<li><span>테이블등록</span></li>
						<li><span>테이블배치</span></li>
						<li><span>주문상태관리</span></li>
						<li><span>결재상태관리</span></li>
					</ul>
				<div class="middle-menu">직원관리</div>
				<div class="middle-menu">고객관리</div>
			</div>
		</div>
		<div id="top">
			<input type="button" value="로그아웃" onClick="accessOut()" />
		</div>
		<div id="content">${AccessInfo.groupCode }
			${AccessInfo.storeInfoList[0].storeCode }
			${AccessInfo.storeInfoList[0].empList[0].empCode }</div>
		<div id="footer"></div>
	</div>
	<script>
	function showMenu(i){
		const menu = document.getElementsByTagName('ul');
		
		if(menu[i].style.display === "none"){
			menu[i].style.display = "block";
        }else{
        	menu[i].style.display = "none";
        }
		
		for(index = 0; index < 3; index++){
			if(i != index) menu[index].style.display = "none";
		}
		
	}

</script>
</body>
</html>