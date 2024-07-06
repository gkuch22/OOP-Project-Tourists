package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/friendRequestServlet")
public class friendRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String referer = request.getHeader("referer");

        String action = request.getParameter("action");
        int fromId = Integer.parseInt(request.getParameter("requesterId"));

//        int toId = 1; //TODO userid get parameter
        DBManager dbManager = (DBManager) getServletContext().getAttribute("db-manager");
        int toId = (int) request.getSession().getAttribute("user_id");


        try {
            dbManager.removeFriendRequest(fromId, toId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if ("accept".equals(action)) {
            try {
                dbManager.addFriendCouple(fromId, toId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (referer.contains("inboxmailpage.jsp")) {
            response.sendRedirect("inboxmailpage.jsp");
        } else if (referer.contains("inboxchatpage.jsp")) {
            response.sendRedirect("inboxchatpage.jsp");
        }

    }

}
