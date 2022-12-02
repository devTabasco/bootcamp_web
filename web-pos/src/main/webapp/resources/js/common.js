/**
 * 
 */
 //<input type="text" name="groupName" placeholder="GROUP NAME" />
 function lengthCheck(obj) {
	//서버로 전송할 데이터 길이의 유효성 판단
	const data = [["groupName",2,20] , ["groupCeo",2,5] , ["groupPin",6,6]];
	let result = false;
	for(let i=0;i<data.length;i++){
		if(obj.getAttribute("name") == data[i][0]){
			result = (obj.value.length >= data[i][1] && 
			obj.value.length <= data[i][2])?true:false;
			
			break;
		}
	}
	return result;
}

function createForm(name, action, method){
	const form = document.createElement("form");
	if(name != "") form.setAttribute("name", name);
	form.setAttribute("action", action);
	form.setAttribute("method", method);
	return form;
}
	
function moveMainPage(){
	location.href = "/web-pos";
}
function movePrePage(previous){
	location.href = "/web-pos/" + previous!=null?previous:"";
}