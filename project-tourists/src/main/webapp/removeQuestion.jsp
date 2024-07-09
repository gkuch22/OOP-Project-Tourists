<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.List" %>
<%@ page import="javaFiles.Question" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="javaFiles.DBManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Get the question_text parameter from the request
    String questionText = request.getParameter("questionText");

    // Remove the question from the database or any other necessary action
    DBManager dbManager = (DBManager) application.getAttribute("db-manager");
    System.out.println(questionText);
    try {
         dbManager.removeQuestion(questionText);
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Update the session attribute if removal was successful


        List<Question> questions = (List<Question>) session.getAttribute("quiz_questions");
        if (questions != null) {
//            questions.removeIf(q -> q.getQuestionText().equals(questionText));
              for(Question q:questions){
                  if(Objects.equals(q.getQuestionText(), questionText)){
                      questions.remove(q);
                  }
              }
            session.setAttribute("quiz_questions", questions);
        }


    // Prepare JSON response indicating success or failure
    response.setContentType("application/json");
    PrintWriter out1 = response.getWriter();
    out1.println("{\"success\":" + true + "}");
    out1.close();
//    out.close();
%>
