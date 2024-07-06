<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 30.06.2024
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.*" %>
<%@ page import="javaFiles.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Take Quiz</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #1B1B32;
            margin: 0;
            padding-top: 100px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: flex-start;
            height: 100vh;
            color: white;
        }
        .quiz-container {
            background-color: #2D2D4B;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 800px;
            margin-top: 20px;
            overflow-y: auto;
        }
        .question {
            background-color: #3A3A5F;
            padding: 10px;
            border-radius: 8px;
            margin-bottom: 10px;
        }
        .question-text {
            background-color: #3A3A5F;
            color: white;
            padding: 10px;
            border-radius: 8px;
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
        .navigation-buttons {
            display: flex;
            justify-content: space-between;
        }
        .nav-button {
            padding: 10px 20px;
            background-color: #0A0A23;
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
        .nav-button:hover {
            background-color: #0056b3;
        }
        #timer {
            font-size: 1.5em;
            margin-bottom: 20px;
            color: white;
        }
        .text-area {
            background-color: #3A3A5F;
            color: white;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: 100%;
            box-sizing: border-box;
        }
        .image-container {
            margin-bottom: 20px; /* Increase margin below image */
        }
    </style>
    <script>
        function startTimer(duration) {
            let timer = duration, minutes, seconds;
            const timerDisplay = document.getElementById('timer');
            const interval = setInterval(() => {
                minutes = parseInt(timer / 60, 10);
                seconds = parseInt(timer % 60, 10);

                minutes = minutes < 10 ? "0" + minutes : minutes;
                seconds = seconds < 10 ? "0" + seconds : seconds;

                timerDisplay.textContent = minutes + ":" + seconds;

                if (--timer < 0) {
                    clearInterval(interval);
                    document.getElementById('quizForm').submit();
                    window.location.href = 'timesUp.jsp';
                }
            }, 1000);
        }
    </script>
</head>
<body>
<jsp:include page="topBar.jsp" />
<%
    session = request.getSession();
    Integer tmp = (Integer) session.getAttribute("quizId");
    int quizId = tmp.intValue();
    DBManager dbManager = new DBManager();
    List<Question> questions = dbManager.getQuestions(quizId);
    Quiz quiz = (Quiz) session.getAttribute("quizz");
    if (quiz.isRandom()) {
        Collections.shuffle(questions);
    }
    boolean isTimed = quiz.isTimed();
    int durationTime = quiz.isTimed() ? quiz.getDurationTime() : -1;
    if (questions != null && !questions.isEmpty()) {
        long startTime = System.currentTimeMillis();
%>
<div class="quiz-container">
    <% if (isTimed) { %>
    <div id="timer">Time left: <span id="timeLeft"><%= durationTime %></span> minutes</div>
    <% } %>
    <form id="quizForm" action="practiceSubmitQuizServlet" method="post">
        <input type="hidden" name="quiz_id" value="<%= quizId %>">
        <input type="hidden" name="startTime" value="<%= startTime %>">
        <% for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
        %>
        <div class="question" id="question_<%= i %>" style="display: <%= i == 0 ? "block" : "none" %>;">
            <div class="question-text">
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
        </div>
        <% } %>
        <div class="navigation-buttons">
            <button type="button" class="nav-button" id="prevButton" onclick="showPrevQuestion()" style="display: none;">Previous</button>
            <button type="button" class="nav-button" id="nextButton" onclick="showNextQuestion()">Next</button>
            <button type="submit" class="nav-button" id="submitButton" style="display: none;">Submit Quiz</button>
        </div>
    </form>
</div>
<%
} else {
%>
<p>Quiz not found or no questions available.</p>
<%
    }
%>
<script>
    let currentQuestionIndex = 0;
    const totalQuestions = <%= questions.size() %>;

    function showQuestion(index) {
        document.getElementById(`question_${currentQuestionIndex}`).style.display = 'none';
        document.getElementById(`question_${index}`).style.display = 'block';
        currentQuestionIndex = index;

        document.getElementById('prevButton').style.display = currentQuestionIndex > 0 ? 'inline-block' : 'none';
        document.getElementById('nextButton').style.display = currentQuestionIndex < totalQuestions - 1 ? 'inline-block' : 'none';
        document.getElementById('submitButton').style.display = currentQuestionIndex === totalQuestions - 1 ? 'inline-block' : 'none';
    }

    function showNextQuestion() {
        if (currentQuestionIndex < totalQuestions - 1) {
            showQuestion(currentQuestionIndex + 1);
        }
    }

    function showPrevQuestion() {
        if (currentQuestionIndex > 0) {
            showQuestion(currentQuestionIndex - 1);
        }
    }
    <% if (quiz.isTimed()) { %>
    window.onload = function() {
        startTimer(<%= durationTime * 60 %>);
    };
    <% } %>
</script>
</body>
</html>




