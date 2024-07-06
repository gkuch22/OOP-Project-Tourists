package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        try {
            DBManager manager = (DBManager) getServletContext().getAttribute("db-manager");
            manager.deleteUser(userId);
        } catch (SQLException e) {
            throw new ServletException("Failed to delete user", e);
        }

        response.sendRedirect("UserPage.jsp");
    }
}
