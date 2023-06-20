<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


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
        .pagination{
            justify-content: center;
            border: 1px solid #aaa;
        }

         .pagination li{ margin: 1px; padding: 1px; background-color: #ffffff; }
    </style>
</head>

<body>
<div class="row my-3">
    <div class="col-9 text-right">
        <form name="searchF" action="/review/write" method="get" >
            <button class="btn btn-outline-primary">작성</button>
        </form>
    </div>
    <div class="col-3 text-left">
        <form name="psF" id="psF" action="/review/list" method="get">
            <select name="select" id ="select" style="padding:5px" onchange="check()">
                <option value="1" <c:if test="${select eq '1'}">selected</c:if>>최신순</option>
                <option value="2" <c:if test="${select eq '2'}">selected</c:if>>조회순</option>
                <option value="3" <c:if test="${select eq '3'}">selected</c:if>>추천순</option>

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
            <form action="/review/view" method="get" id="hidden" hidden="hidden"> <%--hidden="hidden"--%>
                <input type="text" id="review_id" name="review_id" value="">
                <input type="text" id="pageVo" name="pageVo" value="${pageVO}">
            </form>

            <form id="actionForm" action="/review/list" method="get" hidden="hidden">
                <input type = 'text' name="pageNum" value="${pageMaker.cri.pageNum}">
                <input type = 'text' name="amount" value="${pageMaker.cri.amount}">
                <input type = 'text' name="select" value="${pageMaker.cri.sort}">

            </form>
        </tr>
        <div>
            <ul class="pagination" >
                <c:if test="${pageMaker.prev}">
                    <li class="paginate_button previous">
                        <a href="${pageMaker.startPage -1}">  Previous  </a>&nbsp
                    </li>
                </c:if>

                <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                    <li class="paginate_button ">
                        <a style="<c:out value="${pageMaker.cri.pageNum == num ? 'color:red' : 'none'}"/> " href="${num}">${num}</a>&nbsp

                    </li>
                </c:forEach>

                <c:if test="${pageMaker.next}">
                    <li class="paginate_button next">
                        <a href="${pageMaker.endPage +1}">  Next  </a>&nbsp
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
    function check(){
        var psF = $('#psF');
        var numCheck = document.getElementById("select");

        var value = numCheck.options[document.getElementById("select").selectedIndex].value;
        //alert(value+"click 되었습니다");
        return psF.submit();
    }


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

</body>
</html>

