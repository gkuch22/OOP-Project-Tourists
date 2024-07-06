<%@ page import="javaFiles.Quiz" %>
<%@ page import="javaFiles.QuizImpl" %><%--
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
    <%
    Quiz currQuizz = (QuizImpl)request.getSession().getAttribute("quiz");
    %>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            document.getElementById('quizForm').addEventListener('submit', function(event) {
                var questionCount = document.getElementById('questionCount').value;

                if (questionCount == 0) {
                    alert('Please add at least one question to the quiz.');
                    event.preventDefault();
                }
            });
        });
    </script>
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
    </form>
    <form id="quizForm" action="chooseQuestion" method="post">
        <input type="hidden" id="questionCount" name="questionCount" value="<%= currQuizz != null ? currQuizz.getQuestions().size() : 0 %>">
        <input  type="submit" name="action" class="finishButton" value="Finish">
    </form>
</body>
</html>
