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
		if(session.getAttribute("userID") != null) //로그인을 시도 했으면
		{
			userID = (String) session.getAttribute("userID");
		}
	%>

	<%
		if(userID == null)	//로그인을 한적이 없으면 --> 로그인, 회원가입
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
		else if(userID.equals(User.getS_admin_id())){  //로그인 한 사람이 관리자 이면
	%>
		<script>
			location.href = 'adminMain.jsp';
		</script>
	<%
		}
		else	//로그인 한 사람이 user 이면
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