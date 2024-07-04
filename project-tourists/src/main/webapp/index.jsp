<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="javaFiles.*" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz Site - User Page</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<%
    DBManager manager = (DBManager) application.getAttribute("db-manager");
    User user;
    try {
        user = manager.getUserData(1);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
%>

<div class="ProblemsSolved">
    <h1> <span class="Number"> <%=user.getTakenQuizzes().size()%> </span> <span class="Text"> Problems Solved </span> </h1>
</div>

<a href="index.jsp" class="homepage-button">Homepage</a>
<a href="index.jsp" class="logout-button">Log out</a>
<div class="container">
    <div class="left">
        <div class="Achievements">
            <h2>Achievements</h2>
            <ul class="achievements-list">
                <%
                    try {
                        for(String s : manager.getAchievements(user)){
                %>
                <li>
                    <a>
                        <%=s%>
                    </a>
                </li>
                <%
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                %>
            </ul>
        </div>
        <div class="tags">
            <h2>Tags:</h2>
            <ul class="tags-list">
                <%
                    Map<String, Integer> tagCounts = user.getTagCount();
                    for (Map.Entry<String, Integer> entry : tagCounts.entrySet()) {
                %>
                <li>
                    <a><%= entry.getKey() %></a>
                    <span>(<%= entry.getValue() %> solved)</span>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
    <div class="middle">
        <div class="quiz-options">
            <button id="quizzes-taken-btn" class="quiz-btn active">Quizzes Taken</button>
            <button id="quizzes-created-btn" class="quiz-btn">Quizzes Created</button>
        </div>
        <%
            List<QuizPerformance> quizzesTaken = null;
            try {
                quizzesTaken = manager.getUserQuizzes(user.getUser_id());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (quizzesTaken != null) {
        %>
        <table id="quiz-data-table" class="quiz-data">
            <thead>
            <tr>
                <th>Quiz Name</th>
                <th>Date</th>
                <th>Score</th>
            </tr>
            </thead>
            <tbody id="quiz-data-body">
            <% for (QuizPerformance quiz : quizzesTaken) { %>
            <tr>
                <td><%= quiz.getQuiz_name() %></td>
                <td><%= quiz.getDate() %></td>
                <td><%= quiz.getScore() %></td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <%
            } else {
                out.println("<p>No quiz data available.</p>");
            }
        %>
    </div>
    <div class="right">
        <!-- Friends List Section -->
        <h2>Friends List</h2>
        <ul class="friends-list">
            <li>
                <img src="https://www.watchmojo.com/uploads/thumbs720/Fi-T-Top10-Family-Guy-Characters_I2B8Z1-720p30-1.jpg" alt="Friend 1">
                <span>Quaggy</span>
            </li>
            <li>
                <img src="data:image/jpeg;base64,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" alt="Friend 2">
                <span>Joe</span>
            </li>
        </ul>
    </div>
</div>
</body>
</html>