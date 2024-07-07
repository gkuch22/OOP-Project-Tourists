<%@ page import="javaFiles.DBManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="javaFiles.Message" %>
<%@ page import="java.util.List" %>
<%@ page import="javaFiles.Quiz" %>
<%@ page import="javaFiles.User" %><%--
  Created by IntelliJ IDEA.
  User: gkuch
  Date: 07.07.2024
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Quizzerinho</title>
</head>


<body>

<%
    DBManager dbManager = (DBManager) application.getAttribute("db-manager");
    List<Message> reports = null;
    try {
        reports = dbManager.getReports();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

%>

<div class="container">


    <div class="reportlist-box">

        <h2 class="title-box">Reports</h2>

        <%
            for(Message currReport : reports){
                int reporter = currReport.getFromId();
                User reporterUser = dbManager.getUserData(reporter);
                String reporterUsername = reporterUser.getUsername();

                int quizcreator = currReport.getToId();
                User creatorUser = dbManager.getUserData(quizcreator);
                String creatorUsername = creatorUser.getUsername();

                int quizId = Integer.parseInt(currReport.getContext());
                Quiz currQuiz = dbManager.getQuiz(quizId);
                String quizName = currQuiz.getQuiz_name();

                boolean fromReports = true;
        %>
                <div class="report-box">
                    <p>Quiz Name: <%= quizName %></p>
                    <p>Quiz Creator: <%= creatorUsername %></p>
                    <p>Reported by: <%= reporterUsername %></p>
                    <a href="quizStart.jsp?quiz_id=<%= quizId %>">Take Quiz</a>
                    <form action="banUser" method="get">
                        <input type="hidden" name="ban_user_id" value="<%= quizcreator %>">
                        <input type="hidden" name="from_reports" value="<%=fromReports%>">
                        <button class="ban-button" type="submit">Ban User</button>
                    </form>
                    <form action="dismissServlet" method="post">
                        <input type="hidden" name="reporter" value="<%=reporter%>">
                        <input type="hidden" name="creator" value="<%=quizcreator%>">
                        <input type="hidden" name="quizid" value="<%=quizId%>">
                        <button class="dismiss-button" type="submit">Dismiss</button>
                    </form>
                </div>
        <%

            }
        %>

    </div>


</div>



</body>

</html>
