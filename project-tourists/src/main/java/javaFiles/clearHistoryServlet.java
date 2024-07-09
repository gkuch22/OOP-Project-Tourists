package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/clearHistoryServlet")
public class clearHistoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String quizIdStr = request.getParameter("quiz_id");
        int quizId = Integer.parseInt(quizIdStr);
        System.out.println("in edit - -- " + quizId);
        DBManager dbManager = null;

        try {
            dbManager = new DBManager();
            dbManager.clearQuizHistory(quizId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.getRequestDispatcher("quizStart.jsp").forward(request, response);

    }
}