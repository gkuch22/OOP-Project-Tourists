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
