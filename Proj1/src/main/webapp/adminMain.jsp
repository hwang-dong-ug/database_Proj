<%--
  Created by IntelliJ IDEA.
  User: brendan
  Date: 2022/10/23
  Time: 8:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
<meta name="viewport" content="width=devoce-width", initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">

<html>
<head>
    <title>관리자 메인페이지</title>
</head>
<body>
<h3 style="test-align: center;">관리자 메인페이지</h3>
    <div class="container">
        <div class="col-lg-4">
            <div class="jumbotron" style="padding-top: 20px;">
                <button type="button" class="btn btn-primary form-control"  onClick="location.href='logoutAction.jsp'">로그아웃</button>
            </div>
            <div class="jumbotron" style="padding-top: 20px;">
                <button type="button" class="btn btn-primary form-control" onClick="location.href='changeAll.jsp'">모든 사용자 정보 변경</button>
            </div>
            <div class="jumbotron" style="padding-top: 20px;">
                <button type="button" class="btn btn-primary form-control" onClick="location.href='changeAll.jsp'">강좌 개설/삭제</button>
            </div>
            <div class="jumbotron" style="padding-top: 20px;">
                <button type="button" class="btn btn-primary form-control" onClick="location.href='changeAll.jsp'">과목/학생 정보 통계</button>
            </div>

        </div>

    </div>
</body>
</html>


