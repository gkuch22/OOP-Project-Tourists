package javaFiles;

import javafx.util.Pair;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBManager {

    private BasicDataSource dataSource;

    public DBManager() throws SQLException {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/tourists");
        dataSource.setUsername("root");
        dataSource.setPassword("rootroot");
    }

    public List<Quiz> getQuizzes() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM quiz_table");

        List<Quiz> quizzes = new ArrayList<Quiz>();
        while(resultSet.next()) {
            int quiz_id = resultSet.getInt("quiz_id");
            String quiz_name = resultSet.getString("quiz_name");
            String quiz_tag = resultSet.getString("quiz_tag");
            String difficulty = resultSet.getString("difficulty");
            int creator_id = resultSet.getInt("creator_id");
            boolean multiple_pages = resultSet.getBoolean("multiple_pages");
            boolean practice_mode = resultSet.getBoolean("practice_mode");
            boolean gradable = resultSet.getBoolean("gradable");
            Quiz newQuiz = new QuizImpl(quiz_id, quiz_name, quiz_tag, difficulty, creator_id, multiple_pages, practice_mode, gradable);
            quizzes.add(newQuiz);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return quizzes;
    }

    public Map<Integer, Pair<Integer, Integer>> getStatMap() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM review_table");
//        System.out.println("movida3");
        Map<Integer, Pair<Integer, Integer>> mp = new HashMap<Integer, Pair<Integer, Integer>>();
        while(resultSet.next()) {
            int quiz_id = resultSet.getInt("quiz_id");
            int score = resultSet.getInt("score");

            if(mp.containsKey(quiz_id)){
                int count = mp.get(quiz_id).getKey();
                int maxscore = mp.get(quiz_id).getValue();
                if(score > maxscore) maxscore = score;
                mp.put(quiz_id, new Pair<Integer, Integer>(count + 1, maxscore));
            }else {
                mp.put(quiz_id, new Pair<Integer, Integer>(1, score));
            }
        }
//        System.out.println("movida4");
        resultSet.close();
        statement.close();
        connection.close();
        return mp;
    }


    public List<Quiz> getFilteredQuizzes(String difficulty, String tag, String orderBy) throws SQLException {
        List<Quiz> allquizzes = getQuizzes();
        List<Quiz> quizzes = new ArrayList<Quiz>();

        for(Quiz quiz : allquizzes){
            boolean should = true;
            if(!"all".equals(difficulty)){
                if(!(quiz.getDifficulty().toLowerCase().equals(difficulty.toLowerCase()))){

                    should = false;
                }
            }

            if(!"none".equals(tag.toLowerCase())){
                if(!(quiz.getQuiz_tag().contains(tag.toLowerCase()))){
                    should = false;
                }
            }

            if(should){
                quizzes.add(quiz);
            }

        }
        return quizzes;
    }





}
