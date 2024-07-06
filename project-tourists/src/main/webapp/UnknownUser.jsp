<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 06.07.2024
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Unknown User Page</title>
    <link rel="stylesheet" href="UnknownUser.css">
</head>
<body>
<jsp:include page="topBar.jsp" />
<div class="message">
    <a>The user with the name <%=request.getAttribute("current_name")%> does not exist</a>
</div>
</body>
</html>
