<%@ page import="javaFiles.DBManager" %>
<%@ page import="javaFiles.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="javaFiles.Message" %><%--
  Created by IntelliJ IDEA.
  User: gkuch
  Date: 04.07.2024
  Time: 17:11
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
    String username2 = request.getParameter("username2");
%>



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
    <h2><%=username2%></h2>
    <%
        List<Message> messages = dbManager.getMessages(id1, username, username2);

        for (Message currMessage : messages) {
            int fromId = currMessage.getFromId();
            String currContext = currMessage.getContext();
            if (fromId == id1) {
        %>
                <div class="own-text"><%= currContext %></div>
        <%
            }else{
        %>
                <div class="other-text"><%= currContext %></div>
        <%
            }
        }
    %>
</div>






</body>

</html>
