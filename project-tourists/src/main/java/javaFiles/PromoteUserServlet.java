package javaFiles;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PromoteUserServlet")
public class PromoteUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String name = "";
        try {
            DBManager manager = (DBManager) getServletContext().getAttribute("db-manager");
            manager.promoteUserToAdmin(userId);
            name = manager.getUsernameById(userId);
        } catch (SQLException e) {
            throw new ServletException("Failed to promote user to admin", e);
        }

        response.sendRedirect("AnotherUser?name="+name);
    }
}
