<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="javaFiles.*" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Page</title>
    <link rel="stylesheet" href="UserStyle.css">
</head>
<body>
<jsp:include page="topBar.jsp" />
<%
    DBManager manager = (DBManager) application.getAttribute("db-manager");
    User user;
    List<QuizPerformance> quizzesTaken = new ArrayList<QuizPerformance>();
    List<Quiz> quizzesCreated = new ArrayList<Quiz>();


    try {
        user = manager.getUserData((Integer) request.getSession().getAttribute("user_id"));
        quizzesTaken = manager.getUserQuizzes(user.getUser_id());
        quizzesCreated = manager.getUserCreatedQuizzes(user);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    StringBuilder quizzesTakenJson = new StringBuilder("[");
    for (int i = 0; i < quizzesTaken.size(); i++) {
        QuizPerformance quiz = quizzesTaken.get(i);
        quizzesTakenJson.append("{")
                .append("\"quizName\":\"").append(quiz.getQuiz_name()).append("\",")
                .append("\"score\":\"").append(quiz.getScore()).append("\",")
                .append("\"date\":\"").append(quiz.getDate()).append("\"")
                .append("}");
        if (i < quizzesTaken.size() - 1) {
            quizzesTakenJson.append(",");
        }
    }
    quizzesTakenJson.append("]");

    StringBuilder quizzesCreatedJson = new StringBuilder("[");
    for (int i = 0; i < quizzesCreated.size(); i++) {
        Quiz quiz = quizzesCreated.get(i);
        quizzesCreatedJson.append("{")
                .append("\"quizName\":\"").append(quiz.getQuiz_name()).append("\",")
                .append("\"dateCreated\":\"").append(quiz.getDate()).append("\",")
                .append("\"quizId\":\"").append(quiz.getQuiz_id()).append("\"")
                .append("}");
        if (i < quizzesCreated.size() - 1) {
            quizzesCreatedJson.append(",");
        }
    }
    quizzesCreatedJson.append("]");
%>

<div class="UserName">
    <a><%=user.getUsername()%></a>
</div>

<div class="PictureUpload">
    <a class="profilePictureChangeLabel">change your profile picture:</a>
    <form method="post" action="UploadProfilePictureServlet" >
        <div class="image">
            <label for="imageURL" class="profilePictureUploadUrl">Enter image url:</label>
            <input placeholder="ex: https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.smithsonianmag.com%2Fscience-nature%2F14-fun-facts-about-parrots-can-sing-use-tools-and-live-long-time-180957714%2F&psig=AOvVaw2k_fVcL_-d4b2WqlwTi1ir&ust=1720184451651000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCPj89cm4jYcDFQAAAAAdAAAAABAS" type="text" name="imageURL" id="imageURL" class="pictureUploadField">
        </div>
        <button type="submit" class="PictureUploadButton">Upload</button>
    </form>
</div>

<img class="UserProfile" src=<%=user.getProfilePhoto()%>>
<div class="ProblemsSolved">
    <h1><span class="Number"><%= manager.getUniqueUserQuizzes(user.getUser_id()).size() %></span> <span class="Text">Quizzes Taken</span></h1>

</div>
<div class="admin-buttons">
    <%  if(user.isAdmin()){ %>
    <a href="SiteData.jsp" class = "ban-button">See Site Data</a>
    <%}%>
</div>
<div class="container">
    <div class="left">
        <fieldset>
            <legend>Achievements</legend>
            <div class="Achievements">
                <ul class="achievements-list">
                    <%
                        try {
                            for (String s : manager.getAchievements(user)) {
                    %>
                    <li>
                        <a><%= s %></a>
                    </li>
                    <%
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    %>
                </ul>
            </div>
        </fieldset>

        <fieldset>
            <legend>Tags:</legend>
            <div class="tags">
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
        </fieldset>
    </div>
    <div class="middle">
        <fieldset>
            <legend>Quizzes</legend>
            <div class="button-container">
                <button id="quizzes-taken-btn">Quizzes Taken</button>
                <button id="quizzes-created-btn">Quizzes Created</button>
            </div>
            <div class="table-container">
                <table id="quiz-table" class="quiz-data"></table>
            </div>
        </fieldset>
    </div>
    <div class="right">
        <fieldset>
            <legend>Friends List</legend>
            <ul class="friends-list">
                <%
                    try {
                        for (User friend : manager.getFriends(user.getUser_id())) {
                %>
                <li>
                    <a href="AnotherUser?name=<%= friend.getUsername() %>">
                        <img src="<%= friend.getProfilePhoto() %>">
                        <span class="FriendUserName"><%= friend.getUsername() %></span>
                    </a>
                </li>
                <%
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                %>
            </ul>
        </fieldset>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', () => {
        const quizzesTakenBtn = document.getElementById('quizzes-taken-btn');
        const quizzesCreatedBtn = document.getElementById('quizzes-created-btn');
        const quizTable = document.getElementById('quiz-table');

        const quizzesTaken = JSON.parse('<%= quizzesTakenJson.toString() %>');
        const quizzesCreated = JSON.parse('<%= quizzesCreatedJson.toString() %>');


        quizzesTakenBtn.addEventListener('click', () => {
            populateTable(quizzesTaken, 'taken');
        });

        quizzesCreatedBtn.addEventListener('click', () => {
            populateTable(quizzesCreated, 'created');
        });

        function populateTable(data, type) {
            quizTable.innerHTML = '';

            let headers = '';
            if (type === 'taken') {
                headers = `
                    <tr>
                        <th>Quiz Name</th>
                        <th>Score</th>
                        <th>Date</th>
                    </tr>
                `;
            } else if (type === 'created') {
                headers = `
                    <tr>
                        <th>Quiz Name</th>
                        <th>Date Created</th>
                        <th>Link to Quiz</th>
                    </tr>
                `;
            }
            quizTable.innerHTML = headers;

            data.forEach(quiz => {
                let row = '';
                if (type === 'taken') {
                    row = `
                        <tr>
                            <td>${quiz.quizName}</td>
                            <td>${quiz.score}</td>
                            <td>${quiz.date}</td>
                        </tr>
                    `;
                } else if (type === 'created') {
                    row = `
                        <tr>
                            <td>${quiz.quizName}</td>
                            <td>${quiz.dateCreated}</td>
                            <td><a href="quizStart.jsp?quiz_id=${quiz.quizId}">Link</a></td>
                        </tr>
                    `;
                }
                quizTable.innerHTML += row;
            });
        }

        populateTable(quizzesTaken, 'taken');
    });
</script>
</body>
</html>
