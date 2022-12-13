<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CSS example</title>
<link rel="stylesheet" href="resources/css/serve.css">
</head>
<body>
	<div id="header">
			<h2 id="menu-name">종합분석</h2>
			<div id="logout">
				<a href="index.html"><button>로그아웃</button> </a>
			</div>
	</div>
	<div id="content1">
		<div id="storeName">
			<p>${AccessInfo.groupName }</p>
		</div>
	<ul class="menu">	
      <li>
        <a href="#">매출관리</a>
        <ul class="submenu">
          <li><a href="#">종합분석</a></li>
          <li><a href="#">일자별</a></li>
          <li><a href="#">상품별</a></li>
          <li><a href="#">요일별</a></li>
          <li><a href="#">결제수단별</a></li>
        </ul>
      </li>
      <li>
        <a href="pos.html">상품관리</a>
        <ul class="submenu">
          <li><a href="#">submenu01</a></li>
          <li><a href="#">submenu02</a></li>
          <li><a href="#">submenu03</a></li>
          <li><a href="#">submenu04</a></li>
          <li><a href="#">submenu05</a></li>
        </ul>
      </li>
      <li>
        <a href="#">고객관리</a>
        <ul class="submenu">
          <li><a href="#">submenu01</a></li>
          <li><a href="#">submenu02</a></li>
          <li><a href="#">submenu03</a></li>
          <li><a href="#">submenu04</a></li>
          <li><a href="#">submenu05</a></li>
        </ul>
      </li>
      <li>
        <a href="#">매장관리</a>
        <ul class="submenu">
          <li><a href="#">submenu01</a></li>
          <li><a href="#">submenu02</a></li>
          <li><a href="#">submenu03</a></li>
          <li><a href="#">submenu04</a></li>
          <li><a href="#">submenu05</a></li>
        </ul>
      </li>
      <li>
        <a href="#">직원관리</a>
        <ul class="submenu">
          <li><a href="#">submenu01</a></li>
          <li><a href="#">submenu02</a></li>
          <li><a href="#">submenu03</a></li>
          <li><a href="#">submenu04</a></li>
          <li><a href="#">submenu05</a></li>
        </ul>
      </li>
    </ul>
	</div>
	<div id="content2"></div>
	<div id="footer"></div>
</body>
</html>