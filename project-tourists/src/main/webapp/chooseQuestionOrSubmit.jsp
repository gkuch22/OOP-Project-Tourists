<%--
  Created by IntelliJ IDEA.
  User: surma
  Date: 7/3/2024
  Time: 7:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Choose Question Type</title>
    <link rel="stylesheet" href="/chooseQuestionStyle.css">
</head>
<body>
    <!-- Include the top bar -->
    <jsp:include page="topBar.jsp" />
    <h1>Choose question type or finish</h1>
    <form method="post" action="/chooseQuestion">
        <fieldset class="buttons">
            <legend>Choose question type to add question to quiz</legend>
            <div class="firstLine">
            <input type="submit" name="action" value="Question-Response">
            <input type="submit" name="action" value="Fill In The Blank">
            </div>
            <div class="secondLine">
            <input type="submit" name="action" value="Multiple Choice">
            <input type="submit" name="action" value="Picture Response">
            </div>
        </fieldset>
        <input  type="submit" name="action" class="finishButton" value="Finish">
    </form>
</body>
</html>
