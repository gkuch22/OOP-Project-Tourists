package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "chooseQuestion", urlPatterns = {"/chooseQuestion"})
public class QuestionFinishServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Quiz currQuizz = (QuizImpl)request.getSession().getAttribute("quiz");
        String value = (String)request.getParameter("action");
        System.out.println("value - " + value);
        if(currQuizz != null){
            System.out.println("works");
            System.out.println(currQuizz.getQuiz_name());
        }else{
            System.out.println("null");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get in choose question");
        getServletContext().getRequestDispatcher("/chooseQuestionOrSubmit.jsp").forward(request,response);
    }
}
