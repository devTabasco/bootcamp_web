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
  		<span>${AccessInfo.storeInfoList[0].storeName }</span>
  		<span>${StoreList }</span>
  	</div>
  	<div>${AccessInfo.storeInfoList[0].empList[0].empCode }</div>
  	<div id="menu">
  		<ul>
  			<li><span>매출분석</span>
  				<ul>
  					<li><span>종합분석</span></li>
  					<li><span>일자별분석</span></li>
  					<li><span>상품별분석</span></li>
  					<li><span>결재수단별분석</span></li>
  					<li><span>고객별분석</span></li>  					
  				</ul>
  			</li>
  			<li><span>상품</span>
  				<ul>
  					<li><span>상품관리</span></li>
  					<li><span>가격관리</span></li>
  					<li><span>분류관리</span></li>
  				</ul>
  			</li>
  			<li><span>매장</span>
  				<ul>
  					<li><span>판매구역관리</span></li>
  					<li><span>테이블등록</span></li>
  					<li><span>테이블배치</span></li>
  					<li><span>주문상태관리</span></li>
  					<li><span>결재상태관리</span></li>
  				</ul>
  			</li>
  			<li><span>직원관리</span></li>
  			<li><span>고객관리</span></li>
  		</ul>
  	</div>
  </div>
  <div id="top"><input type="button" value="로그아웃" onClick="accessOut()"/></div>
  <div id="content">
  	${AccessInfo.groupCode } ${AccessInfo.storeInfoList[0].storeCode }
		${AccessInfo.storeInfoList[0].empList[0].empCode }
  </div>
  <div id="footer"></div>
</div>
</body>
</html>