<%@ page import="java.util.Objects" %>
<%@ page import="javaFiles.Quiz" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz Results</title>
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
        .result-container {
            background-color: #2D2D4B;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            text-align: center;
            color: white;
        }
        .result {
            font-size: 1.5em;
            margin-bottom: 10px;
        }
        .review-button {
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
            margin-top: 10px;
        }
        .review-button:hover {
            background-color: #0056b3;
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
            margin-top: 10px;
        }
        .button:hover {
            background-color: #0056b3;
        }
    </style>

</head>
<body>
<jsp:include page="topBar.jsp" />
<div class="result-container">
    <div class="result">
        Correct Answers: <%= request.getAttribute("correctAnswers") %> / <%= request.getAttribute("totalQuestions") %>
    </div>

    <div class="result">
        <%
            Object timeTakenObj = request.getAttribute("timeTaken");
            if (timeTakenObj != null) {
                long timeTaken = (Long) timeTakenObj;
                out.print("Time Taken: " + (timeTaken / 1000) + " seconds");
            } else {
                out.print("Time Taken: N/A");
            }
        %>
    </div>
    <div>
        <a href="homePage.jsp" class="button">Return to Home</a>
    </div>

</div>
</body>
</html>
