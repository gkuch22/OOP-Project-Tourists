<%@ page import="java.util.Objects" %>
<%@ page import="javaFiles.Quiz" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
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
            background-color: #3A3A5F;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 90%;
            max-width: 600px;
            margin-top: 80px;
        }
        .result-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }
        .result {
            font-size: 1.5em;
        }
        .stars {
            display: flex;
            flex-direction: row-reverse;
            justify-content: center;
            padding: 20px;
        }
        .stars input {
            display: none;
        }
        .stars label {
            font-size: 2em;
            color: #ccc;
            cursor: pointer;
            transition: color 0.2s;
        }
        .stars input:checked ~ label {
            color: #f5b301;
        }
        .stars input:hover ~ label,
        .stars input:checked ~ label:hover,
        .stars label:hover ~ input:checked ~ label {
            color: #f5b301;
        }
        .textarea {
            margin: 20px 0;
        }
        .textarea textarea {
            width: 85%;
            height: 150px;
            padding: 10px;
            font-size: 1em;
            border: 1px solid #ccc;
            border-radius: 4px;
            resize: none;
            background-color: #3A3A5F;
            color: white;
        }
        .friend-textarea {
            margin: 20px 0;
        }
        .friend-textarea textarea {
            width: 85%;
            height: 50px;
            padding: 10px;
            font-size: 1em;
            border: 1px solid #ccc;
            border-radius: 4px;
            resize: none;
            background-color: #3A3A5F;
            color: white;
        }


        .submit-button {
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


        .submit-button:hover {
            background-color: #333;
        }

        .report-button{
            padding: 10px 20px;
            background-color: #ff4c4c;
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

        .report-button:hover {
            background-color: #cc0000;
        }

        .report-button {
            margin-top: 20px;
        }

    </style>
</head>
<body>
<jsp:include page="topBar.jsp" />
<% System.out.println(request.getParameter("score")); %>
<div class="result-container">
    <div class="result-row">
        <div class="result">
            Correct Answers: <%= session.getAttribute("score") %> / <%= session.getAttribute("totalQuestions") %>
        </div>

        <div class="result">
            <%
                Object timeTakenObj = session.getAttribute("timeTaken");
                if (timeTakenObj != null) {
                    long timeTaken = (Long) timeTakenObj;
                    out.print("Time Taken: " + (timeTaken / 1000) + " seconds");
                } else {
                    out.print("Time Taken: N/A");
                }
            %>
        </div>
    </div>
    <form action="submitReviewServlet" method="post">
        <input type="hidden" name="rating" id="rating" value="0">
        <input type="hidden" name="score" value="<%= request.getAttribute("correctAnswers") %>">
        <input type="hidden" name="timeTaken" value="<%= request.getAttribute("timeTaken") %>">
        <div class="stars">
            <input type="radio" name="star" id="star1" value="5"><label for="star1">&#9733;</label>
            <input type="radio" name="star" id="star2" value="4"><label for="star2">&#9733;</label>
            <input type="radio" name="star" id="star3" value="3"><label for="star3">&#9733;</label>
            <input type="radio" name="star" id="star4" value="2"><label for="star4">&#9733;</label>
            <input type="radio" name="star" id="star5" value="1"><label for="star5">&#9733;</label>
        </div>
        <div class="textarea">
            <textarea name="review" cols="30" placeholder="Describe your experience..."></textarea>
        </div>
        <button type="submit" class="submit-button">Finish</button>
    </form>
    <form action="sendChallengeServlet" method="post">
        <div class="friend-textarea">
            <textarea name="friendName" cols="30" placeholder="Friend's name..."></textarea>
        </div>
        <button type="submit" class="submit-button">Send to Friend</button>
    </form>

    <form action="reportServlet" method="post">
        <button class="report-button" type="submit">Report Quiz</button>
    </form>


</div>
<script>
    document.querySelectorAll('.stars input').forEach(input => {
        input.addEventListener('change', (event) => {
            document.getElementById('rating').value = event.target.value;
        });
    });

    // Show pop-up notifications based on submission status
    const submissionStatus = '<%= request.getAttribute("submissionStatus") %>';
    if (submissionStatus === 'success') {
        alert('Challenge sent successfully!');
    } else if (submissionStatus === 'failure') {
        alert('Failed to send challenge. Please try again.');
    }
</script>
</body>
</html>
