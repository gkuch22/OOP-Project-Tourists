package javaFiles;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet("/submitQuizServlet")
public class submitQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long endTime = System.currentTimeMillis();
        String startTimeStr = request.getParameter("startTime");
        long startTime = Long.parseLong(startTimeStr);

        HttpSession session = request.getSession();
        Integer tmp = (Integer) session.getAttribute("quizId");
//        Integer tmp = (Integer)(request.getAttribute("quiz_id"));
        System.out.println(tmp);
        int quizId = tmp.intValue();
        // String quizIdStr = request.getParameter("quiz_id");
//        if (quizIdStr == null) {
//            System.out.println("id fuck");
//        }
        //int quizId = Integer.parseInt(quizIdStr);
        DBManager dbManager = null;
        try {
            dbManager = new DBManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Question> questions = null;
        try {
            questions = dbManager.getQuestions(quizId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        int correctAnswers = 0, totalQuestions = 0;
        Map<String, String> userAnswers = new HashMap<String, String>();

        for (Question question : questions) {
            String userAnswer = request.getParameter("question_" + question.getQuestionText());
            userAnswers.put(question.getQuestionText(), userAnswer);


            if (question instanceof MultipleChoice) {
                if (Objects.equals(userAnswer, question.getAnswer())) {
                    correctAnswers++;
                }
            }
            if (question instanceof QuestionResponse) {
                if (Objects.equals(userAnswer, question.getAnswer())) {
                    correctAnswers++;
                }
            }
            if (question instanceof FillInTheBlank) {

                if (Objects.equals(userAnswer, question.getAnswer())) {
                    correctAnswers++;
                }
            }
            if (question instanceof PictureResponse) {

                if (Objects.equals(userAnswer, question.getAnswer())) {
                    correctAnswers++;
                }
            }
        }
        System.out.println(correctAnswers + " correctAnswer");

        System.out.println(startTime + " startTime");
        System.out.println(endTime + " endTime");
        session.setAttribute("score", correctAnswers);
        session.setAttribute("timeTaken", endTime - startTime);
        session.setAttribute("totalQuestions", questions.size());
        request.setAttribute("correctAnswers", correctAnswers);
        request.setAttribute("totalQuestions", questions.size());
        request.setAttribute("timeTaken", endTime - startTime);
        //response.sendRedirect("singlePage.jsp?quiz_id=" + 2);
        request.getRequestDispatcher("review.jsp").forward(request, response);
    }
}

