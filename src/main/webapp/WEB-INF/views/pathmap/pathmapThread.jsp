<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <%@ include file="./template/staticTemplate.jsp" %>

</head>
<body>
    <div class="container">
        <div class="d-flex flex-row-reverse bd-highlight">
            <button class="btn main_color" onClick="location.href='/pathmap/mark'">
                <strong>작성하기</strong>
            </button>
        </div>

        <table class="table table table-hover">
            <thead>
              <tr>
                <th scope="col">ID</th>
                <th scope="col">제목</th>
                <th scope="col">작성자</th>
                <th scope="col">작성일</th>
                <th scope="col">추천수</th>
                <th scope="col">조회수</th>
              </tr>
            </thead>
            <tbody id = "listRow">
              <!-- <tr>
                <th scope="row">{id}</th>
                <td>{title}</td>
                <td>{username}</td>
                <td>{createDate}</td>
                <td>{recommends}</td>
                <td>{views}</td>
              </tr> -->
            </tbody>
        </table>
    </div>

    <script>
        
        initPathList()

        function initPathList(){

            $.ajax({
					url : "/api/pathmap",
					type : "GET",
					// data : params,
					contentType: "application/json",
					dataType : "json"
				}).done((response) => {
                    
                    let listRow = document.getElementById("listRow")

                    let result = ''
                    response.forEach(r => {

                        result += " \
                            <tr onclick='window.location.href= \"/pathmap/" + r['pathId'] + "\"'> \
                                <th scope='row'>" + r['pathId'] +"</th> \
                                <td>" + r['pathTitle'] + "</td> \
                                <td>" + r['username'] + "</td> \
                                <td>" + r['createDate'] + "</td> \
                                <td>" + r['pathRecommends'] + "</td> \
                                <td>" + r['pathViews'] + "</td> \
                            </tr> \
                        "
                    })

                    document.getElementById("listRow").innerHTML = result

                }).fail((error) => {
                    alert("불러오기에 실패하였습니다.")
                })
        }
    </script>
</body>
</html>