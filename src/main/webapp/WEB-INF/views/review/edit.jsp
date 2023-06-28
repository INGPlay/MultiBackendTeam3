<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Review Edit</title>
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
      #update{
          background-color: #12bbad;
      }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/views/template/header.jsp" %>
<div class="row">

    <div align="center" id="bbs" class="col-md-8 offset-md-2 my-4">
        <h2>Review Edit</h2>
        <p>
            <a href="#" onclick="back() ">이전 단계로</a>| <a href="list">글목록</a>
        <p>

        <form name="bf" id="bf" role="form" method="post">
            <!-- hidden data---------------------------------  -->
            <input type="hidden" name="review_id" id ="review_id" value="${vo.review_id}"/>
            <input type="hidden" name="pagingvo" value="${pageVO}">
            <input type="hidden" name="review_recommends" id="review_recommends" value="${vo.review_recommends}" readonly />
            <!-- 원본글쓰기: mode=> write
                 답변글쓰기: mode=> rewrite
                  글수정  : mode=> edit
             -->
            <!-- -------------------------------------------- -->
            <table class="table table table-hover">
                <tr>
                    <td style="width:20%"><b>제목</b></td>
                    <td style="width:80%">
                        <input type="text" name="review_title" id="review_title" class="form-control" value="${vo.review_title}">
                    </td>
                </tr>

                <tr>
                    <td style="width:20%"><b>작성자</b></td>
                    <td style="width:80%">
                        <input type="hidden" name="user_id" id="user_id" value="${vo.user_id}" readonly />
                        <input type="text" name="user_name" id="user_name" value="${vo.user_name}" readonly />

                    </td>
                </tr>
                <tr>
                    <td style="width:20%"><b>글내용</b></td>
                    <td style="width:80%">
                        <textarea name="review_content" id="review_content" rows="10" cols="50" class="form-control">${vo.review_content}</textarea>
                    </td>
                </tr>

                <tr>
                    <td colspan="2" class="text-center" style="border-bottom-style: none;">
                        <button id="update" onclick = "edit('update')" class="btn btn-success">글수정</button>
                        <button id="delete" onclick = "edit('delete')" class="btn btn-warning">삭제</button>
                    </td>

                </tr>
            </table>

        </form>
    </div><!-- .col end-->
</div><!-- .row end-->

<!-- footer -->
<%@ include file="/WEB-INF/views/template/footer.jsp" %>
</body>
<script>
    const edit = function(mode){
        if(mode =='update'){
            bf.action="/review/update";
        }
        else if (mode == 'delete'){
            bf.action="/review/delete";

            alert("해당 게시글을 삭제합니다");

        }
        bf.submit();
    }
    const back = function(){
        history.back();
    }
</script>
</html>