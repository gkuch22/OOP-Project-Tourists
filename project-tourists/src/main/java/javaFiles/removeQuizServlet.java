package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/removeQuiz")
public class removeQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int quizId = Integer.parseInt(request.getParameter("removequizId"));
        DBManager dbManager = (DBManager) getServletContext().getAttribute("db-manager");
        try {
            dbManager.removeQuiz(quizId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("movida");
        response.sendRedirect("quizzespage.jsp");

    }

}





