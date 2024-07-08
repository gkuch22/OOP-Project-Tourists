<%@ page import="javaFiles.QuizImpl" %>
<%@ page import="javaFiles.Quiz" %>
<%@ page import="javaFiles.DBManager" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: surma
  Date: 7/8/2024
  Time: 2:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    System.out.println("aq var");
    int quizId = 2;
    //quizId = (Integer)request.getSession().getAttribute("quiz_id");


    DBManager dbManager = (DBManager) application.getAttribute("db-manager");
    Quiz quiz = null;

    try {
        quiz = dbManager.getQuiz(quizId);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    String name = quiz.getQuiz_name();
    String description = quiz.getDescription();
    String tags = quiz.getQuizTagsAsString().replace(';',',');
    String difficulty = quiz.getDifficulty();
    int duration = quiz.getDurationTime();
    int seconds = duration % 60;
    duration /= 60;
    int minutes = duration % 60;
    duration /= 60;
    int hours = duration;
    Boolean randomized = quiz.isRandom();
    Boolean timed = quiz.isTimed();
    Boolean multiplePages = quiz.isMultiple_pages();
    Boolean immediateCorrection = quiz.isImmediatelyCorrected();
    Boolean practiceMode = quiz.isPractice_mode();
    System.out.println("boloshi");
%>

<html>
<head>
    <title>Editing quiz options</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="createQuizzesStyle.css"/>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const timedCheckbox = document.querySelector('input[name="checkbox"][value="timed"]');
            const multiplePagesCheckbox = document.querySelector('input[name="checkbox"][value="multiplePages"]');
            const immediateCorrectionCheckbox = document.querySelector('input[name="checkbox"][value="immediateCorrection"]');
            const durationFields = document.getElementById('duration-fields');

            timedCheckbox.addEventListener('change', function() {
                if (this.checked) {
                    durationFields.style.display = 'block';
                } else {
                    durationFields.style.display = 'none';
                }
            });

            multiplePagesCheckbox.addEventListener('change', function() {
                if (this.checked) {
                    immediateCorrectionCheckbox.disabled = false;
                } else {
                    immediateCorrectionCheckbox.disabled = true;
                    immediateCorrectionCheckbox.checked = false;
                }
            });
        });
    </script>
</head>
<body>
<!-- Include the top bar -->
<jsp:include page="topBar.jsp" />
<form class="createQuizForm" method="post" action="/">
    <label>Quiz name: <input type="text" name="quizname" placeholder="Name" required value="<%= name %>"> </label>
    <label>Description: </label>
    <textarea name="description" required rows="3" cols="30" placeholder="This quiz is about..."><%= description %></textarea>
    <label>Add tags <input name="tags" required type="text" placeholder="history, math, movies,..." value="<%= tags %>"></label>
    <label>
        Difficulty
        <select id="difficulty" name="difficulty">
            <option <%= "Easy".equals(difficulty) ? "selected" : "" %>>Easy</option>
            <option <%= "Medium".equals(difficulty) ? "selected" : "" %>>Medium</option>
            <option <%= "Hard".equals(difficulty) ? "selected" : "" %>>Hard</option>
        </select>
    </label>
    <label>
        <input name="checkbox" class="inline" type="checkbox" value="random" <%= randomized ? "checked" : "" %>>
        Randomized
    </label>

    <label>
        <input name="checkbox" class="inline" type="checkbox" value="timed" <%= timed ? "checked" : "" %>>
        Timed
    </label>

    <div id="duration-fields" style="display: <%= timed ? "block" : "none" %>; margin-left: 20px;">
        <label>Hours: <input class="time-input" type="number" name="hours" min="0" placeholder="0" value="<%= hours %>"></label>
        <label>Minutes: <input class="time-input" type="number" name="minutes" min="0" placeholder="0" value="<%= minutes %>"></label>
        <label>Seconds: <input class="time-input" type="number" name="seconds" min="0" placeholder="0" value="<%= seconds %>"></label>
    </div>

    <label>
        <input name="checkbox" class="inline" type="checkbox" value="multiplePages" <%= multiplePages ? "checked" : "" %>>
        Multiple Pages
    </label>

    <label>
        <input name="checkbox" class="inline" type="checkbox" value="immediateCorrection" <%= immediateCorrection ? "checked" : "" %> <%= multiplePages ? "" : "disabled" %>>
        Immediate Correction
    </label>

    <label>
        <input name="checkbox" class="inline" type="checkbox" value="practiceMode" <%= practiceMode ? "checked" : "" %>>
        Practice Mode
    </label>
    <input class="createQuizSubmit" type="submit" value="Submit">
</form>
</body>
</html>
