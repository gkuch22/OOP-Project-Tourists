<%@ page import="java.sql.*, java.util.*" %>
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
            background-color: #1B1B32;
            margin: 0;
            padding-top: 100px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: flex-start;
            height: 100vh;
            color: #ffffff;
        }
        .quiz-container {
            background-color: #2D2D4B;
            color: #ffffff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 800px;
            margin-top: 20px;
            overflow-y: auto;
        }
        .question-container {
            background-color: #3A3A5F;
            color: #ffffff;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .question {
            font-size: 1.2em;
            margin-bottom: 10px;
        }
        .answers {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        .answers li {
            margin-bottom: 10px;
        }
        .submit-button {
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
        .submit-button:hover {
            background-color: #07071a;
        }
        #timer {
            font-size: 1.5em;
            margin-bottom: 20px;
            color: white;
        }
        .text-area {
            width: 100%;
            height: 100px;
            background-color: #3A3A5F;
            color: #ffffff;
            border: 1px solid #ccc;
            border-radius: 4px;
            padding: 10px;
            resize: vertical;
        }
        .image-container {
            margin-bottom: 10px;
        }
        .imageBox{
            width: 30vw;
            height: 40vh;
        }
    </style>
</head>
<body>
<jsp:include page="topBar.jsp" />
<%
//    String quizIdStr = request.getParameter("quiz_id");
//    int quizId = Integer.parseInt(quizIdStr);
    int quizId = (Integer)session.getAttribute("quizId");
    DBManager dbManager = new DBManager();
    List<Question> questions = dbManager.getQuestions(quizId);
    Quiz quiz = (Quiz) session.getAttribute("quizz");
    boolean isTimed = quiz.isTimed();
    int duration = quiz.getDurationTime();
    if (quiz.isRandom()) {
        Collections.shuffle(questions);
    }

    Map<String, Integer> practiceCounts = (Map<String, Integer>) session.getAttribute("practice");


    if (questions != null) {
        long startTime = System.currentTimeMillis();
%>
<div class="quiz-container">
    <% if (isTimed) { %>
    <div id="timer">Time left: <span id="timeLeft"><%= duration %></span></div>
    <% } %>
    <form id="quizForm" action="practiceSubmitQuizServlet" method="post">
        <input type="hidden" name="startTime" value="<%= startTime %>">
        <% for (Question question : questions) {
            String questionText = question.getQuestionText();
            Integer practiceCount = practiceCounts.getOrDefault(questionText, 0);
            //System.out.println(practiceCount + " practice count");
            if (practiceCount < 3) { %>
        <div class="question-container">
            <div class="question"><%= questionText %></div>
            <ul class="answers">
                <% if (question instanceof MultipleChoice) {
                    MultipleChoice mcQuestion = (MultipleChoice) question;
                    String[] answers = mcQuestion.getPossibleAnswers();
                    for (String answer : answers) { %>
                <li>
                    <input type="radio" name="question_<%= questionText %>" value="<%= answer %>">
                    <%= answer %>
                </li>
                <% }
                }
                    if (question instanceof QuestionResponse) { %>
                <li>
                    <textarea name="question_<%= questionText %>" class="text-area"></textarea>
                </li>
                <% }
                    if (question instanceof FillInTheBlank) { %>
                <li>
                    <textarea name="question_<%= questionText %>" class="text-area"></textarea>
                </li>
                <% }
                    if (question instanceof PictureResponse) {
                        PictureResponse prQuestion = (PictureResponse) question; %>
                <li>
                    <div class="image-container">
                        <img class="imageBox" src="<%= prQuestion.getImageURL() %>" alt="Question Image">
                    </div>
                    <textarea name="question_<%= questionText %>" class="text-area"></textarea>
                </li>
                <% } %>
            </ul>
        </div>
        <% }
        } %>
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
<script>
    <% if (isTimed) { %>
    const duration = <%= duration %>;
    let timeLeft = duration;

    const timerElement = document.getElementById('timeLeft');

    const interval = setInterval(() => {
        timeLeft--;

        const hours = Math.floor(timeLeft / 3600);
        const minutes = Math.floor((timeLeft % 3600) / 60);
        const seconds = timeLeft % 60;

        timerElement.textContent = `${hours}:${minutes < 10 ? '0' : ''}${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;

        if (timeLeft <= 0) {
            clearInterval(interval);
            alert('Sorry, time is up!');
            window.location.href = 'timesUp.jsp';
        }
    }, 1000);
    <% } %>
</script>
</body>
</html>
