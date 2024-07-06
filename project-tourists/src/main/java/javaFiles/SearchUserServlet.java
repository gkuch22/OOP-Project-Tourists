package javaFiles;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import javaFiles.DBManager;
import javaFiles.User;

@WebServlet("/searchUser")
public class SearchUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("partialUsername");
        if (query != null && !query.isEmpty()) {
            DBManager manager = (DBManager) getServletContext().getAttribute("db-manager");
            try {
                List<String> users = manager.searchUsersByPartialUsername(query);
                String json = new Gson().toJson(users);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } catch (SQLException e) {
                throw new ServletException("Database access error", e);
            }
        }
    }
}
