<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="javaFiles.*" %><%--
  Created by IntelliJ IDEA.
  User: surma
  Date: 7/9/2024
  Time: 10:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz Editor</title>
    <link rel="stylesheet" href="editQuizQuestionsStyle.css">
</head>
<body>
<%
    int quizId = 2; // Replace with your actual quiz ID retrieval method
    // quizId = (Integer) request.getSession().getAttribute("quiz_id");
    DBManager dbManager = (DBManager) application.getAttribute("db-manager");
    List<Question> questions = null;
    try {
        questions = dbManager.getQuestions(quizId);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

%>

<h1>Question Editor</h1>
<form method="post" action="removeOrEdit">
<fieldset id="question-editor">
    <input name = "action" type="submit" value="Edit Question">
    <input name = "action" type="submit" value="Remove Question">
</fieldset>
</form>
</body>
</html>
