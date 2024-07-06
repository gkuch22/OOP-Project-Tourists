package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@WebServlet("/sendMessageServlet")
public class sendMessageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String toUsername = request.getParameter("toUsername");
        String message = request.getParameter("message");
        String timestamp = request.getParameter("timestamp");

        if(timestamp == null || timestamp.equals("")){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = new Date();
            timestamp = dateFormat.format(currentDate);
        }

        //TODO getuserid
//        int fromId = 1;
//        String fromUsername = "nick";
        DBManager dbManager = (DBManager) getServletContext().getAttribute("db-manager");
        int fromId = (int) request.getSession().getAttribute("user_id");
        User currUser = null;
        try {
            currUser = (User) dbManager.getUserData(fromId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String fromUsername = currUser.getUsername();



        int toId = 0;
        try {
            toId = dbManager.getIdByUsername(toUsername);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = convertToServerTimeZone(timestamp);

        try {
            dbManager.saveMessageToDatabase(fromId, toId, message, sqlTimestamp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("inboxchatpage.jsp?username2=" + toUsername);
    }

    private Timestamp convertToServerTimeZone(String timestamp) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date parsedDate = dateFormat.parse(timestamp);

            return new Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            throw new RuntimeException("Error converting timestamp", e);
        }
    }

}
