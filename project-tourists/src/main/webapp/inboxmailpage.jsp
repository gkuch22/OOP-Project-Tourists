<%@ page import="javaFiles.DBManager" %>
<%@ page import="javaFiles.User" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page import="javaFiles.Message" %><%--
  Created by IntelliJ IDEA.
  User: gkuch
  Date: 04.07.2024
  Time: 13:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Inbox</title>
    <link rel="stylesheet" href="inboxpageStyle.css">
</head>

<body>
<jsp:include page="topBar.jsp" />
<%
//    int id1 = 1;
//    String username = "nick";
    DBManager dbManager = (DBManager) application.getAttribute("db-manager");
    int id1 = (int) request.getSession().getAttribute("user_id");
    User currUser = (User) dbManager.getUserData(id1);
    String username = currUser.getUsername();

    List<User> friends = null;
    try {
        friends = dbManager.getFriends(id1);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
%>


<div class="container">




    <div class="friends-list">
        <h2>Chats</h2>
        <%
            for(User friend : friends){
                String currUsername = friend.getUsername();
        %>
                <div class="friendboxbig" onclick="window.location.href='inboxchatpage.jsp?username2=<%= currUsername %>'">
                    <img class="profilepicture" src=<%=friend.getProfilePhoto()%>>
                    <div class="friendbox" ><%= currUsername %></div>
                </div>
        <%
            }
        %>

    </div>

    <div class="chat">
        <h2>Chat</h2>

    </div>




    <%
        List<Integer> friendRequests = null;
        try {
            friendRequests = dbManager.getFriendRequests(id1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    %>

    <div class="friend-requests">
        <h2>Friend Requests</h2>
        <%
            for (int requesterId : friendRequests) {
                String requesterUsername = dbManager.getUsernameById(requesterId);

        %>
                <div class="friend-request-box">
                    <p>Username: <%= requesterUsername %></p>
                    <form method="POST" action="friendRequestServlet">
                        <input type="hidden" name="requesterId" value="<%= requesterId %>">
                        <button type="submit" name="action" value="accept">+</button>
                        <button type="submit" name="action" value="decline">-</button>
                    </form>
                </div>
        <%
            }
        %>
    </div>



    <%
        List<Message> challenges = null;
        try {
            challenges = dbManager.getChallenges(id1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    %>
    <div class="challenges">
        <h2>Challenges</h2>
        <%
            for (Message challange : challenges) {
                int fromId = challange.getFromId();
                String fromUsername = dbManager.getUsernameById(fromId);
                String quizIdString = challange.getContext();
                int quizId = Integer.parseInt(quizIdString);
                String quizName = dbManager.getQuizName(quizId);
        %>
                <div class="challenge-box">
                    <p>Challanger: <%= fromUsername %></p>
                    <p>Title: <%= quizName %></p>
                    <form method="POST" action="challengeServlet">
                        <input type="hidden" name="quizId" value="<%= quizId %>">
                        <input type="hidden" name="requesterId" value="<%= fromId %>">
                        <button type="submit" name="action" value="accept">+</button>
                        <button type="submit" name="action" value="decline">-</button>
                    </form>
                </div>
        <%
            }
        %>

    </div>
</div>


</body>

</html>
