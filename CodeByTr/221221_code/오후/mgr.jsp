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
<body onLoad="pageInit('${param.message}', '${AccessInfo.storeInfoList[0].empList[0].accessList[0].accessDate }')">
	<div id="wrap">
		<div id="side">
			<div id="store">
					${options}
			</div>
			<div id="emp">
				<span>${AccessInfo.storeInfoList[0].empList[0].empName }</span><span> 대표님</span>
			</div>
			<div id="menu">
				<div class="menu" onClick="menuClick(0)">매출분석</div>
				<ul>
					<li><span onClick="subMenuClick(0)">종합분석</span></li>
					<li><span onClick="subMenuClick(1)">일자별분석</span></li>
					<li><span onClick="subMenuClick(2)">상품별분석</span></li>
					<li><span onClick="subMenuClick(3)">결재수단별분석</span></li>
					<li><span onClick="subMenuClick(4)">고객별분석</span></li>
				</ul>
				<div class="menu" onClick="menuClick(1)">상품관리</div>
				<ul>
					<li><span onClick="subMenuClick(5);selelctCategoryCtl('${AccessInfo.storeInfoList[0].storeCode}');">분류관리</span></li>
					<li><span onClick="subMenuClick(6);selectGoodsManagement('${AccessInfo.storeInfoList[0].storeCode}');">상품관리</span></li>
					<li><span onClick="subMenuClick(7);selectCostPriceManagement('${AccessInfo.storeInfoList[0].storeCode}');">가격관리</span></li>
				</ul>
				<div class="menu" onClick="menuClick(2)">매장관리</div>
				<ul>
					<li><span onClick="subMenuClick(8)">판매구역관리</span></li>
					<li><span onClick="subMenuClick(9)">테이블등록</span></li>
					<li><span onClick="subMenuClick(10)">테이블배치</span></li>
					<li><span onClick="subMenuClick(11)">주문상태관리</span></li>
					<li><span onClick="subMenuClick(12)">결재상태관리</span></li>
				</ul>
				<div class="menu" onClick="menuClick(3);subMenuClick(13);">직원관리</div>
				<div class="menu" onClick="menuClick(4);subMenuClick(14);">고객관리</div>
			</div>
		</div>
		<div id="top">
			<div id="selectedMenu">서브 메뉴를 선택해 주세요</div>
			<div id="copyright">Designed By HoonZzang</div>
			<div id="accessInfo" onClick="accessOut()"></div>
		</div>
		<div id="content"></div>
		<div id="footer">
			<div class="btn single" onClick="goToSales();">매장입장</div>
		</div>
	</div>
	
	<!-- 사용자 입력 -->
	<div class="communicationBox single" style="display:none">
		<div id="inputZone">
			<input type="text" name="storeCode" placeholder="매장코드" class="box big" />
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
				<div class="button solo" onClick="disableMessage()">확인</div>
			</div>
		</div>
	</div>
	<script>
		const menuList = ["종합 분석", "일자별 분석", "상품별 분석", "결재 수단별 분석", 
			                "고객별 분석", "상품 분류 관리", "상품 정보 관리", 
			                "가격 정보 관리", "판매 구역 관리", "테이블 등록", "테이블배치", 
			                "주문 상태 관리", "결제 상태 관리", "직원 정보 관리", "고객 정보 관리"]
		let currentIdx = null;
		const jsonString = '${serverData}';
		const current = '${param.currentIdx}';
		
		
		function mgrInit(){
			alert("After Sever Call : " + current);
			/* S : 이전 선택 환경 복구 */
			const indexes = current.split(":");
			menuClick(indexes[0]);
			subMenuClick(indexes[1]);
			selelctCategoryCtl(indexes[2]);
			modifyButtonStatus(indexes[3]);
			/* E : 이전 선택 환경 복구 */
			
			const jsonData = JSON.parse(jsonString);
			console.log(jsonData);
		}
		
		function menuClick(idx) {
			currentIdx = idx + "";
			const menu = document.getElementsByClassName("menu");
			const subMenu = document.getElementsByTagName("ul");

			menu[idx].style.color = "rgba(255, 187, 0, 1)";
			if (idx < 3) subMenu[idx].style.display = "block";
			
			for (index = 0; index < menu.length; index++) {
				if(index != idx){	
					menu[index].style.color = "rgba(255, 255, 255, 1)";
					if (index < 3) subMenu[index].style.display = "none";
				}
			}
		}
		
		function subMenuClick(subIdx){
			currentIdx += (":" +subIdx);
			const mainTitle = document.getElementById("selectedMenu");
			mainTitle.innerText = menuList[subIdx];
		}
		
		function selelctCategoryCtl(storeCode){
			/* storeCode >> C? */
			selectCategoryStep1(storeCode);
		}
		
		function selectCategoryStep1(storeCode){
			const itemCode = [["C", "상품분류"], ["S", "상품상태"], ["L", "직원등급"], ["D", "가격분류"], ["Z", "구역분류"], ["O", "주문상태"], ["P", "결제상태"]];
			const content = document.getElementById("content");
						
			const div = createDIV("itemList", "itemZone", "");
			for(let idx=0; idx<7; idx++){
				const button = createDIV("", "item", "serverCall('" + storeCode + "', '" + itemCode[idx][0] + "', "+ idx +", '" + (currentIdx + ":" + storeCode +":" + idx) + "')");
				button.innerText = itemCode[idx][1];
				div.appendChild(button);
			}
			
			content.appendChild(div);			
		}
		
		function modifyButtonStatus(objIdx){
			const item = document.getElementsByClassName("item");
			for(let idx=0; idx<item.length; idx++){
				if(idx==objIdx) item[idx].style.backgroundColor = "rgba(255, 187, 0, 1)";
				else item[idx].style.backgroundColor = "rgba(0, 16, 84, 1)";
			}
		}
		
		function serverCall(storeCode, itemCode, objIdx, cIdx){
			alert("Before Sever Call : " + cIdx);
			modifyButtonStatus(objIdx);
			
			if(storeCode != ''){
				const form = createForm("", "GetGoodsCategory", "post");
				form.appendChild(createInputBox("hidden", "storeCode", storeCode, ""));
				form.appendChild(createInputBox("hidden", "levCode", itemCode, ""));
				form.appendChild(createInputBox("hidden", "currentIdx", cIdx, ""));
				document.body.appendChild(form);
				
				form.submit();
			}
		}
		
		function selelctCategoryStep2(){
			
		}
		
		function selectGoodsManagement(storeCode){
			/* storeCode >> SMCODE~ */
			alert(storeCode);
		}
		function selectCostPriceManagement(storeCode){
			/* storeCode >> SMCODE~ */
			alert(storeCode);
		}
		
		function goToSales(){
			alert("판매화면이동");
		}
	</script>
</body>
</html>