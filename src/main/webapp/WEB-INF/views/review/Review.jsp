<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<!DOCTYPE html>
<html lang="ko">

<head>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Review</title>
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <!-- jQuery library -->
    <script
            src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.min.js"></script>
    <!-- Popper JS -->
    <script
            src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
          type="text/css">
    <link rel="stylesheet" href="/resources/colorful.css">
    <style>
        .pagination{
            justify-content: center;
            display: inline-block;
        }


         .pagination li{ margin: 1px; padding: 1px; background-color: #ffffff; }
         .font{
             font-size: 15px;
         }
    </style>
</head>

<body>
<%@ include file="/WEB-INF/views/template/header.jsp" %>
<div class="container" style="margin-top: 10px">

    <div class="d-flex flex-row bd-highlight mt-2 justify-content-between">

        <form name="psF" id="psF" action="/review/list" method="get" class="d-flex justify-content-start" >
            <select name="select" id ="select" onchange="check()" class="col-auto bd-highlight ms-auto" style="height: 100%" >
                <option value="1" <c:if test="${select eq '1'}">selected</c:if>>최신순</option>
                <option value="2" <c:if test="${select eq '2'}">selected</c:if>>조회순</option>
                <option value="3" <c:if test="${select eq '3'}">selected</c:if>>추천순</option>
            </select>
            <input type="hidden" id="searchType" name="searchType" value="${searchType}"/>

            <div id="changeId"></div>
        </form>

        <form  name="searchF" action="/review/write" method="get" class="d-flex justify-content-end"style="margin-right: 15px">
            <button class="btn main_color ms-auto ml-auto" ><strong>작성하기</strong></button>
        </form>
    </div>

<!-- ------------------------------------------------------- -->

    <table class="table table table-hover mt-2" id="table">
        <thead >
        <th width="10%">번호</th>
        <th width="10%">작성자</th>
        <th width="15%">제목</th>
        <th width="25%">장소</th>
        <th width="10%">수정일</th>
        <th width="10%">조회수</th>
        <th width="10%">추천수</th>
        </thead>
        <tbody>
        <%-- 테이블에 게시글이 없을 경우 --%>
        <c:if test="${list eq null or empty list}">
            <div class = "alert alert-danger">
                <h3>해당 글은 없습니다.</h3>
            </div>
        </c:if>

        <%-- 테이블에 게시글이 있을 경우 --%>
        <c:if test="${list ne null and not empty list}">
            <c:forEach var="vo" items="${list}">
                <tr id="${vo.review_id}" class="font">
                    <td width="10%"><c:out value="${vo.review_id}"/></td>
                    <td width="10%"><c:out value="${vo.user_name}"/></td>
                    <td width="15%"><c:out value="${vo.review_title}"/></td>
                    <td width="25%"><c:out value="${vo.contentName}"/></td>
                    <td width="10%" style="font-size: 10px"><c:out value="${vo.create_date}"/></td>
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
        </form>

        <form id="actionForm" action="/review/list" method="get" >
            <input type = 'hidden' name="pageNum" value="${pageMaker.cri.pageNum}">
            <input type = 'hidden' name="amount" value="${pageMaker.cri.amount}">
            <input type = 'hidden' name="select" value="${pageMaker.cri.sort}">
            <input type = 'hidden' name="searchType" value="${searchType}">
            <input type = 'hidden' name="keyword" value="${keyword}">
            <c:if test="${contentId ne null and not empty contentId}">
                <c:forEach var="vo" items="${contentId}">
                    <input type="hidden" name="contentId" value="${vo}">
                </c:forEach>
            </c:if>
        </form>
    </tr>
    <div>
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <c:if test="${pageMaker.prev}">
                    <li class="page-item">
                        <a class="page-link" href="${pageMaker.startPage -1}">&laquo;</a>&nbsp
                    </li>
                </c:if>

                <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                    <li class="page-item ">
                        <a class="page-link" style="<c:out value="${pageMaker.cri.pageNum == num ? 'background-color: #12bbad' : 'none'}"/> " href="${num}">${num}</a>&nbsp
                    </li>
                </c:forEach>

                <c:if test="${pageMaker.next}">
                    <li class="page-item">
                        <a class="page-link" href="${pageMaker.endPage +1}">&raquo;</a>&nbsp
                    </li>
                </c:if>
            </ul>
            </nav>
    </div>


    <div class="d-flex justify-content-center pb-5">
        <div class="col-5 input-group">

            <!-- 검색 조건 -->
            <select class="col-3" name="searchOption" id="searchOptionSelect" onchange="changeSearchType(this.value)">
                <option value="1" selected>제목</option>
                <option value="2">작성자</option>
                <option value="3">장소</option>
            </select>

            <!-- 검색할 단어 -->
            <input type="text" name="searchWord" class="form-control col-6 border" id="searchWordInput">

            <button type="submit" class="btn btn-outline-dark col-3" onclick="search()">검색</button>

        </div>
    </div>
</div>

<!-- footer -->
<%@ include file="/WEB-INF/views/template/footer.jsp" %>

<style>
    #content .jumbotron, #content .navbar{
        display:none;
    }
</style>

<script>
    var psF = $('#psF');
    function search(){
        var sort = document.getElementById('searchOptionSelect').value;
        var search = document.getElementById('searchWordInput').value;
        if(search==""){alert("검색어를 입력하세요"); return false;}
        var str="";
        if(sort=="3"){
            $.ajax({
                url : "/review/search",
                type : "POST",
                data : {"placeName":search},
                dataType : "json",
                cache:false
            }).done((res)=>{
                $.each(res,(i,vo)=>{
                    str+="<input type='hidden' name='contentId' value='"+res[i].contentId +"'>";
                })

                $('#changeId').html(str);
                $('#psF').submit();
            }).fail((err)=>{
                alert(err.status);
            })
        }else{
            $('#searchType').val(sort);
            str+="<input type='hidden' name='keyword' value='"+search+"'>";
            $('#changeId').html(str);
            $('#psF').submit();
        }

    }



    function check(){
        var numCheck = document.getElementById("select");
        var value = numCheck.options[document.getElementById("select").selectedIndex].value;
        //alert(value+"click 되었습니다");
        return psF.submit();
    }


    // 제목, 작성자, 장소를 선택하면 psF로 내용 전달
    function changeSearchType(type) {
        $('#searchType').val(type);
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
        $(".page-item a").on("click",function(e){
            e.preventDefault();
            actionForm.find("input[name='pageNum']").val($(this).attr("href"));
            actionForm.submit();
        });
    })
</script>
</body>
</html>

