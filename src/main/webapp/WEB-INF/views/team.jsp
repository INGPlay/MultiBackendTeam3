<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Team</title>
    <link rel="stylesheet" href="/css/team.css">



</head>
<body>

    <!-- header -->
    <%@ include file="/WEB-INF/views/template/header.jsp" %>

    <section class="list">
        <div class="main-content">

                <h1>Trip Over</h1>
                <p>
                    Trip Over는 국내 여행을 계획 하고있는 관광객들을 위한 일정 관리를 도와주는 서비스입니다.
                    <br><br>
                    한국관광공사 tour api를 사용하여 관광지,문화시설,축제공연행사,여행코스
                    ,레포츠,숙박,쇼핑,음식점의 데이터들을 활용하여 지도에 정보를 제공하고,
                    마커를 찍어 이동간의 최적의 경로를 확인할 수 있습니다.
                    <br><br>
                    리뷰게시판에는 다른 사람들이 올린 여행일정을 확인하고 댓글을 쓸수 있습니다.
                </p>


        </div>
    </section>



    <!-- footer -->
    <%@ include file="/WEB-INF/views/template/footer.jsp" %>


</body>
</html>