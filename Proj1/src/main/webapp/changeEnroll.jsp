<%--
  Created by IntelliJ IDEA.
  User: brendan
  Date: 2022/10/17
  Time: 12:32 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ page import="user.ClassLookUp" %>
<%@ page import="user.UserDAO" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap.css">
    <title></title>
</head>
<body>
<div class="col-lg-2" style="padding-top: 5px;">
    <button type="button" class="btn btn-primary form-control" onClick="location.href='userMain.jsp'">메인으로 이동</button>
</div>

<div class="container">
    <div class="row">
        <h3> 신청한 강의 변경 </h3>

        <table class="table" width ="750" style="text-align: center; border: 1px solid #dddddd">
            <thead>
            <tr>
                <th style="text-align: center;">         </th>
                <th style="text-align: center;">  class_id  </th>
                <th style="text-align: center;">  course_id  </th>
                <th style="text-align: center;">  class_name  </th>
                <th style="text-align: center;">  lecturer_name  </th>
                <th style="text-align: center;">  person_max  </th>
                <th style="text-align: center;">  room_id  </th>
                <th style="text-align: center;">  building_name  </th>
                <th style="text-align: center;">  opened  </th>
                <th style="text-align: center;">  begin  </th>
                <th style="text-align: center;">  end  </th>
                <th style="text-align: center;">  day  </th>
                <th style="text-align: center;">  </th>
            </tr>

            </thead>
            <tbody>

            <%
                UserDAO userDAO = new UserDAO();
                ArrayList<ClassLookUp> list = userDAO.showMyClass();
                for(int i = 0; i < list.size(); i++) {
            %>

            <tr>
                <form action="ChangeEnroll" method="post">
                    <td><%= (i + 1)%></td>
                    <td><%= list.get(i).getClass_id()%></td>
                    <td><%= list.get(i).getCourse_id()%></td>
                    <td><%= list.get(i).getClass_name()%></td>
                    <td><%= list.get(i).getLecturer_name()%></td>
                    <td><%= list.get(i).getPerson_max()%></td>
                    <td><%= list.get(i).getRoom_id()%></td>
                    <td><%= list.get(i).getBuilding_name()%></td>
                    <td><%= list.get(i).getOpened()%></td>
                    <td><%= list.get(i).getBegin()%></td>
                    <td><%= list.get(i).getEnd()%></td>
                    <td><%= list.get(i).getDay()%></td>
                    <td>
                        <input type="checkbox" name="class_id" value="<%=list.get(i).getClass_id()%>">
                    </td>


                    <%
                        }
                    %>
                    <table>
                        <tr>
                            <div class="col-lg-1" style="padding-top: 5px;">
                                <input type="submit" value="삭제" class="btn btn-primary form-control">
                            </div>
                        </tr>
                    </table>
                </form>
            </tr>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
