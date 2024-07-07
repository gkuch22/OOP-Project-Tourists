package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/dismissServlet")
public class dismissServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBManager dbManager = (DBManager) getServletContext().getAttribute("db-manager");

        int reporter = Integer.parseInt(request.getParameter("reporter"));
        int creator = Integer.parseInt(request.getParameter("creator"));
        int quizId = Integer.parseInt(request.getParameter("quizid"));
        String quizidString = "" + quizId;

        try {
            dbManager.removeReport(reporter, creator, quizidString);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("reportpage.jsp");
    }

}
