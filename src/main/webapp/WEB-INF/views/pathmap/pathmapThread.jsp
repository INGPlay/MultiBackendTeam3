<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

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
        <div class="d-flex flex-row-reverse bd-highlight">
            <button class="btn main_color" onClick="location.href='/pathmap/mark'">
                <strong>작성하기</strong>
            </button>
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

    </div>

    <script>

        let PAGE_PAGE = 1;
        let PAGE_SIZE = 10;
        let PAGE_ORDERBY = "createDate";
        let PAGE_SEARCHWORD = ""
        
        updatePage(PAGE_PAGE, PAGE_SIZE, PAGE_ORDERBY, PAGE_SEARCHWORD)

        function updatePage(page, size, orderBy, searchWord){

            const data = {
                "page" : page,
                "size" : size,
                "orderBy" : orderBy,
                "searchWord" : searchWord
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
                    updatePage(pageNum, PAGE_SIZE, PAGE_ORDERBY, PAGE_SEARCHWORD)
                })
            })

            if (hasPrevious){
                document.getElementById(previousButtonId).addEventListener("click", event => {
                    updatePage(response["previousNum"], PAGE_SIZE, PAGE_ORDERBY, PAGE_SEARCHWORD)
                })
            }

            if (hasNext){
                document.getElementById(nextButtonId).addEventListener("click", event => {
                    updatePage(response["nextNum"], PAGE_SIZE, PAGE_ORDERBY, PAGE_SEARCHWORD)
                })
            }



        }
    </script>
</body>
</html>