package javaFiles;

import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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
import java.util.Objects;

@WebServlet("/imidSubmitQuizServlet")
public class imidSubmitQuizServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quizId = Integer.parseInt(request.getParameter("quiz_id"));
        long startTime = Long.parseLong(request.getParameter("startTime"));
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        int correctCount = Integer.parseInt(request.getParameter("correctCount"));
        int incorrectCount = Integer.parseInt(request.getParameter("incorrectCount"));
        System.out.println(correctCount + " correct");
        System.out.println(incorrectCount + " incorrect");

        // Process correctCount and incorrectCount as needed

        request.setAttribute("correctAnswers", correctCount);
        request.setAttribute("totalQuestions", incorrectCount + correctCount);
        //session.setAttribute("quizId", quizId);
        request.setAttribute("timeTaken", duration);

        // Redirect to the review page
        request.getRequestDispatcher("review.jsp").forward(request, response);
    }

}
