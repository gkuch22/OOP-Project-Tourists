<%--
  Created by IntelliJ IDEA.
  User: surma
  Date: 7/3/2024
  Time: 11:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Quiz</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="createQuizzesStyle.css"/>
</head>
<body>
<!-- Include the top bar -->
<jsp:include page="topBar.jsp" />
<form method="post" action="/createQuiz">
    <label>Quiz name: <input type = "text" name="quizname" placeholder = "Name" required> </label>
    <label >Description: </label>
    <textarea  name="description" required rows="3" cols="30" placeholder="This quiz is about..."></textarea>
    <label>Add tags <input name="tags" required type = "text" placeholder="history, math, movies,..."></label>
    <label>
        Difficulty
        <select id = "difficulty" name="difficulty">
            <option>Easy</option>
            <option>Medium</option>
            <option>Hard</option>
        </select>
    </label>
    <label>
        <input name="checkbox" class ="inline" type = "checkbox" value="random">
        Randomized
    </label>

    <label>
        <input name="checkbox" class ="inline" type = "checkbox" value="timed">
        Timed
    </label>

    <label>
        <input name="checkbox" class ="inline" type = "checkbox" value="multiplePages">
        Multiple Pages
    </label>

    <label>
        <input name="checkbox" class ="inline" type = "checkbox" value="immediateCorrection">
        Immediate Correction
    </label>

    <label>
        <input name="checkbox" class ="inline" type = "checkbox" value="practiceMode">
        Practice Mode
    </label>
    <input type = "submit" value="Next">
</form>
</body>
</html>
