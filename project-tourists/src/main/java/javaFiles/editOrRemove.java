package javaFiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/removeOrEdit")
public class editOrRemove extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBManager dbManager = (DBManager) getServletContext().getAttribute("db-manager");
        String value = (String)request.getParameter("action");
        if(value.equals("Edit Question")){
            Question question = (Question)request.getSession().getAttribute("question");
            if(question instanceof QuestionResponse){
                request.getRequestDispatcher("/edit").forward(request,response);
            }else if(question instanceof FillInTheBlank){
                request.getRequestDispatcher("/edit").forward(request,response);
            }else if(question instanceof MultipleChoice){
                request.getRequestDispatcher("/edit").forward(request,response);
            }else{
                request.getRequestDispatcher("/edit").forward(request,response);
            }
        }else{
            try {
                dbManager.removeQuestion((String)request.getSession().getAttribute("question_text"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.getRequestDispatcher("/editQuizQuestion.jsp").forward(request,response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String questionText = request.getParameter("questionText");
        request.getSession().setAttribute("questionText", questionText);
//        response.sendRedirect("removeOrEdit.jsp");
        request.getRequestDispatcher("/removeOrEdit.jsp").forward(request,response);
    }
}
