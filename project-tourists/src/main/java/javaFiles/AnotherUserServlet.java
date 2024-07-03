package javaFiles;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AnotherUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int visitingId = Integer.parseInt(request.getParameter("AnotherUserId"));
            request.setAttribute("current_id", visitingId);
            RequestDispatcher dispatcher = request.getRequestDispatcher("AnotherUser.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid user ID");
        }
    }
}