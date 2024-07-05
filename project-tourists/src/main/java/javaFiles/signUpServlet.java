package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "signup", urlPatterns = {"/signup"})

public class signUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getParameter("username");
        String password = (String) req.getParameter("password");

        DBManager dbManager = (DBManager) getServletContext().getAttribute("db-manager");

        try {
            if(dbManager.get_user_id(username) == -1){
                int user_id = dbManager.add_user(username,password);
                User user = dbManager.getUserData(user_id);
                req.getSession().setAttribute("user",user);
                req.getRequestDispatcher("/UserPage.jsp").forward(req,resp);
            }else{
                req.getRequestDispatcher("/signuperror.jsp").forward(req,resp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}