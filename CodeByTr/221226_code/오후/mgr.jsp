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
					<li><span onClick="subMenuClick(0, '${AccessInfo.storeInfoList[0].storeCode}')">종합분석</span></li>
					<li><span onClick="subMenuClick(1, '${AccessInfo.storeInfoList[0].storeCode}')">일자별분석</span></li>
					<li><span onClick="subMenuClick(2, '${AccessInfo.storeInfoList[0].storeCode}')">상품별분석</span></li>
					<li><span onClick="subMenuClick(3, '${AccessInfo.storeInfoList[0].storeCode}')">결재수단별분석</span></li>
					<li><span onClick="subMenuClick(4, '${AccessInfo.storeInfoList[0].storeCode}')">고객별분석</span></li>
				</ul>
				<div class="menu" onClick="menuClick(1)">상품관리</div>
				<ul>
					<li><span onClick="subMenuClick(5,'${AccessInfo.storeInfoList[0].storeCode}');">분류관리</span></li>
					<li><span onClick="subMenuClick(6,'${AccessInfo.storeInfoList[0].storeCode}');">상품관리</span></li>
					<li><span onClick="subMenuClick(7,'${AccessInfo.storeInfoList[0].storeCode}');">가격관리</span></li>
				</ul>
				<div class="menu" onClick="menuClick(2)">매장관리</div>
				<ul>
					<li><span onClick="subMenuClick(8, '${AccessInfo.storeInfoList[0].storeCode}')">판매구역관리</span></li>
					<li><span onClick="subMenuClick(9, '${AccessInfo.storeInfoList[0].storeCode}')">테이블등록</span></li>
					<li><span onClick="subMenuClick(10, '${AccessInfo.storeInfoList[0].storeCode}')">테이블배치</span></li>
					<li><span onClick="subMenuClick(11, '${AccessInfo.storeInfoList[0].storeCode}')">주문상태관리</span></li>
					<li><span onClick="subMenuClick(12, '${AccessInfo.storeInfoList[0].storeCode}')">결재상태관리</span></li>
				</ul>
				<div class="menu" onClick="menuClick(3);subMenuClick(13, '${AccessInfo.storeInfoList[0].storeCode}');">직원관리</div>
				<div class="menu" onClick="menuClick(4);subMenuClick(14, '${AccessInfo.storeInfoList[0].storeCode}');">고객관리</div>
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
	<div class="userInputBackground" style="display:none"></div>
	
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
		const itemCode = [[ "매출분석", "종합분석", "매출 종합 분석", "" ],
				[ "매출분석", "일자별분석", "판매 일자별 분석", "" ],
				[ "매출분석", "상품별분석", "판매 상품별 분석", "" ],
				[ "매출분석", "결제수단별분석", "결제 수단별 분석", "" ],
				[ "매출분석", "고객별분석", "고객별 분석", "" ],
				[ "C", "상품분류", "상품 분류 코드 관리", ["GetGoodsCategoryList"], ["InsGoodsCategory", "levName"], ["UpdGoodsCategory", [1, "levName", "상품분류명"]]],
				[ "S", "상품상태", "상품 상태 코드 관리", ["GetGoodsStateList"], ["InsGoodsState", "levName"], ["UpdGoodsState", 1, "상품상태명"]],
				[ "L", "직원등급", "직원 등급 코드 관리", ["GetEmpLevList"], ["InsEmpLev", "levName"], ["UpdEmpLev", 1, "직원등급명"]],
				[ "D", "가격분류", "상품 가격 코드 관리", ["GetPriceCategoryList"], ["InsPriceCategory", "levName"], ["UpdPriceCategory", 1, "가격타입명"]],
				[ "Z", "구역분류", "판매 구역 코드 관리", ["GetZoneList"], ["InsZone", "levName"], ["UpdZone", 1, "매장구역명"]],
				[ "O", "주문상태", "주문 상태 코드 관리", ["GetOrderStateList"], ["InsOrderState", "levName"], ["UpdOrderState", 1, "주문상태명"]],
				[ "P", "결제상태", "결제 상태 코드 관리", ["GetPaymentSateList"], ["InsPaymentState", "levName"], ["UpdPaymentState", 1, "결제상태명"]],
				[ "상품관리", "상품관리", "상품 정보 관리", ["GetGoodsList"], ["SelGoods", "goodsInfo"], ["InsGoods", 1, ""], ["UpdGoods", 1, ""]],
				[ "상품관리", "가격관리", "상품 가격 관리", ["GetGoodsList"], ["SelGoods", "goodsInfo"], ["InsCostPrice", 1, ""]],
				[ "매장관리", "판매구역관리", "판매 구역 정보 관리", "" ],
				[ "매장관리", "테이블관리", "테이블 정보 관리", "" ],
				[ "매장관리", "테이블배치", "구역별 테이블 배치", "" ],
				[ "직원관리", "직원관리", "직원 정보 관리", "" ],
				[ "고객관리", "고객관리", "고객 정보 관리", "" ], ];
		const menu = document.getElementsByClassName("menu");
		const subMenu = document.getElementsByTagName("ul");

		const jsonString = '${serverData}';
		let selectedIdx = [];

		function mgrInit() {
			alert("After Sever Call : ${param.currentIdx}");
			/* S : 이전 선택 환경 복구 */
			selectedIdx = '${param.currentIdx}'.split(":");

			menuClick(selectedIdx[0]);
			if(selectedIdx[0] <= 1){
				subMenuClick(selectedIdx[1], selectedIdx[2]);
			}
			
			menuCtl(JSON.parse(jsonString));
			
			/* E : 이전 선택 환경 복구 */
		}
		
		/* 지정한 HTML Object 모든 내용을 삭제 */
		function removeHTMLObject(layerName) {
			document.getElementById(layerName).innerText = "";
		}

		/* 선택되어진 메뉴 인덱스 저장 */
		function selectedIdxChange(sequence, currentData) {
			if (selectedIdx.length > sequence) {
				if (selectedIdx[sequence] != currentData) {
					selectedIdx[sequence] = currentData;
					for (idx = sequence + 1; idx < selectedIdx.length; idx++) {
						selectedIdx.pop();
					}
					removeHTMLObject("content");
				}
			} else
				selectedIdx.push(currentData);
		}

		/* 저장된 메뉴 인덱스를 하나의 문자열로 변환 */
		function makeCurrentIdx() {
			let currentIdx = "";
			let cnt = -1;
			selectedIdx.forEach(function(idx) {
				cnt++;
				currentIdx += ((cnt == 0 ? "" : ":") + idx);
			});
			alert(currentIdx);
			return currentIdx;
		}

		/* 메뉴 선택 */
		function menuClick() {
			if(arguments.length > 0) {
				selectedIdxChange(0, arguments[0]);
			}
			const idx = parseInt(selectedIdx[0]);
			/* 선택 메뉴의 Style 지정*/
			if (idx < 3)
				subMenu[idx].style.display = "block";
			/* 선택 되지 않은 메뉴의 Style 지정*/
			for (index = 0; index < menu.length; index++) {
				if (index != idx) {
					menu[index].style.color = "rgba(255, 255, 255, 1)";
					if (index < 3)
						subMenu[index].style.display = "none";
				} else
					menu[idx].style.color = "rgba(255,187,0,1)";
			}
		}

		function subMenuClick() {
			if(arguments.length > 0){
				selectedIdxChange(1, arguments[0]);
				selectedIdxChange(2, arguments[1]);	
			}
			
			let subMenuIdx = parseInt(selectedIdx[1]);
			let objIdx = null;
			
			const mainTitle = document.getElementById("selectedMenu");
			mainTitle.innerText = itemCode[subMenuIdx][2];		
			
			if(selectedIdx[0] == 0){/* DatePicker 상태변경을 위한 objIdx 설정 */
							
			}else if(selectedIdx[0] == 1 && selectedIdx.length == 4){
				objIdx = parseInt(selectedIdx[3]);
			}
			
			/* SubMenu의 Click 이벤트 발생시 추가되어져 할 HTML Object */
			if(subMenuIdx < 5) makeDatePicker(true);
			else if(subMenuIdx == 5) {
				makeItemButton(subMenuIdx, 7);
				if(objIdx != null)	modifyButtonStatus(objIdx);
			}
		}
		
		/* DatePicker 만들기 */
		function makeDatePicker(isPair){
			let date = [];
			date.push(createDatePicker("selectedDate", "", ""));
			if(isPair){
				date.push(createDatePicker("selectedDate", "", ""));
			}
			/* body.content에 추가하기 */
		}
		
		/* 분류코드 항목별 Button 만들기 */
		function makeItemButton(sIdx, length) {
			if (content.innerText == "") {
				const div = createDIV("itemList", "itemZone", "");
				for (idx=0; idx<length; idx++) {
					const button = createDIV("", "item", "modifyButtonStatus(" + idx + ");menuCtl("+ idx +");");
					button.innerText = itemCode[sIdx+idx][1];
					div.appendChild(button);
				}
				content.appendChild(div);
			}
		}
		/* Item Button에 Click event 발생시 Item Button에 CSS적용 */
		function modifyButtonStatus(objIdx) {
			const item = document.getElementsByClassName("item");
			for (let idx = 0; idx < item.length; idx++) {
				if (idx == objIdx)
					item[idx].style.backgroundColor = "rgba(255, 187, 0, 1)";
				else
					item[idx].style.backgroundColor = "rgba(0, 16, 84, 1)";
			}
		}
		
		function menuCtl(){
			menuIdx = parseInt(selectedIdx[0]);
			
			if(menuIdx === 0) {salesAnalysisCtl(arguments[0]);}
			else if(menuIdx === 1) {goodsManagementCtl(arguments[0]);}
			else if(menuIdx === 2) {storeManagementCtl(arguments[0]);}
			else if(menuIdx === 3) {empManagementCtl(arguments[0]);}
			else if(menuIdx === 4) {customerManagement(arguments[0]);}
		}
		/* 매출분석 */
		function salesAnalysisCtl(){
			alert(arguments.length);
			if(selectedIdx.length==3 && arguments.length == 2) {
				selectedIdxChange(3, arguments[0]);
				selectedIdxChange(4, arguments[1]);
				
				const jobIdx = parseInt(selectdIdx[1]);
				const formChild = [ createInputBox("hidden", "currentIdx",	makeCurrentIdx(), ""), 
														createInputBox("hidden", "storeCode", selectedIdx[2], "")]
				
				serverCall(3, formChild);
			}else{
				salesAnalysisCallback(arguments[0]);
			}
		}
		/* 상품관리 */
		function goodsManagementCtl(){
			if(selectedIdx.length == 3 && arguments.length == 1) {
				selectedIdxChange(3, arguments[0]);
				console.log(selectedIdx);
				
				const buttons = document.getElementsByClassName("item");
				const jobIdx = 
					(selectedIdx[1] == 5)? parseInt(selectedIdx[1]) + parseInt(selectedIdx[3]) : 
																 parseInt(selectedIdx[1]) + (buttons.length-1); 
				const formChild = [ createInputBox("hidden", "storeCode", selectedIdx[2], ""),
														createInputBox("hidden", "levCode", itemCode[jobIdx][0], "")];
														
				serverCall(3, formChild);
			}else{
				categoryManagementCallBack(arguments[0]);
			}
		}
		/* 매장관리 */
		function storeManagementCtl(){
			alert(arguments.length);
			if(arguments.length == 0) {
				const jobIdx = parseInt(selectedIdx[1]) + 6;  				
				const formChild = [ createInputBox("hidden", "currentIdx",	makeCurrentIdx(), ""), 
														createInputBox("hidden", "storeCode", selectedIdx[2], "")];
				
				serverCall(3, formChild);
			}else {
				storeManagementCallBack(arguments[0]);
			}
		}
		/* 직원관리 */
		function empManagementCtl(){
			alert(arguments.length);
			if(arguments.length == 0) {
				const jobIdx = parseInt(selectedIdx[1]) + 6;  				
				const formChild = [ createInputBox("hidden", "currentIdx",	makeCurrentIdx(), ""), 
														createInputBox("hidden", "storeCode", selectedIdx[2], "")];
				
				serverCall(3, formChild);
			}else {
				empManagementCallBack(arguments[0]);
			}
		}
		/* 고객관리 */
		function customerManagement(){
			alert(arguments.length);
			if(arguments.length == 0) {
				const jobIdx = parseInt(selectedIdx[1]) + 6;  				
				const formChild = [ createInputBox("hidden", "currentIdx",	makeCurrentIdx(), ""), 
														createInputBox("hidden", "storeCode", selectedIdx[2], "")];
				
				serverCall(jobIdx, formChild);
			}else {
				customerManagementCallBack(arguments[0]);
			}
		}
			
		/* Client Request */
		function serverCall(jobIndex, formChild) {
			const recordIdx = parseInt(selectedIdx[1]) + parseInt(selectedIdx[3]);
			
			const form = createForm("", itemCode[recordIdx][jobIndex][0], "post");
			formChild.forEach(function(child){
				form.appendChild(child);
			});
			form.appendChild(createInputBox("hidden", "currentIdx", makeCurrentIdx(), ""));
			
			//console.log(form);			
			document.body.appendChild(form);
			form.submit();
		}
		
		/* 판매 분석 CallBack */
		function salesAnalysisCallback(jsonData) {
	
		}
		
		/* 분류 관리 CallBack */
		function categoryManagementCallBack(jsonData) {
			const recordIdx = parseInt(selectedIdx[1]) + parseInt(selectedIdx[3]);
			const content = document.getElementById("content");
			const categoryZone = createDIV("categoryZone", "", "");
			const categoryAdd = createDIV("categoryAdd", "categoryZone add", "");
			const input = createInputBox("text", itemCode[recordIdx][4][1], "",
					"추가할 분류 이름을 입력하세요");
			input.setAttribute("class", "box big");
			
			const divBtn = createDIV("", "addBtn", "addCategory('" + selectedIdx[2]
					+ "', '" + itemCode[recordIdx][0] + "')");
			divBtn.innerText = "ADD ITEM";
			
			/* 기 입력된 항목의 개수가 10개라면 입력 중지 */
			if(jsonData != null && jsonData.length == 10){
				input.setAttribute("readOnly", true);
				input.setAttribute("placeholder", "분류코드는 10개 까지만 입력 가능");
				divBtn.setAttirbute("disabled", true);
			}
			
			categoryAdd.appendChild(input);
			categoryAdd.appendChild(divBtn);
			const categoryList = createDIV("categoryList", "categoryZone list",
					"");

			const title = createDIV("", "record title", "");
			title.appendChild(createDiv("", "col title", "", "항목코드"));
			title.appendChild(createDiv("", "col title", "", "항목이름"));
			categoryList.appendChild(title);

			if(jsonData != null){
				jsonData.forEach(function(item) {
					const record = createDIV("", "record content",
						"showInputBox("+ recordIdx +")");
					record.appendChild(createDiv("", "col content", "",
						item.levCode));
					record.appendChild(createDiv("", "col content", "",
						item.levName));
					categoryList.appendChild(record);
				});
			}else{
				const record = createDiv("", "record content",
						"", "No Record");
				record.style.textAlign = "center";
				categoryList.appendChild(record);
			}
			categoryZone.appendChild(categoryAdd);
			categoryZone.appendChild(categoryList);
			content.appendChild(categoryZone);
		}

		/* 매장관리 CallBack */
		function storeManagementCallBack(jsonData){
			
		}
		/* 직원관리 CallBack */
		function empManagementCallBack(jsonData){
			
		}
		/* 고객관리 CallBack */
		function customerManagementCallBack(jsonData){
			
		}
		
		
		function addCategory(storeCode, levCode) {
			/* 사용자 입력 유효성 검사 : HTML-Object >> 유효성 */
			const recordIdx = parseInt(selectedIdx[1]) + parseInt(selectedIdx[3]);
			const userInput = document.getElementsByName(itemCode[recordIdx][4][1])[0];
			let formChild = [];
			if(lengthCheck(userInput)){			
				/* hidden Object : storeCode, levCode(1char), levName */
				formChild.push(createInputBox("hidden", "storeCode", selectedIdx[2], ""));
				formChild.push(createInputBox("hidden", "levCode", itemCode[recordIdx][0], ""));
				formChild.push(createInputBox("hidden", itemCode[recordIdx][4][1], userInput.value, ""));
				
				serverCall(4, formChild);
			} else {
				userInput.setAttribute("placeholder", "2~20자 이내로 입력하세요");
			}
		}
		
		
		function showInputBox(itemIdx){
			alert(itemCode[itemIdx][5][1][2]);					
			/* input zone */
			let input;
			const inputZone = createDIV("", "inputZone", "");
			for(idx=0; idx<itemCode[itemIdx][5][1][0]; idx++){
				input = createInputBox("text", itemCode[itemIdx][5][1][1], "", itemCode[itemIdx][5][1][2]);
				input.setAttribute("class", "updBox");
				inputZone.appendChild(input);
			}
						
			/* button zone */
			const transferZone = createDIV("", "transferZone", "");
			const update = createDiv("", "updBtn", "alert('Command Cancel');", "취소");
			const cancel = createDiv("", "updBtn", "alert('Server Call');", "업데이트");
			transferZone.appendChild(update);
			transferZone.appendChild(cancel);
			
			/* command zone */
			const commandZone = createDIV("", "commandZone", "");
			commandZone.appendChild(inputZone);
			commandZone.appendChild(transferZone);
			commandZone.style.width = (itemCode[itemIdx][5][1][0] > 3)? "80%":"50%";
			
			input.style.width = (itemCode[itemIdx][5][1][0] > 3)? "50%" : "100%";
			/* background zone */
			const background = document.getElementsByClassName("userInputBackground")[0];
			background.appendChild(commandZone);
			background.style.display = "block";
			
		}
				
		function goToSales() {
			alert("판매화면이동");
		}
	</script>
</body>
</html>