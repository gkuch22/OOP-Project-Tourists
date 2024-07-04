package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "fillInTheBlankServlet", urlPatterns = {"/fillInTheBlank"})
public class FillInTheBlankServlet extends HttpServlet {

    private String getParameter(String parameter, HttpServletRequest request){
        return (String)request.getParameter(parameter);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Quiz currQuizz = (QuizImpl)request.getSession().getAttribute("quiz");

        String questionText = getParameter("question", request);
        questionText = questionText.replace("*", " _________ ");
        String answers = getParameter("answers", request);

        System.out.println("question - " + questionText);
        System.out.println("answers - " + answers);
        Question newQuestion = new FillInTheBlank(questionText, answers);
        currQuizz.addQuestion(newQuestion);
        request.getRequestDispatcher("/chooseQuestionOrSubmit.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
