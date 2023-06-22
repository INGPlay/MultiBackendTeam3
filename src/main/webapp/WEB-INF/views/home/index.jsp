<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- PAGE settings -->
<link rel="icon"
	href="https://templates.pingendo.com/assets/Pingendo_favicon.ico">
<title>trip</title>
<meta name="description"
	content="Free Bootstrap 4 Pingendo Smke template single landing page team">
<meta name="keywords" content="Pingendo smke free template bootstrap 4">
<!-- CSS dependencies -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
	type="text/css">
<link rel="stylesheet" href="/resources/colorful.css">
<!-- Script: Navbar on-top -->
<script src="/js/navbar-ontop.js"></script>
</head>

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
				<div class="col-lg-8 col-md-10 mx-auto" style="">
					<h1 class="mb-4 display-3">Trip Over</h1>
					<p class="lead mb-5">I can fly away Fly always always always
						Take me to new world anywhere 어디든 답답한 이 곳을 벗어 나기만 하면 Shining light
						light 빛나는 my youth 자유롭게 fly fly 나 숨을 셔 which I enjoy with my whole
						heart.</p>
					<a href="#" class="btn btn-lg btn-primary mx-1">LOGIN</a>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="mx-auto col-lg-6 col-md-8 col-10">
				<a href="#mission"><i class="d-block fa fa-angle-down fa-2x"></i></a>
			</div>
		</div>
	</div>



	<!-- 로그인 등록 또는 로그인 -->
	<div class="py-5">
		<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h2 class="mb-3">REGISTRATION</h2>




					<form class="">

						<div class="form-group">
							<label>Email address</label>
							<input type="email"
								class="form-control" placeholder="Enter email"> <small
								class="form-text text-muted">철통 정보 보안  </small>
						</div>
						<div class="form-group">
							<label>Password</label> <input type="password"
								class="form-control" placeholder="">
						</div>
						<button type="submit" class="btn btn-primary">Submit</button>
					</form>
				</div>
			</div>
		</div>
	</div>



	<!-- 게시판 choose -->
	<div class="py-5 bg-light" id="what">
		<div class="container">
			<div class="row text-center">
				<div class="col-md-10 mx-auto px-4">
					<h2 class="text-muted mb-4">Review Board</h2>
					<div class="row text-left">



						<!-- 	<div class="text-center col-3">
									<div class="col-md-2 col-4">

									</div>
								</div> -->
						<div class="p-3 col-lg-4 col-md-6">
							<div class="row mb-3">
								<div
									class="align-self-center d-flex align-items-center px-0 px-md-2 col-9">
									<div>
										<a href="#"><img src="resources/source/dd.png"
											width="30px" height="60px" title="여행지"></a>
									</div>
									<h5 class="mb-0">
										<b>&nbsp; &nbsp;여&nbsp;행</b>
									</h5>

								</div>

								<br>
								<p class="text-muted">전국 최고의 여행 리뷰에 참여해보세요</p>

							</div>
						</div>
						<div class="p-3 col-lg-4 col-md-6">
							<div class="row mb-3">
								<div class="text-center col-3"></div>
								<div
									class="align-self-center d-flex align-items-center px-0 px-md-2 col-9">
									<div>
										<a href="#"><img src="resources/source/hotel.png"
											width="100px" height="100px" title="여행지"></a>
									</div>
									<h5 class="mb-0">
										<b>&nbsp; 숙&nbsp; 소 </b>
									</h5>

								</div>

								<p class="text-muted">여행지 숙소들을 리뷰를 보고 본인의 리뷰도 작성해 보세요  </p>
							</div>
						</div>
						<div class="p-3 col-lg-4">
							<div class="row mb-3">

								<div class="text-left col-3">

									<div
										class="align-self-center d-flex align-items-center px-0 px-md-2 col-9">
										<div>
											<a href="#"><img src="resources/source/root.png"
												width="100px" height="100px" title="여행지"></a>
										</div>
										<h5 class="mb-0">
											<b>&nbsp; 경&nbsp;로 </b>
										</h5>
									</div>
								</div>
								<p class="text-muted">
								추천되는 경로들 코스들의 리뷰를 볼수있습니다. </p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>



		<!--리뷰 랭킹  -->

		<div class="py-5">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h2 class="mb-3">BEST REVIEW</h2>
						<ul class="list-group">
							<li
								class="list-group-item d-flex justify-content-between align-items-center">
								여행리뷰 <span class="badge badge-primary badge-pill">14</span>
							</li>
							<li
								class="list-group-item d-flex justify-content-between align-items-center">
								숙소리뷰 <span class="badge badge-primary badge-pill">2</span>
							</li>
							<li
								class="list-group-item d-flex justify-content-between align-items-center">
								경로 리뷰 <span class="badge badge-primary badge-pill">1</span>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>






		<!--  지도화면과  나의 경로 -->
			<div class="container">
		<div class="row text-center">
				<div class="col-md-10 mx-auto px-4">
				<h2 class="text-muted mb-4">My Map and MyPlan</h2>
			</div>
		</div>
		<table>
			<tr>
				<td>
					<!--  지도화면 -->
					<div class="py-5">
						<div class="container">
							<div class="row">
								<div class="col-md-12" style="">
									<iframe width="800px" height="400"
										src="https://maps.google.com/maps?q=New%20York&amp;z=14&amp;output=embed"></iframe>
								</div>
							</div>
						</div>
					</div>
				</td>

				<td>
					<!-- 경로-->
					<div class="py-5">
						<div class="container">
							<div class="row">
								<div class="col-md-12">

									<div class="list-group" style="">
										<a href="#"
											class="list-group-item list-group-item-action active"
											type=" button"> 나의 여행 플랜 </a> <a href="#"
											class="list-group-item list-group-item-action"> 첫번째 플랜 </a> <a
											href="#" class="list-group-item list-group-item-action">
											두번째 플랜 </a> <a href="#"
											class="list-group-item list-group-item-action"> 세번째 플랜 </a>
										<!--  <a href="#" class="list-group-item list-group-item-action disabled">내가 쓴 플래너 네번째 </a> -->
									</div>
								</div>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
