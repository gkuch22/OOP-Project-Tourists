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
                    <span><%= entry.getValue() %> solved)</span>
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
            <%
                try {
                    for(User user1 : manager.getFriends(user.getUser_id())){
            %>
            <li>
                <a href="AnotherUser?AnotherUserId=<%=user1.getUser_id()%>">
                    <img src="<%=user1.getProfilePhoto()%>">
                    <span><%=user1.getUsername()%>></span>
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
</div>
</body>
</html>

