<%--
  Created by IntelliJ IDEA.
  User: brendan
  Date: 2022/10/23
  Time: 8:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <meta name="viewport" content="width=devoce-width", initial-scale="1">
    <link rel="stylesheet" href="css/bootstrap.css">
    <title>유저 메인페이지</title>
</head>
<body>
<div class="container">
    <div class="col-lg-4">
            <h3 style="test-align: center;">유저 메인페이지</h3>
        <div class="jumbotron" style="padding-top: 20px;">
            <button type="button" class="btn btn-primary form-control" onClick="location.href='logoutAction.jsp'">로그아웃</button>
        </div>
        <div class="jumbotron" style="padding-top: 20px;">
            <button type="button" class="btn btn-primary form-control" onClick="location.href='showClass.jsp'">강의 검색/수강 신청</button>
        </div>
        <div class="jumbotron" style="padding-top: 20px;">
             <button type="button" class="btn btn-primary form-control" onClick="location.href='change_my.jsp'">나의 정보 변경</button>
        </div>
        <div class="jumbotron" style="padding-top: 20px;">
                <button type="button" class="btn btn-primary form-control" onClick="location.href='changeEnroll.jsp'">신청한 강의 변겅</button>
        </div>
    </div>
</div>
</body>
</html>
