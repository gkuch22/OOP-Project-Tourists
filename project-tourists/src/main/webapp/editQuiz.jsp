<%@ page import="javaFiles.Quiz" %>
<%@ page import="javaFiles.QuizImpl" %><%--
  Created by IntelliJ IDEA.
  User: surma
  Date: 7/8/2024
  Time: 1:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Choose Question Type</title>
    <link rel="stylesheet" href="/chooseQuestionStyle.css">
</head>
<body>
<!-- Include the top bar -->
<jsp:include page="topBar.jsp" />
<h1>Editing Quiz</h1>
<%
    Quiz currQuizz = (QuizImpl)request.getSession().getAttribute("quiz");
%>
<form method="post" action="editQuiz">
    <fieldset class="editQuizFieldSet">
        <legend>Edit quiz options or questions</legend>
        <div class="editQuizFirstLine">
            <input class="editButton" type="submit" name="action" value="Edit quiz options">
            <input class="editButton" type="submit" name="action" value="Edit quiz questions">
        </div>
    </fieldset>
</form>
</body>
</html>
