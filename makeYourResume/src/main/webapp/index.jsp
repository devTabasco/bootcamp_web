<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <title>Make Your Resume</title>
  <!-- BootStrap css -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
  <!-- BootStrap script -->
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.min.js" integrity="sha384-cuYeSxntonz0PPNlHhBs68uyIAVpIIOZZ5JqeqvYYIcEL727kskC66kF92t6Xl2V" crossorigin="anonymous"></script>
  <!-- google fonts -->
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap"
    rel="stylesheet">

  <link rel="stylesheet" href="resources/css/styles.css">

  <!-- font Awesome -->
  <script src="https://kit.fontawesome.com/e468e5c856.js" crossorigin="anonymous"></script>
</head>

<body>

  <section id="title">

    <div class="container-fluid">

      <!-- Nav Bar -->
      <nav class="navbar navbar-expand-lg navbar-dark">
        <a class="navbar-brand" href="#">Make Your Resume</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
          <ul class="navbar-nav ms-auto">
            <li class="nav-item">
              <a class="nav-link" href="#">Job Search</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="login.jsp">Sign In</a>
            </li>
          </ul>
        </div>
      </nav>
      <!-- Title -->
      <div class="row contents">
        <!-- Large size에서만 2개(=12/6)의 column으로 보여주기 -->
        <div class="col-lg-6">
          <h1>손 쉽게 나만의 이력서를 만들어보세요!</h1>
          <a href="login.jsp"><button type="button" class="btn btn-lg btn-primary">지금 이력서 만들러 가기!</button></a>
          </div>
        <div class="col-lg-6" id="main-image">
          <img class="title-image" src="resources/images/career.png" alt="career-img">
        </div>
      </div>
    </div>
    </section>
<script type="text/javascript">
	function serverCall() {
		//로그인 체크 후 로그인 페이지 or 이력서 조회페이지
		
	}
</script>
</body>
</html>