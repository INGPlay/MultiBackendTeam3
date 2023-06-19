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

        <!-- Menu -->
        <div class="d-flex flex-row-reverse bd-highlight">
            <button class="btn btn-primary" onClick="location.href='/'">홈</button>
        </div>

        <table class="table table table-hover">
            <thead>
              <tr>
                <th scope="col">예측시간</th>
                <th scope="col">일 최저기온</th>
                <th scope="col">일 최고기온</th>
                <th scope="col">시간 평균 온도</th>
                <th scope="col">강수량</th>
                <th scope="col">하늘형태</th>
                <th scope="col">강수형태</th>
                <th scope="col">강수확률</th>
                <th scope="col">강수량</th>
                <th scope="col">강설량</th>
              </tr>
            </thead>
            <tbody id = "listRow">
              <!-- <tr>
                <th scope="row">{id}</th>
                <td>{title}</td>
              </tr> -->
            </tbody>
        </table>
    </div>

    <script>
        // Error 무시 : JSP 템플릿 언어임
        const posX = ${posX};
        const posY = ${posY};

        let data = {
            "posX" : posX,
            "posY" : posY
        }

        $.ajax({
            url : "/api/wheather/",
            type : "GET",
            contentType: "application/json",
            data : data,
            dataType : "json"
        }).done((response) => {

            console.log(response)
            viewInfoDetail(response)
        }).fail((error) => {
            // {"readyState":4,"responseText":"{\"status\":404,\"message\":\"NOT FOUND\"}","responseJSON":{"status":404,"message":"NOT FOUND"},"status":404,"statusText":"error"}
            let response = error["responseJSON"];
            console.log(response["message"])
        })


        function viewInfoDetail(response){
            let listRow = document.getElementById("listRow")

            let result = "";
            response.forEach(wheather => {

                result += " \
                        <tr> \
                            <th scope='row'>" + wheather["forecastDateTime"] + "</th> \
                            <td>" + wheather["dayMinTemparature"] + "</td> \
                            <td>" + wheather["dayMaxTemparature"] + "</td> \
                            <td>" + wheather["hourTemparature"] + "</td> \
                            <td>" + wheather["rainMilli"] + "</td> \
                            <td>" + wheather["skyForm"] + "</td> \
                            <td>" + wheather["rainForm"] + "</td> \
                            <td>" + wheather["rainProbability"] + "</td> \
                            <td>" + wheather["rainAmount"] + "</td> \
                            <td>" + wheather["snowAmount"] + "</td> \
                        </tr> \
                    ";
            })

            listRow.innerHTML = result;
        }
    </script>

</body>
</html>