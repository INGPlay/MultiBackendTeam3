<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:set var="myctx" value="${pageContext.request.contextPath}" />
<!-- "/myapp" 컨텍스트명 -->

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Review</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script
            src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
    <!-- Popper JS -->
    <script
            src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

    <style>
        #content {
            overflow-y: scroll;
            -ms-overflow-style: none; /* 인터넷 익스플로러 */
            scrollbar-width: none; /* 파이어폭스 */
        }
        #content::-webkit-scrollbar {
            display: none; /* 크롬, 사파리, 오페라, 엣지 */
        }
    </style>
</head>

<body>
<div class="row my-5">
    <div class="col-12 text-center">
        <h2>상단바</h2>
    </div>
</div>
<div class="row my-3">
    <div class="col-9 text-center">
        <!-- 검색 폼 시작 ---------------------------------------------- -->
        <form name="searchF" action="/review/edit" method="get" onsubmit="a">
            <button class="btn btn-outline-primary">작성</button>
        </form>
    </div>
    <div class="col-3 text-left">
        <form name="psF" action="list">
            <select name="pageSize" style="padding:5px" onchange="">
                <option value="" selected>추천순</option>
                <option value="" > 조회순</option>
            </select>
        </form>
    </div>
</div>
<!-- ------------------------------------------------------- -->
<div class="row">
    <div class='col-10 offset-1'>
        <table class="table table-condensed table-striped">
            <thead>
            <th width="10%">작성번호</th>
            <th width="40%">제목</th>
            <th width="20%">내용</th>
            <th width="20%">생성 날짜</th>
            <th width="20%">수정 날짜</th>
            <th width="10%">조회수</th>
            <th width="10%">추천수</th>
            </thead>
            <tbody>

            </tbody>
            <tfoot>
            <tr>
                <td colspan="3"  style="text-align:center">
                    ${pageNavi}
                </td>
                <td colspan="2">
                    총 게시글수: <span class="text-primary"> <c:out value="${paging.totalCount}"/> 개</span><br>
                    <span class="text-danger">
							<c:out value="${paging.cpage}"/>
						</span>/
                    <c:out value="${paging.pageCount}"/>
                    pages
                </td>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
<style>
    #content .jumbotron, #content .navbar{
        display:none;
    }

</style>
</div>

<!-- footer -->
<div class="jumbotron jumbotron-fluid text-center" style="margin-bottom: 0">
    <h2>하단바</h2>
</div>
</body>
</html>

