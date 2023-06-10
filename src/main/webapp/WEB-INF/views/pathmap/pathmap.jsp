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
		/* 지도 관련 */

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

			let markInfoList = [];
			
			// 드래그가 끝났을 때
			kakao.maps.event.addListener(map, 'dragend', function() {

				let pos = map.getCenter()
				console.log("경도(X) : " +  pos.getLng(), "위도(Y) : " + pos.getLat()) 
				
				let params = {
						"posX":pos.getLng(),
						"posY":pos.getLat(),
						"radius":getRadius(map.getLevel()),
						"pageSize":1000,			// 값이 너무 크면 느려질 수 있음
						"pageNo":1
					}
				
				markBasedLocation(params, markInfoList);
			});

			// 확대 수준이 변경된다면
			kakao.maps.event.addListener(map, 'zoom_changed', function() {

				let pos = map.getCenter()
				console.log("경도(X) : " +  pos.getLng(), "위도(Y) : " + pos.getLat()) 

				let params = {
						"posX":pos.getLng(),
						"posY":pos.getLat(),
						"radius":getRadius(map.getLevel()),
						"pageSize":1000,
						"pageNo":1
					}
					
				markBasedLocation(params, markInfoList);
			});

			// 지도 확대에 따라 동적으로 범위 조절
			function getRadius(mapLevel){

				let result = 0;
				if (mapLevel < 4) {
					result = 1000;
				}
				else if (mapLevel < 5){
					result = 2000;
				} else if (mapLevel < 6){
					result = 3000;
				} else if (mapLevel < 7){
					result = 6000;
				} else if (mapLevel < 8){
					result = 12000;
				} else {
					result = 20000;
				}

				return result;
			}


			function markBasedLocation(params, markInfoList){

				$.ajax({
					url : "/api/tour/location",
					type : "GET",
					data : params,
					contentType: "application/json",
					dataType : "json"
				}).done((response) => {
					// 이전의 마크 초기화
					markInfoList.forEach(markInfo => {
						let mark = markInfo["mark"]
						mark.setMap(null)
					})
					
					// 새로운 마크 표시
					response.forEach(info => {

						// 동기
						// let mark = marking(map, info["posX"], info["posY"], function(){
						// 	alert(info["title"])
						// })

						// 비동기
						promiseMarking(map, info["posX"], info["posY"], function(){
							alert(info["title"])
						})
						.then((marker) => {
							let markInfo = {
								"mark" : marker,
								"info" : info
							}
							markInfoList.push(markInfo)
						})
						.catch((error) => console.log("Error"))



					});

				}).fail((error) => {
					// {"readyState":4,"responseText":"{\"status\":404,\"message\":\"NOT FOUND\"}","responseJSON":{"status":404,"message":"NOT FOUND"},"status":404,"statusText":"error"}
					let response = error["responseJSON"];
					console.log(response["message"])
				})

			}

			function promiseMarking(map, posX, posY, callback){
				return new Promise(function(resolve, reject){
					resolve(marking(map, posX, posY, callback));
				})
			}

			function marking(map, posX, posY, callback){
				var marker = new kakao.maps.Marker({
					map: map,
					position: new kakao.maps.LatLng(posY, posX)
				});

				kakao.maps.event.addListener(marker, "click", callback);

				return marker
			}

		</script>
	</main>

	<script src="<c:url value='/js/bootstrap.bundle.min.js' />" type="text/javascript"></script>
    <script src="<c:url value='/js/sidebars.js' />" type="text/javascript"></script>

</body>
</html>