<%@ page import="javaFiles.User" %>
<%@ page import="javaFiles.UserImpl" %>
<%@ page import="javaFiles.DBManager" %><%--
  Created by IntelliJ IDEA.
  User: surma
  Date: 7/5/2024
  Time: 9:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="topBarStyle.css">
</head>
<body>
<% DBManager dbManager = (DBManager) application.getAttribute("db-manager");
    int userId = 1;
//    userId = (int)request.getSession().getAttribute("userId");
    User user = dbManager.getUserData(userId);
    String pictureURL = user.getProfilePhoto();
%>

<div class="topbar">
    <a href="/homePage.jsp"><img class="homePagePicture" src="logo1.png"></a>
    <a href="/UserPage.jsp"><img class="profilePicture" src="<%=pictureURL%>"></a>
    <a href="inboxmailpage.jsp"><img class="inboxPicture" src="mail.png"></a>
</div>
<div class="search-container">
    <input type="text" id="search-bar" placeholder="Search for users...">
    <div id="search-results"></div>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const searchBar = document.getElementById('search-bar');
        const searchResults = document.getElementById('search-results');

        searchBar.addEventListener('input', function() {
            const query = searchBar.value;
            if (query.length > 0) {
                fetch('searchUser?partialUsername=' + encodeURIComponent(query))
                    .then(response => response.json())
                    .then(users => {
                        searchResults.innerHTML = '';
                        users.forEach(user => {
                            const userLink = document.createElement('a');
                            userLink.href = 'AnotherUser?name=' + user;
                            userLink.textContent = user;
                            searchResults.appendChild(userLink);
                        });
                        searchResults.style.display = 'block';
                    })
                    .catch(error => console.error('Error:', error));
            } else {
                searchResults.innerHTML = '';
                searchResults.style.display = 'none';
            }
        });

        searchBar.addEventListener('keydown', function(event) {
            if (event.key === 'Enter') {
                const query = searchBar.value;
                if (query.length > 0) {
                    window.location.href = 'AnotherUser?name=' + encodeURIComponent(query);
                }
            }
        });

        document.addEventListener('click', function(event) {
            if (!searchBar.contains(event.target) && !searchResults.contains(event.target)) {
                searchResults.style.display = 'none';
            }
        });
    });
</script>
</body>
</html>
