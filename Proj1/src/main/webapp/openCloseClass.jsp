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
    <button type="button" class="btn btn-primary form-control" onClick="location.href='adminMain.jsp'">메인으로 이동</button>
</div>


<%--            새로운 수업 개설--%>
<form action = "openClass" method="post">
    <h3 style="test-align: center;" style="padding-top: 20px;">강좌 개설</h3>
    <input type="text" class="form-control" placeholder="class_id"  name="class_id" maxlength="20">
    <input type="text" class="form-control" placeholder="course_id" name="course_id" maxlength="20">
    <input type="text" class="form-control" placeholder="lecturer_name" name="lecturer_name" maxlength="20">
    <input type="text" class="form-control" placeholder="person_max" name="person_max" maxlength="20">
    <input type="text" class="form-control" placeholder="room_id"name="room_id" maxlength="20">
    <input type="time" value="09:00:00" name="begin" min="09:00:00" max="18:00:00">
    <input type="time" value="10:00:00" name="end" min="10:00:00" max="21:00:00">
    <select name="day" >
        <option value="none"> 요일 </option>
        <option value="mon">mon</option>
        <option value="tues">tues</option>
        <option value="wed">wed</option>
        <option value="thurs">thurs</option>
        <option value="thurs">fri</option>
        <option value="thurs">sat</option>
    </select>
    <div class="col-lg-1">
        <input type="submit" value="수업 개설" class="btn btn-primary form-control">
    </div>
</form>

<%-- 기존 수업 시간표 추가 --%>
<%--<from method="post">--%>

<%--    --%>
<%--    <div class="col-lg-1" style="padding-top: 5px;">--%>
<%--        <input formaction="openClass" type="submit" value="시간 추가" class="btn btn-primary form-control">--%>
<%--    </div>--%>
<%--</from>--%>

<form action="openCloseClass.jsp" accept-charset="UTF-8">
    <h3 style="test-align: center;" style="padding-top: 20px;">수업 조회</h3>
    <input type="text" class="form-control" placeholder="수업번호" name="class_id" maxlength="20">
    <input type="text" class="form-control" placeholder="학수번호" name="course_id" maxlength="20">
    <input type="text" class="form-control" placeholder="교과명" name="class_name" maxlength="20">
    <div class="col-lg-1" style="padding-top: 5px;">
        <input type="submit" value="조회" class="btn btn-primary form-control">
    </div>
</form>

<div class="container">
    <div class="row">
        <h3> 수업조회 결과 </h3>

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
                // userDao,showClass에 전달 될 인자를 오류가 날 수 있는 상황에 맞게 가공

                // initial setting  --> 아무것도 들어 오지 않았을 때
                int class_id=0;
                String course_id=null;
                String class_name=null;

                // --> 인자들이 들어왔을 때
                request.setCharacterEncoding("UTF-8");
                if(request.getParameter("class_id") != null ) {
                    if (request.getParameter("class_id").equals("")) class_id=0;        //input tag에서 빈칸을 submit 하게 되면 empty string이 전달도기 때문에 그에 맞는 처리!
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


                UserDAO userDAO = new UserDAO(session);
                ArrayList<ClassLookUp> list = userDAO.showClass(class_id,course_id,class_name); //class_look_up_extended view에서 조회한 정보를 arraylist의 형태로 가져온다

                for(int i = 0; i < list.size(); i++) {
            %>

            <tr>
                <form method="post">
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
                        <%--                        table에서 각각의 row에 해당하는 정보의 class_id를 전달--%>
                    </td>


                    <%
                        }
                    %>
                    <table>
                        <tr>
                            <td>
                                <div class="col-lg-2" style="padding-top: 5px;">
                                    <input formaction="closeClass" type="submit" value="수업 삭제" class="btn btn-primary form-control">
                                </div>
                            </td>
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
