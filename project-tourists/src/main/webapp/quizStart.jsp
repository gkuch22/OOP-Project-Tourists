<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 28.06.2024
  Time: 00:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.*" %>
<%@ page import="javaFiles.DBManager" %>
<%@ page import="javaFiles.Quiz" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
//    session.setAttribute("quizId", 1);
    session.setAttribute("user_id", 30);
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>quiz.Quiz Page</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #1B1B32;
            margin: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            color: #ffffff;
        }
        .header, .tags {
            background-color: #3A3A5F;
            color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 800px;
            margin-bottom: 20px;
        }
        .header div {
            font-weight: bold;
            font-size: 1.2em;
            margin-bottom: 10px;
        }
        .tags {
            text-align: center;
        }
        .tags p {
            margin: 0;
            font-weight: bold;
            margin-bottom: 10px;
        }
        #tag-list {
            list-style: none;
            padding: 0;
            margin: 0;
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
        }
        #tag-list li {
            display: inline-block;
            background-color: #2D2D4B;
            padding: 5px 10px;
            margin: 5px;
            border-radius: 20px;
            color: #ffffff;
        }
        .buttons {
            display: flex;
            justify-content: space-around;
            width: 100%;
            max-width: 400px;
        }
        .button {
            padding: 10px 20px;
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
        .button.start-quiz {
            background-color: #0A0A23;
        }
        .button.start-quiz:hover {
            background-color: #07071a;
        }
        .button.start-practice {
            background-color: #0A0A23;
        }
        .button.start-practice:hover {
            background-color: #07071a;
        }
    </style>
</head>
<body>
<jsp:include page="topBar.jsp" />
<%
//    int quizId = 18;
//    session.setAttribute("quizId", 18);
    int quizId = Integer.parseInt(request.getParameter("quiz_id"));
    session.setAttribute("quizId", quizId);
    DBManager dbManager = new DBManager();
    Quiz quiz = dbManager.getQuiz(quizId);
    session.setAttribute("quizz", quiz);
    session.setAttribute("quizName", quiz.getQuiz_name());
    session.setAttribute("practiceMode", quiz.isPractice_mode());
    if (quiz != null) {
%>

<div class="header">
    <div id="name"><%= quiz.getQuiz_name() %></div>
    <div id="difficulty"><%= quiz.getDifficulty() %></div>
    <div class="description">Description: <%= quiz.getDescription() %></div>
</div>
<div class="tags">
    <p>Tags:</p>
    <ul id="tag-list">
        <% for (String tag : quiz.getQuiz_tag()) { %>
        <li><%= tag %></li>
        <% } %>
    </ul>
</div>
<%
} else {
%>
<p>Quiz not found.</p>
<%
    }
%>
<div class="buttons">
    <form action="startServlet" method="post">
        <input type="hidden" name="quiz_id" value="<%= quizId %>">
        <button class="button start-quiz">Start Quiz</button>
    </form>
    <%if (quiz.isPractice_mode()) {%>
    <form action="practiceStartServlet" method="post">
        <input type="hidden" name="quiz_id" value="<%= quizId %>">
        <button class="button start-practice">Start Practice</button>
    </form>
    <%}%>
</div>
</body>
</html>



