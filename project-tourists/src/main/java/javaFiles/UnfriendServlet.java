package javaFiles;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class UnfriendServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int loggedInUserId = (Integer)request.getSession().getAttribute("user_id");
        int userId = Integer.parseInt(request.getParameter("userId"));
        request.setAttribute("current_id", userId);
        DBManager manager = (DBManager) getServletContext().getAttribute("db-manager");
        try {
            manager.unfriend(loggedInUserId, userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        RequestDispatcher dispatcher=request.getRequestDispatcher("AnotherUser.jsp");
        dispatcher.forward(request,response);
    }
}
