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
</head>

<body>

<%
//    User currUser = session.getAttribute("user");
    int id1 = 1; //currUser.getUser_id()
    String username = "nick";

    DBManager dbManager = (DBManager) application.getAttribute("db-manager");
    List<User> friends = null;
    try {
        friends = dbManager.getFriends(id1); //currUser.getUser_id()
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
                <div class="friendbox" onclick="window.location.href='inboxchatpage.jsp?username2=<%= currUsername %>'"><%= currUsername %></div>
        <%
            }
        %>

    </div>
    <div class="chat-window">
        <h2>Chat</h2>

    </div>
</div>



</body>

</html>
