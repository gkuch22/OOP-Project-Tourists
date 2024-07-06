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
    <title>Picture Response Question</title>
    <link rel="stylesheet" href="multipleChoiceStyle.css">
</head>
<body>
<!-- Include the top bar -->
<jsp:include page="topBar.jsp" />
<h1>Picture-Response</h1>
<form method="post" action="/pictureResponse">
    <div class="question">
        <label for="question">Type question</label>
        <textarea required placeholder="ex: What kind of bird do you see in the picture?" name="question" id="question" rows="5" cols="40"></textarea>
    </div>
    <div class="imageURL">
        <label for="imageURL">Enter image url</label>
        <input required placeholder="ex: https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.smithsonianmag.com%2Fscience-nature%2F14-fun-facts-about-parrots-can-sing-use-tools-and-live-long-time-180957714%2F&psig=AOvVaw2k_fVcL_-d4b2WqlwTi1ir&ust=1720184451651000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCPj89cm4jYcDFQAAAAAdAAAAABAS" type="text" name="imageURL" id="imageURL">
    </div>
    <div class="answer">
        <label for="answer">Enter correct answer</label>
        <input required name="answer" id="answer" type="text" placeholder="ex: parrot">
    </div>
    <input type="submit" value="Add Question">
</form>
</body>
</html>
