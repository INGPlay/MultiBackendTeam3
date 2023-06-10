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

	<script type="text/javascript" src='//dapi.kakao.com/v2/maps/sdk.js?appkey=<spring:message code="keys.kakao.map" javaScriptEscape="true" />&libraries=services'></script>


	<style>
		/* 지도 전체 */
		.mapContainer{
			/* width: 100%;
			height: 100%; */
		}

		#map{
			width: 100%;
			height: 100%;
		}

		/* 부트스트랩 사이드바 */
		.bd-placeholder-img {
		  font-size: 1.125rem;
		  text-anchor: middle;
		  -webkit-user-select: none;
		  -moz-user-select: none;
		  user-select: none;
		}
  
		@media (min-width: 768px) {
		  .bd-placeholder-img-lg {
			font-size: 3.5rem;
		  }
		}
	</style>

</head>
<body>
	  
	<!-- <main> 지우면 Sidebar 스크롤 기능 꺼짐 -->
	<main class="flex-row-reverse">

		<div class="d-flex flex-column align-items-stretch flex-shrink-0 bg-white" style="width: 380px;">
			<a href="/" class="d-flex align-items-center flex-shrink-0 p-3 link-dark text-decoration-none border-bottom">
			<svg class="bi me-2" width="30" height="24"><use xlink:href="#bootstrap"/></svg>
			<span class="fs-5 fw-semibold">List group</span>
			</a>
			<div class="list-group list-group-flush border-bottom scrollarea">
			<a href="#" class="list-group-item list-group-item-action active py-3 lh-tight" aria-current="true">
				<div class="d-flex w-100 align-items-center justify-content-between">
				<strong class="mb-1">List group item heading</strong>
				<small>Wed</small>
				</div>
				<div class="col-10 mb-1 small">Some placeholder content in a paragraph below the heading and date.</div>
			</a>
			<a href="#" class="list-group-item list-group-item-action py-3 lh-tight">
				<div class="d-flex w-100 align-items-center justify-content-between">
				<strong class="mb-1">List group item heading</strong>
				<small class="text-muted">Tues</small>
				</div>
				<div class="col-10 mb-1 small">Some placeholder content in a paragraph below the heading and date.</div>
			</a>
			<a href="#" class="list-group-item list-group-item-action py-3 lh-tight">
				<div class="d-flex w-100 align-items-center justify-content-between">
				<strong class="mb-1">List group item heading</strong>
				<small class="text-muted">Mon</small>
				</div>
				<div class="col-10 mb-1 small">Some placeholder content in a paragraph below the heading and date.</div>
			</a>
		
			<a href="#" class="list-group-item list-group-item-action py-3 lh-tight" aria-current="true">
				<div class="d-flex w-100 align-items-center justify-content-between">
				<strong class="mb-1">List group item heading</strong>
				<small class="text-muted">Wed</small>
				</div>
				<div class="col-10 mb-1 small">Some placeholder content in a paragraph below the heading and date.</div>
			</a>
			<a href="#" class="list-group-item list-group-item-action py-3 lh-tight">
				<div class="d-flex w-100 align-items-center justify-content-between">
				<strong class="mb-1">List group item heading</strong>
				<small class="text-muted">Tues</small>
				</div>
				<div class="col-10 mb-1 small">Some placeholder content in a paragraph below the heading and date.</div>
			</a>
			<a href="#" class="list-group-item list-group-item-action py-3 lh-tight">
				<div class="d-flex w-100 align-items-center justify-content-between">
				<strong class="mb-1">List group item heading</strong>
				<small class="text-muted">Mon</small>
				</div>
				<div class="col-10 mb-1 small">Some placeholder content in a paragraph below the heading and date.</div>
			</a>
			<a href="#" class="list-group-item list-group-item-action py-3 lh-tight" aria-current="true">
				<div class="d-flex w-100 align-items-center justify-content-between">
				<strong class="mb-1">List group item heading</strong>
				<small class="text-muted">Wed</small>
				</div>
				<div class="col-10 mb-1 small">Some placeholder content in a paragraph below the heading and date.</div>
			</a>
			<a href="#" class="list-group-item list-group-item-action py-3 lh-tight">
				<div class="d-flex w-100 align-items-center justify-content-between">
				<strong class="mb-1">List group item heading</strong>
				<small class="text-muted">Tues</small>
				</div>
				<div class="col-10 mb-1 small">Some placeholder content in a paragraph below the heading and date.</div>
			</a>
			<a href="#" class="list-group-item list-group-item-action py-3 lh-tight">
				<div class="d-flex w-100 align-items-center justify-content-between">
				<strong class="mb-1">List group item heading</strong>
				<small class="text-muted">Mon</small>
				</div>
				<div class="col-10 mb-1 small">Some placeholder content in a paragraph below the heading and date.</div>
			</a>
			<a href="#" class="list-group-item list-group-item-action py-3 lh-tight" aria-current="true">
				<div class="d-flex w-100 align-items-center justify-content-between">
				<strong class="mb-1">List group item heading</strong>
				<small class="text-muted">Wed</small>
				</div>
				<div class="col-10 mb-1 small">Some placeholder content in a paragraph below the heading and date.</div>
			</a>
			<a href="#" class="list-group-item list-group-item-action py-3 lh-tight">
				<div class="d-flex w-100 align-items-center justify-content-between">
				<strong class="mb-1">List group item heading</strong>
				<small class="text-muted">Tues</small>
				</div>
				<div class="col-10 mb-1 small">Some placeholder content in a paragraph below the heading and date.</div>
			</a>
			<a href="#" class="list-group-item list-group-item-action py-3 lh-tight">
				<div class="d-flex w-100 align-items-center justify-content-between">
				<strong class="mb-1">List group item heading</strong>
				<small class="text-muted">Mon</small>
				</div>
				<div class="col-10 mb-1 small">Some placeholder content in a paragraph below the heading and date.</div>
			</a>
			</div>
		</div>


		<!-- Map이 표시될 자리 -->
		<div class="mapContainer flex-fill">
			<div id="map"></div>
		</div>

		<script>
			let container = document.getElementById('map');
			let options = {
				center: new kakao.maps.LatLng(33.450701, 126.570667),
				level: 3
			};

			let map = new kakao.maps.Map(container, options);

			// 지도를 클릭한 위치에 표출할 마커입니다
			let marker = new kakao.maps.Marker({ 
				// 지도 중심좌표에 마커를 생성합니다 
				position: map.getCenter() 
			}); 

			// 지도에 마커를 표시합니다
			marker.setMap(map);


			let tempList = [];
			// 지도에 클릭 이벤트를 등록합니다
			// 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
			kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        
				
				// 클릭한 위도, 경도 정보를 가져옵니다 
				var pos = mouseEvent.latLng; 
				
				// 마커 위치를 클릭한 위치로 옮깁니다
				marker.setPosition(pos);
				
				let temp = {"경도(X)" : pos.getLng(), "위도(Y)" : pos.getLat()} 
				
				let params = {
						"posX":pos.getLng(),
						"posY":pos.getLat(),
						"radius":10000,
						"pageSize":100,
						"pageNo":1
					}
				
				$.ajax({
					url : "/api/tour/location",
					type : "GET",
					data : params,
					contentType: "application/json",
					dataType : "json"
				}).done((data) => {
					alert(data["response"][0]["title"])
					
					let response = data["response"]
					response.forEach(element => {
						marking(map, element["posX"], element["posY"])
					});

				}).fail((exception) => {
					
					alert(exception)
				})
				
				// tempList.push(temp);

				// tempList.forEach(function(f){
				// 	console.log(f["위도"] + " " + f["경도"])
				// })
			});

			function marking(map, posX, posY){
				var marker = new kakao.maps.Marker({
					map: map,
					position: new kakao.maps.LatLng(posY, posX)
				});

				return marker
			}
		</script>
	</main>

	<script src="<c:url value='/js/bootstrap.bundle.min.js' />" type="text/javascript"></script>
    <script src="<c:url value='/js/sidebars.js' />" type="text/javascript"></script>

</body>
</html>