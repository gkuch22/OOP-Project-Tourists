package javaFiles;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "createQuiz", urlPatterns = {"/createQuiz"})
public class CreateQuizServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = (String)request.getParameter("quizname");
        String description = (String)request.getParameter("description");
        String tags = (String)request.getParameter("tags");
        String difficulty = (String)request.getParameter("difficulty");
        String[] boxes = (String[])request.getParameterValues("checkbox");
        System.out.println("name - " + name);
        System.out.println("description - " + description);
        System.out.println("tags - " + tags);
        System.out.println("diff - " + difficulty);
        String tagsFixed = "";
        String[] tagsArr = tags.split(",");
        for(int i = 0; i < tagsArr.length; i++){
            tagsFixed += tagsArr[i];
            if(i != tagsArr.length - 1){
                tagsFixed += ';';
            }
            System.out.println(tagsArr[i]);
        }

        // we should get from dataBase
        int id = 0;
        //we should get from user attribute
        int creatorId = 0;
        boolean isRandom = Arrays.asList(boxes).contains("random");
        boolean isTimed = Arrays.asList(boxes).contains("timed");
        boolean multiplePages = Arrays.asList(boxes).contains("multiplePages");
        boolean immediatelyCorrected = Arrays.asList(boxes).contains("immediateCorrection");
        boolean practiceMode = Arrays.asList(boxes).contains("practiceMode");
        //should add this input in createQuizzes;
        boolean gradable = false;
        Quiz quiz = new QuizImpl(id, name, tagsFixed,difficulty, creatorId,
                isRandom, isTimed, multiplePages, immediatelyCorrected,
                practiceMode, gradable);
        HttpSession session = request.getSession();
        session.setAttribute("quiz", quiz);
        //response.sendRedirect(request.getContextPath() + "/chooseQuestion");

        request.getRequestDispatcher("/chooseQuestionOrSubmit.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("get");
        getServletContext().getRequestDispatcher("/createQuizes.jsp").forward(request,response);
    }
}
