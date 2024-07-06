<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Time's Up</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #1B1B32;
            margin: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            color: white;
        }
        .message-container {
            background-color: #2D2D4B;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            text-align: center;
            color: white;
        }
        .message {
            font-size: 1.5em;
            margin-bottom: 20px;
        }
        .button {
            padding: 10px 20px;
            background-color: #0A0A23;
            color: white;
            border: none;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<jsp:include page="topBar.jsp" />
<div class="message-container">
    <div class="message">Sorry, time is up!</div>
    <a href="homePage.jsp" class="button">Return to Home</a>
</div>
</body>
</html>