</div>



		<!--여행지  -->
		<div class="py-5 section bg-light">
			<div class="container p-3">
				<div class="row align-items-center mb-4">
					<div class="col-lg-3">
						<h2 class="mb-3">추천 여행지</h2>
					</div>
					<div class="pl-lg-4 col-lg-8  mb-2">
						<p class="lead mb-0 text-muted">전국 추천 최고의 여행지를 제공해 드리고 있습니다</p>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12"></div>
				</div>
				<div class="row d-flex align-items-center justify-content-between">
					<div class="col-md-2 col-4">
						<img class="center-block img-fluid d-block"
							src="resources/assets/team/logo_4.png">
					</div>
					<div class="col-md-2 col-4">
						<img class="center-block img-fluid d-block"
							src="resources/assets/team/logo_2.png">
					</div>
					<div class="col-md-2 col-4">
						<img class="center-block img-fluid d-block"
							src="resources/assets/team/logo_1.png">
					</div>
					<div class="col-md-2 col-4">
						<img class="center-block img-fluid d-block"
							src="resources/assets/team/logo_3.png">
					</div>
					<div class="col-md-2 col-4">
						<img class="center-block img-fluid d-block"
							src="resources/assets/team/logo_5.png">
					</div>
					<div class="col-md-2 col-4">
						<img class="center-block img-fluid d-block"
							src="resources/assets/team/logo_6.png">
					</div>
				</div>
			</div>
		</div>


		<!-- 숙소 -->

		<div class="py-5 text-center" id="who">
			<div class="container">
				<div class="row">
					<div class="col-md-12"></div>
				</div>
				<div class="row align-items-center mb-4">
					<div class="col-lg-3">
						<h2 class="mb-4">추천 숙소</h2>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 px-4 mb-4 col-6 px-lg-5">
						<a data-toggle="modal" data-target=".yellow" href="">
							<div class="card project">
								<img class="card-img rounded-circle"
									src="assets/team/yellow.jpg" alt="Card image">
								<div class="card-body p-0">
									<p class="mt-3 mb-1">
										<b>호텔</b>
									</p>
									<p class="text-muted m-0">
										<small>지역</small>
									</p>
								</div>
							</div>
						</a>
					</div>
					<div class="col-md-4 px-4 mb-4 col-6 px-lg-5">
						<a data-toggle="modal" data-target=".cyan" href="">
							<div class="card project">
								<img class="card-img rounded-circle" src="resources/assets/team/cyan.jpg"
									alt="Card image">
								<div class="card-body p-0">
									<p class="mt-3 mb-1">
										<b>리조트(콘도) </b>
									</p>
									<p class="text-muted m-0">
										<small>지역선택 </small>
									</p>
								</div>
							</div>
						</a>
					</div>
					<div class="col-md-4 px-4 mb-4 col-6 px-lg-5">
						<a data-toggle="modal" data-target=".purple" href="">
							<div class="card project">
								<img class="card-img rounded-circle"
									src="assets/team/purple.jpg" alt="Card image">
								<div class="card-body p-0">
									<p class="mt-3 mb-1">
										<b>풀빌라(임대별장)</b>
									</p>
									<p class="text-muted m-0">
										<small>지역</small>
									</p>
								</div>
							</div>
						</a>
					</div>
					<div class="col-md-4 px-4 mb-4 col-6 px-lg-5">
						<a data-toggle="modal" data-target=".red" href="">
							<div class="card project">
								<img class="card-img rounded-circle" src="resources/assets/team/red.jpg"
									alt="Card image">
								<div class="card-body p-0">
									<p class="mt-3 mb-1">
										<b>레지던스</b>
									</p>
									<p class="text-muted m-0">
										<small>지역 </small>
									</p>
								</div>
							</div>
						</a>
					</div>

					<div class="col-md-4 px-4 mb-4 col-6 px-lg-5">
						<a data-toggle="modal" data-target=".white" href="">
							<div class="card project">
								<img class="card-img rounded-circle" src="resources/assets/team/white.jpg"
									alt="Card image">
								<div class="card-body p-0">
									<p class="mt-3 mb-1">
										<b>키즈숙소</b>
									</p>
									<p class="text-muted m-0">
										<small>지역</small>
									</p>
								</div>
							</div>
						</a>
					</div>
					<div class="col-md-4 px-4 mb-4 col-6 px-lg-5">
						<a data-toggle="modal" data-target=".blue" href="">
							<div class="card project">
								<img class="card-img rounded-circle" src="resources/assets/team/blue.jpg"
									alt="Card image">
								<div class="card-body p-0">
									<p class="mt-3 mb-1">
										<b>펫과 함께하는 숙소 </b>
									</p>
									<p class="text-muted m-0">
										<small>지역 </small>
									</p>
								</div>
							</div>
						</a>
					</div>
				</div>
			</div>
		</div>







		<div class="text-center py-5">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h2 class="text-muted">My Page</h2>
					</div>
				</div>
				<div class="row">
					<div class="text-right col-md-6">
						<div class="row my-5">
							<div class="order-lg-2 text-center col-lg-2 col-3">
								<span class="fa-stack fa-2x"> <i
									class="fa fa-circle fa-stack-2x text-primary"></i> <i
									class="fa fa-stack-1x fa-battery-full text-white"></i>
								</span>
							</div>
							<div class="text-lg-right text-left order-lg-1 col-lg-10 col-9">
								<h4>여행 날짜  </h4>
								<p>여행 기간  7일  날짜  7월 25일 ~ 8월 7일 </p>


															</div>
						</div>
						<div class="row my-5">
							<div class="order-lg-2 text-center col-lg-2 col-3">
								<span class="fa-stack fa-2x"> <i
									class="fa fa-circle fa-stack-2x text-secondary"></i> <i
									class="fa fa-stack-1x fa-bolt text-white"></i>
								</span>
							</div>
							<div class="text-lg-right text-left order-lg-1 col-lg-10 col-9">
								<h4>여행  날씨 정보   </h4>
								<p>25일 맑음 26일 흐핌 27일 더움 29일 눈  </p>
							</div>
						</div>
						<div class="row mt-5">
							<div class="order-lg-2 text-center col-lg-2 col-3">
								<span class="fa-stack fa-2x"> <i
									class="fa fa-circle fa-stack-2x"></i> <i
									class="fa fa-stack-1x fa-bomb text-white"></i>
								</span>
							</div>
							<div class="text-lg-right text-left order-lg-1 col-lg-10 col-9">
								<h4>나의 여행지 버킷리스트 10    </h4>
								<p>
								기장해수욕장, 오시리아, 해운대 ,광안리 , 자갈치시장 , 부산깡통시장 ,김해 유적지 , 과학관

								</p>
							</div>
						</div>
					</div>
					<div class="text-left col-md-6">
						<div class="row my-5">
							<div class="text-center col-lg-2 col-3">
								<span class="fa-stack fa-2x"> <i
									class="fa fa-circle fa-stack-2x text-danger"></i> <i
									class="fa fa-stack-1x text-white fa-dot-circle-o"></i>
								</span>
							</div>
							<div class="col-lg-10 col-9">
								<h4>예약 리스트 전화 번호 </h4>
								<p>25일
								          호텔   051 -333-44444  홈페이지
								         롯데 월드 051-5555-5555  홈페이지
								         루찌    051-2222 -4444  홈페이지
								          부산 과학관  홈페이지  </p>
							</div>
						</div>
						<div class="row my-5">
							<div class="text-center col-lg-2 col-3">
								<span class="fa-stack fa-2x"> <i
									class="fa fa-circle fa-stack-2x text-info"></i> <i
									class="fa fa-stack-1x text-white fa-gamepad"></i>
								</span>
							</div>
							<div class="col-lg-10 col-9">
								<h4>예약 완료된 리스트 전화번호  </h4>
								<p>
								호텔 얘약  완료 25일부터 30일 5일간
								과학관 예약  7월25일  오후 3시



								 </p>
							</div>
						</div>
						<div class="row mt-5">
							<div class="text-center col-lg-2 col-3">
								<span class="fa-stack fa-2x"> <i
									class="fa fa-circle fa-stack-2x text-warning"></i> <i
									class="fa fa-stack-1x text-white fa-rocket"></i>
								</span>
							</div>
							<div class="col-lg-10 col-9">
								 <h4> 결제 완료된 리스트 전화번호  </h4>
								<p>
								롯데월드 051-222-5555 결제완료 200,000 신한카드
								루치  051-222-4444 결제완료  200,000 카카오
								호텔  051-222-3333  500,0000  국민카드
								   총결제  900,000



								 </p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


		<!--내 장바구니 -->
		<div class="py-5 filter-dark cover bg-fixed bg-primary"
			style="background-image: url('resources/assets/team/planes.jpg'); background-position: center bottom; background-size: cover;"
			id="mission">
			<div class="container my-auto">
				<div class="row">
					<div class="col-md-8 p-3 text-white">
						<h2 class="mb-4"> 결제 하시겠습니까 ?  </h2>
						<p class=""> 한화 리조트3박 4일 
								   총결제  900,000 원입니다 <br>
								   결제는  아래 버튼을 이용하세요 
						
						 </p>
					</div>
				</div>
			</div>
		</div>
