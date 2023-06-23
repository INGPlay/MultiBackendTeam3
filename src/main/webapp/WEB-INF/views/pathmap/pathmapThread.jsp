<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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

    <%@ include file="./template/staticTemplate.jsp" %>

</head>
<body>
    <div class="container">
        <div class="d-flex flex-row bd-highlight">
            <!-- 조회 조건 -->
            <select class="col-1" name="orderBy" id="orderBySelect" onchange="setOrderBy(this)">
                <option value="createDate" selected>최신순</option>
                <option value="view">조회순</option>
                <option value="recommend">추천순</option>
            </select>

            <!-- 로그인 한 사용자만 작성가능 -->
            <sec:authorize access="isAuthenticated()">
                <button class="btn main_color ms-auto" onClick="location.href='/pathmap/mark'">
                    <strong>작성하기</strong>
                </button>
            </sec:authorize>

        </div>

        <table class="table table table-hover">
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
        <div class="row justify-content-center">
            <div class="col-5">
                <div class="input-group mb-3">

                    <!-- 검색 조건 -->
                    <select class="col-2" name="searchOption" id="searchOptionSelect">
                        <option value="title" selected>제목</option>
                        <option value="author">글쓴이</option>
                    </select>

                    <!-- 검색할 단어 -->
                    <input type="text" name="searchWord" class="form-control col-5" id="searchWordInput">

                    <button type="submit" class="btn btn-outline-dark col-2" onclick="search()">검색</button>

                </div>
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
        

        updatePageStaticOption()

        
        function updatePageStaticOption(){
            updatePage(PAGE_PAGE, PAGE_SIZE, PAGE_ORDERBY, PAGE_SEARCHWORD, PAGE_SEARCHOPTION)
        }

        function updatePage(page, size, orderBy, searchWord, searchOption){

            const data = {
                "page" : page,
                "size" : size,
                "orderBy" : orderBy,
                "searchWord" : searchWord,
                "searchOption" : searchOption
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
                    updatePage(pageNum, PAGE_SIZE, PAGE_ORDERBY, PAGE_SEARCHWORD, PAGE_SEARCHOPTION)
                })
            })

            if (hasPrevious){
                document.getElementById(previousButtonId).addEventListener("click", event => {
                    updatePage(response["previousNum"], PAGE_SIZE, PAGE_ORDERBY, PAGE_SEARCHWORD, PAGE_SEARCHOPTION)
                })
            }

            if (hasNext){
                document.getElementById(nextButtonId).addEventListener("click", event => {
                    updatePage(response["nextNum"], PAGE_SIZE, PAGE_ORDERBY, PAGE_SEARCHWORD, PAGE_SEARCHOPTION)
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
    </script>
</body>
</html>