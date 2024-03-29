<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>
		가입하기
	</title>

	<!-- CSS dependencies -->
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
		type="text/css">
	<link rel="stylesheet" href="/resources/colorful.css">

	<style>
		.validText {
			color: red;
		}
	</style>

</head>

<body>

	<!-- header -->
	<%@ include file="/WEB-INF/views/template/header.jsp" %>

	<!--회원가입 -->
	<div class="text-center py-1">
		<div class="container">
			<div class="row">
			<div class="mx-auto col-lg-6 col-10">
				<h1 class="text-primary align-items-start text-center">회원가입&nbsp;</h1>

				<form:form action="/user/register" method="post" modelAttribute="registerForm" class="text-left">

					<div class="form-group form-row"> 
						<label for="form16" class="col-sm-2 col-form-label">아이디&nbsp;</label>
						<div class="col-sm-10">
							<form:input type="text" class="form-control mt-2 border" id="form16"  placeholder="영어 소문자와 숫자만 입력 가능합니다" required="required"
										path="username" name="username" oninput="this.value = this.value.replace(/[^A-Za-z0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
						</div>

						<small class="form-text validText" style="color: red;">
							<form:errors path = "username" />
						</small>
						<small class="form-text validText" style="color: red;">
							<spring:hasBindErrors name="registerForm">
								<c:if test="${errors.hasGlobalErrors() }">
									<c:forEach var="globalError" items="${errors.getGlobalErrors()}">
										<c:if test="${globalError.getCode() eq 'UniqueCheck.username'}">
											${globalError.defaultMessage}
										</c:if>
									</c:forEach>
								</c:if>
							</spring:hasBindErrors>	
						</small>

					</div>

					<div class="form-row">
						<div class="form-group col-md-6"> 

							<label for="form19">비밀번호&nbsp;</label> 
							<form:input type="password" class="form-control border" id="form19" placeholder="••••" required="required"
										path="password" name="username"/> 

							<small class="form-text validText" style="color: red;">
								<form:errors path="password" />
							</small>

						</div>

						<div class="form-group col-md-6"> 

							<label for="form20">비밀번호 확인</label> 
							<form:input type="password" class="form-control border" id="form20" placeholder="••••" required="required"
									path="passwordCheck" name="passwordCheck" /> 

							<!-- 비밀번호 확인 -->
							<small class="form-text validText" style="color: red;">
								<spring:hasBindErrors name="registerForm">
									<c:if test="${errors.hasGlobalErrors() }">
										<c:forEach var="globalError" items="${errors.getGlobalErrors()}">
											<c:if test="${globalError.getCode() eq 'NotMatch.passwordCheck'}">
												${globalError.defaultMessage}
											</c:if>
										</c:forEach>
									</c:if>
								</spring:hasBindErrors>	
							</small>
									
						</div>

					</div>

					<div class="form-group"> 

						<label for="form17">전화번호&nbsp;</label> 
						<form:input type="text" class="form-control border" id="phone" placeholder="숫자만 입력해주세요" required="required"
								path="phone" name="phone" oninput="phoneForm(this)"/>

						<small class="form-text validText" style="color: red;">
							<form:errors path="phone" />
						</small>
						<small class="form-text validText" style="color: red;">
							<spring:hasBindErrors name="registerForm">
								<c:if test="${errors.hasGlobalErrors() }">
									<c:forEach var="globalError" items="${errors.getGlobalErrors()}">
										<c:if test="${globalError.getCode() eq 'UniqueCheck.phone'}">
											${globalError.defaultMessage}
										</c:if>
									</c:forEach>
								</c:if>
							</spring:hasBindErrors>	
						</small>

					</div>

					<div class="form-group"> 

						<label for="form18">이메일&nbsp;</label> 
						<input type="email" class="form-control border" id="form18" placeholder="이메일을 입력해주세요" required="required"
								path="email" name="email" />

						<small class="form-text validText" style="color: red;">
							<form:errors path="email" />
						</small>
						<small class="form-text validText" style="color: red;">
							<spring:hasBindErrors name="registerForm">
								<c:if test="${errors.hasGlobalErrors() }">
									<c:forEach var="globalError" items="${errors.getGlobalErrors()}">
										<c:if test="${globalError.getCode() eq 'UniqueCheck.email'}">
											${globalError.defaultMessage}
										</c:if>
									</c:forEach>
								</c:if>
							</spring:hasBindErrors>	
						</small>

					</div>

					<!-- <div class="form-group">
						<div class="form-check"> 
							<input class="form-check-input" type="checkbox" id="form21" value="on" required="required"> 
							<label class="form-check-label" for="form21">개인정보 사용에 동의&nbsp;<br></label> 
						</div>
					</div>  -->

					<button type="submit" class="btn btn-light text-primary">회원가입&nbsp;</button>

				</form:form>
			</div>
			</div>
		</div>
	</div>

	<!-- footer -->
	<%@ include file="/WEB-INF/views/template/footer.jsp" %>


	<!-- ===================================================== -->


	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
		integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
		integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
		crossorigin="anonymous"></script>

	<script>
		function phoneForm(target) {
			let result = target.value
				.replace(/[^0-9]/g, '');
			
			// 넘어가면 자르기
			if (result.length > 11){
				
				result = result.substring(11, -1)
			}

			target.value = result
				.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1 $2 $3`);
		}
	</script>
</body>



</html>