<!--  결제 선택 창 -->
		<div class="py-5">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<div class="btn-group">
							<button class="btn btn-primary dropdown-toggle"
								data-toggle="dropdown">결제</button>
							<div class="dropdown-menu">
								<a class="dropdown-item" href="#">카드 </a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="#">무통장</a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="#">카카오뱅크</a>
								<div class="dropdown-divider"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


		<!--footer  -->
		<div class="text-white bg-dark" id="where">
			<div class="container">
				<div class="row">
					<div class="p-5 col-md-6">
						<h3>
							<b>삼시개발</b>
						</h3>
						<p class="">
							<a href="#" target="_blank">백앤드 개발 10회차 3조 <br>202303
								-202307 (CH)
							</a>
						</p>
						<p class="">
							<a href="#">back10-03@naver.com</a>
						</p>
						<p class="mb-3">
							<a href="#">backend101!&nbsp;</a>
						</p>
						<a href="#" target="_blank"><i
							class="fa d-inline fa-lg mr-3 text-white fa-linkedin"></i></a> <a
							href="#" target="_blank"><i
							class="fa fa-facebook d-inline fa-lg mr-3 text-white"></i></a>
					</div>
					<div class="p-5 col-md-6">
						<h3>Help</h3>
						<form>
							<div class="form-group">
								<input type="email" class="form-control form-control-sm"
									placeholder="Email" required="required" name="email">
							</div>
							<div class="form-group">
								<input type="text" class="form-control form-control-sm"
									id="inlineFormInput" placeholder="Subject" required="required"
									name="subject">
							</div>
							<div class="form-group">
								<textarea class="form-control p-1 form-control-sm"
									id="exampleTextarea" rows="3" placeholder="Your message"
									name="message"></textarea>
							</div>
							<button type="submit" class="btn btn-outline-light btn-sm">SUBMIT</button>
						</form>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12 mt-3">
						<p class="text-center text-muted">© Copyright 2023
							-M.C.B.D.10-03 All rights reserved.</p>
					</div>
				</div>
			</div>
		</div>

