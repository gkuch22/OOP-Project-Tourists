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
    int user_id = (Integer)request.getSession().getAttribute("user_id");

    try {
        list = manager.get_Announcement_List();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
%>

<div class="left_container">
    <nav class="left_nav">
        <div class="left_div takeQuiz">
            <a class="left_href takeQuiz" href = "quizzespage.jsp"> Take Quiz </a>
        </div>
        <%
            if(user_id != -1){

        %>
            <div class="left_div createQuiz">
                <a class="left_href creatQuiz" href = "createQuizes.jsp"> Create Quiz </a>
            </div>
        <%
            }
        %>
        <div class="left_div history">
            <a class="left_href history" href = "siteHistoryPage.jsp"> History </a>
        </div>
        <div class="left_div announcement active">
            <a class="left_href announcement" href = "homePage.jsp"> Announcements </a>
        </div>
        <%
                User user = null;
                try {
                    if(user_id != -1) user = manager.getUserData(user_id);

//                    user.setAdminStatus(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
//                System.out.println("isAdmin - " + user.isAdmin());
                if(user != null && user.isAdmin()){
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
            <h1>Announcements</h1>
        </div>
        <ul class="messagebox">
            <%
                try {
                    for (Announcement s : list) {
//                        System.out.println(s.get_post_id() + " " + s.get_post_name() + " " + s.get_post_text() + " " + s.get_user_id() + " " + s.get_date());
            %>
            <li class="item">
                <div class="text-div announcement_name">
                    <h2><%= s.get_post_name() %></h2>
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
