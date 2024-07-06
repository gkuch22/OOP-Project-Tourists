package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/challengeServlet")
public class challengeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String referer = request.getHeader("referer");

        String action = request.getParameter("action");
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        int fromId = Integer.parseInt(request.getParameter("requesterId"));

//        int toId = 1; //TODO useer!
//        User currUser = (User) request.getSession().getAttribute("user");
//        int toId = currUser.getUser_id();
        DBManager dbManager = (DBManager) getServletContext().getAttribute("db-manager");
        int toId = (int) request.getSession().getAttribute("user_id");


        try {
            dbManager.removeChallengeRequest(fromId, toId, quizId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if("accept".equals(action)) {
            //TODO challenge quizze gadaxtoma
            String path = "quizStart.jsp?quiz_id=" + quizId;
            response.sendRedirect(path);

        }else{
            if (referer.contains("inboxmailpage.jsp")) {
                response.sendRedirect("inboxmailpage.jsp");
            } else if (referer.contains("inboxchatpage.jsp")) {
                response.sendRedirect("inboxchatpage.jsp");
            }
        }

    }

}
