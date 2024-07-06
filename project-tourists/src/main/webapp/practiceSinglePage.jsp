<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 30.06.2024
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.sql.*, java.util.*" %>
<%--<%@ page import="javaFiles.Question" %>--%>
<%@ page import="java.util.Date" %>
<%@ page import="javaFiles.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Take Quiz - Single Page</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f0f2f5;
            margin: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: flex-start;
            height: 100vh;
            color: #333;
        }
        .quiz-container {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 800px;
            margin-top: 20px;
            overflow-y: auto;
        }
        .question {
            font-size: 1.2em;
            margin-bottom: 10px;
        }
        .answers {
            list-style: none;
            padding: 0;
            margin: 0 0 20px 0;
        }
        .answers li {
            margin-bottom: 10px;
        }
        .submit-button {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
    </style>
</head>
<body>
<%
    //    session = request.getSession();
//    Integer tmp = (Integer) session.getAttribute("quizId");
//    int quizId = tmp.intValue();

    String quizIdStr = request.getParameter("quiz_id");
    int quizId = Integer.parseInt(quizIdStr);
    // System.out.println("fuuuuuuuuck");
    DBManager dbManager = new DBManager();
    List<Question> questions = dbManager.getQuestions(quizId);
    Quiz quiz = (Quiz) session.getAttribute("quizz");
    if (quiz.isRandom()) {
        Collections.shuffle(questions);
    }
    if (questions != null) {
        long startTime = System.currentTimeMillis();
%>
<div class="quiz-container">
    <form id="quizForm" action="practiceSubmitQuizServlet" method="post">
        <%--        <input type="hidden" name="quiz_id" value="<%= quizId %>">--%>
        <input type="hidden" name="startTime" value="<%= startTime %>">
        <% for (Question question : questions) { %>
        <div class="question">
            <%= question.getQuestionText() %>
        </div>
        <ul class="answers">
            <% if (question instanceof MultipleChoice) {
                MultipleChoice mcQuestion = (MultipleChoice) question;
                String[] answers = mcQuestion.getPossibleAnswers();
                for (String answer : answers) { %>
            <li>
                <input type="radio" name="question_<%= question.getQuestionText() %>" value="<%= answer %>">
                <%= answer %>
            </li>
            <% }
            }
                if (question instanceof QuestionResponse) {
                    QuestionResponse qrQuestion = (QuestionResponse) question; %>
            <li>
                <textarea name="question_<%= question.getQuestionText() %>" class="text-area"></textarea>
            </li>
            <%} %>

            <% if (question instanceof FillInTheBlank) {
                FillInTheBlank qrQuestion = (FillInTheBlank) question; %>
            <li>
                <textarea name="question_<%= question.getQuestionText() %>" class="text-area"></textarea>
            </li>
            <%}%>

            <% if (question instanceof PictureResponse) {
                PictureResponse prQuestion = (PictureResponse) question;%>
            <li>
                <div class="image-container">
                    <img src="<%= prQuestion.getImageURL() %>" alt="Question Image">
                </div>
                <textarea name="question_<%= question.getQuestionText() %>" class="text-area"></textarea>
            </li>
            <%}%>
        </ul>
        <% } %>
        <button type="submit" class="submit-button">Submit Quiz</button>
    </form>
</div>
<%
} else {
%>
<p>Quiz not found or no questions available.</p>
<%
    }
%>
</body>
</html>

