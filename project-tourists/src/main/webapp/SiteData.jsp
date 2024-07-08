<%@ page import="javaFiles.DBManager" %>
<%@ page import="java.util.Map" %>
<%@ page import="javafx.util.Pair" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Statistics</title>
    <link rel="stylesheet" href="SiteData.css">
</head>
<body>
<jsp:include page="topBar.jsp" />
<%DBManager manager = (DBManager) application.getAttribute("db-manager");%>
<div class="container">
    <h1>General Statistics</h1>
    <ul>
        <li>Total Users: <%=manager.getUserCount()%></li>
        <li>Total Quizzes: <%=manager.getQuizzes().size()%></li>
        <li>Total Quiz Attempts: <%=manager.getReviewCount()%></li>
    </ul>

    <div class="section">
        <h2>User Engagement</h2>

        <h3>Most Active Users</h3>
        <%  Map<String,Integer> userActivity = manager.getSiteActivity();%>
        <table>
            <tr>
                <th>Username</th>
                <th>Activity Count</th>
            </tr>
            <%
                for(Map.Entry<String, Integer> entry : userActivity.entrySet()){%>
                    <tr>
                        <td><a href="AnotherUser?name=<%= entry.getKey()%>"><%=entry.getKey()%></a></td>
                        <td><%=entry.getValue()%></td>
                    </tr>


                <%}%>
        </table>

        <h2>User Feedback</h2>
        <ul>
            <li>Average Rating: <%=manager.getAverageRating()%></li>
            <li>Total Reviews: <%=manager.getReviewCount()%></li>
        </ul>
    </div>

    <div class="section">
        <h2>Highest Performing Quizzes</h2>
        <%List<Pair<String,Double>> getHighPerformanceQuizzes = manager.getHighPerformanceQuizzes();%>
        <table>
            <tr>
                <th>Quiz Name</th>
                <th>Average Score</th>
            </tr>
            <%for(Pair<String,Double> cur : getHighPerformanceQuizzes){%>
            <tr>
                <td><a href="quizStart.jsp?quiz_id=<%=cur.getKey()%>"><%=cur.getKey()%></a></td>
                <td><%=cur.getValue()%></td>
            </tr>
            <%}%>
        </table>
    </div>

    <div class="section">
        <h2>Tag Data</h2>
        <%Map<String,Integer> tagData = manager.getSiteTagData();%>
        <div class="tag-container">
            <%for(Map.Entry<String, Integer> entry : tagData.entrySet()){%>
            <div class="tag"><%=entry.getKey()%></div>
            <%}%>
        </div>

        <h3>Number of Quizzes per Tag</h3>
        <table>
            <tr>
                <th>Tag</th>
                <th>Number of Quizzes</th>
            </tr>
            <%for(Map.Entry<String, Integer> entry : tagData.entrySet()){%>
            <tr>
                <td><%=entry.getKey()%></td>
                <td><%=entry.getValue()%></td>
            </tr>
            <%}%>
        </table>
    </div>

    <div class="section">
        <h2>Quizzes Created by Administrators</h2>
        <ul>
            <li>Quizzes Created: <%=manager.getAdminQuizzesCount()%></li>
        </ul>

        <h2>User Management Statistics</h2>
        <ul>
            <li>Total Bans: <%=manager.getBanCount()%></li>
        </ul>
    </div>
</div>
</body>
</html>
