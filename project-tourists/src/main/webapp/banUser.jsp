<%@ page import="javaFiles.DBManager" %>
<%@ page import="javaFiles.User" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: surma
  Date: 7/6/2024
  Time: 7:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="createQuizzesStyle.css">
</head>
<body>
<jsp:include page="topBar.jsp" />
<% int userID = (Integer) request.getSession().getAttribute("ban_user_id");
    DBManager dbManager = (DBManager) application.getAttribute("db-manager");
    User user = null;
    try {
        user = dbManager.getUserData(userID);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    String userName = user.getUsername();
%>
<h1 class="banUserH1">Banning user - <%=userName%></h1>
<form method="post" action="/banUser">
    <label>Enter the reason for the ban</label>
    <textarea name="reason" required rows="5" cols="30" placeholder="You are banned because..."></textarea>
<%--    date to be added--%>
    <label for="banDate">Select ban date:</label>
    <input type="date" id="banDate" name="banDate" required>
    <input type="submit" value="submit">
</form>
</body>
</html>
