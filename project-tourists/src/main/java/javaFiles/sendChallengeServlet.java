package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/sendChallengeServlet")
public class sendChallengeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String friendName = request.getParameter("friendName");

        HttpSession session = request.getSession();
        int fromId = (Integer) session.getAttribute("user_id");

        try {
            DBManager dbManager = new DBManager();
            int toId = dbManager.getUserIdByName(friendName);

            if (toId != -1 && dbManager.areFriends(fromId, toId)) {
                Integer tmp = (Integer) session.getAttribute("quizId");
                String tmp2 = tmp.toString();
                dbManager.sendMail(fromId, toId, "challenge", tmp2);
                response.sendRedirect("review.jsp");
            } else {
                response.sendRedirect("review.jsp");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
}