<!-- 숙소 모달 창   -->

		<div class="modal fade yellow">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header bg-light">
						<h5 class="modal-title">
							<b>Yellow</b> - CEO
						</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">×</span>
						</button>
					</div>
					<div class="modal-body bg-light">
						<div class="row">
							<div class="col-md-4 px-3">
								<img class="img-fluid d-block mx-auto rounded-circle"
									src="assets/team/yellow.jpg">
							</div>
							<div class="col-md-8 p-2">
								<p class="">
									Lorem ipsum dolor sit amet, consectetur adipisici elit, sed
									eiusmod tempor incidunt ut labore et dolore magna aliqua.&nbsp;
									<br> <br>Ut enim ad minim veniam, quis nostrud
									exercitation ullamco laboris nisi ut aliquid ex ea commodi
									consequat. Quis aute iure reprehenderit in voluptate velit esse
									cillum dolore eu fugiat nulla pariatur.
								</p>
								<a target="_blank" href="#"><i
									class="d-block  fa fa-lg fa-linkedin text-muted"></i></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade cyan">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header bg-light">
						<h5 class="modal-title">
							<b>Cyan</b> - Managing Partner
						</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">×</span>
						</button>
					</div>
					<div class="modal-body bg-light">
						<div class="row">
							<div class="col-md-4 px-3">
								<img class="img-fluid d-block mx-auto rounded-circle"
									src="assets/team/cyan.jpg">
							</div>
							<div class="col-md-8 p-2">
								<p class="">
									Lorem ipsum dolor sit amet, consectetur adipisici elit, sed
									eiusmod tempor incidunt ut labore et dolore magna aliqua.&nbsp;
									<br> <br>Ut enim ad minim veniam, quis nostrud
									exercitation ullamco laboris nisi ut aliquid ex ea commodi
									consequat. Quis aute iure reprehenderit in voluptate velit esse
									cillum dolore eu fugiat nulla pariatur.
								</p>
								<a href="#" target="_blank"><i
									class="d-block  fa fa-lg fa-linkedin text-muted"></i></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade red">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header bg-light">
						<h5 class="modal-title">
							<b>Red</b> - Vice President
						</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">×</span>
						</button>
					</div>
					<div class="modal-body bg-light">
						<div class="row">
							<div class="col-md-4 px-3">
								<img class="img-fluid d-block mx-auto rounded-circle"
									src="assets/team/red.jpg">
							</div>
							<div class="col-md-8 p-2">
								<p class="">Lorem ipsum dolor sit amet, consectetur
									adipisici elit, sed eiusmod tempor incidunt ut labore et dolore
									magna aliqua. Ut enim ad minim veniam, quis nostrud
									exercitation ullamco laboris nisi ut aliquid ex ea commodi
									consequat.</p>
								<a href="#" target="_blank"><i
									class="d-block  fa fa-lg fa-linkedin text-muted"></i></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade purple">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header bg-light">
						<h5 class="modal-title">
							<b>Purple</b> - CFO
						</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">×*********</span>
						</button>
					</div>
					<div class="modal-body bg-light">
						<div class="row">
							<div class="col-md-4 px-3">
								<img class="img-fluid d-block mx-auto rounded-circle"
									src="assets/team/purple.jpg">
							</div>
							<div class="col-md-8 p-2">
								<p class="">
									Lorem ipsum dolor sit amet, consectetur adipisici elit, sed
									eiusmod tempor incidunt ut labore et dolore magna aliqua.&nbsp;
									<br> <br>Ut enim ad minim veniam, quis nostrud
									exercitation ullamco laboris nisi ut aliquid ex ea commodi
									consequat. Quis aute iure reprehenderit in voluptate velit esse
									cillum dolore eu fugiat nulla pariatur.
								</p>
								<a href="#" target="_blank"><i
									class="d-block  fa fa-lg fa-linkedin text-muted"></i></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade white">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header bg-light">
						<h5 class="modal-title">
							<b>White</b> - Consultant
						</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">×</span>
						</button>
					</div>
					<div class="modal-body bg-light">
						<div class="row">
							<div class="col-md-4 px-3">
								<img class="img-fluid d-block mx-auto rounded-circle"
									src="assets/team/white.jpg">
							</div>
							<div class="col-md-8 p-2">
								<p class="">
									Lorem ipsum dolor sit amet, consectetur adipisici elit, sed
									eiusmod tempor incidunt ut labore et dolore magna aliqua.&nbsp;
									<br> <br>Ut enim ad minim veniam, quis nostrud
									exercitation ullamco laboris nisi ut aliquid ex ea commodi
									consequat.
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade blue">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header bg-light">
						<h5 class="modal-title">
							<b>Blue</b> - Field Researcher
						</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">×</span>
						</button>
					</div>
					<div class="modal-body bg-light">
						<div class="row">
							<div class="col-md-4 px-3">
								<img class="img-fluid d-block mx-auto rounded-circle"
									src="assets/team/blue.jpg">
							</div>
							<div class="col-md-8 p-2">
								<p class="">
									Lorem ipsum dolor sit amet, consectetur adipisici elit, sed
									eiusmod tempor incidunt ut labore et dolore magna aliqua.&nbsp;
									<br> <br>Ut enim ad minim veniam, quis nostrud
									exercitation ullamco laboris nisi ut aliquid ex ea commodi
									consequat. Quis aute iure reprehenderit in voluptate velit esse
									cillum dolore eu fugiat nulla pariatur.
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div> 



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
	<!-- Script: Smooth scrolling between anchors in the same page -->
	<script src="js/smooth-scroll.js"></script>
  <pingendo onclick="window.open('https://pingendo.com/', '_blank')" style="cursor:pointer;position: fixed;bottom: 20px;right:20px;padding:4px;background-color: #00b0eb;border-radius: 8px; width:220px;display:flex;flex-direction:row;align-items:center;justify-content:center;font-size:14px;color:white">
		멀티	백엔드 3조 &nbsp;&nbsp;<img
		src="https://pingendo.com/site-assets/Pingendo_logo_big.png"
		class="d-block" alt="백엔드3조" height="16"></pingendo>
</body>



</html>