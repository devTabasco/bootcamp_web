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
										<p>아래의 인적사항을 추가해주세요.</p>
										<div id="joinDataLayer">
											<div class="form-outline mb-4">
												<input type="date" id="form2Example11" class="form-control"
													placeholder="생일을 입력해주세요" name="memberBirth" />
											</div>

											<div class="form-outline mb-4">
												<input type="text" id="form2Example11" class="form-control"
													placeholder="전화번호를 입력하세요." name="memberHomePhone" />
											</div>

											<div class="form-outline mb-4">
												<div class="form-check" style="display: inline-block">
													<input class="form-check-input" type="radio"
														name="memberSex" id="flexRadioDefault1" value="남"> <label
														class="form-check-label" for="flexRadioDefault1">
														남성 </label>
												</div>
												<div class="form-check" style="display: inline-block">
													<input class="form-check-input" type="radio"
														name="memberSex" id="flexRadioDefault2" value="녀">
													<label class="form-check-label" for="flexRadioDefault2">
														여성 </label>
												</div>
											</div>

											<div class="form-outline mb-4">
												<input type="button" class="btn btn-primary gradient-custom-2" style="margin-top:0; margin-bottom:5%;" onclick="sample6_execDaumPostcode()" value="우편번호 찾기">
												<input type="text" id="sample6_postcode" class="form-control" name="memberAddr" placeholder="우편번호">
												<input type="text" id="sample6_address" class="form-control" name="memberAddr" placeholder="주소">
												<input type="text" id="sample6_detailAddress" class="form-control" name="memberAddr" placeholder="상세주소">
												<input type="text" id="sample6_extraAddress" class="form-control" name="memberAddr" placeholder="참고항목">
											</div>
										</div>
										<div class="text-center">
											<button class="btn btn-primary gradient-custom-2"
												type="button" style="width: 100%" onClick="regist()">추가정보등록</button>
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
<!-- kakao addr API -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</body>
<script type="text/javascript">
	function regist() {
		const form = createForm("", "addInfo", "post");
		const joinDataLayer = document.getElementById("joinDataLayer");
		const addrArray = document.getElementsByName("memberAddr");
		
		form.appendChild(document.getElementsByName("memberBirth")[0]);
		form.appendChild(document.getElementsByName("memberHomePhone")[0]);
		form.appendChild(document.getElementsByName("memberSex")[0]);
		
		addrArray[0].value = "(" + addrArray[0].value + ")";
		let addrString = '';
		
		for(let i = 0; i<addrArray.length; i++){
			if(i===2) addrString += (" " + addrArray[i].value);
			else addrString += addrArray[i].value;
		}
		//type, name, value, placeholder
		form.appendChild(createInputBox("hidden","memberAddr",addrString,""));

		joinDataLayer.appendChild(form);
		form.submit();
	}
</script>
<script>
    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample6_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample6_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    }
</script>
</html>