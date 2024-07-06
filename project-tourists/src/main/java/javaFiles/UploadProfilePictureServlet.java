package javaFiles;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.nio.file.Paths;

public class UploadProfilePictureServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageURL = (String)request.getParameter("imageURL");
        DBManager manager = (DBManager) getServletContext().getAttribute("db-manager");
        try {
            manager.updateProfilePicture((Integer) request.getSession().getAttribute("user_id"),imageURL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        RequestDispatcher dispatcher=request.getRequestDispatcher("UserPage.jsp");
        dispatcher.forward(request,response);
    }
}