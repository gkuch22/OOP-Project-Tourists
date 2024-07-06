package javaFiles;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@WebServlet(name = "banUser", urlPatterns = {"/banUser"})
public class banUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBManager dbManager = (DBManager) getServletContext().getAttribute("db-manager");
        int userId = (int) request.getSession().getAttribute("ban_user_id");
        String reason = request.getParameter("reason");
        String dateInString = (String)request.getParameter("banDate");
        System.out.println("user_id - " + userId);
        System.out.println("reason - " + reason);
        System.out.println("date - " + dateInString);
        try {
            dbManager.banUser(userId, dateInString, reason);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = 1;
//        id = Integer.parseInt(request.getParameter("user_ban_id"));
        request.getSession().setAttribute("ban_user_id", id);
        request.getRequestDispatcher("/banUser.jsp").forward(request,response);
    }
}
