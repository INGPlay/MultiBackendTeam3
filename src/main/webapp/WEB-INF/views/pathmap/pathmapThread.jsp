<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <%@ include file="/WEB-INF/views/template/staticTemplate.jsp" %>

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


    <div class="container">
        <div class="d-flex flex-row bd-highlight mt-2">
            <!-- 조회 조건 -->
            <select class="me-auto col-1" name="orderBy" id="orderBySelect" onchange="setOrderBy(this)">
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

        <table class="table table table-hover mt-2">
            <thead>
              <tr>
                <th scope="col">ID</th>
                <th scope="col">제목</th>
                <th scope="col">작성자</th>
                <th scope="col">작성일</th>
                <th scope="col">추천수</th>
                <th scope="col">조회수</th>
              </tr>
            </thead>
            <tbody id = "listRow">
              <!-- <tr>
                <th scope="row">{id}</th>
                <td>{title}</td>
                <td>{username}</td>
                <td>{createDate}</td>
                <td>{recommends}</td>
                <td>{views}</td>
              </tr> -->
            </tbody>
        </table>

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
        <div class="d-flex justify-content-center pb-5">
            <div class="col-5 input-group">

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

    <script>


        let PAGE_PAGE = 1;
        let PAGE_SIZE = 10;

        const orderBySelect  = document.getElementById("orderBySelect");
        let PAGE_ORDERBY = orderBySelect.options[orderBySelect.selectedIndex].value;

        const searchWordInput = document.getElementById("searchWordInput")
        let PAGE_SEARCHWORD = searchWordInput.value

        const searchOptionSelect = document.getElementById("searchOptionSelect")
        let PAGE_SEARCHOPTION = searchOptionSelect.options[searchOptionSelect.selectedIndex].value;
        

        let PAGE_ISFAVORITE = false


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

                }).fail((error) => {
                    alert("불러오기에 실패하였습니다.")
                })
        }

        function initPathList(pathList){
            
            let result = ''
            pathList.forEach(path => {

                result += " \
                    <tr onclick='window.location.href= \"/pathmap/" + path['pathId'] + "\"'> \
                        <th scope='row'>" + path['pathId'] +"</th> \
                        <td>" + path['pathTitle'] + "</td> \
                        <td>" + path['username'] + "</td> \
                        <td>" + path['createDate'] + "</td> \
                        <td>" + path['pathRecommends'] + "</td> \
                        <td>" + path['pathViews'] + "</td> \
                    </tr> \
                "
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