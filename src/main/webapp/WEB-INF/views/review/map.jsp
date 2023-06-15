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

    <script type="text/javascript" src='//dapi.kakao.com/v2/maps/sdk.js?appkey=<spring:message code="keys.kakao.map" javaScriptEscape="true" />&libraries=services'></script>

</head>
<body>

	<div id="map" style="width:500px;height:400px;"></div>
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
			var latlng = mouseEvent.latLng; 
			
			// 마커 위치를 클릭한 위치로 옮깁니다
			marker.setPosition(latlng);
			
			let temp = {"위도" : latlng.getLat(), "경도" : latlng.getLng()} 
			
			alert(temp["위도"] + " " + temp["경도"]);
			
			// tempList.push(temp);

			// tempList.forEach(function(f){
			// 	console.log(f["위도"] + " " + f["경도"])
			// })
		});
	</script>

</body>
</html>