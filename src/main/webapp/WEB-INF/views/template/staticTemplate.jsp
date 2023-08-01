<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- BootStrap Template -->
    <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/css/sidebars.css' />">
    <script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
    <script src="<c:url value='/js/pathmap/greed.js' />" type="text/javascript"></script>
    <link rel="stylesheet" href="<c:url value='/css/pathmap/color.css' />">
    <script src="<c:url value='/js/pathmap/info.js' />" type="text/javascript"></script>

    <!-- Google tag (gtag.js) -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=G-DD0XCTP2XP"></script>
    <script>
      window.dataLayer = window.dataLayer || [];
      function gtag(){dataLayer.push(arguments);}
      gtag('js', new Date());

      gtag('config', 'G-DD0XCTP2XP', { 'debug_mode':true });
      gtag('event', '제외 트래픽', { 'traffic_type': 'internal' });
    </script>

</head>
</html>