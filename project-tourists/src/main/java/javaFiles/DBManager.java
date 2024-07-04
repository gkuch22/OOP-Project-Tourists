package javaFiles;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        statement.close();
        resultSet.close();
        connection.close();
        return quizzes;
    }

    public User getUserData(int user_id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("Select * from user_table where user_table.user_id = ?");
        statement.setInt(1,user_id);
        ResultSet resultSet = statement.executeQuery();
        List<Quiz> quizzesTaken = new ArrayList<Quiz>();
        User user = null;
        if(resultSet.next()) {
            int User_id = resultSet.getInt("user_id");
            String username = resultSet.getString("username");
            boolean is_admin = resultSet.getBoolean("is_admin");
            boolean practiced = resultSet.getBoolean("practiced");
            int created_quizzes = resultSet.getInt("created_Quizzes");
            boolean scoredHighest = resultSet.getBoolean("scored_Highest");
            String profilePhoto = resultSet.getString("profilePhoto");
            user = new UserImpl(User_id,username,is_admin,practiced,created_quizzes,scoredHighest,profilePhoto,quizzesTaken);
        }
        resultSet.close();
        statement.close();
        PreparedStatement statement1 = connection.prepareStatement("Select * from review_table where user_id = ?;");
        statement1.setInt(1,user_id);
        ResultSet resultSet1 = statement1.executeQuery();
        while(resultSet1.next()) {
            PreparedStatement statement2 = connection.prepareStatement("SELECT * from quiz_table where quiz_id=?");
            statement2.setInt(1,resultSet1.getInt("quiz_id"));
            ResultSet resultSet2 = statement2.executeQuery();
            resultSet2.next();
            int quiz_id = resultSet2.getInt("quiz_id");
            String quiz_name = resultSet2.getString("quiz_name");
            String quiz_tag = resultSet2.getString("quiz_tag");
            String difficulty = resultSet2.getString("difficulty");
            int creator_id = resultSet2.getInt("creator_id");
            boolean multiple_pages = resultSet2.getBoolean("multiple_pages");
            boolean practice_mode = resultSet2.getBoolean("practice_mode");
            boolean gradable = resultSet2.getBoolean("gradable");
            Quiz newQuiz = new QuizImpl(quiz_id, quiz_name, quiz_tag, difficulty, creator_id, multiple_pages, practice_mode, gradable);
            quizzesTaken.add(newQuiz);
            resultSet2.close();
            statement2.close();
        }

        resultSet1.close();
        statement1.close();
        connection.close();
        user.setTakenQuizzes(quizzesTaken);
        System.out.println(quizzesTaken.size());
        return user;
    }

    public List<String> getAchievements(User user) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("Select * from achievement_table where num_created < ? OR num_taken < ? OR (had_highest_score = true AND ? = true) OR (practiced = true AND ? = true);");
        statement.setInt(1,user.getNumberOfCreatedQuizzes());
        statement.setInt(2,user.getTakenQuizzes().size());
        statement.setBoolean(3,user.scoredHighest());
        statement.setBoolean(4,user.hasPracticed());
        ResultSet resultSet = statement.executeQuery();
        List<String> result = new ArrayList<String>();
        while(resultSet.next()){
            result.add(resultSet.getString("achievement"));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return result;
    }

    public List<QuizPerformance> getUserQuizzes(int User_id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("Select * from review_table where user_id = ? ORDER BY review_table.date DESC;");
        statement.setInt(1,User_id);
        ResultSet resultSet = statement.executeQuery();

        List<QuizPerformance> quizzes = new ArrayList<QuizPerformance>();
        while(resultSet.next()) {
            int user_id = resultSet.getInt("user_id");
            int quiz_id = resultSet.getInt("quiz_id");
            String quiz_name = resultSet.getString("quiz_name");
            String review = resultSet.getString("review_text");
            int score = resultSet.getInt("score");
            Date date = resultSet.getDate("date");
            int rating = resultSet.getInt("rating");
            QuizPerformance newQuiz = new QuizPerformanceimpl(user_id,quiz_id,quiz_name,score,date,rating,review);
            quizzes.add(newQuiz);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return quizzes;
    }

    public List<User> getFriends(int User_id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("Select * from friend_table where user_id_1 = ?");
        statement.setInt(1,User_id);
        ResultSet resultSet = statement.executeQuery();

        List<User> quizzes = new ArrayList<User>();
        while(resultSet.next()) {
            int user_id_2 = resultSet.getInt("user_id_2");
            quizzes.add(getUserData(user_id_2));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return quizzes;
    }

}
