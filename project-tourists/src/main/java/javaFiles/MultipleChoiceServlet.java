package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "multipleChoiceQuestion", urlPatterns = {"/multipleChoice"})
public class MultipleChoiceServlet extends HttpServlet {

    private String getParameter(String parameter, HttpServletRequest request){
        return (String)request.getParameter(parameter);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Quiz currQuizz = (QuizImpl)request.getSession().getAttribute("quiz");
        String value = (String)request.getParameter("action");
        String questionText = getParameter("question", request);
        String possibleAnswers = getParameter("possAnswers", request);
        String answer = getParameter("answer", request);
        System.out.println("question - " + questionText);
        System.out.println("answer - " + answer);
        String[] possAnswers = possibleAnswers.split(",");
        Question newQuestion = new MultipleChoice(questionText, answer, possAnswers);
        currQuizz.addQuestion(newQuestion);
        request.getRequestDispatcher("/chooseQuestionOrSubmit.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
