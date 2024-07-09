<%--
  Created by IntelliJ IDEA.
  User: surma
  Date: 7/9/2024
  Time: 10:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz Editor</title>
    <link rel="stylesheet" href="editQuizQuestionsStyle.css">
</head>
<body>
<h1>Quiz Editor</h1>
<fieldset id="quiz-editor">
    <div id="question-buttons" class="editQuizFieldSet">
        <!-- Question buttons will be inserted here by JavaScript -->
    </div>
    <div id="question-editor" class="editQuizFieldSet" style="display:none;">
        <h2>Question Editor</h2>
        <div id="editor-fields">
            <!-- Editor fields will be inserted here by JavaScript -->
        </div>
        <input type="button" class="finishButton" value="Finish Editing" onclick="hideEditor()">
    </div>
</fieldset>
<script>
    const quiz = {
        questions: [
            { id: 1, text: "1" },
            { id: 2, text: "2" },
            { id: 1, text: "1" },
            { id: 2, text: "2" },
            { id: 1, text: "1" },
            { id: 2, text: "2" },
            { id: 1, text: "1" },
            { id: 2, text: "2" },
            { id: 1, text: "1" },
            { id: 2, text: "2" },

            // Add more questions as needed
        ]
    };

    document.addEventListener("DOMContentLoaded", () => {
        const questionButtonsContainer = document.getElementById("question-buttons");
        quiz.questions.forEach((question, index) => {
            const button = document.createElement("input");
            button.type = "button";
            button.value = `${index + 1}`;
            button.className = "questionButton";
            button.onclick = () => showEditor(question.id);
            questionButtonsContainer.appendChild(button);
        });
    });

    function showEditor(questionId) {
        const editor = document.getElementById("question-editor");
        const editorFields = document.getElementById("editor-fields");
        editorFields.innerHTML = `Editing Question ID: ${questionId}`;
        editor.style.display = "block";
    }

    function hideEditor() {
        const editor = document.getElementById("question-editor");
        editor.style.display = "none";
    }
</script>
</body>
</html>

