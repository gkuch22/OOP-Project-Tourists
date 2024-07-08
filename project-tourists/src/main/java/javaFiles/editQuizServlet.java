package javaFiles;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

@WebServlet(name = "editQuiz", urlPatterns = {"/editQuiz"})
public class editQuizServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("shemovida");
        DBManager dbManager = (DBManager) getServletContext().getAttribute("db-manager");
        String value = (String)request.getParameter("action");

        System.out.println(value);

        if(value.equals("Edit quiz options")){
            request.getRequestDispatcher("/editQuizOptions.jsp").forward(request, response);
        }else if(value.equals("Edit quiz questions")){
            request.getRequestDispatcher("/editQuizQuestions.jsp").forward(request, response);
        }else if(value.equals("Finish")){
//
        }
        //request.getRequestDispatcher("/chooseQuestionOrSubmit.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
