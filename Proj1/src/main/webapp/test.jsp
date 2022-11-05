<%@ page import="user.UserDAO" %>
<%@ page import="user.User" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: brendan
  Date: 2022/10/26
  Time: 11:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container">
    <h3 style="test-align: center;">모든 사용자 정보</h3>
    <div class="row">
        <%--            <input type="text" value="입력" name="input" >--%>
        <table class="table" width="750" style="text-align: center; border: 1px solid #dddddd">
            <thead>
            <tr>
                <th style="text-align: center;"></th>
                <th style="text-align: center;"> 아이디</th>
                <th style="text-align: center;"> 비밀번호</th>
                <th style="text-align: center;"> 이름</th>
                <th style="text-align: center;"> 성별</th>
                <th style="text-align: center;"> 이메일</th>
                <th style="text-align: center;"></th>
            </tr>

            </thead>
            <tbody>
            <form method="post">
                <%
                    UserDAO userDAO = new UserDAO();
                    ArrayList<User> list = userDAO.showAll();

                    for (int i = 0; i < list.size(); i++) {
                %>

                <tr>
                    <td><%= (i + 1)%>
                    </td>
                    <td><%= list.get(i).getUserID()%>
                    </td>
                    <td><%= list.get(i).getUserPassword()%>
                    </td>
                    <td><%= list.get(i).getUserName()%>
                    </td>
                    <td><%= list.get(i).getUserGender()%>
                    </td>
                    <td><%= list.get(i).getUserEmail()%>
                    </td>

                    <% if (i == 0) continue; //admin은 삭제 불가%>
                    <td>
                        <input type="checkbox" name="userID" value="<%=list.get(i).getUserID()%>">
                    </td>
                </tr>
                <%
                    }
                %>
                <table>
                    <tr>
                        <div class="col-lg-2" style="padding-top: 5px;">
                            <input formaction="deleteUser" type="submit" value="유저 삭제"
                                   class="btn btn-primary form-control">
                        </div>
                    </tr>
                </table>
            </form>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
