<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="height: 100%; background-color: #FEFCF3;">
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
		<a href="index.jsp" style="text-decoration-line: none; color: #ff4c68">
		<h1>Make Your Resume</h1></a>
	</div>
	<div class="row">
		<div class="col-9 resume-left">
			<div id="box">
				<div>
					<div class="contents-area">
						<h4>인적사항</h4>
						<div class="row">
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="이름" value="${memberInfo.memberName}" ReadOnly>
							</div>
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="생년월일" value="${memberInfo.memberBirth}" ReadOnly>
							</div>
							<div class="col col-lg-2">
								<input class="input-data form-control form-control-lg" type="text" placeholder="성별" value="${memberInfo.memberSex}" ReadOnly>
							</div>
							<div class="col col-lg-4">
								<input class="input-data form-control form-control-lg" type="text" placeholder="이메일" value="${memberInfo.memberEmail}" ReadOnly>
							</div>
						</div>
						<div class="row">
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="전화번호" value="${memberInfo.memberHomePhone}" ReadOnly>
							</div>
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="휴대폰번호" value="${memberInfo.memberPhone}" ReadOnly>
							</div>
							<div class="col col-lg-6">
								<input class="input-data form-control form-control-lg" type="text" placeholder="주소" value="${memberInfo.memberAddr}" ReadOnly>
							</div>
						</div>
					</div>

					<!-- 학력 -->
					<div class="contents-area" id="target-1" style="display: none;">
						<h4>학력</h4>
						<div class="row">
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="학교구분">
							</div>
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="학교명">
							</div>
							<div class="col col-lg-2">
								<input class="input-data form-control form-control-lg" type="text" placeholder="입학년월">
							</div>
							<div class="col col-lg-2">
								<input class="input-data form-control form-control-lg" type="text" placeholder="졸업년월">
							</div>
							<div class="col col-lg-1">
								<input class="input-data form-control form-control-lg" type="text" placeholder="졸업상태">
							</div>
							<div class="col col-lg-1">
								<input class="input-data form-control form-control-lg" type="text" placeholder="편입여부">
							</div>

						</div>
						<div class="row">
							<div class="col col-lg-6">
								<input class="input-data form-control form-control-lg" type="text" placeholder="전공명">
							</div>
							<div class="col col-lg-2">
								<input class="input-data form-control form-control-lg" type="text" placeholder="학점">
							</div>
							<div class="col col-lg-2">
								<input class="input-data form-control form-control-lg" type="text" placeholder="총점">
							</div>
							<div class="col col-lg-2"></div>
						</div>
					</div>

					<!-- 경력 -->
					<div class="contents-area" id="target-2" style="display: none;">
						<h4>경력</h4>
						<div class="row">
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="회사명">
							</div>
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="부서명">
							</div>
							<div class="col col-lg-2">
								<input class="input-data form-control form-control-lg" type="text" placeholder="입사년월">
							</div>
							<div class="col col-lg-2">
								<input class="input-data form-control form-control-lg" type="text" placeholder="퇴사년월">
							</div>
							<div class="col col-lg-2">
								<input class="input-data form-control form-control-lg" type="text" placeholder="재직여부">
							</div>
						</div>
						<div class="row">
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="직급/직책">
							</div>
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="연봉">
							</div>
							<div class="col col-lg-2">
								<input class="input-data form-control form-control-lg" type="text" placeholder="담당업무">
							</div>
							<div class="col col-lg-4"></div>
						</div>
						<div class="row">
							<div class="col col-lg-12">
								<input class="input-data form-control form-control-lg" type="text" placeholder="담당업무">
							</div>
						</div>
					</div>

					<!-- 인턴/대외활동 -->
					<div class="contents-area" id="target-3" style="display: none;">
						<h4>인턴/대외활동</h4>
						<div class="row">
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="활동구분">
							</div>
							<div class="col col-lg-5">
								<input class="input-data form-control form-control-lg" type="text" placeholder="회사/기관/단체명">
							</div>
							<div class="col col-lg-2">
								<input class="input-data form-control form-control-lg" type="text" placeholder="시작년월">
							</div>
							<div class="col col-lg-2">
								<input class="input-data form-control form-control-lg" type="text" placeholder="종료년월">
							</div>
						</div>
						<div class="row">
							<div class="col col-lg-12">
								<input class="input-data form-control form-control-lg" type="text" placeholder="활동내용">
							</div>
						</div>
					</div>

					<!-- 교육이수 -->
					<div class="contents-area" id="target-4" style="display: none;">
						<h4>교육이수</h4>
						<div class="row">
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="교육명">
							</div>
							<div class="col col-lg-5">
								<input class="input-data form-control form-control-lg" type="text" placeholder="교육기관">
							</div>
							<div class="col col-lg-2">
								<input class="input-data form-control form-control-lg" type="text" placeholder="시작년월">
							</div>
							<div class="col col-lg-2">
								<input class="input-data form-control form-control-lg" type="text" placeholder="종료년월">
							</div>
						</div>
						<div class="row">
							<div class="col col-lg-12">
								<input class="input-data form-control form-control-lg" type="text" placeholder="내용">
							</div>
						</div>
					</div>

					<!-- 자격증 -->
					<div class="contents-area" id="target-5" style="display: none;">
						<h4>자격증</h4>
						<div class="row">
							<div class="col col-lg-6">
								<input class="input-data form-control form-control-lg" type="text" placeholder="자격증명">
							</div>
							<div class="col col-lg-4">
								<input class="input-data form-control form-control-lg" type="text" placeholder="발행처">
							</div>
							<div class="col col-lg-2">
								<input class="input-data form-control form-control-lg" type="text" placeholder="취득일">
							</div>
						</div>
					</div>

					<!-- 수상 -->
					<div class="contents-area" id="target-6" style="display: none;">
						<h4>수상</h4>
						<div class="row">
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="수상명">
							</div>
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="수여기관">
							</div>
							<div class="col col-lg-4">
								<input class="input-data form-control form-control-lg" type="text" placeholder="수상연도">
							</div>
							<div class="col col-lg-2"></div>
						</div>
						<div class="row">
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="전화번호">
							</div>
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="휴대폰번호">
							</div>
							<div class="col col-lg-6">
								<input class="input-data form-control form-control-lg" type="text" placeholder="주소">
							</div>
						</div>
					</div>

					<!-- 해외경험 -->
					<div class="contents-area" id="target-7" style="display: none;">
						<h4>해외경험</h4>
						<div class="row">
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="국가명">
							</div>
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="시작년월">
							</div>
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="종료년월">
							</div>
							<div class="col col-lg-3"></div>
						</div>
						<div class="row">
							<div class="col col-lg-12">
								<input class="input-data form-control form-control-lg" type="text" placeholder="내용">
							</div>
						</div>
					</div>

					<!-- 어학 -->
					<div class="contents-area" id="target-8" style="display: none;">
						<h4>어학</h4>
						<div class="row">
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="구분">
							</div>
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="외국어명">
							</div>
							<div class="col col-lg-3">
								<input class="input-data form-control form-control-lg" type="text" placeholder="회화능력">
							</div>
							<div class="col col-lg-3"></div>
						</div>
					</div>

					<!-- 자기소개서 -->
					<div class="contents-area" id="target-9" style="display: none;">
						<h4>자기소개서</h4>
						<div class="row">
							<div class="col col-lg-12">
								<input class="input-data form-control form-control-lg" type="text" placeholder="내용추가">
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>

		<div class="col-3 resume-right">
			<div class="fixed-right">
				<p>학력</p>
				<input type='button' id="menu-btn btn-1" value='학력' onclick='toggleDiv(1)' /><br>
				<p>경력</p>
				<input type='button' id="menu-btn btn-2" value='경력' onclick='toggleDiv(2)' /><br>
				<p>인턴/대외활동</p>
				<input type='button' id="menu-btn btn-3" value='인턴/대외활동' onclick='toggleDiv(3)' /><br>
				<p>교육이수</p>
				<input type='button' id="menu-btn btn-4" value='교육이수' onclick='toggleDiv(4)' /><br>
				<p>자격증</p>
				<input type='button' id="menu-btn btn-5" value='자격증' onclick='toggleDiv(5)' /><br>
				<p>수상</p>
				<input type='button' id="menu-btn btn-6" value='수상' onclick='toggleDiv(6)' /><br>
				<p>해외경험</p>
				<input type='button' id="menu-btn btn-7" value='해외경험' onclick='toggleDiv(7)' /><br>
				<p>어학</p>
				<input type='button' id="menu-btn btn-8" value='어학' onclick='toggleDiv(8)' /><br>
				<p>자기소개</p>
				<input type='button' id="menu-btn btn-9" value='자기소개' onclick='toggleDiv(9)' /><br>
				<button style="background-color: #ff4c68" type="button" class="btn btn-primary" onClick="serverCall()">저장하기</button>
			</div>
		</div>
	</div>

	<!-- div id='my_div'>버튼을 누르면 이 div는 토글됩니다.</div>
			<input type='button' value='toggleDiv' onclick='toggleDiv(1)' /-->

	<!-- 박스 추가되는 영역 -->
	<script>
		function serverCall(){
			alert("servercall");
		}
	
		function toggleDiv(i) {
			const div = document.getElementById('target-' + i);
			const btn = document.getElementById('btn-' + i);
			
			//var parent = div.parentNode;
			//parent.after(div, parent.childNodes[2]);

			if (div.style.display === 'none') {
				div.style.display = 'block';
			} else {
				div.style.display = 'none';
			}
		}
		
	</script>
</body>
</html>