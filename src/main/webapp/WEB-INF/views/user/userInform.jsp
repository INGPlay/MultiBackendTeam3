<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>개인정보 페이지</title>

    <%@ include file="/WEB-INF/views/template/staticTemplate.jsp" %>

    <!-- 카카오 지도랑 충돌 -->
    <!-- CSS dependencies -->
    <link rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
    type="text/css">
    <link rel="stylesheet" href="/resources/colorful.css">

</head>

<body>
    <!-- header -->
    <%@ include file="/WEB-INF/views/template/header.jsp" %>


    <div class="container py-3">
        <div class="row justify-content-center">

            <div class="col-4">

                <dl class="row">
                    <dt class="col-sm-3">유저명</dt>
                    <dd class="col-sm-9">${userInform.username}</dd>
    
                    <dt class="col-sm-3">이메일</dt>
                    <dd class="col-sm-9">${userInform.email}</dd>
    
                    <dt class="col-sm-3">전화번호</dt>
                    <dd class="col-sm-9">${userInform.phone}</dd>
                
                </dl>
    
                <form:form action="/user/inform" method="post" modelAttribute="updatePasswordForm">
    
                    <c:if test="${param.success ne null}">
                        <h4 class="row justify-content-center text-success">
                            회원정보가 수정되었습니다!
                        </h4>
                    </c:if>
                    <spring:hasBindErrors name="updatePasswordForm">
                        <c:if test="${errors.hasGlobalErrors()}">
                            <c:forEach var="globalError" items="${errors.getGlobalErrors()}">
                                <c:if test="${globalError.getCode() eq 'Duplicated.newPassword'}">
                                    <h4 class="row justify-content-center text-danger">
                                        ${globalError.defaultMessage}
                                    </h4>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </spring:hasBindErrors>


                    
                    <div class="mb-3">
                        <label>현재 비밀번호</label> 
                        <form:input type="password" class="form-control border" required="required"
                                    path="currentPassword" name="currentPassword"/> 

                        <small class="form-text validText">
                            <form:errors path="currentPassword" />
                        </small>

                        <small class="form-text validText" style="color: red;">
                            <spring:hasBindErrors name="updatePasswordForm">
                                <c:if test="${errors.hasGlobalErrors()}">
                                    <c:forEach var="globalError" items="${errors.getGlobalErrors()}">
                                        <c:if test="${globalError.getCode() eq 'NotMatch.currentPasswordCheck'}">
                                            ${globalError.defaultMessage}
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </spring:hasBindErrors>	
                        </small>

                    </div>
    
                    <div class="mb-3">
                        <label>새 비밀번호</label> 
                        <form:input type="password" class="form-control border" required="required"
                                    path="newPassword" name="newPassword"/> 
    
                        <small class="form-text validText">
                            <form:errors path="newPassword" />
                        </small>

                    </div>
    
                    <div class="mb-3">
                        <label>새 비밀번호 확인</label> 
                        <form:input type="password" class="form-control border" required="required"
                                    path="newPasswordCheck" name="passwordCheck"/> 

                        <!-- 글로벌 에러 -->
                        <small class="form-text validText" style="color: red;">
                            <spring:hasBindErrors name="updatePasswordForm">
                                <c:if test="${errors.hasGlobalErrors()}">
                                    <c:forEach var="globalError" items="${errors.getGlobalErrors()}">
                                        <c:if test="${globalError.getCode() eq 'NotMatch.passwordCheck'}">
                                            ${globalError.defaultMessage}
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </spring:hasBindErrors>	
                        </small>

                    </div>
    
    
                    <div class="mb-3">
                        <button type="submit" class="btn btn-primary">비밀번호 수정</button>
                    </div>
    
                </form:form>
            </div>

            <!-- footer -->
            <%@ include file="/WEB-INF/views/template/footer.jsp" %>



        </div>
    </div>
</body>
</html>