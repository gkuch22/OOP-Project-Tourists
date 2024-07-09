<html>
<head>
    <title>WELCOME</title>
    <link rel="stylesheet" href="mystyle1.css">
</head>

<body>

<div class="left_container">
    <nav class="left_nav">
        <div class="left_div login">
            <a class="left_href login_href" href = "signin.jsp"> LOGIN </a>
        </div>
        <div class="left_div signup">
            <a class="left_href signup_href" href = "signup.jsp"> SIGN UP </a>
        </div>
        <div class="left_div guest active">
            <a class="left_href guest_href" href = "guest.jsp" > GUEST MODE </a>
        </div>
        <div class="left_div about">
            <a class="left_href about_href" href = "about.jsp"> ABOUT US </a>
        </div>
    </nav>
</div>

<div class="right_container">
    <div class = "head">
        <img class="homePagePicture" src="logo1.png">
    </div>
    <form method="post" action="guestServlet">
        <div class = "form">
            <div class = "buttonDiv">
                <button class="sign"> ENTER AS GUEST </button>
            </div>
        </div>
    </form>
</div>


</body>

</html>
