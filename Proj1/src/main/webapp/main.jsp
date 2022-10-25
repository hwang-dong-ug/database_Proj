<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="user.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
	<meta name="viewport" content="width=devoce-width", initial-scale="1">
	<link rel="stylesheet" href="css/bootstrap.css">
    <title>메인페이지</title>
</head>
<body>
	<%
		String userID = null;
		if(session.getAttribute("userID") != null)
		{
			userID = (String) session.getAttribute("userID");
		}
	%>

	<%
		if(userID == null)
		{
	%>
	<div class="container">
		<h3 style="test-align: center;">메인페이지</h3>
		<div class="col-lg-4">
			<div class="jumbotron" style="padding-top: 20px;">
				<button type="button" class="btn btn-primary form-control"  onClick="location.href='login.jsp'">로그인</button>
			</div>
			<div class="jumbotron" style="padding-top: 20px;">
				<button type="button" class="btn btn-primary form-control"  onClick="location.href='join.jsp'">회원가입</button>
			</div>

		</div>
	</div>
	<%		
		}
		else if(userID.equals(User.getS_admin_id())){
	%>
		<script>
			location.href = 'adminMain.jsp';
		</script>
	<%
		}
		else
		{
	%>
		<script>
			location.href = 'userMain.jsp';
		</script>
	<%
		}
	%>
</body>
</html>