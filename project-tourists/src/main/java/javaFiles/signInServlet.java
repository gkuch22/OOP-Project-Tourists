package javaFiles;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "signin", urlPatterns = {"/signin"})
public class signInServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getParameter("username");
        String password = (String) req.getParameter("password");

        DBManager dbManager = (DBManager) getServletContext().getAttribute("db-manager");
        try {
            int user_id = dbManager.user_password_is_correct(username,password);
//            System.out.println("sign in id - " + user_id);
            if(user_id == -1){
                req.getRequestDispatcher("/signinerror.jsp").forward(req,resp);
            }else{
                req.getSession().setAttribute("user_id",user_id);
                req.getRequestDispatcher("/homePage.jsp").forward(req,resp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
