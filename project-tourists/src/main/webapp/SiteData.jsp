<%@ page import="javaFiles.DBManager" %><%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 06.07.2024
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Site Data</title>
    <link rel="stylesheet" href="UnknownUser.css">
</head>
<body>
<jsp:include page="topBar.jsp" />
<%DBManager manager = (DBManager) application.getAttribute("db-manager");%>
<div class="message">
    <p>There are <%=manager.getQuizzes().size()%> on the site</p>
    <p><%=manager.getReviewCount()%> quizzes have been taken in total</p>
</div>
</body>
</html>
