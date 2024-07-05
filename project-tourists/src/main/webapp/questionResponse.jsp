<%--
  Created by IntelliJ IDEA.
  User: surma
  Date: 7/4/2024
  Time: 11:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Qeustion-Responce question</title>
    <link rel="stylesheet" href="multipleChoiceStyle.css">
</head>
<body>
    <h1>Question-Response</h1>
    <form method="post" action="/questionResponse">
        <div class="questionText">
          <label for="question">Type question </label>
            <textarea name="question" id="question" rows="5" cols="40" placeholder="ex:What year did Stalin..."></textarea>
        </div>
        <div class="answerText">
            <label for="answer">Enter answer</label>
            <textarea name="answer" id="answer" rows = "3" cols = "32" placeholder="ex:1922"></textarea>
        </div>
        <input type="submit" value="Add Question">
    </form>
</body>
</html>
