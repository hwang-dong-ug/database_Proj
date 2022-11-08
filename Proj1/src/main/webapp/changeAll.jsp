<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="user.UserDAO" %>
<%@ page import="user.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap.css">
    <style>
        table, th, td {
            border: 1px solid #bcbcbc;
        }

        table {
            width: 1000px;
        }

        th.t1{
            width: 400px;
        }
    </style>
    <title></title>
</head>
<body>

<div class="col-lg-2" style="padding-top: 5px;">
    <button type="button" class="btn btn-primary form-control" onClick="location.href='adminMain.jsp'">메인으로 이동</button>
</div>

<div class="container">
    <div class="col-lg-4">
        <div class="jumbotron" style="padding-top: 20px;">
            <form>
                <table>
                    <thead>
                    <tr>
                        <th class="t1">
                            <input type="text" class="form-control" placeholder="학번" name="student_id"
                                   maxlength="20">
                        </th>
                        <th>
                            <input type="submit" formaction="timetable.jsp" class="btn btn-primary form-control" value="금학기 시간표 조회">
                        </th>
                        <th>
                            <input type="submit" formaction="credit.jsp" class="btn btn-primary form-control" value="성적 조회">
                        </th>
                        <th>
                            <input type="submit" formaction="state_lecturer.jsp" class="btn btn-primary form-control" value="재학/휴학/자퇴 등 상태 변경 및 지도 교수 조회">
                        </th>
                    </tr>
                    </thead>
                </table>
            </form>
            <form method="post" action="ClassAddNum">
                <table>
                    <thead>
                    <tr>
                        <th class="t1">
                            <input type="text" class="form-control" placeholder="class_id" name="class_id"
                                   maxlength="20">
                        </th>
                        <th class="t1">
                            <input type="text" class="form-control" placeholder="증원 수" name="add_num"
                                   maxlength="20">
                        </th>
                        <th>
                            <input type="submit" class="btn btn-primary form-control" value="증원">
                        </th>
                    </tr>
                    </thead>
                </table>
            </form>
            <form method="post" action="AllowClass">
                <table>
                    <thead>
                    <tr>
                        <th class="t1"><input type="text" class="form-control" placeholder="class_id" name="class_id"
                                   maxlength="20"></th>
                        <th class="t1"><input type="text" class="form-control" placeholder="student_id" name="student_id"
                                   maxlength="20"></th>
                        <th>
                            <input type="submit" class="btn btn-primary form-control" value="수강허용">
                        </th>
                    </tr>
                    </thead>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>