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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/practiceStartServlet")
public class practiceStartServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizIdStr = request.getParameter("quiz_id");

        //System.out.println(quizIdStr);

        //Map<String, Integer> practice = new HashMap<String, Integer>();

        DBManager dbManager = null;

        try {
            dbManager = new DBManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        HttpSession session = request.getSession();
        //String quizIdStr = request.getParameter("quiz_id");
        //int quizId = Integer.parseInt(quizIdStr);

        //int quizId = Integer.parseInt(quizIdStr);
        int quizId = (Integer)session.getAttribute("quizId");
        Quiz quiz = (Quiz) session.getAttribute("quizz");
        List<Question> questions;
//        try {
//            questions = dbManager.getQuestions(quizId);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        for (Question question : questions) {
//            practice.put(question.getQuestionText(), 0);
//        }

//        session.setAttribute("practice", practice);

        //int quizId = (int) session.getAttribute("quizId");
        System.out.println(quizId + " alaaaa");
        session.setAttribute("currentQuestionIndex", 0);

        Map<String, Integer> practice = (Map<String, Integer>) session.getAttribute("practice");
        int allDone = 0;
        for (String key : practice.keySet()) {
            int tmp3 = practice.get(key);
            System.out.println(key + "   " + tmp3);
            if (tmp3 >= 3) allDone++;
        }
        if (allDone == practice.size()) {
            System.out.println("allDone");
            response.sendRedirect("practiceUp.jsp");
            return;
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
