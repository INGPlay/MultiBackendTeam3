<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <%@ include file="../template/staticTemplate.jsp" %>

</head>
<body>
    <div class="container">

        <!-- Menu -->
        <div class="d-flex flex-row-reverse bd-highlight">
            <button class="btn btn-primary" onClick="location.href='/'">홈</button>
        </div>

        <table class="table table table-hover">
            <thead>
              <tr>
                <th scope="col">ID</th>
                <th scope="col">제목</th>
                <th scope="col">작성자</th>
                <th scope="col">작성일</th>
                <th scope="col">추천수</th>
                <th scope="col">조회수</th>
              </tr>
            </thead>
            <tbody id = "listRow">
              <!-- <tr>
                <th scope="row">{id}</th>
                <td>{title}</td>
                <td>{username}</td>
                <td>{createDate}</td>
                <td>{recommends}</td>
                <td>{views}</td>
              </tr> -->
            </tbody>
        </table>
    </div>

</body>
</html>