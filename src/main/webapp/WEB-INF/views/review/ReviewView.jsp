<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Review Edit</title>
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.hnbhjbjhbjhbjsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

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
        #content {
            overflow-y: scroll;
            -ms-overflow-style: none; /* 인터넷 익스플로러 */
            scrollbar-width: none; /* 파이어폭스 */
        }
        #content::-webkit-scrollbar {
            display: none; /* 크롬, 사파리, 오페라, 엣지 */
        }

        .div{
            width: 40%;
            margin: 40px auto;
            position:relative;
            border: none;
        }
        .div img{
            position: absolute;
            top:40%;
            left:50%;
            width:20%;
            transform: translate(-50%, -50%);

        }
        .div span{
            position: absolute;
            top:40%;
            left:50%;
            width:10%;
            transform: translate(-50%, -50%);
            text-align: center;
        }
        .link{
            font-size: 14px;
        }
        .del{
            width: 10px;
            height: 10px;
            margin-top: auto;
        }
        .date{
            font-size: 10px;
        }
        .fontRe{
            font-size:18px;
            font-weight: bold;
            color: #12bbad;
        }

        button{
           background-color: #12bbad;
        }
        .tableTr{
            height: 10px;
        }
        .center{
            text-align: center;
        }
        .rerecom{
            width: 90%;
        }
        .link:hover{

        }


    </style>
</head>
<body>
<%@ include file="/WEB-INF/views/template/header.jsp" %>


