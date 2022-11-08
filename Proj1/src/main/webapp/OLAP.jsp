<%@ page import="user.UserDAO" %>
<%@ page import="user.OLAP" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: brendan
  Date: 2022/11/05
  Time: 5:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap.css">
    <title></title>
</head>
<div class="col-lg-2" style="padding-top: 5px;">
    <button type="button" class="btn btn-primary form-control" onClick="history.go(-1)">메인으로 이동</button>
</div>
<h1> OLAP </h1>

<table class="table" width ="750" style="text-align: center; border: 1px solid #dddddd">
    <thead>
    <tr>
        <th style="text-align: center;">         </th>
        <th style="text-align: center;">  course_id  </th>
        <th style="text-align: center;">   (평점평균 - 각과목 학점) 의 평균 </th>
    </tr>
    </thead>
    <tbody>
    <%
        UserDAO userDAO = new UserDAO(session);
        ArrayList<OLAP> list = userDAO.showOLAP();
        for(int i = 0; i < list.size(); i++) {
    %>
    <tr>
        <td><%= (i + 1)%></td>
        <td><%= list.get(i).getCourse_id()%></td>
        <td><%= list.get(i).getAverage()%></td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
</body>
</html>
