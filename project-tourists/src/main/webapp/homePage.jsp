<%@ page import="javaFiles.DBManager" %>
<%@ page import="javaFiles.Announcement" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="javaFiles.User" %>
<html>
<head>
    <title>WELCOME</title>
    <link rel="stylesheet" href="mystyle1.css">
</head>

<body>
<jsp:include page="topBar.jsp" />
<%
    DBManager manager = (DBManager) application.getAttribute("db-manager");
    List<Announcement> list = new ArrayList<Announcement>();

    list = manager.get_Announcement_List();
%>

<div class="left_container">
    <nav class="left_nav">
        <div class="left_div takeQuiz">
            <a class="left_href takeQuiz" href = "quizzespage.jsp"> Take Quiz </a>
        </div>
        <div class="left_div createQuiz">
            <a class="left_href creatQuiz" href = "createQuizes.jsp"> Create Quiz </a>
        </div>
        <%
                User user = null;
                try {
                    user = manager.getUserData((Integer)request.getSession().getAttribute("user_id"));

//                    user.setAdminStatus(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("isAdmin - " + user.isAdmin());
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
            <p>Announcements</p>
        </div>
        <ul class="messagebox">
            <%
                try {
                    for (Announcement s : list) {
            %>
            <li class="item">
                <div class="text-div announcement_name">
                    <h2><%= "announcement name" %></h2>
                </div>
                <div class="text-div announcement_text">
                    <p><%= s.get_post_text() %></p>
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
