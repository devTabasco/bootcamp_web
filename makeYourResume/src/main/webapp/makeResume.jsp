<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Make Your Resume</title>
<!-- BootStrap css -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
<!-- BootStrap script -->
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
	integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.min.js"
	integrity="sha384-cuYeSxntonz0PPNlHhBs68uyIAVpIIOZZ5JqeqvYYIcEL727kskC66kF92t6Xl2V"
	crossorigin="anonymous"></script>
<!-- google fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap"
	rel="stylesheet">

<link rel="stylesheet" href="resources/css/styles.css">

<!-- font Awesome -->
<script src="https://kit.fontawesome.com/e468e5c856.js"
	crossorigin="anonymous"></script>
</head>
<body>

	<div id="resume-body">
		<h1>Make Your Resume</h1>

		<form>
			<div id="box">
				<input type="text"> <input type="button" value="추가"
					onclick="add_textbox()">
			</div>
		</form>

	</div>
	<!-- 박스 추가되는 영역 -->
	<script>
        const add_textbox = () => {
			let inputData = ["<input type='text'> <input type='button' value='삭제' onclick='remove(this)'>", 
				"<input type='text'> <input type='button' value='삭제' onclick='remove(this)'>", 
				"<input type='text'> <input type='button' value='삭제' onclick='remove(this)'>"];
            const box = document.getElementById("box");
            const newP = document.createElement('div');
            
            newP.innerHTML = inputData[1];
            box.appendChild(newP);
        }
        const remove = (obj) => {
            document.getElementById('box').removeChild(obj.parentNode);
        }
    </script>
</body>
</html>