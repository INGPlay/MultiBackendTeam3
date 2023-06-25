<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- PAGE settings -->
	<link rel="icon"
		href="https://templates.pingendo.com/assets/Pingendo_favicon.ico">
	<title>trip</title>

	<!-- CSS dependencies -->
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
		type="text/css">
	<link rel="stylesheet" href="/resources/colorful.css">



</head>

<!-- header -->
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<body>
	<!-- 메인 영상화면 -->
	<div class="py-5 text-center text-white"
		style="position: relative; overflow: hidden;">
		<video autoplay="" loop="" muted="" plays-inline=""
			style="position: absolute; right: 0; top: 0; min-width: 100%; z-index: -100;">
			<source src="/resources/source/ocean.mp4" type="video/mp4">
		</video>
		<div class="container py-5">

			<div class="row">

				<div class="col-lg-8 col-md-10 mx-auto">
					<h1 class="mb-4 display-3">Trip Over</h1>

					<p class="lead mb-5">
                        <p>
                            I can fly away Fly always always always
                        </p>
                        <p>
                            Take me to new world anywhere 어디든 답답한 이 곳을 벗어 나기만 하면 Shining light
                        </p>
                        <p>
                            light 빛나는 my youth 자유롭게 fly fly 나 숨을 셔 which I enjoy with my whole heart.
                        </p>
        		  	</p>
					
                    <sec:authorize access="isAnonymous()">
                        <a href="/user/login" class="btn btn-lg btn-primary mx-1">로그인</a>
                        <a href="/user/register" class="btn btn-lg btn-primary mx-1">가입하기</a>
                    </sec:authorize>

                    <sec:authorize access="isAuthenticated()">
                        <a class="btn btn-lg btn-primary mx-1" onclick="document.getElementById('logout').submit()">로그아웃</a>

                        <form action="/user/logout" method="post" id="logout"></form>
                    </sec:authorize>

				</div>

			</div>

		</div>

		<!-- <div class="row">
			<div class="mx-auto col-lg-6 col-md-8 col-10">
				<a href="#mission"><i class="d-block fa fa-angle-down fa-2x"></i></a>
			</div>
		</div> -->
	</div>


	<!-- ===================================================== -->



    <pingendo onclick="" style="cursor:pointer;position: fixed;bottom: 20px;right:20px;padding:4px;background-color: #00b0eb;border-radius: 8px; width:220px;display:flex;flex-direction:row;align-items:center;justify-content:center;font-size:14px;color:white">
		멀티	백엔드 3조 &nbsp;&nbsp;
        <img src="https://pingendo.com/site-assets/Pingendo_logo_big.png" class="d-block" alt="백엔드3조" height="16">
    </pingendo>
</body>



</html>