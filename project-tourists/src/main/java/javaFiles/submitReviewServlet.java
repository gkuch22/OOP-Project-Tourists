package javaFiles;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;


@WebServlet("/submitReviewServlet")
public class submitReviewServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int quizId = (int) session.getAttribute("quizId");
        int userId = (int) session.getAttribute("user_id");
        int rating = Integer.parseInt(request.getParameter("rating"));
        System.out.println(rating + " rating");
        Quiz quiz = (Quiz) session.getAttribute("quizz");

        String review = request.getParameter("review");
        if (review == null) review = "";
        System.out.println(review + " review");
        Date date = new Date();

        System.out.println(quizId + " quiz id");
        System.out.println(userId + " user id");
        System.out.println(rating + " rating");
        System.out.println(review + " review");
        System.out.println(date + " date");

        int score = (Integer) session.getAttribute("score");
        //int score = Integer.parseInt(request.getParameter("score"));
        long time = (Long) session.getAttribute("timeTaken");
        //long time = Long.parseLong(request.getParameter("timeTaken"));
        System.out.println(score + " score");

        DBManager dbManager;

        try {
            dbManager = new DBManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int highestScore = -10000;
        try {
            highestScore = dbManager.getHighestScore(quizId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(score + " score");
        System.out.println(highestScore + " highest");
        if (quiz.isGradable()) {
            score -= (int) (time / 10);
        }
        if (score > highestScore) {
            try {
                dbManager.updateUserScoredHighest(userId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        try {
            dbManager.saveReview(userId, quizId, score, time, date, rating, review, (String) session.getAttribute("quizName"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        response.sendRedirect("homePage.jsp");
    }
}
