<%@ page import="javaFiles.User" %>
<%@ page import="javaFiles.UserImpl" %>
<%@ page import="javaFiles.DBManager" %><%--
  Created by IntelliJ IDEA.
  User: surma
  Date: 7/5/2024
  Time: 9:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="topBarStyle.css">
</head>
<body>
<% DBManager dbManager = (DBManager) application.getAttribute("db-manager");
    int userId = 1;
//    userId = (int)request.getSession().getAttribute("userId");
    User user = dbManager.getUserData(userId);
    String pictureURL = user.getProfilePhoto();
%>
<div class="topbar">
    <a href="/index.jsp"><img class="homePagePicture" src="logo1.png"></a>
    <a href="/UserPage.jsp"><img class="profilePicture" src="<%=pictureURL%>"></a>
    <a href="inboxmailpage.jsp"><img class="inboxPicture" src="mail.png"></a>
</div>
</body>
</html>
