<%--
  Created by IntelliJ IDEA.
  User: brendan
  Date: 2022/10/23
  Time: 9:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <meta name="viewport" content="width=devoce-width", initial-scale="1">
    <link rel="stylesheet" href="css/bootstrap.css">
    <title>나의 정보 변경</title>
</head>
<body>
<div class="container">
    <div class="col-lg-4">
        <div class="jumbotron" style="padding-top: 20px;">
            <form method="post" action="change_my">
                <%--                위에 action 변경--%>
                <h3 style="test-align: center;">나의 정보 변경</h3>
                <table>
                    <thead>필수정보</thead>
                    <tbody>
                    <tr>
                        <div class="form-group">
                           <th><input type="text" class="form-control" placeholder="기존 아이디" name="old_userID" maxlength="20"></th>
                            <th><input type="text" class="form-control" placeholder="새로운 아이디" name="new_userID" maxlength="20"></th>
                        </div>
                    </tr>
                    <tr>
                        <div class="form-group">
                            <th><input type="password" class="form-control" placeholder="기존 비밀번호" name="old_userPassword" maxlength="20"></th>
                            <th><input type="password" class="form-control" placeholder="새로운 비밀번호" name="new_userPassword" maxlength="20"></th>
                        </div>
                    </tr>
                    </tbody>
                </table>
                <table>
                    <thead>세부정보</thead>
                    <tbody>
                    <tr>
                        <div class="form-group">
                            <input type="password" class="form-control" placeholder="새로운 유저이름" name="new_userName" maxlength="20">
                        </div>
                    </tr>
                    <tr>
                        <div class="form-group">
                            <input type="password" class="form-control" placeholder="새로운 성" name="new_userGender" maxlength="20">
                        </div>
                    </tr>
                    <tr>
                        <div class="form-group">
                            <input type="password" class="form-control" placeholder="새로운 이메일" name="new_userEmail" maxlength="20">
                        </div>
                    </tr>
                    </tbody>
                </table>
                <input type="submit" class="btn btn-primary form-control" value="변경">
            </form>
            <button type="button" class="btn btn-primary form-control" onClick="location.href='main.jsp'">메인으로 이동</button>
        </div>
    </div>
</div>
</body>
</html>
