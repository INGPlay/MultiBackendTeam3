<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<!-- ckeditor 4 cdn ------------------------------------------------------- -->
<script src="https://cdn.ckeditor.com/4.17.2/standard/ckeditor.js"></script>
<!-- ---------------------------------------------------------------------- -->
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
    #btn{
        text-align: center;
    }

</style>
<%@ include file="/WEB-INF/views/template/header.jsp" %>

<div class="row">
    <div align="center" id="bbs" class="col-md-8 offset-md-2 my-4">
        <h2>Create Review</h2>
        <form name="bf" id="bf" role="form" action="/review/write" method="post" enctype="multipart/form-data" >
            <table class="table table table-hover">
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
                        <input readonly type="text" name="user_name" id="username" class="form-control" value="${user_name} ">
                    </td>
                </tr>
                <tr>
                    <td style="width:20%" class="center"><b>장소</b></td>
                    <td align="left">
                        <select id="areaLargeSelect" class="select" name="areaLarge" onchange="getAreaSmallCode(this.value)">
                            <option value="">지역</option>
                        </select>

                        <select id="areaSmallSelect" class="select" name="areaSmall">
                            <option value="">시군구</option>
                        </select>
                        <select id ="areaResultSelect" class="select" name="contentId" onchange="result()">
                            <option value="">검색 내용</option>
                        </select>

                        <input id="keywordSearch" name ="placeName" type="text" class="form-control" style="width: 70%">
                        <button style="text-align: right" type="button" onclick="searchTourInfoKeyword(document.getElementById('keywordSearch').value)">→</button>
                    </td>
                </tr>
                <tr>
                    <td style="width:20%"><b>글내용</b></td>
                    <td style="width:80%">
                        <textarea name="review_content" id="review_content" rows="10" cols="50" class="form-control"></textarea>
                    </td>
                </tr>


                <tr>
                    <td colspan="2" id="btn">
                        <button type="submit" id="btnWrite" class="align-items-center flex-shrink-0 p-2 link-dark text-decoration-none" style="background-color: #12bbad; padding: 5px;">작성하기</button>
                    </td>
                </tr>

            </table>
        </form>


    </div><!-- .col end-->
</div><!-- .row end-->
<script>
    renewAreaLargeCode();
    let markContentTypeCode = "12";
    <%--  장소 검색 이벤트 지정--%>
    document.getElementById("keywordSearch").addEventListener("keyup", function (event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            searchTourInfoKeyword(this.value)
            alert(this);
        }
    });

    function result(){
        const txt = $('#areaResultSelect option:checked').text();

        $('#keywordSearch').val(txt);
    }

    function renewAreaLargeCode(){
        $.ajax({
            url : "/api/tour/area/code",
            type : "GET",
            contentType: "application/json",
            dataType : "json"
        }).done((response) => {
            console.log(response)

            let areaLargeSelect = document.getElementById("areaLargeSelect")

            areaLargeSelect.innerHTML = ""
            let result = "<option value=''>지역</option>";
            response.forEach(r => {
                result += "<option value='"+ r["code"] +"'>" + r["name"] + "</option>"
            })

            areaLargeSelect.innerHTML = result;

        }).fail((error) => {
            console.log(error["responseJSON"]["message"])
        })
    }


    /* 메인 클릭시 소분류 장소 가져오기*/
    function getAreaSmallCode(largeCode){
        $.ajax({
            url : "/api/tour/area/code/" + largeCode,
            type : "GET",
            contentType : "application/json",
            dataType : "json"
        }).done((response) => {
            console.log(response);

            let areaSmallSelect = document.getElementById("areaSmallSelect")

            areaSmallSelect.innerHTML = ""
            let result = "<option value=''>시군구</option>";
            response.forEach(r => {
                result += "<option value='"+ r["code"] +"'>" + r["name"] + "</option>"
            })

            areaSmallSelect.innerHTML = result;

        }).fail((error) => {
            console.log(error["responseJSON"]["message"])
        })
    }


    // 검색 후

    function searchTourInfoKeyword(keyword){
        areaLargeSelect = document.getElementById("areaLargeSelect")
        areaSmallSelect = document.getElementById("areaSmallSelect")

        let data = {
            "keyword" : keyword,
            "areaLargeCode" : areaLargeSelect.value,
            "areaSmallCode" : areaSmallSelect.value,
            "pageSize" : 100,
            "pageNo" : 1,
            "contentTypeCode" : markContentTypeCode
        }

        $.ajax({
            url : "/api/tour/keyword",
            type : "GET",
            data : data,
            contentType : "application/json",
            dataType : "json"
        }).done((res) => {
            //alert(JSON.stringify(res))
            let ars = document.getElementById("areaResultSelect")
            ars.innerHTML = ""
            let result = "<option value=''>장소</option>";
            res.forEach(r => {
                result += "<option value='"+ r["contentId"] +"'>" + r["title"] + "</option>"
            })

            ars.innerHTML = result;

        }).fail((error) => {
            console.log(error)
            console.log(error["responseJSON"]["message"])
            if (error["status"] === 404){
                let ars = document.getElementById("areaResultSelect")
                let result = "<option value=''>검색 결과가 없습니다</option>";
                ars.innerHTML = result;
            }
        })
    }










</script>