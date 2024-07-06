<%--
  Created by IntelliJ IDEA.
  User: gkuch
  Date: 06.07.2024
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Make Announcement</title>
    <link rel="stylesheet" href="writeAnnouncementStyle.css">
</head>


<body>
<jsp:include page="topBar.jsp" />

    <div class="container">
        <form class="announcementbox" action="writeAnnouncement" method="post">
            <h2 class="titlebox">Title</h2>
            <input class="titlefield" type="text" name="titleText" placeholder="...">
            <h2 class="contextbox">Announcement</h2>
            <textarea class="contextfield" name="contextText" placeholder="..."></textarea>
            <button class="makebutton" type="submit">Make Announcement</button>
        </form>
    </div>

</body>

</html>
