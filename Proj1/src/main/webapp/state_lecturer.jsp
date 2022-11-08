<%@ page import="user.UserDAO" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: brendan
  Date: 2022/11/05
  Time: 2:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap.css">
    <style>
        h3,label {
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
    ArrayList<String> info = new UserDAO(session).stateAndLecturer(student_id);
    String userID = info.get(0);
    String userName = info.get(1);
    String lecturer_name = info.get(2);
    String state = info.get(3);
%>
<h1>재학/휴학/자퇴 등 상태 변경 및 지도 교수 조회 </h1>
<h3>학번: <%=student_id%>
</h3>
<h3>이름: <%=userName%>
</h3>
<h3>아이디: <%=userID%>
</h3>
<h3>지도교수: <%=lecturer_name%>
</h3>
<h3>재학 여부: <%=state%>
</h3>


<form action="ChangeState" method="post">
    <input type="text" readonly name="student_id" value="<%=student_id%>">
    <label><input type="radio" name="state" value="재학"> 재학</label>
    <label><input type="radio" name="state" value="휴학"> 휴학</label>
    <label><input type="radio" name="state" value="자퇴"> 자퇴</label>
    <input type="submit" value="변경">
</form>

</body>
</html>
