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

<%--신청버튼, 수업번호, 학수번호, 교과목명, 교강사이름, 수업시간, 신청인원/수강정원, 강의실(건물+호수) 표시
--%>
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
    <form action="enrollClass.jsp" accept-charset="UTF-8">
        <h3 style="test-align: center;" style="padding-top: 20px;">모든 class 정보</h3>
        <input type="text" class="form-control" placeholder="수업번호" name="class_id" maxlength="20">
        <input type="text" class="form-control" placeholder="학수번호" name="course_id" maxlength="20">
        <input type="text" class="form-control" placeholder="교과명" name="class_name" maxlength="20">
        <div class="col-lg-1" style="padding-top: 5px;">
            <input type="submit" value="조회" class="btn btn-primary form-control">
        </div>
    </form>

    <%
        int total_credit =new UserDAO().totalCredit();
    %>
    <script>
        document.write("<br>");
        document.write("<br>");
        document.write("<div class=\"col-lg-2\" style=\"padding-top: 5px;\">");
        document.write('신청한 학점 :' + <%=total_credit%> + '<br>');
        document.write('최대 학점 :' + <%=18%> + '<br>');
        document.write("</div>");
    </script>


    <div class="container">
        <div class="row">
            <h3> 수업조회 </h3>

            <table class="table" width ="750" style="text-align: center; border: 1px solid #dddddd">
                <thead>
                <tr>
                    <th style="text-align: center;">         </th>
                    <th style="text-align: center;">  class_id  </th>
                    <th style="text-align: center;">  course_id  </th>
                    <th style="text-align: center;">  class_name  </th>
                    <th style="text-align: center;">  lecturer_name  </th>
                    <th style="text-align: center;">  person_max  </th>
                    <th style="text-align: center;">  user_count  </th>
                    <th style="text-align: center;">  credit  </th>
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
                    int class_id=0;
                    String course_id=null;
                    String class_name=null;
                    request.setCharacterEncoding("UTF-8");
                    if(request.getParameter("class_id") != null ) {
                        if (request.getParameter("class_id").equals("")) class_id=0;
                        else class_id = Integer.parseInt(request.getParameter("class_id"));
                    }
                    if(request.getParameter("course_id") != null){
                        if(request.getParameter("course_id").equals("")) course_id=null;
                        else course_id = request.getParameter("course_id");
                    }
                    if(request.getParameter("class_name") != null){
                        if(request.getParameter("class_name").equals("")) class_name=null;
                        else class_name = request.getParameter("class_name");
                    }


                    UserDAO userDAO = new UserDAO();
                    ArrayList<ClassLookUp> list = userDAO.showClass(class_id,course_id,class_name);

//                    System.out.println(request.getParameter("class_name"));   // test 해보기 (오류 해결)!!!!!!!!1

                    for(int i = 0; i < list.size(); i++) {
                %>

                <tr>
                    <form action="enrollClass" method="post">
                    <td><%= (i + 1)%></td>
                    <td><%= list.get(i).getClass_id()%></td>
                    <td><%= list.get(i).getCourse_id()%></td>
                    <td><%= list.get(i).getClass_name()%></td>
                    <td><%= list.get(i).getLecturer_name()%></td>
                    <td><%= list.get(i).getPerson_max()%></td>
                    <td><%= list.get(i).getUser_count()%></td>
                    <td><%= list.get(i).getCredit()%></td>
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
                                <input type="submit" value="신청" class="btn btn-primary form-control">
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
