<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- ckeditor 4 cdn ------------------------------------------------------- -->
<script src="https://cdn.ckeditor.com/4.17.2/standard/ckeditor.js"></script>
<!-- ---------------------------------------------------------------------- -->


<div class="row">

    <div align="center" id="bbs" class="col-md-8 offset-md-2 my-4">
        <form name="bf" id="bf" role="form" action="/test" method="post" enctype="multipart/form-data" >
            <table class="table">
                <tr>
                    <td><b>제목</b></td>
                    <td>
                        <input type="text" name="review_title" id="review_title" class="form-control" value="">
                    </td>
                    <td><b>장소</b></td>
                    <td>
                        <input type="text" name="" id="" class="form-control" value="">
                    </td>
                </tr>
                <tr>
                    <td><b>작성자</b></td>
                    <td>
                        <input type="text" name="userid" id="userid" value="" class="form-control">
                    </td>
                </tr>
                <tr>
                    <td><b>글내용</b></td>
                    <td>
                        <textarea name="review_content" id="review_content" rows="10" cols="50" class="form-control"></textarea>
                    </td>
                </tr>

                <tr>
                    <td colspan="2" class="text-center">
                        <button type="submit" id="btnWrite" class="btn btn-success">글수정</button>
                    </td>
                </tr>

            </table>


        </form>
    </div><!-- .col end-->
</div><!-- .row end-->