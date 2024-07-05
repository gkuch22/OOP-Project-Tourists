<html>
<head>
    <title>WELCOME</title>
    <link rel="stylesheet" href="mystyle1.css">
</head>

<body>

<div class="left_container">
    <nav class="left_nav">
        <div class="left_div login active">
            <a class="left_href login_href" href = "login.jsp"> LOGIN </a>
        </div>
        <div class="left_div signup">
            <a class="left_href signup_href" href = "signup.jsp"> SIGN UP </a>
        </div>
        <div class="left_div guest">
            <a class="left_href guest_href" href = "guest.jsp"> GUEST MODE </a>
        </div>
        <div class="left_div about">
            <a class="left_href about_href" href = "about.jsp"> ABOUT US </a>
        </div>
    </nav>
</div>

<div class="right_container">
    <div class = "head">
        <p>Quizerinio</p>
    </div>

    <div class = "form">
        <div class = "fillers">
            <input type="text" placeholder="USERNAME" class = "username" name = "username"/>
            <input type="password" placeholder="PASSWORD" class="password" name = "password"/>
        </div>
        <div class = "buttonDiv">
            <button class="sign"> SIGN IN </button>
<%--            <button class="register"> register </button>--%>
        </div>
    </div>
</div>


</body>

</html>
