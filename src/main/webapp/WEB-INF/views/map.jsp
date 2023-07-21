<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
    <script type="text/javascript" src='//dapi.kakao.com/v2/maps/sdk.js?appkey=<spring:message code="keys.kakao.map" javaScriptEscape="true" />&libraries=services'></script>

	<style>
		.mapContainer{
			position:absolute;
			width: 100%;
			height: 100%;
		}

		#map{
			width: 100%;
			height: 100%;
		}
	</style>

</head>
<body>

	<!-- Map이 표시될 자리 -->
	<div class="mapContainer">
		<div id="map"></div>
	</div>


	<script>
		let container = document.getElementById('map');
		let options = {
			center: new kakao.maps.LatLng(33.450701, 126.570667),
			level: 3
		};


		let map = new kakao.maps.Map(container, options);

		for (let i = 0; i < 15; i++){
			
			let pos = new kakao.maps.LatLng(33.450701, 126.570667 + 0.2*i)


			setSequenceMarker(i, pos)
		}

		function setSequenceMarker(index, position){
			// 세 번째 파라메터로 options를 사용.
			let icon = new kakao.maps.MarkerImage(
				'http://t1.daumcdn.net/localimg/localimages/07/2012/img/marker_normal.png',
				new kakao.maps.Size(43, 52),
				{
					spriteOrigin: new kakao.maps.Point(315, 52 * index),    
					spriteSize: new kakao.maps.Size(644, 946) 
				}
			);


			// 지도를 클릭한 위치에 표출할 마커입니다
			let marker = new kakao.maps.Marker({ 
				// 지도 중심좌표에 마커를 생성합니다 
				position: position,
				image: icon
			}); 

			// 지도에 마커를 표시합니다
			marker.setMap(map);
		}
		
	</script>
</body>
</html>