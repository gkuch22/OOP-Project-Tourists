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
    <link rel="stylesheet" href="quizzesStyle.css"/>
</head>
<body>
<form>
    <label>Quiz name: <input type = "text" placeholder = "Name"> </label>
    <label>Description: </label>
    <textarea  rows="3" cols="30" placeholder="This quiz is about..."></textarea>
    <label>Add tags <input type = "text" placeholder="history, math, movies,..."></label>
    <label>
        Difficulty
        <select id = "difficulty">
            <option>Easy</option>
            <option>Medium</option>
            <option>Hard</option>
        </select>
    </label>
    <label>
        <input class ="inline" type = "checkbox" value="random">
        Randomized
    </label>

    <label>
        <input class ="inline" type = "checkbox" value="timed">
        Timed
    </label>

    <label>
        <input class ="inline" type = "checkbox" value="MultiplePages">
        Multiple Pages
    </label>

    <label>
        <input class ="inline" type = "checkbox" value="immediateCorrection">
        Immediate Correction
    </label>

    <label>
        <input class ="inline" type = "checkbox" value="practiceMode">
        Practice Mode
    </label>
    <input type = "submit" value="Next">
</form>
</body>
</html>
