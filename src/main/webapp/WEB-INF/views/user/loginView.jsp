<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>로그인</title>

	<!-- CSS dependencies -->
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
		type="text/css">
	<link rel="stylesheet" href="/resources/colorful.css">

</head>

<body>
	<!-- header -->
	<%@ include file="/WEB-INF/views/template/header.jsp" %>

	<!-- 로그인 등록 또는 로그인 -->

	<div class="container py-5" id="loginSlot">
		<div class="row justify-content-center">
			<div class="col-4">
				<form action="/login-process" method="post">

					<div>
						<small class="form-text validText" style="color: red;">
							<c:if test="${param.fail ne null}">
								<p>
									로그인에 실패하였습니다. 
									<br/>
									아이디와 비밀번호를 확인해주세요
								</p>
							</c:if>
						</small>
					</div>

					<c:if test="${param.success ne null}">
                        <h4 class="row justify-content-center text-success">
                            가입에 성공하였습니다
                        </h4>
                    </c:if>


					<div class="form-group">
						<label>아이디</label>
						<input type="text" class="form-control" placeholder="아이디를 입력하세요"
								name = "username">
						<!-- <small class="form-text text-muted">'
							추후에 에러용?
						</small> -->
					</div>

					<div class="form-group">
						<label>비밀번호</label>
						<input type="password" class="form-control" placeholder="비밀번호를 입력하세요"
								name = "password">
					</div>

					<div class="form-check mb-3">
						<input class="form-check-input" type="checkbox" name="remember-me" id="remember-me">
						<label class="form-check-label" for="remember-me">
							Remember-me
						</label>
					</div>

					<button type="submit" class="btn btn-primary">로그인</button>

				</form>
			</div>
		</div>
	</div>

	<!-- footer -->
	<%@ include file="/WEB-INF/views/template/footer.jsp" %>

</body>



</html>