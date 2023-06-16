<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


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
    <div class="col-9 text-right">
        <form name="searchF" action="/review/write" method="get" >
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
        <table class="table table-condensed table-striped" id="table">
            <thead>
            <th width="10%">번호</th>
            <th width="10%">작성자</th>
            <th width="20%">제목</th>
            <th width="20%">내용</th>
            <th width="10%">생성 날짜</th>
            <th width="10%">수정 날짜</th>
            <th width="10%">조회수</th>
            <th width="10%">추천수</th>
            </thead>
            <tbody>
                <%-- 테이블에 게시글이 없을 경우 --%>
                <c:if test="${list eq null or empty list}">
                    <div class = "slert alert-danger">
                        <h3>해당 글은 없습니다.</h3>
                    </div>
                </c:if>

                <%-- 테이블에 게시글이 있을 경우 --%>
                <c:if test="${list ne null and not empty list}">
                    <c:forEach var="vo" items="${list}">
                        <tr id="${vo.review_id}">
                                <td width="10%"><c:out value="${vo.review_id}"/></td>
                                <td width="10%"><c:out value="${vo.user_name}"/></td>
                                <td width="20%"><c:out value="${vo.review_title}"/></td>
                                <td width="20%"><c:out value="${vo.review_content}"/></td>
                                <td width="10%"><c:out value="${vo.create_date}"/></td>
                                <td width="10%"><c:out value="${vo.create_date}"/></td>
                                <td width="10%"><c:out value="${vo.review_views}"/></td>
                                <td width="10%"><c:out value="${vo.review_recommends}"/></td>
                        </tr>
                    </c:forEach>
                </c:if>

            </tbody>


        </table>
        <tr>
            <form action="/review/view" method="get" id="hidden" hidden="hidden">
                <input type="text" id="review_id" name="review_id" value="">
            </form>

            <form id="actionForm" action="/review/list" method="get" hidden="hidden">
                <input type = 'text' name="pageNum" value="${pageMaker.cri.pageNum}">
                <input type = 'text' name="amount" value="${pageMaker.cri.amount}">
            </form>
        </tr>
        <div class="pull-left">
            <ul class="pagination">
                <c:if test="${pageMaker.prev}">
                    <li class="paginate_button previous">
                        <a href="${pageMaker.startPage -1}">Previous</a>
                    </li>
                </c:if>

                <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                    <li class="paginate_button">
                        <a href="${num}"> ${num}</a>
                    </li>
                </c:forEach>

                <c:if test="${pageMaker.next}">
                    <li class="paginate_button next">
                        <a href="${pageMaker.endPage +1}">Next</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</div>

<style>
    #content .jumbotron, #content .navbar{
        display:none;
    }
</style>

<script>
    $(()=>{
        var actionForm = $("#actionForm");
        $(document).on('click', 'tr', function(event) {

            var id = $(this).attr('id');
            //alert(id);

            if(typeof id == "undefined" || id == "" || id ==null){
                return false;
            }
            else{
                $('#review_id').val(id);
                //console.log($('#review_id').val());


                return $('#hidden').submit() ;

            }

        });
        $(".paginate_button a").on("click",function(e){
            e.preventDefault();
            actionForm.find("input[name='pageNum']").val($(this).attr("href"));
            actionForm.submit();
        });
    })
</script>


<!-- footer -->
<div class="jumbotron jumbotron-fluid text-center" style="margin-bottom: 0">
    <h2>하단바</h2>
</div>
</body>
</html>

