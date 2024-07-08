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

        if(request.getSession().getAttribute("from_reports") != null) {
            int reporter = (Integer) request.getSession().getAttribute("reporterrr");
            int creator = (Integer) request.getSession().getAttribute("creatorrr");
            String quizidString = (String) request.getSession().getAttribute("quiziddd");
            try {
                dbManager.removeReport(reporter, creator, quizidString);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
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
        request.getSession().removeAttribute("ban_user_id");
        request.getSession().removeAttribute("from_reports");
        request.getRequestDispatcher("/homePage.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idInStr = request.getParameter("ban_user_id");
        System.out.println("id in banUser - " + idInStr);
        int id = Integer.parseInt(request.getParameter("ban_user_id"));
        if(request.getParameter("from_reports") != null) {
            int reporter = Integer.parseInt(request.getParameter("reporter"));
            int creator = Integer.parseInt(request.getParameter("creator"));
            int quizId = Integer.parseInt(request.getParameter("quizid"));
            String quizidString = "" + quizId;
            request.getSession().setAttribute("from_reports", true);
            request.getSession().setAttribute("reporterrr", reporter);
            request.getSession().setAttribute("creatorrr", creator);
            request.getSession().setAttribute("quiziddd", quizidString);
        }
        request.getSession().setAttribute("ban_user_id", id);
        request.getRequestDispatcher("/banUser.jsp").forward(request,response);
    }
}
