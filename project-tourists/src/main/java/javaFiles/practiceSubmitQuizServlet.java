package javaFiles;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet("/practiceSubmitQuizServlet")
public class practiceSubmitQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long endTime = System.currentTimeMillis();
        String startTimeStr = request.getParameter("startTime");
        long startTime = Long.parseLong(startTimeStr);

        HttpSession session = request.getSession();
        Integer tmp = (Integer) session.getAttribute("quizId");
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

        Map<String, Integer> practice = (Map<String, Integer>) session.getAttribute("practice");

        for (Question question : questions) {
            String userAnswer = request.getParameter("question_" + question.getQuestionText());
            userAnswers.put(question.getQuestionText(), userAnswer);


            if (question instanceof MultipleChoice) {
                int tmp4 = practice.get(question.getQuestionText());
                if (!(tmp4 >= 3)) {

                    if (Objects.equals(userAnswer, question.getAnswer())) {
                        correctAnswers++;
                        String tmp3 = question.getQuestionText();
                        int a = practice.get(tmp3);
                        a++;
                        practice.put(tmp3, a);
                    } else {
                        String tmp3 = question.getQuestionText();
                        practice.put(tmp3, 0);
                    }
                }

            }
            if (question instanceof QuestionResponse) {
                int tmp4 = practice.get(question.getQuestionText());
                if (!(tmp4 >= 3)) {

                    if (Objects.equals(userAnswer, question.getAnswer())) {
                        correctAnswers++;
                        String tmp3 = question.getQuestionText();
                        int a = practice.get(tmp3);
                        a++;
                        practice.put(tmp3, a);
                    } else {
                        String tmp3 = question.getQuestionText();
                        practice.put(tmp3, 0);
                    }
                }

            }
            if (question instanceof FillInTheBlank) {
                int tmp4 = practice.get(question.getQuestionText());
                if (!(tmp4 >= 3)) {
                    String tmp1 = getUserAnswer(userAnswer.toLowerCase());
                    if (Objects.equals(tmp1, question.getAnswer().toLowerCase())) {
                        correctAnswers++;
                        String tmp3 = question.getQuestionText();
                        int a = practice.get(tmp3);
                        a++;
                        practice.put(tmp3, a);
                    } else {
                        String tmp3 = question.getQuestionText();
                        practice.put(tmp3, 0);
                    }
                }
            }
            if (question instanceof PictureResponse) {
                int tmp4 = practice.get(question.getQuestionText());
                if (!(tmp4 >= 3)) {

                    if (Objects.equals(userAnswer, question.getAnswer())) {
                        correctAnswers++;
                        String tmp3 = question.getQuestionText();
                        int a = practice.get(tmp3);
                        a++;
                        practice.put(tmp3, a);
                    } else {
                        String tmp3 = question.getQuestionText();
                        practice.put(tmp3, 0);
                    }
                }
            }
        }

        try {
            dbManager.updatePracticedField((int) session.getAttribute("user_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        request.setAttribute("correctAnswers", correctAnswers);
        request.setAttribute("totalQuestions", questions.size());
        request.setAttribute("timeTaken", endTime - startTime);
        //response.sendRedirect("singlePage.jsp?quiz_id=" + 2);
        request.getRequestDispatcher("quizResult.jsp").forward(request, response);
    }

    public String getUserAnswer(String ans){
        String answers = ans;
        answers  = answers.replaceAll("\\s*,\\s*", ",");
        answers = answers.replace(',',';');
        System.out.println(answers);
        return answers;
    }
}

