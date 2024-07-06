package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/writeAnnouncement")
public class writeAnnouncementServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("titleText");
        String context = request.getParameter("contextText");
//        int userId = (int) request.getSession().getAttribute("user_id");
//        int userId = 1;
        DBManager dbManager = (DBManager) getServletContext().getAttribute("db-manager");
        int userId = (int) request.getSession().getAttribute("user_id");

        try {
            dbManager.addAnnouncement(title, context, userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("homePage.jsp");
    }


}
