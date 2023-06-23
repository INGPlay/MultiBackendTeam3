<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

	<%@ include file="./template/staticTemplate.jsp" %>

</head>

<body>
    <div class="row justify-content-center">
        <div class="col-4">

            <form action="/login-process" method="post">

                <div class="mb-3">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" class="form-control" id="username" aria-describedby="username"
                    name = "username">
                    <div class="form-text">아이디를 입력하세요</div>
                </div>

                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" class="form-control" id="password"
                    name = "password">
                    <div class="form-text">비밀번호를 입력하세요</div>
                </div>

                <!-- Remember-me -->
                <div class="form-check mb-3">
                    <input class="form-check-input" type="checkbox" name="remember-me" id="remember-me">
                    <label class="form-check-label" for="remember-me">
                        Remember-me
                    </label>
                </div>

                <div class="mb-3">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>

            </form>


        </div>
    </div>
</body>

</html>