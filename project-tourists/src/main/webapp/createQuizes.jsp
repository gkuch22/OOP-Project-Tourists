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
<form class="createQuizForm" method="post" action="/createQuiz">
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

    <div id="duration-fields" style="display: none; margin-left: 20px;">
        <label>Hours: <input class="time-input" type="number" name="hours" min="0" placeholder="0"></label>
        <label>Minutes: <input class="time-input" type="number" name="minutes" min="0" placeholder="0"></label>
        <label>Seconds: <input class="time-input" type="number" name="seconds" min="0" placeholder="0"></label>
    </div>

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
    <input class="createQuizSubmit" type = "submit" value="Next">
</form>
</body>
</html>
