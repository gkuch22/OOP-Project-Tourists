package javaFiles;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class AnotherUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String visitingName = request.getParameter("name");
            DBManager manager = (DBManager) getServletContext().getAttribute("db-manager");
            if(!manager.userExists(visitingName)){
                request.setAttribute("current_name",visitingName);
                RequestDispatcher dispatcher = request.getRequestDispatcher("UnknownUser.jsp");
                dispatcher.forward(request,response);
                return;
            }
            if((int) request.getSession().getAttribute("user_id") < 0){
                request.getRequestDispatcher("GuestUserPage.jsp").forward(request,response);
                return;
            }

            int visitingId = manager.getIdByUsername(visitingName);

            request.setAttribute("current_id", visitingId);
            RequestDispatcher dispatcher=null;
            if(visitingId==(Integer)request.getSession().getAttribute("user_id")){
                dispatcher = request.getRequestDispatcher("UserPage.jsp");
            }
            else{
                System.out.println(visitingId);
                dispatcher = request.getRequestDispatcher("AnotherUser.jsp");
            }

            dispatcher.forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid user ID");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}