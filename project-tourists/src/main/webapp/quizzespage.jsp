<%--
  Created by IntelliJ IDEA.
  User: gkuch
  Date: 02.07.2024
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="javaFiles.DBManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="javaFiles.Quiz" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quizzerinho</title>
</head>

<body>
<%
    DBManager dbManager = (DBManager) application.getAttribute("db-manager");
    List<Quiz> quizzes = null;
    try {
        quizzes = dbManager.getQuizzes();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    List<String> tags = new ArrayList<String>();
    for(Quiz quiz : quizzes) {
        List<String> currTags = quiz.getQuiz_tag();
        for (String tag : currTags) tags.add(tag);
    }
%>

<div id="header">
    <h1><a href="homepage.jsp">Quizzerinho</a></h1>
</div>

<div id="search">
    <form action="index.jsp" method="get">
        <input type="text" name="quizName" placeholder="Quiz name">
        <input type="submit" value="Search">
    </form>
</div>

<div id="filters">
    <form method="GET" action="quizzes.jsp">

        <label for="tags">Tags:</label>
        <select name="tags" id="tags">
            <option value="none">None</option>
            <%
                for(String tag : tags){
            %>
            <option value="<%= tag %>"><%=tag%></option>
            <%
                }
            %>
        </select>




        <label for="difficulty">Difficulty:</label>
        <select name="difficulty" id="difficulty">
            <option value="all">All</option>
            <option value="easy">Easy</option>
            <option value="medium">Medium</option>
            <option value="hard">Hard</option>
        </select>

        <label for="orderby">Order By:</label>
        <select name="orderby" id="orderby">
            <option value="none">None</option>
            <option value="difficulty">Difficulty</option>
            <option value="popularity">Popularity</option>
            <option value="recent">Recent</option>
        </select>

        <button type="submit">Apply</button>


    </form>
</div>

<div id="quizTable">
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Difficulty</th>
            <th>Quizzes taken</th>
            <th>Max Score</th>
            <th>Link to Quiz</th>
        </tr>
        </thead>
        <tbody>
        <%
            String[] quizNames = {"Quiz 1", "Quiz 2", "Quiz 3"};
            String[] difficulties = {"Easy", "Medium", "Hard"};
            int[] historyCounts = {10, 5, 2};
            int[] maxScores = {100, 80, 90};

            for (int i = 0; i < quizNames.length; i++) {
        %>
        <tr>
            <td><%= quizNames[i] %></td>
            <td><%= difficulties[i] %></td>
            <td><%= historyCounts[i] %></td>
            <td><%= maxScores[i] %></td>
            <td><a href="quiz.jsp?id=<%= i %>">Take Quiz</a></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>


</body>


</html>
