<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>플래너 게시판</title>

    <%@ include file="/WEB-INF/views/template/staticTemplate.jsp" %>
    <script type="text/javascript" src='//dapi.kakao.com/v2/maps/sdk.js?appkey=<spring:message code="keys.kakao.map" javaScriptEscape="true" />&libraries=services'></script>

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


    <div class="container mb-5" style="height: 80%;">

        <div class="d-flex flex-row" style="height:100%">

            <div class="mt-2 d-flex align-items-center" style="width: 50%;">
                <!-- 지도를 표시할 div 입니다 -->
                <div id="map" style="width:100%;height:100%;"></div>

            </div>
    
            <div class="flex-grow-1 ms-2 flex-column">
    
                <div class="d-flex flex-row bd-highlight mt-2">
                    <!-- 조회 조건 -->
                    <select class="me-auto form-select" name="orderBy" id="orderBySelect" onchange="setOrderBy(this)" style="width: 100px;">
                        <option value="createDate" selected>최신순</option>
                        <option value="view">조회순</option>
                        <option value="recommend">추천순</option>
                    </select>
        
                    <!-- 로그인 한 사용자만 -->
                    <sec:authorize access="isAuthenticated()">
                        <button class="btn btn-outline-primary" onClick="toggleFavorites()">
                            <strong id="favoriteButtonText">추천한 게시글</strong>
                        </button>
        
                        <button class="btn main_color" onClick="location.href='/pathmap/mark'">
                            <strong>작성하기</strong>
                        </button>
                    </sec:authorize>
                </div>
    
                <!-- 게시글 리스트 -->
                <div class="list-group list-group-flush border-bottom scrollarea" id="listRow" style="height: 90%;">

                </div>
        
                <div class="mt-auto">
                    <!-- 페이징 바 -->
                    <div class="d-flex justify-content-center">
                        <nav aria-label="Page navigation example">
            
                            <!-- 페이징 리스트 -->
                            <ul class="pagination" id = "pagingButtonList">
            
                                <!-- 이전 페이지 -->
                                <li class="page-item">
                                    <a class="page-link" href="#" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
            
                                <li class="page-item"><a class="page-link" href="#">1</a></li>
                                <li class="page-item"><a class="page-link" href="#">2</a></li>
                                <li class="page-item"><a class="page-link" href="#">3</a></li>
                                
                                <!-- 다음 페이지 -->
                                <li class="page-item">
                                    <a class="page-link" href="#" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
            
                            </ul>
                        </nav>
                    </div>
            
                    <!--  검색  -->
                    <div class="d-flex justify-content-center">
                        <div class="input-group">
            
                            <!-- 검색 조건 -->
                            <select class="col-3" name="searchOption" id="searchOptionSelect">
                                <option value="title" selected>제목</option>
                                <option value="author">글쓴이</option>
                            </select>
            
                            <!-- 검색할 단어 -->
                            <input type="text" name="searchWord" class="form-control col-6 border" id="searchWordInput">
            
                            <button type="submit" class="btn btn-outline-dark col-3" onclick="search()">검색</button>
            
                        </div>
                    </div>
                </div>

            </div>

        </div>

    </div>

    <!-- footer -->
    <%@ include file="/WEB-INF/views/template/footer.jsp" %>

    <script>

        // 왼쪽 맵 ===================================================================

        let mapContainer = document.getElementById('map'), // 지도를 표시할 div 
            mapOption = { 
                center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
                level: 3 // 지도의 확대 레벨
            };

        // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
        let map = new kakao.maps.Map(mapContainer, mapOption); 


        let selectedId;

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
            //  "area" : "제주도 제주시"
			// }
		];

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

        function renewMap(pathId){
            userSelectList.length = 0       // 배열 초기화
            
            document.getElementById("pathPanel" + selectedId).classList.remove("active")
            document.getElementById("pathPanel" + pathId).classList.add("active")
            selectedId = pathId;
            
            loadUserSelectList(pathId)
            renewUserSelectMapObject()
        }

        function loadUserSelectList(pathId){
			$.ajax({
				url : "/api/pathmap/" + pathId,
				type : "GET",
				async:false,		// 비동기로
			}).done(response => {
				let infoList = response["infoList"]

				infoList.forEach(info => {
					userSelectList.push(info)
				})
				
				console.log(infoList)

			}).fail(error => {
				alert("error")
			})
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

            var bounds = new kakao.maps.LatLngBounds();

            userSelectList.forEach((info, index) => {
                const pos = new kakao.maps.LatLng(info["posY"], info["posX"])

                bounds.extend(pos)

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

            map.setBounds(bounds)

            mapPolyLine.setPath(linePath)
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
                                <button onclick='window.open(\"" + detailUri + "\");'>정보</button> \
                                <button onclick='window.open(\"" + wheatherUri + "\");'>날씨</button> \
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




        // 오른쪽 게시글=============================================================

        let PAGE_PAGE = 1;
        let PAGE_SIZE = 8;

        const orderBySelect  = document.getElementById("orderBySelect");
        let PAGE_ORDERBY = orderBySelect.options[orderBySelect.selectedIndex].value;

        const searchWordInput = document.getElementById("searchWordInput")
        let PAGE_SEARCHWORD = searchWordInput.value

        const searchOptionSelect = document.getElementById("searchOptionSelect")
        let PAGE_SEARCHOPTION = searchOptionSelect.options[searchOptionSelect.selectedIndex].value;
        

        let PAGE_ISFAVORITE = false


        // init
        updatePageStaticOption()

        
        function updatePageStaticOption(){
            updatePage(PAGE_PAGE, PAGE_SIZE, PAGE_ORDERBY, PAGE_SEARCHWORD, PAGE_SEARCHOPTION, PAGE_ISFAVORITE)
        }

        function updatePage(page, size, orderBy, searchWord, searchOption, isFavorite){

            const data = {
                "page" : page,
                "size" : size,
                "orderBy" : orderBy,
                "searchWord" : searchWord,
                "searchOption" : searchOption,
                "isFavorite" : isFavorite
            }

            $.ajax({
					url : "/api/pathmap",
					type : "GET",
					data : data,
					contentType: "application/json",
					dataType : "json",
                    async:false
				}).done((response) => {

                    console.log(response)
                    
                    initPathList(response["pathInfoResponses"])

                    initPagingBar(response)

                    // 선택된 패스 id 갱신
                    selectedId = response["pathInfoResponses"][0]["pathId"];

                    // 맵 갱신
                    renewMap(selectedId)

                }).fail((error) => {
                    alert("불러오기에 실패하였습니다.")
                })
        }

        function initPathList(pathList){
            
            let result = ''
            pathList.forEach(path => {
                
                result += ' \
                    <a onclick="renewMap(' + path["pathId"] + ')" class="list-group-item list-group-item-action" aria-current="true" id="pathPanel' + path["pathId"] + '"> \
                        <div class="d-flex w-100 justify-content-between"> \
                            <h5 class="mb-1">' + path["pathTitle"] + '</h5> \
                            <small>' + path["updateDate"] + '</small> \
                        </div> \
                        <div class="d-flex w-100 justify-content-between"> \
                            <p class="mb-1">' + path["pathStartingArea"] + ' → ' + path["pathDestinationArea"] + '</p> \
                            <small>추천 ' + path["pathRecommends"] + ' | 조회 ' + path["pathViews"] + ' </small> \
                        </div> \
                        <div class="d-flex w-100 justify-content-between"> \
                            <small>by ' + path["username"] + '</small> \
                            <button onclick="window.location.href=' + '\'/pathmap/' + path["pathId"] + '\'' + '"> \
                                자세히 보기 \
                            </button> \
                        </div>\
                    </a> \
                '
            })

            let listRow = document.getElementById("listRow")
            document.getElementById("listRow").innerHTML = result
        }

        function initPagingBar(response){

            let result = ''

            const hasPrevious = response["hasPrevious"]
            const hasNext = response["hasNext"]
            const pageNumList = response["pageNumList"]

            const previousButtonId = "previousButton"
            const nextButtonId = "nextButton"
            const pageNumIdTemplate = "pageButton"
            
            if (hasPrevious){

                result += ' \
                    <li class="page-item"> \
                        <a id = "' + previousButtonId + '" class="page-link" aria-label="Previous"> \
                            <span aria-hidden="true">&laquo;</span> \
                        </a> \
                    </li> \
                '
            }

            pageNumList.forEach(pageNum => {

                if (pageNum === response["currentPageNum"]){
                    result += '\
                    <li class="page-item active"> \
                    '
                } else {
                    result += '\
                    <li class="page-item"> \
                    '
                }

                let pageNumId = pageNumIdTemplate + pageNum
                result += ' \
                        <a id = "' + pageNumId + '" class="page-link">' + pageNum + '</a> \
                    </li> \
                '
            })


            if (hasNext){
                result += ' \
                    <li class="page-item"> \
                        <a id = "' + nextButtonId + '" class="page-link" aria-label="next" > \
                            <span aria-hidden="true">&raquo;</span> \
                        </a> \
                    </li> \
                '
            }

            let pagingBar = document.getElementById("pagingButtonList")
            pagingBar.innerHTML = result;

            // 이벤트 등록
            pageNumList.forEach(pageNum => {
                let pageNumId = pageNumIdTemplate + pageNum

                document.getElementById(pageNumId).addEventListener("click", event => {
                    updatePage(pageNum, PAGE_SIZE, PAGE_ORDERBY, PAGE_SEARCHWORD, PAGE_SEARCHOPTION, PAGE_ISFAVORITE)
                })
            })

            if (hasPrevious){
                document.getElementById(previousButtonId).addEventListener("click", event => {
                    updatePage(response["previousNum"], PAGE_SIZE, PAGE_ORDERBY, PAGE_SEARCHWORD, PAGE_SEARCHOPTION, PAGE_ISFAVORITE)
                })
            }

            if (hasNext){
                document.getElementById(nextButtonId).addEventListener("click", event => {
                    updatePage(response["nextNum"], PAGE_SIZE, PAGE_ORDERBY, PAGE_SEARCHWORD, PAGE_SEARCHOPTION, PAGE_ISFAVORITE)
                })
            }

        }

        function setOrderBy(order){
            PAGE_ORDERBY = order.options[order.selectedIndex].value
            updatePageStaticOption()
        }
        
        function search(){
            const searchWordInput = document.getElementById("searchWordInput")
            PAGE_SEARCHWORD = searchWordInput.value

            const searchOptionSelect = document.getElementById("searchOptionSelect")
            PAGE_SEARCHOPTION = searchOptionSelect.options[searchOptionSelect.selectedIndex].value;

            updatePageStaticOption()
        }

        function toggleFavorites(){
            let favoriteButtonText = document.getElementById("favoriteButtonText")
            if (PAGE_ISFAVORITE === false){
                PAGE_ISFAVORITE = true;
                favoriteButtonText.innerHTML = '돌아가기'
            } else {
                PAGE_ISFAVORITE = false;
                favoriteButtonText.innerHTML = '추천한 게시글'
            }

            updatePageStaticOption();
        }
    </script>
</body>
</html>