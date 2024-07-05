<%--
  Created by IntelliJ IDEA.
  User: gkuch
  Date: 03.07.2024
  Time: 14:01
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="javaFiles.DBManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="javaFiles.Quiz" %>
<%@ page import="javafx.util.Pair" %>
<%@ page import="java.util.*" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quizzerinho</title>
    <link rel="stylesheet" href="quizzespageStyle.css">
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
        for (String tag : currTags) {
            if(!tags.contains(tag)) tags.add(tag);
        }
    }
%>

<div class="header">
    <h1><a href="index.jsp">Quizzerinho</a></h1>
</div>

<div class="container">

    <div class="filters">
        <h2>Filters</h2>
        <form action="filteredquizzes" method="get">

            <input type="text" name="quizName" placeholder="Title">
            <input type="submit" value="Search">

            <label for="tags">Tags:</label>
            <select name="tags" id="tags">
                <option value="none">None</option>
                <%
                    for(String tag : tags){
                %>
                        <option value="<%= tag %>" <%= tag.equals(request.getParameter("tags")) ? "selected" : "" %>><%= tag %></option>
                <%
                    }
                %>
            </select>

            <%
                String defaultDifficulty = request.getParameter("difficulty");
            %>
            <label for="difficulty">Difficulty:</label>
            <select name="difficulty" id="difficulty">
                <option value="all" <%= "all".equals(defaultDifficulty) ? "selected" : "" %>>All</option>
                <option value="easy" <%= "easy".equals(defaultDifficulty) ? "selected" : "" %>>Easy</option>
                <option value="medium" <%= "medium".equals(defaultDifficulty) ? "selected" : "" %>>Medium</option>
                <option value="hard" <%= "hard".equals(defaultDifficulty) ? "selected" : "" %>>Hard</option>
            </select>

            <label for="orderby">Order By:</label>
            <select name="orderby" id="orderby">
                <option value="none" <%= "none".equals(request.getParameter("orderby")) ? "selected" : "" %>>None</option>
                <option value="difficulty" <%= "difficulty".equals(request.getParameter("orderby")) ? "selected" : "" %>>Difficulty</option>
                <option value="popularity" <%= "popularity".equals(request.getParameter("orderby")) ? "selected" : "" %>>Popularity</option>
                <option value="recent" <%= "recent".equals(request.getParameter("orderby")) ? "selected" : "" %>>Recent</option>
            </select>

            <button type="submit">Apply</button>

        </form>
    </div>

    <%--<p>aaaaaaaaaaaaaa</p>--%>

    <div class="quizTable">
        <table>
            <thead>
            <tr>
                <th>Title</th>
                <th>Difficulty</th>
                <th>Taken</th>
                <th>Max Score</th>
                <th>Date</th>
                <th>Link to Quiz</th>
            </tr>
            </thead>
            <tbody>
            <%
                Map<Integer, Pair<Integer, Integer>> mp;
                try {
                    mp = dbManager.getStatMap();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                quizzes = (List<Quiz>) request.getAttribute("quizzeslist");
                for(Quiz quiz : quizzes){
                    String name = quiz.getQuiz_name();
                    String difficulty = quiz.getDifficulty();
                    int quiz_id = quiz.getQuiz_id();
                    int quizzestaken = 0;
                    int maxscore = 0;
                    Date currDate = quiz.getDate();
                    if(mp.containsKey(quiz_id)){
                        quizzestaken = mp.get(quiz_id).getKey();
                        maxscore = mp.get(quiz_id).getValue();
                    }

            %>
            <tr>
                <td><%= name %></td>
                <td><%= difficulty %></td>
                <td><%= quizzestaken %></td>
                <td><%= maxscore %></td>
                <td><%= currDate %></td>
                <td><a href="quizStart.jsp?quiz_id=<%= quiz.getQuiz_id() %>">Take Quiz</a></td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

</div>

</body>


</html>

