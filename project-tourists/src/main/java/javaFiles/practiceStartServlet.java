package javaFiles;

import javaFiles.DBManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/practiceStartServlet")
public class practiceStartServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizIdStr = request.getParameter("quiz_id");

        //System.out.println(quizIdStr);


        HttpSession session = request.getSession();
        //String quizIdStr = request.getParameter("quiz_id");
        //int quizId = Integer.parseInt(quizIdStr);
        Quiz quiz = (Quiz) session.getAttribute("quizz");

        int quizId = Integer.parseInt(quizIdStr);
        //int quizId = (int) session.getAttribute("quizId");
        System.out.println(quizId + " alaaaa");
        session.setAttribute("currentQuestionIndex", 0);
        DBManager dbManager;
        try {
            dbManager = new DBManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        boolean multiplePages = false;
        try {
            multiplePages = dbManager.isMultiplePages(quizId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (multiplePages) {
//            HttpSession session = request.getSession();
//            session.setAttribute("quiz_id", quizId);
//            response.sendRedirect("multiPageQuizServlet");
            if (quiz.isImmediatelyCorrected()) {
                response.sendRedirect("pracImidMultiPage.jsp?quiz_id=" + quizId);
            } else {
                response.sendRedirect("practiceMultiPage.jsp?quiz_id=" + quizId);
            }
        } else {
            response.sendRedirect("practiceSinglePage.jsp?quiz_id=" + quizId);
        }
    }
}
