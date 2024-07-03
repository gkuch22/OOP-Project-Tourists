package javaFiles;

import javafx.util.Pair;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.*;

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


    public List<Quiz> getFilteredQuizzes(String difficulty, String tag, String orderBy, String searchName) throws SQLException {
        List<Quiz> allquizzes = getQuizzes();
        List<Quiz> quizzes = new ArrayList<Quiz>();

//        System.out.println(difficulty);
//        System.out.println(tag);
//        System.out.println(orderBy);
//        System.out.println(searchName);

        for(Quiz quiz : allquizzes){
            boolean should = true;

            if(!"".equals(searchName)){
                if(!quiz.getQuiz_name().toLowerCase().contains(searchName.toLowerCase())){
                    should = false;
                }
            }

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

        orderBy = orderBy.toLowerCase();
        System.out.println(orderBy);
        if(!"none".equals(orderBy)){
            if(orderBy.equals("difficulty")){
                List<Quiz> lst = new ArrayList<Quiz>(quizzes);
                quizzes.clear();
                List<Quiz> easy = new ArrayList<Quiz>();
                List<Quiz> medium = new ArrayList<Quiz>();
                List<Quiz> hard = new ArrayList<Quiz>();
                for(Quiz quiz : lst){
                    String currDifficulty = quiz.getDifficulty().toLowerCase();
                    if(currDifficulty.equals("easy")) easy.add(quiz);
                    if(currDifficulty.equals("medium")) medium.add(quiz);
                    if(currDifficulty.equals("hard")) hard.add(quiz);
                }
                for(Quiz quiz : easy) quizzes.add(quiz);
                for(Quiz quiz : medium) quizzes.add(quiz);
                for(Quiz quiz : hard) quizzes.add(quiz);
            }

            if(orderBy.equals("popularity")){
                Map<Integer, Pair<Integer, Integer>> mp = getStatMap();
                Map<Integer, ArrayList<Quiz>> myMap = new HashMap<Integer, ArrayList<Quiz>>();

                for(Quiz quiz : quizzes) {
                    int quiz_id = quiz.getQuiz_id();
                    int popularity = 0;
                    if (mp.containsKey(quiz_id)) {
                        popularity = mp.get(quiz_id).getKey();
                    }
                    if(myMap.containsKey(popularity)){
                        myMap.get(popularity).add(quiz);
                    }else{
                        ArrayList<Quiz> temp = new ArrayList<Quiz>();
                        temp.add(quiz);
                        myMap.put(popularity, temp);
                    }
                }

                quizzes.clear();
                for(int pop : myMap.keySet()){
                    ArrayList<Quiz> lst = myMap.get(pop);
                    for(Quiz quiz : lst){
                        quizzes.add(quiz);
                    }
                }
                Collections.reverse(quizzes);
            }

        }

        return quizzes;
    }





}
