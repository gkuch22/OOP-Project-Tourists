package javaFiles;

import javafx.util.Pair;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.*;
import java.util.Date;
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
            Date date_created = resultSet.getDate("date_created");
            Quiz newQuiz = new QuizImpl(quiz_id, quiz_name, quiz_tag, difficulty,
                                creator_id, multiple_pages, practice_mode, gradable, date_created);
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
//        System.out.println(orderBy);
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

            if(orderBy.equals("recent")){
                Map<Date, ArrayList<Quiz>> myMap = new HashMap<Date, ArrayList<Quiz>>();

                for(Quiz quiz : quizzes) {
                    Date date_created = quiz.getDate();
                    if(myMap.containsKey(date_created)){
                        myMap.get(date_created).add(quiz);
                    }else{
                        ArrayList<Quiz> temp = new ArrayList<Quiz>();
                        temp.add(quiz);
                        myMap.put(date_created, temp);
                    }
                }
                quizzes.clear();

                List<Date> sortedDates = new ArrayList<Date>(myMap.keySet());
                Collections.sort(sortedDates, Collections.reverseOrder());

                for (Date currDate : sortedDates) {
                    ArrayList<Quiz> lst = myMap.get(currDate);
                    for (Quiz quiz : lst) {
                        quizzes.add(quiz);
                    }
                }
            }


        }
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
            Date date = resultSet2.getDate("date_created");
            Quiz newQuiz = new QuizImpl(quiz_id, quiz_name, quiz_tag, difficulty, creator_id, multiple_pages, practice_mode, gradable,date);
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

        List<User> friends = new ArrayList<User>();
        while(resultSet.next()) {
            int user_id_2 = resultSet.getInt("user_id_2");
            friends.add(getUserData(user_id_2));
        }
        resultSet.close();
        statement.close();

        PreparedStatement statement1 = connection.prepareStatement("Select * from friend_table where user_id_2 = ?");
        statement1.setInt(1,User_id);
        ResultSet resultSet1 = statement1.executeQuery();

        while(resultSet1.next()) {
            int user_id_1 = resultSet1.getInt("user_id_1");
            friends.add(getUserData(user_id_1));
        }
        resultSet1.close();
        statement1.close();

        connection.close();
        return friends;
    }



    public int getIdByUsername(String username) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        int userId = -1;

        connection = dataSource.getConnection();
        String query = "SELECT user_id FROM user_table WHERE username = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, username);

        resultSet = statement.executeQuery();
        if (resultSet.next()) {
            userId = resultSet.getInt("user_id");
        }

        resultSet.close();
        statement.close();
        connection.close();
        return userId;
    }

    public String getUsernameById(int id) throws SQLException {
        String username = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        connection = dataSource.getConnection();

        String query = "SELECT username FROM user_table WHERE user_id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        resultSet = statement.executeQuery();
        if (resultSet.next()) {
            username = resultSet.getString("username");
        }

        resultSet.close();
        statement.close();
        connection.close();

        return username;
    }

    public List<Message> getMessages(int id1, String username1, String username2) throws SQLException {
        int id2 = getIdByUsername(username2);

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Message> messages = new ArrayList<Message>();

        connection = dataSource.getConnection();
        String query = "SELECT * FROM mail_table WHERE type = 'textmessage' AND ((from_id = ? AND to_id = ?) OR (from_id = ? AND to_id = ?)) ORDER BY date ASC";

        statement = connection.prepareStatement(query);
        statement.setInt(1, id1);
        statement.setInt(2, id2);
        statement.setInt(3, id2);
        statement.setInt(4, id1);

        resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int fromId = resultSet.getInt("from_id");
            int toId = resultSet.getInt("to_id");
            String messageType = resultSet.getString("type");
            String messageText = resultSet.getString("message");
            Date date = resultSet.getTimestamp("date");

            Message message = new MessageImpl(messageType, fromId, toId, messageText, date);
            messages.add(message);
        }
        resultSet.close();
        statement.close();
        connection.close();

        return messages;
    }

    public void saveMessageToDatabase(int fromId, int toId, String message, java.sql.Timestamp timestamp, DBManager dbManager) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        connection = dataSource.getConnection();
        String query = "INSERT INTO mail_table (from_id, to_id, type, message, date) VALUES (?, ?, ?, ?, ?)";
        statement = connection.prepareStatement(query);

        statement.setInt(1, fromId);
        statement.setInt(2, toId);
        statement.setString(3, "textmessage");
        statement.setString(4, message);
        statement.setTimestamp(5, timestamp);

        statement.executeUpdate();

        statement.close();
        connection.close();
    }



    public List<Integer> getFriendRequests(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        connection = dataSource.getConnection();
        String query = "SELECT * FROM mail_table WHERE type = 'friendrequest' AND (to_id = ?) ORDER BY date ASC";

        statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        resultSet = statement.executeQuery();

        List<Integer> friendRequests = new ArrayList<Integer>();
        while (resultSet.next()) {
            int fromId = resultSet.getInt("from_id");
            friendRequests.add(fromId);
        }
        resultSet.close();
        statement.close();
        connection.close();

        return friendRequests;
    }


    public void removeFriendRequest(int fromId, int toId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        connection = dataSource.getConnection();
        String query = "DELETE FROM mail_table WHERE type = 'friendrequest' AND from_id = ? AND to_id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, fromId);
        statement.setInt(2, toId);
        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    public void addFriendCouple(int fromId, int toId) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO friend_table (user_id_1, user_id_2) VALUES (?, ?)");

        statement.setInt(1, fromId);
        statement.setInt(2, toId);

        statement.executeUpdate();

        statement.close();
        connection.close();
    }



    public List<Message> getChallenges(int id) throws SQLException {
        List<Message> challenges = new ArrayList<Message>();

        Connection connection = dataSource.getConnection();
        String query = "SELECT * FROM mail_table WHERE type = 'challenge' AND to_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int fromId = resultSet.getInt("from_id");
            int toId = resultSet.getInt("to_id");
            String messageType = resultSet.getString("type");
            String messageText = resultSet.getString("message");
            Date date = resultSet.getTimestamp("date");

            Message message = new MessageImpl(messageType, fromId, toId, messageText, date);
            challenges.add(message);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return challenges;
    }

    public String getQuizName(int quizId) throws SQLException {
        String quizName = null;

        String query = "SELECT * FROM quiz_table WHERE quiz_id = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, quizId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            quizName = resultSet.getString("quiz_name");
        }

        resultSet.close();
        statement.close();
        connection.close();

        return quizName;
    }

    public void removeChallengeRequest(int fromId, int toId, int quizId) throws SQLException {
        String query = "DELETE FROM mail_table WHERE type = 'challenge' AND from_id = ? AND to_id = ? AND message = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, fromId);
        statement.setInt(2, toId);
        statement.setString(3, String.valueOf(quizId));
        statement.executeUpdate();

        statement.close();
        connection.close();
    }


}
