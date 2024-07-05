<%--
  Created by IntelliJ IDEA.
  User: surma
  Date: 7/4/2024
  Time: 11:53 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FillInTheBlank Question</title>
    <link rel="stylesheet" href="multipleChoiceStyle.css">
</head>
<body>
<!-- Include the top bar -->
<jsp:include page="topBar.jsp" />
    <h1>Fill in the blank</h1>
    <form method="post" action="fillInTheBlank">
        <div class="question">
        <label for="question">Enter question, instead of the word that you want to be
        blank, type asterisk(*).</label>
        <textarea name="question" id="question" rows="5" cols="40"
                  placeholder="ex: The capital of * is Tbilisi, And the capital of * is London."></textarea>
        </div>
        <div class="answers">
            <label for="answers">
                Enter answers for asterisks in the correct order, separated by commas
            </label>
            <textarea placeholder="ex: Georgia,England" name="answers" id="answers" rows="2" cols="30"></textarea>
        </div>
        <input type="submit" value="Add Question">
    </form>
</body>
</html>
