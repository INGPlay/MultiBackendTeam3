<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- ckeditor 4 cdn ------------------------------------------------------- -->
<script src="https://cdn.ckeditor.com/4.17.2/standard/ckeditor.js"></script>
<!-- ---------------------------------------------------------------------- -->


<div class="row">
    <div align="center" id="bbs" class="col-md-8 offset-md-2 my-4">
        <h2>Create Review</h2>
        <form name="bf" id="bf" role="form" action="/review/write" method="post" enctype="multipart/form-data" >
            <table class="table">
                <tr>
                    <td style="width:20%"><b>제목</b></td>
                    <td style="width:80%">
                        <input type="text" name="review_title" id="review_title" class="form-control" >
                    </td>
                </tr>
                <tr>
                    <td style="width:20%"><b>장소</b></td>
                    <td style="width:80%">
                        <input type="text" name="place" id="place"  class="form-control">
                    </td>
                </tr><tr>
                    <td style="width:20%"><b>작성자</b></td>
                    <td style="width:80%">
                        <input type="text" name="user_id" id="userid1" class="form-control">
                    </td>
                </tr>
                <tr>
                    <td style="width:20%"><b>글내용</b></td>
                    <td style="width:80%">
                        <textarea name="review_content" id="review_content" rows="10" cols="50" class="form-control"></textarea>
                    </td>
                </tr>

                <tr>
                    <td colspan="2" class="text-center">
                        <button type="submit" id="btnWrite" class="btn btn-success">작성하기</button>

                    </td>
                </tr>

            </table>


        </form>
    </div><!-- .col end-->
</div><!-- .row end-->