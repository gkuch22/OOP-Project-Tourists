<%@ page import="java.util.*" %>
<%@ page import="javaFiles.DBManager" %>
<%@ page import="javaFiles.Quiz" %>
<%@ page import="javaFiles.User" %>
<%@ page import="javaFiles.Question" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //    session.setAttribute("quizId", 1);
    // session.setAttribute("user_id", 30);
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
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            color: #ffffff;
            margin: 50px 0 0;
        }
        .header, .tags, .top-scores {
            background-color: #3A3A5F;
            color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 800px;
            margin-bottom: 20px;
        }
        .header {
            display: flex;
            justify-content: space-between;
            margin-top: 60px;
        }
        .header div {
            font-weight: bold;
            font-size: 1.2em;
            margin-bottom: 10px;
        }
        .creator {
            align-self: flex-start;
            margin-left: 20px;
        }
        a {
            color: #ffffff;
            text-decoration: none;
            font-size: 0.9em;
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
            max-width: 700px;
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
        .button.edit-quiz {
            background-color: #0A0A23;
        }
        .button.edit-quiz:hover {
            background-color: #07071a;
        }
        .button.delete-history {
            background-color: #0A0A23;
        }
        .button.delete-history:hover {
            background-color: #07071a;
        }
        .top-scores {
            max-width: 800px;
            margin-top: 20px;
            text-align: center;
        }
        .top-scores h2 {
            margin: 0 0 20px;
        }
        .top-scores ul {
            list-style: none;
            padding: 0;
        }
        .top-scores li {
            background-color: #2D2D4B;
            padding: 10px;
            margin: 5px 0;
            border-radius: 4px;
        }
        .top-scores ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .top-scores li {
            background-color: #2D2D4B;
            padding: 10px;
            margin: 5px 0;
            border-radius: 4px;
            display: flex;
            justify-content: space-between;
        }

        .top-scores li span {
            flex: 1;
            text-align: center;
        }
    </style>
</head>
<body>
<jsp:include page="topBar.jsp" />
<%
//    int quizId = 38;
//    session.setAttribute("quizId", 38);
    int quizId = Integer.parseInt(request.getParameter("quiz_id"));
    System.out.println("quiz id - " + quizId);
    session.setAttribute("quizId", quizId);
    DBManager dbManager = new DBManager();
//    DBManager dbManager = (DBManager) application.getAttribute("db-manager");
    Quiz quiz = dbManager.getQuiz(quizId);
    System.out.println("same quiz id - " + quiz.getQuiz_id());
    session.setAttribute("quizz", quiz);
    int id = (Integer)session.getAttribute("user_id");
    User userr = null;
    if(id != -1){
        userr = dbManager.getUserData((Integer) session.getAttribute("user_id"));
    }

    session.setAttribute("quizName", quiz.getQuiz_name());
    session.setAttribute("practiceMode", quiz.isPractice_mode());
    String username = dbManager.getUsernameById(quiz.getCreator_id());
    System.out.println(username + " username");
    if (quiz != null) {
%>

<div class="header">
    <div>
        <div id="name"><%= quiz.getQuiz_name() %></div>
        <div id="difficulty"><%= quiz.getDifficulty() %></div>
        <div class="description">Description: <%= quiz.getDescription() %></div>
    </div>
    <div class="creator">
        Creator: <%=username%>
        <br>
        <a href="AnotherUser?name=<%=username%>">View Profile</a>
    </div>
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
<%if ((Integer)session.getAttribute("user_id") >= 0) {%>
<div class="buttons">
    <form action="startServlet" method="post">
        <input type="hidden" name="quiz_id" value="<%= quizId %>">
        <button class="button start-quiz">Start Quiz</button>
    </form>
    <%if (quiz.isPractice_mode()) {%>
    <%
        Map<String, Integer> practice = new HashMap<String, Integer>();
        List<Question> questions = null;
        try {
            questions = dbManager.getQuestions(quizId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Question question : questions) {

            practice.put(question.getQuestionText(), 0);
        }
        System.out.println(practice.get(0) + " practice");
        session.setAttribute("practice", practice);
    %>
    <form action="practiceStartServlet" method="post">
        <input type="hidden" name="quiz_id" value="<%= quizId %>">
        <button class="button start-practice">Start Practice</button>
    </form>
    <%}%>
        <%if (quiz.getCreator_id() == (Integer)session.getAttribute("user_id")) {%>
        <form action="editQuizOptions" method="get">
            <input type="hidden" name="quiz_id" value="<%= quizId %>">
            <button class="button edit-quiz">Edit Quiz</button>
        </form>
        <%}%>
        <%if (userr.isAdmin()) {%>
        <form action="clearHistoryServlet" method="post">
            <input type="hidden" name="quiz_id" value="<%= quizId %>">
            <button class="button delete-history">Delete History</button>
        </form>
        <%}%>
</div>
<%}%>
<div class="top-scores">
    <h2>Top Scores</h2>
    <ul>
        <li>
            <span>Name</span>
            <span>Score</span>
            <span>Time Taken</span>
            <span>Date</span>
        </li>
        <%
            List<String> topScorers = dbManager.getTopScorers(quizId, 10);
            for (String user : topScorers) {
                String[] output = user.split(";");
        %>
        <li>
            <span><a href="AnotherUser?name=<%=output[0]%>"><%=output[0]%></a></span>
<%--            <span><%= output[0] %></span>--%>
            <span><%= output[1] %></span>
            <span><%= output[2] %></span>
            <span><%= output[3] %></span>
        </li>
        <% } %>
    </ul>
</div>
<div class="top-scores">
    <h2>Top Scores For Today</h2>
    <ul>
        <li>
            <span>Name</span>
            <span>Score</span>
            <span>Time Taken</span>
            <span>Date</span>
        </li>
        <%
            List<String> topScorersToday = dbManager.getTodaysTopScorers(quizId, 10);
            for (String user : topScorersToday) {
                String[] output = user.split(";");
        %>
        <li>
            <span><a href="AnotherUser?name=<%=output[0]%>"><%=output[0]%></a></span>
<%--            <span><%= output[0] %></span>--%>
            <span><%= output[1] %></span>
            <span><%= output[2] %></span>
            <span><%= output[3] %></span>
        </li>
        <% } %>
    </ul>
</div>
</body>
</html>
