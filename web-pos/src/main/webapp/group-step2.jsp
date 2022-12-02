<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MEMBER JOIN</title>
<script src="resources/js/common.js"></script>
<script>
function registration(){
	const form = createForm("", "MemberJoin", "get");	
	const joinDataLayer = document.getElementById("joinDataLayer");
	let group = [];
	let submitResult = false;
	
	group.push(document.getElementsByName("groupName")[0]);
	group.push(document.getElementsByName("groupCeo")[0]);
	group.push(document.getElementsByName("groupPin")[0]);
	
	for(let idx=0; idx<group.length; idx++){
		submitResult = lengthCheck(group[idx]);
		if(submitResult) form.appendChild(group[idx]);	
		else break;
	}
	
	if(submitResult){
		joinDataLayer.appendChild(form);
		form.submit();
	}
}

</script>

</head>
<body>
  <div id="joinDataLayer">
  	<input type="text" name="groupName" value="${groupName}" placeholder="GROUP NAME" readOnly/>
  	<input type="text" name="groupCeo" placeholder="CEO NAME" />
  	<input type="password" name="groupPin" placeholder="ACCESS GROUP PIN NUMBER" />
  </div>
  <div id="joinEventLayer">
  	<input type="button" value="NEXT"  onClick="registration()"/>
  	<input type="button" value="PRE"  onClick="movePrePage('${param.previous}')"/>
  	<input type="button" value="MAIN"  onClick="moveMainPage()"/>
  </div>
</body>
</html>