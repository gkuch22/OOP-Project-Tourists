<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="javaFiles.*" %>
<%@ page import="java.util.Map" %>

<%
    Integer user_id = (Integer) request.getAttribute("current_id");
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz Site - User Page</title>
    <link rel="stylesheet" href="UserStyle.css">
</head>
<body>
<%
    DBManager manager = (DBManager) application.getAttribute("db-manager");
    List<QuizPerformance> quizzesTaken = new ArrayList<QuizPerformance>();
    List<Quiz> quizzesCreated = new ArrayList<Quiz>();
    User user;
    try {
        user = manager.getUserData(user_id);
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

<div class="ProblemsSolved">
    <h1><span class="Number"><%= manager.getUniqueUserQuizzes(user.getUser_id()).size() %></span> <span class="Text">Quizzes Attempted</span></h1>
</div>

<a href="index.jsp" class="homepage-button">Homepage</a>
<img class="UserProfile" src=<%=user.getProfilePhoto()%>>
<a href="UserPage.jsp" class="Userpage-button">Back to User Page</a>
<%
    try {
        boolean isFriend = false;
        for(User user1 : manager.getFriends(user.getUser_id())){
            if(user1.getUser_id()==1) isFriend=true;
        }
        if(isFriend){
    %>
    <button id="unfriend" class="unfriend-button"> Unfriend </button>
        <%}else{%>

    <button id="sendRequestBtn" class="unfriend-button">Send Friend Request</button>
    <%
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
%>

<div class="container">
    <div class="left">
        <div class="Achievements">
            <h2>Achievements</h2>
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
        <div class="button-container">
            <button id="quizzes-taken-btn">Quizzes Taken</button>
            <button id="quizzes-created-btn">Quizzes Created</button>
        </div>
        <div class="table-container">
            <table id="quiz-table" class="quiz-data">

            </table>
        </div>
    </div>
    <div class="right">
        <h2>Friends List</h2>
        <ul class="friends-list">
            <%
                try {
                    for (User user1 : manager.getFriends(user.getUser_id())) {
            %>
            <li>
                <a href="AnotherUser?AnotherUserId=<%= user1.getUser_id() %>">
                    <img src="<%= user1.getProfilePhoto() %>">
                    <span><%= user1.getUsername() %>></span>
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
                            <td><a href="QuizPage?id=${quiz.quizId}">Link</a></td>
                        </tr>
                    `;
                }
                quizTable.innerHTML += row;
            });
        }

        populateTable(quizzesTaken, 'taken');
    });
</script>
<script>
    document.getElementById('sendRequestBtn').addEventListener('click', function() {
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'SendFriendRequestServlet', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.send('userId=<%= user.getUser_id() %>');
    });
</script>
<script>
    document.getElementById('unfriend').addEventListener('click', function () {
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'UnfriendServlet', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.send('userId=<%= user.getUser_id() %>');
    });
</script>
</body>
</html>
