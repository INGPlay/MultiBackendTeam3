<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Review Edit</title>
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
<div class="row">

    <div align="center" id="bbs" class="col-md-8 offset-md-2 my-4">
        <h2>Review view</h2>
        <p>
            <a href="#" onclick="edit()">글수정</a>| <a href="javascript:history.back()">글목록</a>
        <p>

        <form name="bf" id="bf" role="form" method="post">
            <!-- hidden data---------------------------------  -->
            <input type="hidden" name="review_id" id ="review_id" value="${vo.review_id}"/>


            <!-- 원본글쓰기: mode=> write
                 답변글쓰기: mode=> rewrite
                  글수정  : mode=> edit
             -->
            <!-- -------------------------------------------- -->
            <table class="table">
                <tr>
                    <td style="width:20%"><b>제목</b></td>
                    <td style="width:80%">
                        <input type="text" name="review_title" id="review_title" class="form-control" value="${vo.review_title}" readonly>
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
                        <textarea name="review_content" id="review_content" rows="10" cols="50" class="form-control" readonly >${vo.review_content} </textarea>
                    </td>
                </tr>

            </table>
        </form>
    </div><!-- .col end-->
</div><!-- .row end-->

</body>
<script>
    const edit = function(){
        var review_id =$('#review_id').val();
        //alert(1)
        bf.method='post';
        bf.action='edit'
        bf.submit();
    }
</script>
</html>