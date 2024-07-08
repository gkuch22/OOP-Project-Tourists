<%--
  Created by IntelliJ IDEA.
  User: gkuch
  Date: 07.07.2024
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Error</title>
    <link rel="stylesheet" href="errorpageStyle.css">
</head>

<body>

    <h1>Something went WRONG!</h1>

    <%
        String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
        Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
        if (exception != null) {
            errorMessage = exception.getMessage();
        }
        if(errorMessage == null){
            errorMessage = "Unknown ERROR";
        }
    %>
    <p>Error Details: <%=errorMessage%></p>

    <a href="/signin.jsp">
        <img src="home.png">
    </a>

</body>

</html>
