<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 08.07.2024
  Time: 15:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Guest User Page</title>
    <link rel="stylesheet" href="UnknownUser.css">
</head>
<body>
<jsp:include page="topBar.jsp" />
<div class="message">
    <a>You are logged in as a guest, '</a><a href="signin.jsp"> log in </a><a>' or '</a> <a href="signup.jsp">register</a> <a>' to use this feature.</a>
</div>
</body>
</html>
