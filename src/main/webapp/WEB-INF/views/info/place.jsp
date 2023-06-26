<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <!-- header -->
    <%@ include file="/WEB-INF/views/template/header.jsp" %>

    <div class="container">

        <table class="table table table-hover">
            <thead>
              <tr>
                <th scope="col">항목</th>
                <th scope="col">정보</th>
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
        const contentTypeId = ${contentTypeId};
        const contentId = ${contentId};

        const contentRowName = {
            // "contenttypeid" : "콘텐츠타입ID",
            "accomcount" : "수용인원",
            "chkbabycarriage" : "유모차대여정보",
            "chkcreditcard" : "신용카드가능정보",
            "chkpet" : "애완동물동반가능정보",
            "expagerange" : "체험가능연령",
            "expguide" : "체험안내",
            "heritage1" : "세계문화유산유무",
            "heritage2" : "세계자연유산유무",
            "heritage3" : "세계기록유산유무",
            "infocenter" : "문의및안내",
            "opendate" : "개장일",
            "parking" : "주차시설",
            "restdate" : "쉬는날",
            "useseason" : "이용시기",
            "usetime" : "이용시간",
            "accomcountculture" : "수용인원",
            "chkbabycarriageculture" : "유모차대여정보",
            "chkcreditcardculture" : "신용카드가능정보",
            "chkpetculture" : "애완동물동반가능정보",
            "discountinfo" : "할인정보",
            "infocenterculture" : "문의및안내",
            "parkingculture" : "주차시설",
            "parkingfee" : "주차요금",
            "restdateculture" : "쉬는날",
            "usefee" : "이용요금",
            "usetimeculture" : "이용시간",
            "scale" : "규모",
            "spendtime" : "관람소요시간",
            "agelimit" : "관람가능연령",
            "bookingplace" : "예매처",
            "discountinfofestival" : "할인정보",
            "eventenddate" : "행사종료일",
            "eventhomepage" : "행사홈페이지",
            "eventplace" : "행사장소",
            "eventstartdate" : "행사시작일",
            "festivalgrade" : "축제등급",
            "placeinfo" : "행사장위치안내",
            "playtime" : "공연시간",
            "program" : "행사프로그램",
            "spendtimefestival" : "관람소요시간",
            "sponsor1" : "주최자정보",
            "sponsor1tel" : "주최자연락처",
            "sponsor2" : "주관사정보",
            "sponsor2tel" : "주관사연락처",
            "subevent" : "부대행사",
            "usetimefestival" : "이용요금",
            "distance" : "코스총거리",
            "infocentertourcourse" : "문의및안내",
            "schedule" : "코스일정",
            "taketime" : "코스총소요시간",
            "theme" : "코스테마",
            "accomcountleports" : "수용인원",
            "chkbabycarriageleports" : "유모차대여정보",
            "chkcreditcardleports" : "신용카드가능정보",
            "chkpetleports" : "애완동물동반가능정보",
            "expagerangeleports" : "체험가능연령",
            "infocenterleports" : "문의및안내",
            "openperiod" : "개장기간",
            "parkingfeeleports" : "주차요금",
            "parkingleports" : "주차시설",
            "reservation" : "예약안내",
            "restdateleports" : "쉬는날",
            "scaleleports" : "규모",
            "usefeeleports" : "입장료",
            "usetimeleports" : "이용시간",
            "accomcountlodging" : "수용가능인원",
            "benikia" : "베니키아여부",
            "checkintime" : "입실시간",
            "checkouttime" : "퇴실시간",
            "chkcooking" : "객실내취사여부",
            "foodplace" : "식음료장",
            "goodstay" : "굿스테이여부",
            "hanok" : "한옥여부",
            "infocenterlodging" : "문의및안내",
            "parkinglodging" : "주차시설",
            "pickup" : "픽업서비스",
            "roomcount" : "객실수",
            "reservationlodging" : "예약안내",
            "reservationurl" : "예약안내홈페이지",
            "roomtype" : "객실유형",
            "scalelodging" : "규모",
            "subfacility" : "부대시설 (기타)",
            "barbecue" : "바비큐장여부",
            "beauty" : "뷰티시설정보",
            "beverage" : "식음료장여부",
            "bicycle" : "자전거대여여부",
            "campfire" : "캠프파이어여부",
            "fitness" : "휘트니스센터여부",
            "karaoke" : "노래방여부",
            "publicbath" : "공용샤워실여부",
            "publicpc" : "공용 PC실여부",
            "sauna" : "사우나실여부",
            "seminar" : "세미나실여부",
            "sports" : "스포츠시설여부",
            "refundregulation" : "환불규정",
            "chkbabycarriage shopping" : "유모차대여정보",
            "chkcreditcard shopping" : "신용카드가능정보",
            "chkpetshopping" : "애완동물동반가능정보",
            "culturecenter" : "문화센터바로가기",
            "fairday" : "장서는날",
            "infocentershopping" : "문의및안내",
            "opendateshopping" : "개장일",
            "opentime" : "영업시간",
            "parkingshopping" : "주차시설",
            "restdateshopping" : "쉬는날",
            "restroom" : "화장실설명",
            "saleitem" : "판매품목",
            "saleitemcost" : "판매품목별가격",
            "scaleshopping" : "규모",
            "shopguide" : "매장안내",
            "chkcreditcardfood" : "신용카드가능정보",
            "discountinfofood" : "할인정보",
            "firstmenu" : "대표메뉴",
            "infocenterfood" : "문의및안내",
            "kidsfacility" : "어린이놀이방여부",
            "opendatefood" : "개업일",
            "opentimefood" : "영업시간",
            "packing" : "포장가능",
            "parkingfood" : "주차시설",
            "reservationfood" : "예약안내",
            "restdatefood" : "쉬는날",
            "scalefood" : "규모",
            "seat" : "좌석수",
            "smoking" : "금연/흡연여부",
            "treatmenu" : "취급메뉴",
            "lcnsno" : "인허가번호"
        }

        $.ajax({
            url : "/api/tour/detail/" + contentTypeId + "/" + contentId,
            type : "GET",
            contentType: "application/json",
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
            Object.entries(response).forEach((entry) => {
                console.log(entry[0], entry[1])
                let key = entry[0]
                let value = entry[1]

                // 등록된 컬럼에 존재하는 경우에만 표시
                if (contentRowName[key]){
                    result += " \
                        <tr> \
                            <th scope='row'>" + contentRowName[key] + "</th> \
                            <td>" + value + "</td> \
                        </tr> \
                    ";
                }

            })

            listRow.innerHTML = result;
        }
    </script>

</body>
</html>