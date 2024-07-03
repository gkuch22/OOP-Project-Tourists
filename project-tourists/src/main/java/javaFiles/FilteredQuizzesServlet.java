package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/filteredquizzes")
public class FilteredQuizzesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tag = request.getParameter("tags");
        String difficulty = request.getParameter("difficulty");
        String orderBy = request.getParameter("orderby");
        String searchName = request.getParameter("quizName");


        DBManager dbManager = (DBManager) getServletContext().getAttribute("db-manager");
        List<Quiz> filteredQuizzes = null;
        try {
            if ("all".equals(difficulty) && "none".equals(tag) && "none".equals(orderBy)) {
                filteredQuizzes = dbManager.getQuizzes();
            } else {
                filteredQuizzes = dbManager.getFilteredQuizzes(difficulty, tag, orderBy, searchName);
            }
//            filteredQuizzes = dbManager.getFilteredQuizzes(difficulty, tag, orderBy, searchName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("quizzeslist", filteredQuizzes);
        request.getRequestDispatcher("filteredquizzes.jsp").forward(request, response);
    }

}
