package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "questionResponseServlet", urlPatterns = {"/questionResponse"})
public class QuestionResponseServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Quiz currQuizz = (QuizImpl)request.getSession().getAttribute("quiz");
        String value = (String)request.getParameter("action");
        String questionText = (String)request.getParameter("question");
        String answer = (String)request.getParameter("answer");
        System.out.println("question - " + questionText);
        System.out.println("answer - " + answer);
        Question newQuestion = new QuestionResponse(questionText, answer);
        currQuizz.addQuestion(newQuestion);
        request.getRequestDispatcher("/chooseQuestionOrSubmit.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
