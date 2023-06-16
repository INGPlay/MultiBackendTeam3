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
		.mapContainer{
			position: relative;
		}

		/* 커스텀 바 */
		.custom_typecontrol {position:absolute;top:10px;right:45px;overflow:hidden;width:115px;height:40px;margin:0;padding:0;z-index:1;font-size:15px;font-family:'Malgun Gothic', '맑은 고딕', sans-serif;}
		.custom_typecontrol span {display:block;width:115px;height:40px;float:left;text-align:center;line-height:30px;cursor:pointer;}
		.radius_border{border:1px solid #919191;border-radius:5px;}  

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
<body style="height: 100%;">
	  
	<!-- <main> 지우면 Sidebar 스크롤 기능 꺼짐 -->
	<main class="flex-row-reverse">

		<div class="d-flex flex-column align-items-stretch flex-shrink-0 bg-white" style="width: 380px;">
			<div class="input-group input-group-lg">
				<span onclick="window.location.href='/pathmap'" class="input-group-text" id="inputGroup-sizing-lg">←</span>

				<!-- 제목 -->
				<input type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg"
					id = "pathmapTitle" disabled>
			</div>

			<!-- 패스맵 리스트 -->
			<div class="list-group list-group-flush border-bottom scrollarea" id="userSelectListView">

			</div>

			<div class="mt-auto d-flex justify-content-center" id="submitUserSelectList">
				<button class="d-flex align-items-center flex-shrink-0 p-3 link-dark text-decoration-none border-bottom"
						style="width: 100%; justify-content: center; background-color:skyblue;">
					<!-- Submit -->
					<span class="fs-5 fw-semibold">댓글?</span>
				</button>
			</div>
		</div>


		<!-- Map이 표시될 자리 -->
		<div class="mapContainer flex-fill">
			<!-- 맵 -->
			<div id="map"></div>

			<!-- 맵 컨트롤 -->
			<div class="custom_typecontrol radius_border" style="background-color: skyblue;"> 
				<span id="boundButton" class="fw-semibold">한눈에 보기</span>
			</div>
		</div>

		<script>
			// 변수 초기화
			const container = document.getElementById('map');
			let options = {
				center: new kakao.maps.LatLng(33.450701, 126.570667),
				level: 3
			};

			const map = new kakao.maps.Map(container, options);

			// 최대 확대수준
			map.setMaxLevel(13)

			// 줌 컨트롤
			const zoomControl = new kakao.maps.ZoomControl();
			map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

			// 선그리기
			const polyline = new kakao.maps.Polyline({
				map: map,
				strokeWeight: 5, // 선의 두께 입니다
				strokeColor: '#FFAE00', // 선의 색깔입니다
				strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
				strokeStyle: 'solid' // 선의 스타일입니다
			});

			// 유저가 선택한 장소 리스트
			const userSelectList = [];

			// 백에서 받는 pathId
			const pathId = ${pathId}

			// path의 mark 가져오기
			initUserSelectList(pathId);

			function initUserSelectList(pathId){
				$.ajax({
					url : "/api/pathmap/" + pathId,
					type : "GET",
					contentType: "application/json",
				}).done(response => {
					let title = response["title"]
					let infoList = response["infoList"]

					document.getElementById("pathmapTitle").value = title

					infoList.forEach(info => {
						userSelectList.push(info)
					})
					
					console.log(infoList)

					// 초기화 함수
					updatePage();

				}).fail(error => {
					alert("error")
				})
			}

			// callback -> 클릭할 경우 발생하는 함수
			function promiseMarking(map, posX, posY, callback){
				return new Promise(function(resolve, reject){
					resolve(marking(map, posX, posY, callback));
				})
			}

			function marking(map, posX, posY, callback){
				let marker = new kakao.maps.Marker({
					map: map,
					position: new kakao.maps.LatLng(posY, posX)
				});

				kakao.maps.event.addListener(marker, "click", callback);

				return marker
			}

			// 한눈에 보기 버튼
			document.getElementById("boundButton").addEventListener("click", setMapBounds)

			// 인포윈도우를 띄움
			function responseinfoWindow(map, posX, posY, content){

				let infoWindow = new kakao.maps.InfoWindow({
					map: map,
					position: new kakao.maps.LatLng(posY, posX),
					content: content,
					removable: true
				})
			}

			// 인포윈도우에서 추가를 선택했을 때 실행
			function addUserSelectList(info){
				
				if (isNotDuplicated(info)){
					userSelectList.push(info);
				}
				viewUserSelectList()

				// 함수 내 함수
				function isNotDuplicated(newInfo){
					for (let info of userSelectList){
						if (info["contentId"] === newInfo["contentId"]){
							return false;
						}
					}

					return true;
				}
			}


			function updatePage(){
				markUserSelectList();
				viewUserSelectList();
				setMapBounds()
				setPolyLine()
			}

			function markUserSelectList(){
				userSelectList.forEach(info => {
					console.log("좌표: " + info["posX"] + ", " + info["posY"])
					promiseMarking(map, info["posX"], info["posY"], function(){

							let content = "\
								<div class='container pt-1 pb-1' style='background-color: white; outline: solid 1px black; width: 320px;'> \
									<div class='d-flex flex-row align-items-center'> \
										<div class='flex-shrink-0'> \
											<img src= '" + info["firstImageURI2"] + "'  style='width:150px; height:auto;'> \
										</div> \
										<div class='flex-grow-1 ms-1'>\
											<p class='h5 fw-bold'>" + info["title"] + "</p> \
											<p class='text-muted lh-sm font-monospace' style='font-size:13px;'>" + info["contentType"] + "</p> \
											<p class='font-monospace' style='font-size:14px;'>" + info["tel"] + "</p> \
										</div> \
									</div> \
								</div> \
							"; 

							// 백틱 템플릿은 왠지 모르게 안된다
							/*
							`\
							<div> \
								<h5>${info["title"]}</h5>
								<p>${info["addr1"]} ${info["addr2"]}</p> \
								<p>${info["tel"]}</p> \
							</div> \
							`;
							*/

							responseinfoWindow(map, info["posX"], info["posY"], content)
						})
						.then(marker => {

							// 임시
						})
						.catch(error => {
							console.log("Error");
						})
					})
			}

			function viewUserSelectList(){

				let userSelectListView = document.getElementById("userSelectListView")
				userSelectListView.innerHTML = "";

				for (let i = 0 ; i < userSelectList.length; i++){

					let info = userSelectList[i]
					// 가져올 때는 .userSelectContainer로 가져오기
					let listTemplate = " \
						<a href='#' class='list-group-item list-group-item-action active py-3 lh-tight userSelectContainer' aria-current='true'> \
							<div class='d-flex flex-row align-items-center'> \
							\
								<div class='flex-shrink-0'> \
									<img src='"+ info["firstImageURI2"] +"' style='width:160px; height:auto;'> \
								</div> \
								\
								<div class='flex-grow-1 ms-1'> \
									<div class='d-flex w-100 align-items-center justify-content-between'> \
										<strong class='mb-1'>" + info["title"] + "</strong> \
										<small>" + info["contentType"] + "</small> \
									</div> \
									<div class='col-10 mb-1 small'>" + info["addr1"] + " " + info["addr2"] + "</div> \
								</div> \
							</div> \
						</a> \
					"
					
					userSelectListView.innerHTML += listTemplate
				}

			}

			// userSelectList의 특정 인덱스의 값을 삭제
			function deleteUserSelectByIndex(index){
				userSelectList.splice(index, 1)
				viewUserSelectList()
			}

			function upUserSelect(index){
				let temp = userSelectList[index]
				userSelectList[index] = userSelectList[index -1]
				userSelectList[index-1] = temp

				viewUserSelectList()
			}

			function downUserSelect(index){
				let temp = userSelectList[index]
				userSelectList[index] = userSelectList[index + 1]
				userSelectList[index+1] = temp

				viewUserSelectList()
			}

			document.getElementById("submitUserSelectList").addEventListener("click", () => {
				let title = document.getElementById('pathmapTitle').value;

				let data = {
					"title" : JSON.stringify(title),
					"request" : JSON.stringify(userSelectList)
				}

				console.log("제출")
				
				$.ajax({
					url: "/api/pathmap",
					type: 'POST',
					dataType: "json",
					data : data
				})
				.done(function(response) {
					// { "response" : "OK" }
					console.log(response["response"])
					window.location.replace("/pathmap");
				})
				.fail(function(error) {
					console.log("Error : " + error)
				});
			})

			// 등록된 좌표 중간에서 다 보여주도록 함
			function setMapBounds(){

				// 아무것도 없이 바운드 설정하면 지도 깨짐
				if (userSelectList.length <= 0){
					return;
				}

				let bounds = new kakao.maps.LatLngBounds(); 
				userSelectList.forEach(info => {
					bounds.extend(
						new kakao.maps.LatLng(info["posY"], info["posX"])
					)
				})

				map.setBounds(bounds)
			}

			function setPolyLine(){

				const linePath = []
				userSelectList.forEach(info => {
					linePath.push(new kakao.maps.LatLng(info["posY"], info["posX"]))
				})

				polyline.setPath(linePath)
			}
		</script>
	</main>

	<script src="<c:url value='/js/bootstrap.bundle.min.js' />" type="text/javascript"></script>
    <script src="<c:url value='/js/sidebars.js' />" type="text/javascript"></script>

</body>
</html>