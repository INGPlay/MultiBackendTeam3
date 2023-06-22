<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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

            <form:form action="/register" method="post" modelAttribute="registerForm">

                <div class="mb-3">
                    <label for="username" class="form-label">Username</label>
                    <form:input type="text" class="form-control" id="username" name = "username" aria-describedby="username" 
                                path="username" placeholder="아이디를 입력하세요" />

                    <div class="form-text">
                        <form:errors path = "username" />
                    </div>
                </div>

                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <form:input type="password" class="form-control" id="password" name = "password" 
                                path="password" placeholder="비밀번호를 입력하세요"/>

                    <div class="form-text">
                        <form:errors path="password" />
                    </div>
                </div>

                <div class="mb-3">
                    <label for="passwordCheck" class="form-label">Password Check</label>
                    <form:input type="password" class="form-control" id="passwordCheck" name = "passwordCheck" 
                            path="passwordCheck" placeholder="비밀번호 확인" />

                    <div class="form-text">
                        <form:errors path="passwordCheck" />
                    </div>
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label">E-Mail</label>
                    <form:input type="text" class="form-control" id="email" name = "email"
                                path="email" placeholder="이메일을 입력하세요" />

                    <div class="form-text">
                        <form:errors path="email" />
                    </div>
                </div>

                <div class="mb-3">
                    <label for="phone" class="form-label">Phone</label>
                    <form:input type="text" class="form-control" id="phone" name = "phone"
                                path="phone" placeholder="전화번호를 입력하세요" />
                                
                    <div class="form-text">
                        <form:errors path="phone" />
                    </div>
                </div>


                <div class="mb-3">
                    <button type="submit" class="btn btn-primary">Register</button>
                </div>

            </form:form>


        </div>
    </div>
</body>

</html>