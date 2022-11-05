<%--
  Created by IntelliJ IDEA.
  User: brendan
  Date: 2022/11/05
  Time: 11:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="user.UserDAO" %>
<%@ page import="user.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="user.TimeTable" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap.css">
    <style>
        h3{
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
    ArrayList<TimeTable> timeTables = new UserDAO().createTimeTable(student_id);

    String userName = timeTables.get(0).getUserName();
    String userID = timeTables.get(0).getUserID();

%>
<h1>시간표 </h1>
<h3>학번: <%=student_id%></h3>
<h3>이름: <%=userName%></h3>
<h3>아이디: <%=userID%></h3>

<table class="table" width ="750" style="text-align: center; border: 1px solid #dddddd">
    <thead>
    <tr>
        <th style="text-align: center;">         </th>
        <th style="text-align: center;">  class_id  </th>
        <th style="text-align: center;">  begin  </th>
        <th style="text-align: center;">  end  </th>
        <th style="text-align: center;">  day  </th>
    </tr>
    </thead>
    <tbody>

<%
        for(int i = 0; i < timeTables.size() ; i++) {
%>
<tr>
    <td><%= (i + 1)%></td>
    <td><%= timeTables.get(i).getClass_id()%></td>
    <td><%= timeTables.get(i).getBegin()%></td>
    <td><%= timeTables.get(i).getEnd()%></td>
    <td><%= timeTables.get(i).getDay()%></td>
</tr>

    <%
    }
    %>

    </tbody>
</table>

</body>
</html>