<div class ="container" style="margin-top: 30px">
        <h2 class="center">Review view</h2>
        <p class="center" >
            <c:if test="${result eq 'yes'}">
            <a href="#" onclick="edit()">글수정</a>|

                </c:if><a href = "#" onclick="goList()">글목록</a>
        <p>

        <form name="bf" id="bf" role="form">
            <!-- hidden data---------------------------------  -->
            <input type="hidden" name="review_id" id ="review_id" value="${vo.review_id}"/>


            <!-- 원본글쓰기: mode=> write
                 답변글쓰기: mode=> rewrite
                  글수정  : mode=> edit
             -->
            <!-- -------------------------------------------- -->
            <table class="table">
                <tr>
                    <td style="width:20%" class="center"><b>제목</b></td>
                    <td style="width:80%">
                        <input type="text" name="review_title" id="review_title" class="form-control" value="${vo.review_title}" readonly>
                    </td>

                </tr>
                <tr>
                    <td style="width:20%" class="center"><b>작성자</b></td>
                    <td style="width:80%">
                        <input type="hidden" name="user_id" id="user_id" value="${vo.user_id}" readonly />
                        <input type="text" name="user_name" id="user_name" value="${vo.user_name}" readonly />
                    </td>
                </tr>
                <tr>
                    <td style="width:20%" class="center"><b>장소</b></td>
                    <td align="left">
                        <span>${PlaceName}</span>
                    </td>
                    <td><input type="hidden" name="contentId" id="contentId" value="${vo.contentId}"></td>
                </tr>


                <tr>
                    <td style="width:20%" class="center"><b>글내용</b></td>
                    <td style="width:80%; border-bottom-style: hidden;text-align: left ">
                        <div contenteditable="true">
                        <c:if test="${vo.filename ne null}">
                                <img src="/resources/upload/<c:out value="${vo.filename}"/>" style="width:30%; height: 150px"><br><br>
                       </c:if>
                            ${vo.review_content}
                            </div>
                        </td>
                    </tr>


                    <tr>
                         <td colspan="2" style="border-bottom-style: hidden">
                             <div onclick="com()"width="100px" height="100px"  class="div">
                                 <img class="img" src="/image/re.png"/>
                                 <input type="hidden" class="img_text" name="review_recommends" id="review_recommends" value="${vo.review_recommends}" readonly />
                                 <span id="resultRecommends">${vo.review_recommends} </span>
                             </div>
                         </td>
                        <td>
                            <input type="hidden" name="review_recommends" id="ConnectUserName" value="${ConnectUserName}" readonly />
                        </td>

                    </tr>
                </table>
            </form>
    <div id="comment_list" align="center" id="b" class="container"></div>

    </div><!-- .row end-->
    <div class="container" style="color: #12bbad">

        <form>
            <table class="table table table-hover">
                <tr id="commentInsert">
                    <td width="90%" height="100px">
                        <textarea id="content" style="width: 100%; height: 100%;" name="content"></textarea>
                    </td>
                    <td width="10%" class="center" style="vertical-align: middle"><div style="color: #12bbad;"><input type="button" onclick="insertComment()"  style="background-color: #12bbad;height: 100% " value="등록"/></div>
                    </td>
                </tr>
            </table>
        </form>

    </div>
        <form method="get" action="list" name="reset" id="reset"></form>

    <!-- footer -->
    <%@ include file="/WEB-INF/views/template/footer.jsp" %>

    </body>
    <script>
        const edit = function(){
            bf.method='post';
            bf.action='edit'
            bf.submit();
        }

        const goList = function (){
            reset.submit();
        }

        const com = function(){
            const review_id = document.getElementById('review_id').value;
            $.ajax({
                type:'post',
                url:'/review/view',
                dataType:'json',
                data:{"review_id":review_id},
                cache:false
            }).done((res)=>{
                $("#resultRecommends").text(res.review_recommends);
                if(res.result=="1"){
                    alert("추천했습니다");
                }else{
                    alert("추천을 취소했습니다");
                }
            }).fail((err)=>{
                alert(err.status);
            })
        }

        const deleteComment = function(id){
            $.ajax({
                type:'post',
                url:'/review/deleteComment',
                dataType:'text',
                data:{"id":id},
                cache:false,
            }).done((res)=>{
                alert("삭제되었습니다.")
                init(1);
            }).fail((err)=>{
                alert(err.status)
            })
        }


        const insertComment = function(){
            let rid=$('#review_id').val();
            let con=$('#content').val();
            let uid=$('#user_id').val();

            if(con==""){
                alert("내용을 넣어주세요");
                return false;
            }
            let jsonData={
                review_id:rid,
                content:con,
                user_id:uid
            };

            $.ajax({
                type:'post',
                url:'/review/insert',
                dataType:'text',
                contentType:'application/json; charset=UTF-8',
                data:JSON.stringify(jsonData),
                cache: false
            }).done((res)=>{
                alert("댓글을 작성했습니다");
                document.getElementById("content").value='';
                init(1);
            }).fail((err)=>{
                alert(err.status)
            })

        }

        const recomment = function(id){
            //alert(id);
            var list= id.split('/');
            var x = document.getElementsByClassName(list[0]+"div");
            var y = document.getElementById(list[0]+"re"+list[1]);

            var i;
            for (i=0; i < x.length; i++) {
            var obj=x[i];
                if (obj.style.display == "none") {
                    obj.style.display = "";
                }
                else {
                    y.value="" ;
                    obj.style.display = "none";
                }
            }
        }

        const CheckMessage = function(id){
            var list= id.split('btn');
            var total = list[0]+"re"+list[1];
            var x = document.getElementById(total);
            var reset = list[0]+"/"+list[1];
            var message = x.value;

            if(message.trim()!=0){
                insertCommentRe(list[0],message);
            }else{
                recomment(reset);
            }
        }

        const insertCommentRe = function(list,message){
            var main = document.getElementById('commentInsert');
            let jsonData={
                comment_id:list,
                content:message
            };

            $.ajax({
                type:'post',
                url:'/review/recomment',
                dataType:'text',
                contentType:'application/json; charset=UTF-8',
                data:JSON.stringify(jsonData),
                cache: false
            }).done((res)=>{
                alert("대댓글이 추가되었습니다.")
                main.style.display='';
                init(1);
            }).fail((err)=>{
                alert(err.status)
            })
        }


        const showComment = function(response,i){
            let CUS=$('#ConnectUserName').val();
            let str = "<table id='table1' style='width: 100%;' >"
            let res = response.commentList;
            /* 1행 */
            str+='<tr class="tableTr">';
                str+='<td colspan="4">';
                str+=`<div><b>댓글(\${response.totalCount})</b> &nbsp;&nbsp;`;
                if(i==1){
                    str+='<a class="link" id="link1" style="color: red" onclick="init(1)">등록순</a> |&nbsp;';
                    str+='<a class="link" id="link2" style="color: black"onclick="init(2)">최신순</a></div>';
                }else{
                    str+='<a class="link" id="link1" style="color: black" onclick="init(1)">등록순</a> |&nbsp;';
                    str+='<a class="link" id="link2" style="color: red" onclick="init(2)">최신순</a></div>';
                }

                str+='</td>';
            str+='</tr>';

            $.each(res,(i,vo)=>{
                /* 2행 */
                str+='<tr class="tableTr">';
                    str+='<td colspan="4">';
                    str+='<hr style="color: #12bbad;">';
                    str+='</td>';
                str+='</tr>';


                /* 3행 */
                str+='<tr style="margin-top: 10px;">';
                str+='<td colspan="2"><span style="text-align: left">';
                if(res[i].comment_depth!=0){
                str+='<span class="fontRe">ㄴ</span> &nbsp;'
                }
                str+=res[i].user_name;
                str+='</span><span class="date" style="text-align: left">&nbsp;&nbsp;(';
                str+=res[i].create_date;
                str+=')</span>';
                str+='</td>';

                str+='<td class="date">';


                if( res[i].user_id == CUS || CUS=="1"){
                    str+='<td colspan="2">';
                    str += '<p style="text-align: right"><img  class = "del" src="/image/delete.png" onclick="deleteComment(this.id)" id="' + res[i].comment_id + '"/></p>';
                    str+='</td>'
                }
                str+='</tr>';

                /* 4행 */
                str+='<tr>';
                str+='<td colspan="4"><p>';
                str+=res[i].content;
                str+='</p></td>';

                if(res[i].comment_depth==0) {
                    /* 5행 메인 댓글일 경우*/
                    str += '<tr class="tableTr">';
                    str += '<td colspan="4"><p style="text-align: left"><button onclick="recomment(this.id)" id="' + res[i].comment_group + '/' + res[i].comment_depth + '"> 답글 </button></p></td>'
                    str += '</tr>';
                    /* 6행 메인 댓글일 경우*/
                    str += '<tr class="tableTr">';
                    str += '<td colspan="3" class="rerecom"><div style="display: none; text-align: center" class="'+res[i].comment_group+'div">';
                    str += '<input id="' + res[i].comment_group + 're' +res[i].comment_depth+'" style="width: 100%;">';
                    str+='</div></td>';
                    str+='<td colspan="1"><div style="display: none;text-align: right" class="'+res[i].comment_group+'div">';
                    str+='<button onclick="CheckMessage(this.id)" id="' + res[i].comment_group + 'btn' + res[i].comment_depth + '"> 등록</input>';
                    str += '</div></td>';
                    str += '</tr><tfoot></tfoot>';
                }
            })
            str+='</table>';

            $('#comment_list').html(str);
        }

        const init = function(i){
            let rid = $('#review_id').val();
            //alert(rid);
            $.ajax({
                type:'get',
                url:'/review/comment',
                data:{"review_id":rid,
                      "sort":i
                },
                dataType:'json',
                cache:false
            }).done((res)=>{
                //alert('댓글 조회 확인');
                //alert(JSON.stringify(res));
                showComment(res,i);
            }).fail((err)=>{
                alert(err.status);
            })
        }


        $(()=>{
            init(1);}
        )

    </script>
    </html>