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
        .feedback {
            margin: 10px 0;
            padding: 10px;
            border-radius: 4px;
        }
        .feedback.correct {
            background-color: #d4edda;
            color: #155724;
        }
        .feedback.incorrect {
            background-color: #f8d7da;
            color: #721c24;
        }
        .navigation-buttons {
            display: flex;
            justify-content: space-between;
        }
        .nav-button {
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
        .nav-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
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
    if (questions != null && !questions.isEmpty()) {
        long startTime = System.currentTimeMillis();
%>
<div class="quiz-container">
    <form id="quizForm" action="imidSubmitQuizServlet" method="post">
        <input type="hidden" name="quiz_id" value="<%= quizId %>">
        <input type="hidden" name="startTime" value="<%= startTime %>">
        <input type="hidden" name="correctCount" id="correctCount" value="0">
        <input type="hidden" name="incorrectCount" id="incorrectCount" value="0">
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
                    <input type="radio" name="question_<%= i %>" value="<%= answer %>">
                    <%= answer %>
                </li>
                <% }
                }
                    if (question instanceof QuestionResponse) {
                        QuestionResponse qrQuestion = (QuestionResponse) question; %>
                <li>
                    <textarea name="question_<%= i %>" class="text-area"></textarea>
                </li>
                <%} %>

                <% if (question instanceof FillInTheBlank) {
                    FillInTheBlank qrQuestion = (FillInTheBlank) question; %>
                <li>
                    <textarea name="question_<%= i %>" class="text-area"></textarea>
                </li>
                <%}%>

                <% if (question instanceof PictureResponse) {
                    PictureResponse prQuestion = (PictureResponse) question;%>
                <li>
                    <div class="image-container">
                        <img src="<%= prQuestion.getImageURL() %>" alt="Question Image">
                    </div>
                    <textarea name="question_<%= i %>" class="text-area"></textarea>
                </li>
                <%}%>
            </ul>
            <input type="hidden" name="correct_answer_<%= i %>" value="<%= question.getAnswer() %>">
            <div class="feedback" id="feedback_<%= i %>" style="display: none;"></div>
        </div>
        <% } %>
        <div class="navigation-buttons">
            <button type="button" class="nav-button" id="nextButton" onclick="validateAndMove()">Next</button>
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
    let correctCount = 0;
    let incorrectCount = 0;
    let isFeedbackShown = false;

    function validateAndMove() {
        const questionElem = document.getElementById(`question_${currentQuestionIndex}`);
        const feedbackElem = document.getElementById(`feedback_${currentQuestionIndex}`);
        const inputs = questionElem.querySelectorAll('input[type="radio"], textarea');
        let isValid = false;
        let userAnswer = null;

        inputs.forEach(input => {
            if ((input.type === "radio" && input.checked) || (input.type === "textarea" && input.value.trim() !== "")) {
                isValid = true;
                userAnswer = input.value.trim();
            }
        });

        if (!isValid) {
            alert("Please provide an answer before moving to the next question.");
            return;
        }

        // Update hidden input with user answer
        const userAnswerInput = document.createElement('input');
        userAnswerInput.type = 'hidden';
        userAnswerInput.name = `user_answer_${currentQuestionIndex}`;
        userAnswerInput.value = userAnswer;
        questionElem.appendChild(userAnswerInput);

        if (!isFeedbackShown) {
            const correctAnswer = document.querySelector(`input[name="correct_answer_${currentQuestionIndex}"]`).value;
            if (userAnswer === correctAnswer) {
                feedbackElem.textContent = "Correct!";
                feedbackElem.classList.add("correct");
                correctCount++;
            } else {
                feedbackElem.textContent = "Incorrect!";
                feedbackElem.classList.add("incorrect");
                incorrectCount++;
            }
            feedbackElem.style.display = "block";
            isFeedbackShown = true;
            document.getElementById('nextButton').textContent = "Next Question";
        } else {
            // Hide current question and feedback
            questionElem.style.display = 'none';
            feedbackElem.style.display = 'none';
            feedbackElem.classList.remove("correct", "incorrect");
            feedbackElem.textContent = "";

            if (currentQuestionIndex < totalQuestions - 1) {
                // Show next question
                currentQuestionIndex++;
                document.getElementById(`question_${currentQuestionIndex}`).style.display = 'block';
                document.getElementById('nextButton').textContent = "Submit Answer";
            } else {
                // All questions answered, update hidden fields and submit the form
                document.getElementById('correctCount').value = correctCount;
                document.getElementById('incorrectCount').value = incorrectCount;
                document.getElementById('quizForm').submit();
            }
            isFeedbackShown = false;
        }
    }
</script>
</body>
</html>
