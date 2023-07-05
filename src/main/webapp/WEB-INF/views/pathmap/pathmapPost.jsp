<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>플래너</title>

	<%@ include file="/WEB-INF/views/template/staticTemplate.jsp" %>

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
		.custom_oneshot {position:absolute;bottom:10px;right:10px;overflow:hidden;width:115px;height:40px;margin:0;padding:0;z-index:1;font-size:15px;font-family:'Malgun Gothic', '맑은 고딕', sans-serif;}
		.custom_oneshot span {display:block;width:115px;height:40px;float:left;text-align:center;line-height:30px;cursor:pointer;}

		.custom_contentType {position:absolute;top:10px;left:10px;width:520px;height:40px;margin:0;padding:0;z-index:1;font-size:15px;font-family:'Malgun Gothic', '맑은 고딕', sans-serif;}
		.custom_contentType span {display:block;width:64px;height:40px;float:left;text-align:center;line-height:30px;cursor:pointer;}
		.custom_contentType .selected {background-color: burlywood;}

		.custom_searchBar {position:absolute;top:55px;left:10px;overflow:hidden;width:350px;height:40px;margin:0;padding:0;z-index:1;font-size:15px;font-family:'Malgun Gothic', '맑은 고딕', sans-serif;}
		.custom_searchBar span {display:block;width:350px;height:40px;float:left;text-align:center;line-height:30px;cursor:pointer;}
		.select {width: 80px;}

		.custom_alert {position:absolute;top:95px;left:10px;overflow:hidden;width:520px;height:20px;margin:0;padding:0;z-index:1;font-size:15px;font-family:'Malgun Gothic', '맑은 고딕', sans-serif;pointer-events: none;}
		.custom_alert span {display:block;width:520px;height:20px;float:left;text-align:left;cursor:pointer;}

		.radius_border{border:1px solid #919191;border-radius:5px;} 

		/* 오버레이 */
		.custom_overlay{pointer-events: none;}

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

	<!-- Info Modal -->
	<div class='modal fade' id='place' tabindex='-1' aria-hidden='true'>
		<div class='modal-dialog modal-dialog-scrollable modal-lg'>
			<div class='modal-content'>
			<div class='modal-header'>
				<h5 class='modal-title' id='exampleModalLabel'>장소 정보 <small id="modal-sub" class="link-secondary"></small></h5>
				<button type='button' class='btn-close' data-bs-dismiss='modal' aria-label='Close'></button>
			</div>
			<div class='modal-body'>
				<table class='table table table-hover'>
					<thead>
						<tr>
							<th scope='col'>항목</th>
							<th scope='col'>정보</th>
						</tr>
					</thead>
					<tbody id = 'placeRow'>
					</tbody>
				</table>
				<div class='d-flex justify-content-center'>
					<div class='spinner-border text-info' role='status' id='placeSpinner'>
						<span class='visually-hidden'>Loading...</span>
					</div>
				</div>
			</div>
			<div class='modal-footer'>
				<button type='button' class='btn btn-secondary' data-bs-dismiss='modal'>닫기</button>
			</div>
			</div>
		</div>
	</div>
	<!-- Weather Modal -->
	<div class='modal fade' id='weather' tabindex='-1' aria-hidden='true'>
		<div class='modal-dialog modal-dialog-scrollable modal-xl'>
			<div class='modal-content'>
			<div class='modal-header'>
				<h5 class='modal-title' id='exampleModalLabel'>날씨 정보</h5>
				<button type='button' class='btn-close' data-bs-dismiss='modal' aria-label='Close'></button>
			</div>
			<div class='modal-body'>
				<table class='table table table-hover'>
					<thead>
						<tr>
							<th scope='col'>예측시간</th>
							<th scope='col'>일 최저기온</th>
							<th scope='col'>일 최고기온</th>
							<th scope='col'>시간 평균 온도</th>
							<th scope='col'>하늘형태</th>
							<th scope='col'>강수형태</th>
							<th scope='col'>강수확률</th>
							<th scope='col'>강수량</th>
							<th scope='col'>강설량</th>
						</tr>
					</thead>
					<tbody id = 'wheatherRow'>
					</tbody>
				</table>
				<div class='d-flex justify-content-center'>
					<div class='spinner-border text-info' role='status' id='wheatherSpinner'>
						<span class='visually-hidden'>Loading...</span>
					</div>
				</div>
			</div>
			<div class='modal-footer'>
				<button type='button' class='btn btn-secondary' data-bs-dismiss='modal'>닫기</button>
			</div>
			</div>
		</div>
	</div>
	  
	<!-- <main> 지우면 Sidebar 스크롤 기능 꺼짐 -->
	<main class="flex-row">

		<!-- Map이 표시될 자리 -->
		<div class="mapContainer flex-fill">
			<!-- 맵 -->
			<div id="map"></div>

			<!-- 오른쪽 아래 한눈에 보기 버튼 -->
			<div class="custom_oneshot radius_border main_color"> 
				<span class="fw-semibold" onclick="setUserSelectListBounds()">한눈에 보기</span>
			</div>

		</div>


		<!-- 오른쪽 사이드바 -->
		<div class="d-flex flex-column align-items-stretch bg-white" style="width: 420px;">

			<!-- 제목 작성 -->
			<div class="input-group input-group-lg">
				<span onclick="window.location.href='/pathmap'" class="input-group-text main_color" id="inputGroup-sizing-lg">←</span>
				<input type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg"
					id = "pathmapTitle" disabled>

				<!-- 로그인한 사용자만 수정 가능 -->
				<sec:authorize access="isAuthenticated()">
					<c:if test="${authorizedAuthor eq 'true'}">
						<span onclick="window.location.href='/pathmap/update/' + ${pathId}" 
							class="input-group-text main_color" id="inputGroup-sizing-lg">수정</span>
					</c:if>
				</sec:authorize>
			</div>

			<!-- 패스맵 리스트 -->
			<div class="list-group list-group-flush border-bottom scrollarea" id="userSelectListView">
			</div>

			<!-- 추천, 복사, 댓글 -->
			<div class="mt-auto d-flex flex-row justify-content-center">
				<sec:authorize access="isAuthenticated()">
					<button class="d-flex align-items-center p-3 text-decoration-none border-bottom main_color radius_border"
							style="width: 100%; justify-content: center;"
							type="button" onclick="toggleFavorite()" id="favoriteButton">
						<span class="fs-5 fw-semibold" id = "favoriteButtonText">추천</span>
					</button>

					<button class="d-flex align-items-center p-3 text-decoration-none border-bottom main_color radius_border"
							style="width: 100%; justify-content: center;" onclick="copyUserSelectList()"
							type="button">
						<span class="fs-5 fw-semibold">복사</span>
					</button>
				</sec:authorize>

				<button class="d-flex align-items-center p-3 text-decoration-none border-bottom main_color radius_border" 
						style="width: 100%; justify-content: center;" onclick="hideUserSelectListView()" 
						type="button" data-bs-toggle="collapse" data-bs-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
					<span class="fs-5 fw-semibold" id = "commentButtonText">댓글</span>
				</button>

			</div>
			<script>
				let isOpenComment = false;
				function hideUserSelectListView(){
					let element = document.getElementById("userSelectListView")
					
					if (isOpenComment === false){
						element.style.height="50%"
						getComment()
						isOpenComment = true;
					} else {
						element.style.height="100%"
						isOpenComment = false;
					}
				}
			</script>


			<!-- 댓글창 -->
			<div class="collapse list-group list-group-flush border-bottom scrollarea" id="collapseExample">
				
				<!-- 스크롤링 -->
				<div class='list-group-item list-group-item-action py-3 lh-tight userSelectContainer' aria-current='true' target='_blank' rel='noopener noreferrer'>

					<!-- 댓글 리스트 -->
					<dl id = "commentList" class="row mb-0" style="word-break:break-all;">
					</dl>

				</div>
				
				<!-- 로그인한 사용자만 댓글 작성 가능 -->
				<sec:authorize access="isAuthenticated()">
					<div class="input-group input-group-lg">
						<input type="text" class="form-control" id = "commentInput">
		
						<span type="button" onclick="submitComment()" class="input-group-text">작성</span>
					</div>
				</sec:authorize>

			</div>

		</div>

	</main>

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

		// 좌표 변환
		const mapGeocoder = new kakao.maps.services.Geocoder()

		// 선그리기
		const mapObject = {
			"polyLine" : new kakao.maps.Polyline({
				map: map,
				strokeWeight: 5, // 선의 두께 입니다
				strokeColor: '#FFAE00', // 선의 색깔입니다
				strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
				strokeStyle: 'solid' // 선의 스타일입니다
			}),
			"overlayList" : [],
			"sequenceMarker": []
		}
		
		// {contentId : {
				// 	"marker" : marker,
				// 	"info" : info
				// 	}
		let markInfoMap = new Map();

		// 선택된 컨텐츠 타입
		let markContentTypeCode = "12";
		const contentTypeCssIdMap = new Map([
			["12", "tourSpot"],
			["14", "curtureSite"],
			["15", "festival"],
			["25", "tourCourse"],
			["28", "leports"],
			["32", "accomodation"],
			["38", "shopping"],
			["39", "restaurant"]
		])

		const contentTypeNameMap = new Map([
			["12", "관광지"],
			["14", "문화시설"],
			["15", "페스티벌"],
			["25", "여행코스"],
			["28", "레포츠"],
			["32", "숙박"],
			["38", "쇼핑"],
			["39", "식당"]
		])

		// 유저가 선택한 장소 리스트
		const userSelectList = [
			// testData
			// {
			// 	"title" : "temp",
			// 	"addr1" : "제주시 제주도",
			// 	"addr2" : "제주",
			// 	"contentId" : 12345,
			// 	"contentType" : "식당",
			// 	"dist" : "11111.1111",
			// 	"firstImageURI" : "",
			// 	"firstImageURI2" : "",
			// 	"posX" : 126.570667,
			// 	"posY" : 33.450701,
			// 	"tel" : "010-1010-1010"
			// }
		];

		// 백에서 받는 pathId
		const pathId = ${pathId}

		// path의 mark 가져오기
		loadUserSelectList(pathId);

		// 초기화 함수
		updatePage();

		// 댓글 불러오기
		getComment()
		
		function loadUserSelectList(pathId){
			$.ajax({
				url : "/api/pathmap/" + pathId,
				type : "GET",
				async:false,		// 비동기로
			}).done(response => {
				let title = response["title"]
				let infoList = response["infoList"]

				document.getElementById("pathmapTitle").value = title

				infoList.forEach(info => {
					userSelectList.push(info)
				})
				
				console.log(infoList)

			}).fail(error => {
				alert("error")
			})
		}
		
		// 리스너 함수
		// 드래그가 끝났을 때 -> 너무 많은 Api 요청이 필요함
		/*
		kakao.maps.event.addListener(map, 'dragend', function() {

			// 위치 갱신
			let pos = map.getCenter()
			let params = {
					"posX":pos.getLng(),
					"posY":pos.getLat(),
					"radius":getRadius(map.getLevel()),
					"pageSize":300,			// 값이 너무 크면 느려질 수 있음
					"pageNo":1
				}
				
			console.log("경도(X) : " +  pos.getLng(), "위도(Y) : " + pos.getLat()) 
			
			markBasedLocation(params, markInfoMap);
		});
		*/

		// 확대 수준이 변경된다면 -> 너무 많은 Api 요청이 필요함
		/*
		kakao.maps.event.addListener(map, 'zoom_changed', function() {

			// 위치 갱신
			let pos = map.getCenter()
			let params = {
					"posX":pos.getLng(),
					"posY":pos.getLat(),
					"radius":getRadius(map.getLevel()),
					"pageSize":300,			// 값이 너무 크면 느려질 수 있음
					"pageNo":1
				}

			console.log("경도(X) : " +  pos.getLng(), "위도(Y) : " + pos.getLat()) 
				
			markBasedLocation(params, markInfoMap);
		});
		*/

		// 맵을 클릭한다면
		// kakao.maps.event.addListener(map, "click", function(mouseEvent){

		// 	// 위치 갱신
		// 	let pos = mouseEvent.latLng;
		// 	let params = {
		// 			"posX":pos.getLng(),
		// 			"posY":pos.getLat(),
		// 			"radius":getRadius(map.getLevel()),
		// 			"pageSize":300,			// 값이 너무 크면 느려질 수 있음
		// 			"pageNo":1,
		// 			"contentTypeCode":markContentTypeCode
		// 		}

		// 	// 애니메이션 움직임
		// 	map.panTo(pos)
		// 	console.log("경도(X) : " +  pos.getLng(), "위도(Y) : " + pos.getLat()) 
		// 	markBasedLocation(params, markInfoMap);
		// })

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

		// 위치에 따른 마킹
		function markBasedLocation(params, markInfoMap){

			// 콘텐트 타입이 없는 경우 빈 경우
			if (params["contentType"] === ""){
				return;
			}

			$.ajax({
				url : "/api/tour/location",
				type : "GET",
				data : params,
				dataType : "json"
			}).done((response) => {
				resultAlert(changeMToKm(getRadius(map.getLevel())) + "km 안에 " + response.length + "건의 " + contentTypeNameMap.get(markContentTypeCode) + "이/가 검색되었습니다.", "green")
				updateMarkingInMapByResponse(response, false)
			}).fail((error) => {
				// {"readyState":4,"responseText":"{\"status\":404,\"message\":\"NOT FOUND\"}","responseJSON":{"status":404,"message":"NOT FOUND"},"status":404,"statusText":"error"}
				console.log(error)
				console.log(error["responseJSON"]["message"])
				if (error["status"] === 404){
					resultAlert(changeMToKm(getRadius(map.getLevel())) + "km 안에 " + contentTypeNameMap.get(markContentTypeCode) + "가 없습니다.", "red")
				}
			})

		}

		function changeMToKm(meter){
			return meter / 1000;
		}

		function updateMarkingInMapByResponse(response, isCentered) {
			let responseInfoMap = new Map()

			let bounds = new kakao.maps.LatLngBounds();   
			response.forEach(info => {
				responseInfoMap.set(info["contentId"], info)

				if (isCentered === true){
					// 0, null, undefined 체크
					if (!info["posY"] || !info["posX"]){
						console.log("잘못나옴 : " + info["posX"] + " " + info["posY"])
					} else {
						bounds.extend(
							new kakao.maps.LatLng(info["posY"], info["posX"])
						)
					}

				}
			})

			if (isCentered === true){
				console.log("bounds : " + bounds)
				map.setBounds(bounds)
			}

			// responseInfoMap의 키 (info["contentId"])를 array로 변환
			let responseInfoIdList = Array.from(responseInfoMap.keys())
			let markInfoIdList = Array.from(markInfoMap.keys())

			// 새로 생성 될 거
			let createIdList = responseInfoIdList.filter(contentId => {
				return !markInfoIdList.includes(contentId)
			})		// (responseInfoIdSet - markInfoIdSet)

			// 삭제 될 거
			let removeIdList = markInfoIdList.filter(contentId => {
				return !responseInfoIdList.includes(contentId)
			})		// (markInfoIdSet - responseInfoIdSet)

			// 삭제할 마크 안보이게
			removeIdList.forEach(contentId => {
				let markInfo = markInfoMap.get(contentId)
				markInfo["marker"].setMap(null)
				markInfoMap.delete(contentId)
			})

			// 새로운 마크 표시
			createIdList.forEach(contentId => {
				let info = responseInfoMap.get(contentId);
				
				promiseMarkingInMap(info)
			})
		}

		function promiseMarkingInMap(info) {
			
			return promiseMarking(map, info["posX"], info["posY"], function(){
				
				responseinfoWindowBasic(map, info)

			})
			.then(marker => {
				let markInfo = {
					"marker" : marker,
					"info" : info
				}
				markInfoMap.set(info["contentId"], markInfo);
			})
			.catch(error => {
				console.log(error);
			})
		}

		// callback -> 클릭할 경우 발생하는 함수
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

		// 순서 마커
		function promiseMarkingSequenceInMap(info, index){
			return promiseSequenceMarking(map, info["posX"], info["posY"], index, function(){
				
				// 마커를 클릭하면 인포윈도우가 뜬다
				responseinfoWindowBasic(map, info)
			})
			.then(marker => {
				mapObject["sequenceMarker"].push(marker)
			})
			.catch(error => {
				console.log(error);
			})
		}

		function promiseSequenceMarking(map, posX, posY, index, callback){
			return new Promise(function(resolve, reject){
				resolve(markingSequence(map, posX, posY, index, callback));
			})
		}

		// 1 ~ 15 까지
		function markingSequence(map, posX, posY, index, callback){
			// 세 번째 파라메터로 options를 사용.
			let icon = new kakao.maps.MarkerImage(
				'http://t1.daumcdn.net/localimg/localimages/07/2012/img/marker_normal.png',
				new kakao.maps.Size(43, 52),
				{
					spriteOrigin: new kakao.maps.Point(315, 52 * index - 1),    
					spriteSize: new kakao.maps.Size(644, 946) 
				}
			);

			let marker = new kakao.maps.Marker({
				map:map,
				position: new kakao.maps.LatLng(posY, posX),
				image : icon
			})
			kakao.maps.event.addListener(marker, "click", callback);

			return marker
		}

		// 인포윈도우를 띄움
		function responseinfoWindow(map, posX, posY, content){

			let infoWindow = new kakao.maps.InfoWindow({
				map: map,
				position: new kakao.maps.LatLng(posY, posX),
				content: content,
				removable: true
			})
		}

		// 인포윈도우 기본 형식
		function responseinfoWindowBasic(map, info){
			const detailUri = "/pathmap/detail/" + info["contentTypeId"] + "/" + info["contentId"]

			let xy = dfs_xy_conv("toXY", info["posY"], info["posX"])
			const wheatherUri = "/info/wheather/" + xy["x"] + "/" + xy["y"]
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
							<div class='me-auto d-flex flex-row'> \
								<button data-bs-toggle='modal' data-bs-target='#place' onclick='renewPlace(" + info["contentTypeId"] + ", " + info["contentId"] + ")' class = 'me-1 badge main_color_only'>장소</button> \
								<button data-bs-toggle='modal' data-bs-target='#weather' onclick='renewWheather(" + info["posX"] + ", " + info["posY"] + ")' class = 'badge main_color_only'>날씨</button> \
							</div> \
						</div> \
					</div> \
				</div> \
			"

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
		}

		// 인포윈도우에서 추가를 선택했을 때 실행
		function addUserSelectList(info){
			
			if (isNotDuplicated(info)){
				userSelectList.push(info);
			}
			console.log(userSelectList)
			updatePage()

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

		// 페이지 갱신
		function updatePage(){

			// 유저가 선택한 마커 관련 오브젝트(경로선, 마커 등) 갱신
			renewUserSelectMapObject();

			// 사이드바 갱신
			renewUserSelectSidebar();

			// 바운드
			setUserSelectListBounds()

			// 사용자가 추천했는지 갱신
			renewFavorite()
		}

		async function renewUserSelectSidebar(){
			
			let userSelectListView = document.getElementById("userSelectListView")
			userSelectListView.innerHTML = "";

			let beforeInfo;
			for (let i = 0 ; i < userSelectList.length; i++){

				let info = userSelectList[i]

				let listTemplate = "";

				// <a href='http://map.naver.com/index.nhn?slng="+ beforeInfo["posX"] +"&slat=" + beforeInfo["posY"] + "&stext="+ beforeInfo["title"] + "&elng=" + info["posX"] + "&elat=" + info["posY"] + "&pathType=0&showMap=true&etext=" + info["title"] + "&menu=route' target='_blank' rel='noopener noreferrer' class='list-group-item list-group-item-action active py-3 lh-tight userSelectContainer' aria-current='true'> \
				// 경로 사이 길찾기 링크 생성
				if (i > 0){

					// 비동기 함수의 콜백값을 가져오려는 몸부림
					let beforeWCObject = await promiseTransWgs84ToWcongnamul(beforeInfo["posX"], beforeInfo["posY"])
					let currentWCObject = await promiseTransWgs84ToWcongnamul(info["posX"], info["posY"])

					console.log("beforeWtm : " + beforeWCObject["wtmX"] + ", " + beforeWCObject["wtmY"])
					console.log("currentWtm : " + currentWCObject["wtmX"] + ", " + currentWCObject["wtmY"])

					// 길찾기 버튼
					if (beforeWCObject !== null || currentWCObject !== null){
						listTemplate += "\
							<a href='https://map.kakao.com/?map_type=TYPE_MAP&target=car&rt="+ beforeWCObject["wtmX"] + "," + beforeWCObject["wtmY"] + "," + currentWCObject["wtmX"] + "," + currentWCObject["wtmY"] + "&rt1=" + beforeInfo["title"] + "&rt2=" + info["title"] + "' target='_blank' rel='noopener noreferrer' \
							class='list-group-item list-group-item-action py-3 lh-tight userSelectContainer radius_border main_color' aria-current='true'> \
								<div class='d-flex flex-column align-items-center'> \
									<div> \
										길찾기 \
									</div> \
									<div> \
										" + beforeInfo["title"] + " → " + info["title"] + "  \
									</div> \
								</div> \
							</a> \
						"
					} else {
						listTemplate += " \
							<div class='list-group-item list-group-item-action py-3 lh-tight userSelectContainer radius_border main_color'> \
								<div> \
									길찾기 \
								</div> \
								<div> \
									좌표 변환에 실패하였습니다.  \
								</div> \
							</div> \
						"
					}

				}

				const detailUri = "/pathmap/detail/" + info["contentTypeId"] + "/" + info["contentId"]
				console.log(detailUri)

				// 가져올 때는 .userSelectContainer로 가져오기
				listTemplate += " \
					<a class='list-group-item list-group-item-action py-3 lh-tight userSelectContainer' aria-current='true' target='_blank' rel='noopener noreferrer'> \
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
								\
								<div class = 'd-flex flex-row'> \
									<div class='me-auto d-flex flex-row'> \
										<button data-bs-toggle='modal' data-bs-target='#place' onclick='renewPlace(" + info["contentTypeId"] + ", " + info["contentId"] + ")' class = 'me-1 badge main_color_only'>장소</button> \
										<button data-bs-toggle='modal' data-bs-target='#weather' onclick='renewWheather(" + info["posX"] + ", " + info["posY"] + ")' class = 'badge main_color_only'>날씨</button> \
									</div> \
								</div> \
							</div> \
						</div> \
					</a> \
				"
				
				userSelectListView.innerHTML += listTemplate

				// renewWheather(xy, i)
				// renewPlace(info["contentTypeId"], info["contentId"], i)

				beforeInfo = info;
			}

		}

		// 좌표 변환 함수
		function promiseTransWgs84ToWcongnamul(wgs84X, wgs84Y){

			let promiseTransCoords = new Promise((resolve, reject) => {
				mapGeocoder.transCoord(wgs84X, wgs84Y, (result, status) => {
					if (status === kakao.maps.services.Status.OK) {
						let wtmX = result[0].x;
						let wtmY = result[0].y;

						resolve({
							"wtmX" : wtmX,
							"wtmY" : wtmY 
						})

					} else {
						reject(null)
					}
				}, {
					input_coord: kakao.maps.services.Coords.WGS84,
					output_coord: "WCONGNAMUL"
				})
			})

			return promiseTransCoords
		}

		// 추천 버튼 갱신
		function renewFavorite(){
			
			const data = {
				"pathId" : pathId
			}

			$.ajax({
				url : "/api/pathmap/favorite",
				data : data,
				type : "GET"
			})
			.done((response) => {
				console.log(response)

				let favoriteButton = document.getElementById("favoriteButton")
				let favoriteButtonText = document.getElementById("favoriteButtonText")
				if (response["isFavorite"] === true) {
					favoriteButtonText.innerHTML = "추천완료"
					favoriteButton.classList.replace('main_color', 'main_color_selected')
				} else {
					favoriteButtonText.innerHTML = "추천"
					favoriteButton.classList.replace('main_color_selected', 'main_color')
				}

			}).fail((error) => {
				console.log(error['status'])
			})
		}

		function toggleFavorite(){
			
			const data = {
				"pathId" : pathId
			}

			$.ajax({
				url : "/api/pathmap/favorite",
				data : JSON.stringify(data),
				type : "POST",
				contentType: "application/json",
				dataType : "json"
			}).done((response) => {
				console.log(response)

				renewFavorite()

			}).fail((error) => {
				console.log("error : " + error)
			})
		}



		// userSelectList의 특정 인덱스의 값을 삭제
		function deleteUserSelectByIndex(index){
			userSelectList.splice(index, 1)
			
			updatePage()
		}

		function upUserSelect(index){
			let temp = userSelectList[index]
			userSelectList[index] = userSelectList[index -1]
			userSelectList[index-1] = temp

			updatePage()
		}

		function downUserSelect(index){
			let temp = userSelectList[index]
			userSelectList[index] = userSelectList[index + 1]
			userSelectList[index+1] = temp

			updatePage()
		}

		// 패스맵 제출, 저장
		function submitUserSelectList(){
			let title = document.getElementById('pathmapTitle').value;

			let data = {
				"title" : title,
				"request" : JSON.stringify(userSelectList)
			}

			console.log("제출")
			
			$.ajax({
				url: "/api/pathmap",
				type: 'POST',
				data : JSON.stringify(data),
				contentType :"application/json",
				dataType: "json"
			})
			.done(function(response) {
				// { "response" : "OK" }
				console.log(response["response"])
				window.location.replace("/pathmap");
			})
			.fail(function(error) {
				console.log("Error : " + error)
			});
		}

		// 패스맵 복사
		function copyUserSelectList(){
			let title = document.getElementById('pathmapTitle').value;

			let data = {
				"title" : "[복사] " + title,
				"markers" : JSON.stringify(userSelectList)
			}

			console.log("제출")
			
			$.ajax({
				url: "/api/pathmap",
				type: 'POST',
				data : JSON.stringify(data),
				contentType: "application/json",
				dataType: "json"
			})
			.done(function(response) {
				// { "response" : "OK" }
				console.log(response["response"])
				window.location.replace("/pathmap/" + response["pathId"]);
			})
			.fail(function(error) {
				console.log("Error : " + error)
			});
		}

		// 댓글 달기
		function getComment(){

			let data = {
				"pathId" : pathId
			}

			$.ajax({
				url: "/api/pathmap/comment",
				type: 'GET',
				dataType: "json",
				data : data
			})
			.done(function(response){
				let commentList = document.getElementById("commentList")

				let commentButtonText = document.getElementById("commentButtonText")

				if (response){
					commentButtonText.innerHTML = "댓글 " + response.length
				}

				let result = "";
				
				response.forEach(comment => {
					result += '\
						<dt class="col-sm-3">' + comment["username"] + '</dt> \
						<dd class="col-sm-9 mb-1"> \
							<p> \
							' + comment["content"] + '  \
							</p> \
							<p class = "d-flex"> \
								<small>' + comment["updateDate"] + '</small>'

					if (comment["username"] === '${username}'){
						result += '<button class="ms-auto" onclick="deleteComment(' + comment["commentId"] + ')"> 삭제 </button>'
					}
					result += ' \
							</p> \
						</dd> \
					'
				})

				commentList.innerHTML = result;
			})
			.fail(function(error) {
				console.log("Error : " + error)
			});
		}

		// 댓글 달기
		function submitComment(){
			let commentContent = document.getElementById('commentInput').value;

			let data = {
				"comment" : commentContent,
				"pathId" : pathId
			}

			$.ajax({
				url: "/api/pathmap/comment",
				type: 'POST',
				data : JSON.stringify(data),
				contentType: "application/json",
				dataType: "json"
			})
			.done(function(response){
				document.getElementById('commentInput').value = null;
				getComment()
			})
			.fail(function(error) {
				console.log("Error : " + error)
			});
		}

		function deleteComment(commentId){

			let data = {
				"commentId" : commentId
			}

			$.ajax({
				url : "/api/pathmap/comment",
				type : 'DELETE',
				data : JSON.stringify(data),
				contentType: "application/json",
				dataType : "json"
			})
			.done(function(response){
				
				getComment()
			})
			.fail(function(error){

			})
		}


		// 등록된 좌표 중간에서 다 보여주도록 함
		function setUserSelectListBounds(){

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

			// 맵 안의 마커 초기화
			markInfoMap.forEach(markInfo => {
				markInfo["marker"].setMap(null)
			})
			markInfoMap.clear()
		}

		// 지도 위의 선 그리기
		function renewUserSelectMapObject(){

			const mapPolyLine = mapObject["polyLine"]
			const mapOverlayList = mapObject["overlayList"]
			const mapSequenceMarker = mapObject["sequenceMarker"]

			// overlay 초기화
			mapOverlayList.forEach(o => {
				o.setMap(null)
			})

			// 시퀀스마커 초기화
			mapSequenceMarker.forEach(m => {
				m.setMap(null)
			})

			const linePath = []
			userSelectList.forEach((info, index) => {
				const pos = new kakao.maps.LatLng(info["posY"], info["posX"])

				// 시퀀스마커 추가
				promiseMarkingSequenceInMap(info, index)

				// 경로선 추가
				linePath.push(pos)


				let overlayContent = '\
					<div class="custom_overlay"> \
						<div style="font-size=20px"><strong>'+ info["title"] + " : " + (index + 1) + '</strong></div> \
						<div class="text-muted font-size=8px">' + info["contentType"] + '</div> \
					</div> \
				'
				// 순서 오버레이
				let overlay = new kakao.maps.CustomOverlay({
						map: map,
						// clickable: true,
						content: overlayContent,
						position: pos,
						xAnchor: 0.5,
						yAnchor: 0,
						zIndex: -1
				})
				mapOverlayList.push(overlay)
				
			})

			mapPolyLine.setPath(linePath)
		}

		// 왼쪽 위의 ContentType 바 설정
		function setMarkContentType(inputCode){

			markContentTypeCode = inputCode

			for (let [code, id] of contentTypeCssIdMap){
				let menu = document.getElementById(id)

				if (code === inputCode){
					menu.className ="selected text-wrap" 
				} else {
					menu.className = "badge text-wrap"
				}
			}
		}

		function renewAreaLargeCode(){
			
			$.ajax({
				url : "/api/tour/area/code",
				type : "GET",
				dataType : "json"
			})
			.done((response) => {
				console.log(response)
				
				let areaLargeSelect = document.getElementById("areaLargeSelect")

				areaLargeSelect.innerHTML = ""
				let result = "<option value=''>지역</option>";
				response.forEach(r => {
					result += "<option value='"+ r["code"] +"'>" + r["name"] + "</option>"
				})

				areaLargeSelect.innerHTML = result;

			}).fail((error) => {
				console.log(error["responseJSON"]["message"])
			})
		}

		function getAreaSmallCode(largeCode){
			$.ajax({
				url : "/api/tour/area/code/" + largeCode,
				type : "GET",
				dataType : "json"
			})
			.done((response) => {
				console.log(response);

				let areaSmallSelect = document.getElementById("areaSmallSelect")

				areaSmallSelect.innerHTML = ""
				let result = "<option value=''>시군구</option>";
				response.forEach(r => {
					result += "<option value='"+ r["code"] +"'>" + r["name"] + "</option>"
				})

				areaSmallSelect.innerHTML = result;

			}).fail((error) => {
				console.log(error["responseJSON"]["message"])
			})
		}

		function searchTourInfoKeyword(keyword){
			areaLargeSelect = document.getElementById("areaLargeSelect")
			areaSmallSelect = document.getElementById("areaSmallSelect")

			let data = {
				"keyword" : keyword,
				"areaLargeCode" : areaLargeSelect.value,
				"areaSmallCode" : areaSmallSelect.value,
				"pageSize" : 100,
				"pageNo" : 1,
				"contentTypeCode" : markContentTypeCode
			}

			$.ajax({
				url : "/api/tour/keyword",
				type : "GET",
				data : data,
				dataType : "json"
			})
			.done((response) => {
				
				let resultText = ""
				if (areaLargeSelect.value){
					areaLargeText = areaLargeSelect.options[areaLargeSelect.selectedIndex].text
					areaSmallText = areaSmallSelect.options[areaSmallSelect.selectedIndex].text

					resultText += areaLargeText + " " + areaSmallText + "에 존재하는 ";
				}
				resultText += response.length + "건의 " + contentTypeNameMap.get(markContentTypeCode) + "이/가 검색되었습니다."

				resultAlert(resultText, "green")
				
				updateMarkingInMapByResponse(response, true)

			}).fail((error) => {
				console.log(error)
				console.log(error["responseJSON"]["message"])
				if (error["status"] === 404){
					resultAlert("조건에 만족하는 결과를 찾지 못하였습니다.", "red")
				}
			})
		}

		function resultAlert(message, color){
			let alert = document.getElementById("resultAlert");
			alert.innerHTML = message;
			alert.style.color = color
		}
	</script>


	<script src="<c:url value='/js/bootstrap.bundle.min.js' />" type="text/javascript"></script>
    <script src="<c:url value='/js/sidebars.js' />" type="text/javascript"></script>

</body>
</html>