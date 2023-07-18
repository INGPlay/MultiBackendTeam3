<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <!-- BootStrap Template -->
    <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="<c:url value='/css/pathmap/color.css' />">
    <link rel="stylesheet" href="<c:url value='/css/headers.css' />">

    <link rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
    type="text/css">
    <link rel="stylesheet" href="/resources/colorful.css">

</head>

<body>

    <div class="container">
        <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-1 border-bottom">
    
            <ul class="nav col-12 col-md-auto justify-content-center mb-md-0">
                <li><a href="/" class="nav-link px-2 link-secondary">Trip Over</a></li>
                <li><a href="/pathmap" class="nav-link px-2 link-dark">지도</a></li>
                <li><a href="/review/list" class="nav-link px-2 link-dark">리뷰</a></li>
                <!-- <li><a href="#" class="nav-link px-2 link-dark">페이지</a></li> -->
                <li><a href="/team" class="nav-link px-2 link-dark">소개</a></li>
            </ul>
    
            <div class="col-md-5 text-end d-flex flex-row-reverse">
                <div>

                    <sec:authorize access="hasRole('ADMIN')">
                        <a href="/admin" class="btn btn-danger">관리자</a>
                    </sec:authorize>

                    <sec:authorize access="isAnonymous()">
                        <a href="/user/login" class="btn btn-outline-primary">로그인</a>
                        <a href="/user/register" class="btn btn-primary">가입하기</a>
                    </sec:authorize>

                    <sec:authorize access="isAuthenticated()">
                        <sec:authentication property="principal.username" var="username" />
                        <a href="/user/inform" class="btn btn-outline-primary">${username}</a>
                        <a class="btn btn-primary" style="color: white;" onclick="document.getElementById('logout').submit()">로그아웃</a>

                        <form action="/user/logout" method="post" id="logout"></form>
                    </sec:authorize>
                </div>
            </div>
        </header>
    </div>
      
</body>
</html>