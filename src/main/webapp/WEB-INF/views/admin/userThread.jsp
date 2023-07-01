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
    <title>관리자 게시판</title>

    <%@ include file="/WEB-INF/views/template/staticTemplate.jsp" %>

    <!-- 카카오 지도랑 충돌 -->
    <!-- CSS dependencies -->
    <link rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
    type="text/css">
    <link rel="stylesheet" href="/resources/colorful.css">

    <!-- 모달창을 위한 부트스트랩 js -->
    <script src="<c:url value='/js/bootstrap.bundle.min.js' />" type="text/javascript"></script>

</head>

<body>
    <!-- header -->
    <%@ include file="/WEB-INF/views/template/header.jsp" %>

    <div class="container">
        <div class="d-flex flex-row bd-highlight mt-2">
            <!-- 조회 조건 -->
            <select class="me-auto col-1 form-select" name="orderBy" id="orderBySelect" onchange="setOrderBy(this)">
                <option value="role" selected>역할순</option>
                <option value="username">이름순</option>
                <option value="email">이메일순</option>
                <option value="phone">전화번호순</option>
            </select>

        </div>

        <table class="table table table-hover mt-2">
            <thead>
              <tr>
                <th scope="col">ID</th>
                <th scope="col">유저이름</th>
                <th scope="col">이메일</th>
                <th scope="col">전화번호</th>
                <th scope="col">권한</th>
                <th scope="col">삭제하기</th>
              </tr>
            </thead>
            <tbody id = "listRow">
              <!-- <tr>
                <th scope="row">{id}</th>
                <td>{username}</td>
                <td>{email}</td>
                <td>{phone}</td>
                <td>{role}</td>
                <td><button>삭제하기</button></td>
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
                    <option value="username" selected>유저명</option>
                    <option value="email">이메일</option>
        `            <option value="role">역할명</option>
                    <option value="phone">전화번호</option>
                </select>

                <!-- 검색할 단어 -->
                <input type="text" name="searchWord" class="form-control col-6 border" id="searchWordInput">

                <button type="submit" class="btn btn-outline-dark col-3" onclick="search()">검색</button>

            </div>
        </div>

    </div>
    <!-- footer -->
    <%@ include file="/WEB-INF/views/template/footer.jsp" %>

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
                "searchOption" : searchOption,
            }

            $.ajax({
                url : "/api/admin/user",
                type : "GET",
                data : data,
                dataType : "json",
                async:false
            }).done((response) => {
                
                console.log(response)
                
                initUserList(response["pathInfoResponses"])

                initPagingBar(response)

            }).fail((error) => {
                alert("불러오기에 실패하였습니다.")
            })
        }

        function initUserList(userList){
            
            let result = ''
            userList.forEach(user => {

                result += " \
                    <tr> \
                        <th scope='row'>" + user['id'] +"</th> \
                        <td>" + user['username'] + "</td> \
                        <td>" + user['email'] + "</td> \
                        <td>" + user['phone'] + "</td> \
                        <td>" + user['role'] + "</td> \
                        <td> \
                            <button class='btn btn-danger' data-bs-toggle='modal' data-bs-target='#modal" + user['id'] + "'>삭제하기</button> \
                        </td>\
                    </tr> \
                    \
                    <!-- Modal --> \
                    <div class='modal fade' id='modal" + user['id'] + "' tabindex='-1' aria-labelledby='exampleModalLabel' aria-hidden='true'> \
                        <div class='modal-dialog'> \
                            <div class='modal-content'> \
                                <div class='modal-header'> \
                                <h5 class='modal-title' id='exampleModalLabel'>Modal title</h5> \
                                <button type='button' class='btn-close' data-bs-dismiss='modal' aria-label='Close'></button> \
                                </div> \
                                <div class='modal-body'> \
                                    " + user["username"] + " : 정말 삭제하시겠습니까? \
                                </div> \
                                <div class='modal-footer'> \
                                    <button type='button' class='btn btn-danger' onclick='deleteUser(" + user['id'] + ")' data-bs-dismiss='modal'>삭제하기</button> \
                                    <button type='button' class='btn btn-info' data-bs-dismiss='modal'>닫기</button> \
                                </div> \
                            </div> \
                        </div> \
                    </div> \
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

        function deleteUser(userId){
            const data = {
                "userId" : userId
            }

            $.ajax({
                url : "/api/admin/user",
                type : "DELETE",
                data : JSON.stringify(data),
                contentType : "application/json",
                dataType : "json",
                async:false
            })
            .done((response) => {
                
                console.log(response)

                updatePageStaticOption()

            }).fail((error) => {
                alert("삭제에 실패하였습니다")
            })
        }

    </script>
</body>
</html>