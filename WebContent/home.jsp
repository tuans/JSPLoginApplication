<%@page import="utils.User"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Home Page</title>
</head>
<body>
<%User user = (User) session.getAttribute("User"); %>
<h3>Hi <%=user.getName() %></h3>
<strong>Your Name</strong>: <%=user.getName() %><br>

<br>
<form action="Logout" method="post">
<input type="submit" value="Logout" >
</form>
</body>
</html>