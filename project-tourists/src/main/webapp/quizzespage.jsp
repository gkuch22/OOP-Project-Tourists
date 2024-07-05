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

            <input class="searchfield" type="text" name="quizName" placeholder="Title">
            <input class="searchbutton" type="submit" value="Search">

            <label class="taglabel" for="tags">Tags:</label>
            <select class="tagcombobox" name="tags" id="tags">
                <option value="none">None</option>
                <%
                    for(String tag : tags){
                %>
                        <option value="<%= tag %>"><%=tag%></option>
                <%
                    }
                %>
            </select>

            <label class="difficultylabel" for="difficulty">Difficulty:</label>
            <select class="difficultycombobox" name="difficulty" id="difficulty">
                <option value="all">All</option>
                <option value="easy">Easy</option>
                <option value="medium">Medium</option>
                <option value="hard">Hard</option>
            </select>

            <label class="orderbylabel" for="orderby">Order By:</label>
            <select class="orderbycombobox" name="orderby" id="orderby">
                <option value="none">None</option>
                <option value="difficulty">Difficulty</option>
                <option value="popularity">Popularity</option>
                <option value="recent">Recent</option>
            </select>

            <button class="applybutton" type="submit">Apply</button>

        </form>
    </div>

    <div class="quiztable">
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
