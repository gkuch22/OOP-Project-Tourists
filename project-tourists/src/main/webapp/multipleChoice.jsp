<%--
  Created by IntelliJ IDEA.
  User: surma
  Date: 7/4/2024
  Time: 11:53 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MultipleChoice Question</title>
    <link rel="stylesheet" href="multipleChoiceStyle.css">
</head>
<body>
<h1>Multiple Choice</h1>
    <form method="post" action="/multipleChoice">
        <div class="question">
            <label for="question">Type question</label>
            <textarea placeholder="ex: How many goals did Mikautadze score?" name="question" id="question" rows="5" cols="40"></textarea>
        </div>
        <div class="possibleAnswers">
            <label for="possAnswers">Enter possible answers</label>
            <textarea placeholder="ex: 15,3,4,11" name="possAnswers" id="possAnswers" rows="3" cols="30"></textarea>
        </div>
        <div class="answer">
            <label for="answer">Enter correct answer</label>
            <input name="answer" id="answer" type="text" placeholder="ex: 3">
        </div>
        <input type="submit" value="Add Question">
    </form>
</body>
</html>
