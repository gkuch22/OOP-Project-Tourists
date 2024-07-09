<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page import="javaFiles.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="javafx.util.Pair" %>

<%
    Integer user_id = (Integer) request.getAttribute("current_id");
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Another User Page</title>
    <link rel="stylesheet" href="UserStyle.css">
</head>
<body>
<jsp:include page="topBar.jsp" />
<%
    DBManager manager = (DBManager) application.getAttribute("db-manager");
    List<QuizPerformance> quizzesTaken;
    List<Quiz> quizzesCreated;
    User user;
    User mainUser=null;
    boolean isCurrentUserAdmin = false;
    boolean isVisitedUserAdmin = false;
    try {
        user = manager.getUserData(user_id);
        int mainUserId = (Integer) request.getSession().getAttribute("user_id");
        if(mainUserId!=-1)  mainUser = manager.getUserData(mainUserId);
        quizzesTaken = manager.getUserQuizzes(user.getUser_id());
        quizzesCreated = manager.getUserCreatedQuizzes(user);
        if(mainUser!=null){
            isCurrentUserAdmin = mainUser.isAdmin();
        }
        isVisitedUserAdmin = user.isAdmin();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    StringBuilder quizzesTakenJson = new StringBuilder("[");
    for (int i = 0; i < quizzesTaken.size(); i++) {
        QuizPerformance quiz = quizzesTaken.get(i);
        quizzesTakenJson.append("{")
                .append("\"quizName\":\"").append(quiz.getQuiz_name()).append("\",")
                .append("\"score\":\"").append(quiz.getScore()).append("\",")
                .append("\"date\":\"").append(quiz.getDate()).append("\",")
                .append("\"quizId\":\"").append(quiz.getQuiz_id()).append("\"")
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
    <h1><span class="Number"><%= manager.getUniqueUserQuizzes(user.getUser_id()).size() %></span> <span class="Text">Quizzes Taken</span></h1>
</div>
<div class="UserName">
    <a><%=user.getUsername()%></a>
</div>
<img class="UserProfile" src="<%=user.getProfilePhoto()%>" alt="<%=user.getProfilePhoto()%>">

<%
    if(mainUser!=null){
    try {
        boolean isFriend = false;
        for(User user1 : manager.getFriends(user.getUser_id())){
            if(user1.getUser_id()==mainUser.getUser_id()) isFriend=true;
        }
        if(isFriend){
%>
<a id="unfriend" class="unfriend-button" href="UnfriendServlet?userId=<%= user.getUser_id() %>"> Unfriend </a>
<%}else{%>

<button id="sendRequestBtn" class="unfriend-button">Send Friend Request</button>
<%
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }
%>
<div class="admin-buttons">
    <% if (isCurrentUserAdmin && isVisitedUserAdmin) { %>
    <form action="DemoteUserServlet" method="post">
        <input type="hidden" name="userId" value="<%=user.getUser_id()%>">
        <button type="submit" class="demote-button">Demote</button>
    </form>
    <% } else if (isCurrentUserAdmin && !isVisitedUserAdmin) { %>
    <form action="PromoteUserServlet" method="post">
        <input type="hidden" name="userId" value="<%=user.getUser_id()%>">
        <button type="submit" class="promote-button">Promote</button>
    </form>
    <% } %>
    <%  if(isCurrentUserAdmin){ %>
    <form action="DeleteUserServlet" method="post">
        <input type="hidden" name="userId" value="<%=user.getUser_id()%>">
        <button type="submit" class="delete-button">Delete User</button>
    </form>
    <form action="banUser" method="get">
        <input type="hidden" name="ban_user_id" value="<%=user.getUser_id()%>">
        <button type="submit" class="ban-button">Ban User</button>
    </form>
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
                            for (Pair<String,String> s : manager.getAchievements(user)) {
                    %>
                    <li>
                        <a>
                            <img src="<%= s.getValue() %>" alt="<%= s.getKey() %>">
                            <span class="achievement-tooltip"><%= manager.getAchievementDescription(s.getKey()) %></span>
                        </a>
                        <span class="FriendUserName"><%= s.getKey() %></span>
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
            <legend>Tags</legend>
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
                <table id="quiz-table" class="quiz-data">

                </table>
            </div>
        </fieldset>
    </div>
    <div class="right">
        <fieldset>
            <legend>Friends List</legend>
            <ul class="friends-list">
                <%
                    try {
                        for (User user1 : manager.getFriends(user.getUser_id())) {
                %>
                <li>
                    <a href="AnotherUser?name=<%= user1.getUsername() %>">
                        <img src="<%= user1.getProfilePhoto() %>">
                        <span class="FriendUserName"><%= user1.getUsername() %></span>
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

        quizzesTakenBtn.classList.add('active');
        populateTable(quizzesTaken, 'taken');

        quizzesTakenBtn.addEventListener('click', () => {
            quizzesTakenBtn.classList.add('active');
            quizzesCreatedBtn.classList.remove('active');
            populateTable(quizzesTaken, 'taken');
        });

        quizzesCreatedBtn.addEventListener('click', () => {
            quizzesCreatedBtn.classList.add('active');
            quizzesTakenBtn.classList.remove('active');
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
                    <td><a href="quizStart.jsp?quiz_id=${quiz.quizId}">${quiz.quizName}</a></td>
                    <td>${quiz.score}</td>
                    <td>${quiz.date}</td>
                </tr>
            `;
                } else if (type === 'created') {
                    row = `
                <tr>
                    <td>${quiz.quizName}</td>
                    <td>${quiz.dateCreated}</td>
                    <td><a href="quizStart.jsp?quiz_id=${quiz.quizId}">Go to quiz</a></td>
                </tr>
            `;
                }
                quizTable.innerHTML += row;
            });
        }
    });
</script>
<script>
    document.getElementById('sendRequestBtn').addEventListener('click', function() {
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'SendFriendRequestServlet', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.send('userId=<%= user.getUser_id() %>');
        document.getElementById('sendRequestBtn').classList.add('clicked');
        setTimeout(function() {
            document.getElementById('sendRequestBtn').classList.remove('clicked');
        }, 300);
    });
</script>
</body>
</html>
