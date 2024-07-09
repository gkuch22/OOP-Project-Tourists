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
import java.util.Arrays;

@WebServlet(name = "editQuizOptions", urlPatterns = {"/editQuizOptions"})
public class editQuizOptionsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBManager dbManager = (DBManager) getServletContext().getAttribute("db-manager");
        String name = (String)request.getParameter("quizname");
        String description = (String)request.getParameter("description");
        String tags = (String)request.getParameter("tags");
        String difficulty = (String)request.getParameter("difficulty");
        String[] boxes = (String[])request.getParameterValues("checkbox");
        int hours = 0;
        if(!request.getParameter("hours").equals("")){
            hours = Integer.parseInt(request.getParameter("hours"));
        }
        int minutes = 0;
        if(!request.getParameter("minutes").equals("")){
            minutes = Integer.parseInt(request.getParameter("minutes"));
        }
        int seconds = 0;
        if(!request.getParameter("seconds").equals("")){
            seconds = Integer.parseInt(request.getParameter("seconds"));
        }

//        System.out.println("name - " + name);
//        System.out.println("description - " + description);
//        System.out.println("tags - " + tags);
//        System.out.println("diff - " + difficulty);
        String tagsFixed = "";
        String[] tagsArr = tags.split(",");
        for(int i = 0; i < tagsArr.length; i++){
            tagsFixed += tagsArr[i];
            if(i != tagsArr.length - 1){
                tagsFixed += ';';
            }
            System.out.println(tagsArr[i]);
        }

        //we should get from user attribute
        int creatorId = (Integer)request.getSession().getAttribute("user_id");;
        boolean isRandom = false;
        boolean isTimed = false;
        boolean multiplePages = false;
        boolean immediatelyCorrected = false;
        boolean practiceMode = false;
        if(boxes != null){
            isRandom = Arrays.asList(boxes).contains("random");
            isTimed = Arrays.asList(boxes).contains("timed");
            multiplePages = Arrays.asList(boxes).contains("multiplePages");
            immediatelyCorrected = Arrays.asList(boxes).contains("immediateCorrection");
            practiceMode = Arrays.asList(boxes).contains("practiceMode");
        }

        int durationTime = seconds + minutes*60 + hours*3600;
        //should add this input in createQuizzes;
        boolean gradable = false;

        int nextQuizId = 2;
        // nextQuizId = (Integer)request.getSession().getAttribute("quiz_id");
        Quiz quiz = new QuizImpl(nextQuizId, name, tagsFixed,difficulty, creatorId,
                isRandom, isTimed, multiplePages, immediatelyCorrected,
                practiceMode, gradable, description,durationTime);

        try {
            dbManager.editQuizOptions(quiz);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //response.sendRedirect(request.getContextPath() + "/chooseQuestion");

        request.getRequestDispatcher("/editQuiz.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("get");
        //request.getRequestDispatcher("/createQuizes.jsp").forward(request,response);
    }
}
