package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "chooseQuestion", urlPatterns = {"/chooseQuestion"})
public class QuestionFinishServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBManager dbManager = (DBManager) getServletContext().getAttribute("db-manager");
        Quiz currQuizz = (QuizImpl)request.getSession().getAttribute("quiz");
        String value = (String)request.getParameter("action");
        System.out.println("value - " + value);
//        if(currQuizz != null){
//            System.out.println("works");
//            System.out.println(currQuizz.getQuiz_name());
//        }else{
//            System.out.println("null");
//        }

        if(value.equals("Question-Response")){
            request.getRequestDispatcher("/questionResponse.jsp").forward(request, response);
        }else if(value.equals("Fill In The Blank")){
            request.getRequestDispatcher("/fillInTheBlank.jsp").forward(request, response);
        }else if(value.equals("Multiple Choice")){
            request.getRequestDispatcher("/multipleChoice.jsp").forward(request, response);
        }else if(value.equals("Picture Response")){
            request.getRequestDispatcher("/pictureResponse.jsp").forward(request, response);
        }else if(value.equals("Finish")){
//            ArrayList<Question> questions = currQuizz.getQuestions();
//            for(int i = 0; i < questions.size(); i++){
//                System.out.println(questions.get(i).getQuestionText());
//            }
            try {
                dbManager.addQuiz(currQuizz);
                dbManager.addQuestions(currQuizz);
                request.getSession().removeAttribute("quiz");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get in choose question");
        getServletContext().getRequestDispatcher("/chooseQuestionOrSubmit.jsp").forward(request,response);
    }
}
