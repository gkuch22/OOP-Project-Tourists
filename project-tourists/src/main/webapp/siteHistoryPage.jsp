<%@ page import="javaFiles.DBManager" %>
<%@ page import="javaFiles.Announcement" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="javaFiles.User" %>
<%@ page import="javaFiles.History" %>
<html>
<head>
    <title>WELCOME</title>
    <link rel="stylesheet" href="mystyle1.css">
</head>

<body>
<jsp:include page="topBar.jsp" />
<%
    DBManager manager = (DBManager) application.getAttribute("db-manager");
    List<History> list = new ArrayList<History>();

    try {
        list = manager.get_History_List();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
%>

<div class="left_container">
    <nav class="left_nav">
        <div class="left_div takeQuiz">
            <a class="left_href takeQuiz" href = "quizzespage.jsp"> Take Quiz </a>
        </div>
        <div class="left_div createQuiz">
            <a class="left_href creatQuiz" href = "createQuizes.jsp"> Create Quiz </a>
        </div>
        <div class="left_div history active">
            <a class="left_href history" href = "siteHistoryPage.jsp"> History </a>
        </div>
        <div class="left_div announcement">
            <a class="left_href announcement" href = "homePage.jsp"> Announcements </a>
        </div>
        <%
            User user = null;
            try {
                user = manager.getUserData((Integer)request.getSession().getAttribute("user_id"));

//                    user.setAdminStatus(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
//                System.out.println("isAdmin - " + user.isAdmin());
            if(user.isAdmin()){
        %>
        <div class="left_div announce">
            <a class="left_href creatQuiz" href = "writeannouncement.jsp"> Announce </a>
        </div>
        <%
            }
        %>
    </nav>
</div>

<div class="right_container">

    <div class = "announcements">
        <div class="line">
            <h1>History</h1>
        </div>
        <ul class="messagebox">
            <%
                try {
                    for (History s : list) {
            %>
            <li class="item">
                <div class="text-div announcement_name">
                    <h2><%= s.get_history_name() %></h2>
                </div>
                <div class="text-div announcement_text">
                    <p><%="description: " + s.get_history_description() %></p>
                </div>
            </li>
            <%
                    }
                } catch (RuntimeException e) {
                    throw new RuntimeException(e);
                }
            %>
        </ul>
    </div>
</div>


</body>

</html>
