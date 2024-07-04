package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "imageResponseServlet", urlPatterns = {"/pictureResponse"})
public class PictureResponseServlet extends HttpServlet {
    private String getParameter(String parameter, HttpServletRequest request){
        return (String)request.getParameter(parameter);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Quiz currQuizz = (QuizImpl)request.getSession().getAttribute("quiz");
        String questionText = getParameter("question", request);
        String imageURL = getParameter("imageURL", request);
        String answer = getParameter("answer", request);
        System.out.println("question - " + questionText);
        System.out.println("answer - " + answer);
        System.out.println("imagerURl - " + imageURL);
        Question newQuestion = new PictureResponse(questionText, answer, imageURL);
        currQuizz.addQuestion(newQuestion);
        request.getRequestDispatcher("/chooseQuestionOrSubmit.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
