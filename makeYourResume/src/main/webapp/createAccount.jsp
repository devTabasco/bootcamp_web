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

<!-- Custom Script -->
<script src="resources/js/common.js"></script>

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
	<section class="h-100 gradient-form" style="background-color: #eee;">
		<div class="container py-5 h-100">
			<div
				class="row d-flex justify-content-center align-items-center h-100">
				<div class="col-xl-10">
					<div class="card rounded-3 text-black">
						<div class="row g-0">
							<div class="col-lg-6">
								<div class="card-body p-md-5 mx-md-4">

									<div class="text-center">
										<a href="index.jsp"
											style="text-decoration: none; color: #ff4c68">
											<h2 class="mt-1 mb-5 pb-1">Make Your Resume</h2>
										</a>
									</div>

									<form>
										<p>등록할 계정 정보를 입력해주세요.</p>
										<div id="joinDataLayer">
											<div class="form-outline mb-4">
												<input type="email" id="form2Example11" class="form-control"
													placeholder="성과 이름을 입력하세요." name="memberName" />
											</div>

											<div class="form-outline mb-4">
												<input type="email" id="form2Example11" class="form-control"
													placeholder="핸드폰 번호를 입력하세요." name="memberPhone" />
											</div>

											<div class="form-outline mb-4">
												<input type="email" id="form2Example11" class="form-control"
													placeholder="이메일 주소를 입력하세요." name="memberEmail" />
											</div>

											<div class="form-outline mb-4">
												<input type="password" id="form2Example22"
													class="form-control" placeholder="비밀번호를 입력하세요."
													name="memberPassword" />
											</div>
										</div>

										<div class="text-center">
											<button class="btn btn-primary gradient-custom-2"
												type="button" style="width: 100%" onClick="regist()">계정
												생성</button>
										</div>

										<div
											class="d-flex align-items-center justify-content-center pb-4">
											<p class="mb-0 me-2">이미 계정이 있으신가요?</p>
											<a href="login.jsp"><button type="button"
													class="btn btn-outline-danger">로그인</button></a>
										</div>
									</form>

								</div>
							</div>
							<div class="col-lg-6 d-flex align-items-center gradient-custom-2">
								<div class="text-white px-3 py-4 p-md-5 mx-md-4">
									<h4 class="mb-4">Make Your Resume</h4>
									<p class="small mb-0">
										우리는 누구나 원하는 곳에서 일하길 바랍니다.<br>Make Your Resume와 함께 쉽게 멋진
										이력서와 포트폴리오를 만들어보세요!
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<script type="text/javascript">
	function regist() {
		const form = createForm("", "regist", "post");
		const joinDataLayer = document.getElementById("joinDataLayer");

		form.appendChild(document.getElementsByName("memberName")[0]);
		form.appendChild(document.getElementsByName("memberPhone")[0]);
		form.appendChild(document.getElementsByName("memberEmail")[0]);
		form.appendChild(document.getElementsByName("memberPassword")[0]);

		joinDataLayer.appendChild(form);
		form.submit();
	}
</script>
</html>