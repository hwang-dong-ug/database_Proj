<%--
  Created by IntelliJ IDEA.
  User: brendan
  Date: 2022/11/05
  Time: 2:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="user.UserDAO" %>
<%@ page import="user.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="user.TimeTable" %>
<%@ page import="user.Credit" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap.css">
    <style>
        h3 {
            text-align: left;
        }
    </style>
    <title>Title</title>
</head>
<body>
<div class="col-lg-2" style="padding-top: 5px;">
    <button type="button" class="btn btn-primary form-control" onClick="location.href='changeAll.jsp'">이전 페이지</button>
</div>


<%
    String student_id = request.getParameter("student_id");
    ArrayList<Credit> credits = new UserDAO(session).lookUpCredit(student_id);

    String userName = credits.get(0).getUserName();
    String userID = credits.get(0).getUserID();

%>
<h1>성적 </h1>
<h3>학번: <%=student_id%>
</h3>
<h3>이름: <%=userName%>
</h3>
<h3>아이디: <%=userID%>
</h3>

<table class="table" width="750" style="text-align: center; border: 1px solid #dddddd">
    <thead>
    <tr>
        <th style="text-align: center;"></th>
        <th style="text-align: center;"> course_id</th>
        <th style="text-align: center;"> year</th>
        <th style="text-align: center;"> grade</th>
    </tr>
    </thead>
    <tbody>

    <%
        for (int i = 0; i < credits.size(); i++) {
    %>
    <tr>
        <td><%= (i + 1)%>
        </td>
        <td><%= credits.get(i).getCourse_id()%>
        </td>
        <td><%= credits.get(i).getYear()%>
        </td>
        <td><%= credits.get(i).getGrade()%>
        </td>
    </tr>

    <%
        }
    %>
    </tbody>
</table>
</body>
</html>
