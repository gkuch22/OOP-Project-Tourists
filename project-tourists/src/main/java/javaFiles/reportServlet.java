package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/reportServlet")
public class reportServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int quizId = (Integer) session.getAttribute("quizId");
        int fromId = (Integer) session.getAttribute("user_id");

        DBManager dbManager = (DBManager) getServletContext().getAttribute("db-manager");
        try {
            dbManager.saveReportToDataBase(quizId, fromId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("review.jsp").forward(request, response);
    }


}
