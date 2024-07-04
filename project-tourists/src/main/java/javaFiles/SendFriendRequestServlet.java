package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class SendFriendRequestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int loggedInUserId = 1;
        int userId = Integer.parseInt(request.getParameter("userId"));
        DBManager manager = (DBManager) getServletContext().getAttribute("db-manager");
        try {
            manager.sendFriendRequest(loggedInUserId, userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}