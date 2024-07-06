package javaFiles;

//import javafx.util.Pair;
import javafx.util.Pair;
import org.apache.commons.dbcp2.BasicDataSource;

//import javax.jms.Connection;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
//import java.sql.Date;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;


public class DBManager {

    private BasicDataSource dataSource;

    public DBManager() throws SQLException {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/tourists");
        dataSource.setUsername("root");
        dataSource.setPassword("rootroot");
    }

    public int get_user_id(String name) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("select user_id from login_table where username = ?");
        statement.setString(1,name);
        ResultSet res = statement.executeQuery();
        ArrayList<Integer> list = new ArrayList<Integer>();

        while(res.next()){
            int num = res.getInt("user_id");
            list.add(num);
        }

        res.close();
        statement.close();
        connection.close();
        if(list.size() == 0) return -1;
        if(list.size() > 1) return -1;
        return list.get(0);
    }

    public int user_password_is_correct(String name, String password) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("select user_id from login_table where (username = ?) AND (password = ?)");
        statement.setString(1,name);
        statement.setString(2,password);
        ResultSet res = statement.executeQuery();
        ArrayList<Integer> list = new ArrayList<Integer>();

        while(res.next()){
            int num = res.getInt("user_id");
            list.add(num);
        }

        res.close();
        statement.close();
        connection.close();
        if(list.size() != 1) return -1;
        return list.get(0);
    }

    public int add_user(String name, String password) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("insert into login_table (username, password) values (?, ?)");
        statement.setString(1,name);
        statement.setString(2,password);
        statement.executeUpdate();
        statement.close();
        int user_id = get_user_id(name);

        if(user_id != -1){
            statement = connection.prepareStatement("insert into user_table (user_id, username) values (?, ?)");
            statement.setInt(1,user_id);
            statement.setString(2,name);
            statement.executeUpdate();
            statement.close();
        }
        connection.close();
        return user_id;
    }

    public List<Announcement> get_Announcement_List(){
        List<Announcement> list = new ArrayList<Announcement>();
        System.out.println("here");
        for(int i=0;i<10;i++) {
            Announcement announcement = new AnnouncementImpl(i, "welcome people, it is our new site, nice to meet you, have fun. " + Integer.toString(i), 10, new Date());
            list.add(announcement);
        }
//        System.out.println("here");
        return list;
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
        resultSet.next();
        System.out.println(user_id);
        int User_id = resultSet.getInt("user_id");
        String username = resultSet.getString("username");
        boolean is_admin = resultSet.getBoolean("is_admin");
        boolean practiced = resultSet.getBoolean("practiced");
        int created_quizzes = resultSet.getInt("created_Quizzes");
        boolean scoredHighest = resultSet.getBoolean("scored_Highest");
        String profilePhoto = resultSet.getString("profilePhoto");
        user = new UserImpl(User_id,username,is_admin,practiced,created_quizzes,scoredHighest,profilePhoto,quizzesTaken);

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
        return user;
    }


    public List<Quiz> getUserCreatedQuizzes(User user) throws SQLException {
        List<Quiz> res = new ArrayList<Quiz>();

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM quiz_table where creator_id=? ORDER BY date_created DESC;");
        statement.setInt(1,user.getUser_id());
        ResultSet resultSet = statement.executeQuery();

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
            res.add(newQuiz);
        }
        resultSet.close();
        statement.close();
        connection.close();

        return res;
    }



    public List<String> getAchievements(User user) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("Select * from achievement_table where num_created < ? OR num_taken < ? OR (had_highest_score = true AND ? = true) OR (practiced = true AND ? = true);");
        statement.setInt(1,user.getNumberOfCreatedQuizzes());
        statement.setInt(2,this.getUniqueUserQuizzes(user.getUser_id()).size());
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

    public void deleteUser(int user_id) throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement statement8 = connection.prepareStatement("DELETE FROM login_table where user_id=?;");
        statement8.setInt(1,user_id);
        statement8.executeUpdate();
        statement8.close();

        connection.close();
    }

    public int getReviewCount() throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("Select * from review_table");
        ResultSet resultSet = statement.executeQuery();
        int cnt=0;
        while(resultSet.next()){
            cnt++;
        }
        return cnt;
    }

    public List<String> searchUsersByPartialUsername(String partialUsername) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("Select username from user_table where username Like ?");
        statement.setString(1,partialUsername+"%");
        ResultSet resultSet = statement.executeQuery();
        List<String> res = new ArrayList<>();
        while (resultSet.next()){
            res.add(resultSet.getString("username"));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return res;
    }

    public void promoteUserToAdmin(int userid) throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement statement = connection.prepareStatement("UPDATE user_table Set is_admin=true where user_id=?");
        statement.setInt(1,userid);

        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    public void demoteUserFromAdmin(int userid) throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement statement = connection.prepareStatement("UPDATE user_table Set is_admin=false where user_id=?");
        statement.setInt(1,userid);

        statement.executeUpdate();

        statement.close();
        connection.close();
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


    public List<Integer> getUniqueUserQuizzes(int User_id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("Select DISTINCT quiz_id,user_id from review_table where user_id = ?;");
        statement.setInt(1,User_id);
        ResultSet resultSet = statement.executeQuery();

        List<Integer> quizzes = new ArrayList<Integer>();
        while(resultSet.next()) {
            int quiz_id = resultSet.getInt("quiz_id");
            quizzes.add(quiz_id);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return quizzes;
    }

    public boolean userExists(String name) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("Select * from user_table where username = ?");
        statement.setString(1,name);
        ResultSet resultSet = statement.executeQuery();

        int cnt = 0;
        while(resultSet.next()){
            cnt++;
        }

        resultSet.close();
        statement.close();
        connection.close();
        return cnt>0;
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

    public void saveMessageToDatabase(int fromId, int toId, String message, java.sql.Timestamp timestamp) throws SQLException {
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

    public void removeQuiz(int quizId) throws SQLException{
        String query = "DELETE FROM quiz_table WHERE quiz_id = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, quizId);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    public void addAnnouncement(String title, String context, int userId) throws SQLException{
        String query = "INSERT INTO post_table (post_title, post_text, user_id, date) VALUES (?, ?, ?, NOW())";
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, title);
        statement.setString(2, context);
        statement.setInt(3, userId);

        statement.executeUpdate();
        statement.close();
        connection.close();
    }


    public void sendFriendRequest(int user1Id,int user2Id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement checker = connection.prepareStatement("Select * from mail_table where from_id=? AND to_id=? AND type='friendrequest'");
        checker.setInt(1,user1Id);
        checker.setInt(2,user2Id);
        ResultSet res = checker.executeQuery();
        if(res.next()){
            return;
        }
        res.close();
        checker.close();


        PreparedStatement statement = connection.prepareStatement("INSERT INTO mail_table (from_id, to_id, type, message, date) VALUES (?,?,'friendrequest','be my friend',Now())");
        statement.setInt(1,user1Id);
        statement.setInt(2,user2Id);
        statement.executeUpdate();
        connection.close();
    }

    public void unfriend(int user_id_1,int user_id_2) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM friend_table WHERE (user_id_1=? AND user_id_2=?) OR (user_id_1=? AND user_id_2=?);");
        statement.setInt(1,user_id_1);
        statement.setInt(2,user_id_2);
        statement.setInt(4,user_id_1);
        statement.setInt(3,user_id_2);
        statement.executeUpdate();

        connection.close();
    }
    public void updateProfilePicture(int userId, String imageURL) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE user_table SET profilePhoto = ? WHERE user_id = ?");
        statement.setString(1, imageURL);
        statement.setInt(2, userId);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    public void addQuiz(Quiz quiz) throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO quiz_table " +
                "(quiz_name, quiz_description, quiz_tag, difficulty," +
                "creator_id, date_created, multiple_pages, practice_mode, gradable, immediate_correction, random_questions," +
                "timed, duration_time)" +
                "VALUES (?, ?, ?, ?, ?, sysdate(), ?, ?, ?, ?, ?,?,?);");
                statement.setString(1, quiz.getQuiz_name());
                statement.setString(2, quiz.getDescription());
                statement.setString(3, quiz.getQuizTagsAsString());
                statement.setString(4, quiz.getDifficulty());
                statement.setInt(5, quiz.getCreator_id());
                statement.setBoolean(6, quiz.isMultiple_pages());
                statement.setBoolean(7, quiz.isPractice_mode());
                statement.setBoolean(8, quiz.isGradable());
                statement.setBoolean(9, quiz.isImmediatelyCorrected());
                statement.setBoolean(10, quiz.isRandom());
                statement.setBoolean(11, quiz.isTimed());
                statement.setInt(12, quiz.getDurationTime());
                statement.executeUpdate();
                statement.close();
                connection.close();
    }

    public void addQuestions(Quiz quiz) throws SQLException {
        Connection connection = dataSource.getConnection();
        ArrayList<Question> questions = quiz.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            Question currQuestion = questions.get(i);
            String questionText = currQuestion.getQuestionText();
            String answer = currQuestion.getAnswer();
            String possibleAnswers = "";
            if(currQuestion instanceof MultipleChoice){
                possibleAnswers = ((MultipleChoice) currQuestion).getPossibleAnswersAsString();
            }
            int quizId = quiz.getQuiz_id();
            String imageURL = "";
            if(currQuestion instanceof PictureResponse){
                imageURL = ((PictureResponse) currQuestion).getImageURL();
            }

            int questionType = 0;
            if (currQuestion instanceof QuestionResponse) {
                questionType = 1;
            } else if (currQuestion instanceof FillInTheBlank) {
                questionType = 2;
            } else if (currQuestion instanceof MultipleChoice) {
                questionType = 3;
            }else{
                questionType = 4;
            }
            PreparedStatement statement = connection.prepareStatement("INSERT INTO question_table " +
                    "(question, possible_answers, answer, quiz_id, question_type, imageURL) " +
                    "VALUES (?, ?, ?, ?, ?, ?)");

            statement.setString(1, questionText);
            statement.setString(2, possibleAnswers);
            statement.setString(3, answer);
            statement.setInt(4, quizId);
            statement.setInt(5, questionType);
            statement.setString(6, imageURL);
            statement.executeUpdate();
            statement.close();
        }
        connection.close();
    }


    public Quiz getQuiz(int quizId) throws SQLException {
        String name = "", diff = "", quizTag = "", practice = "";
        String query = "SELECT quiz_name, quiz_description, quiz_tag,difficulty, creator_id, date_created, multiple_pages, practice_mode, gradable, immediate_correction, random_questions, timed, duration_time FROM quiz_table WHERE quiz_id = ?";
        Quiz quiz = null;
        // System.out.println(query);
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        // System.out.println("Shevida");
        ps.setInt(1, quizId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            name = rs.getString("quiz_name");
            diff = rs.getString("difficulty");
            quizTag = rs.getString("quiz_tag");
            boolean tmp = rs.getBoolean("practice_mode");
            if (tmp) {
                practice = "true";
            } else {
                practice = "false";
            }
            quiz = new QuizImpl(quizId, rs.getString("quiz_name"), rs.getString("quiz_tag"),
                    rs.getString("difficulty"), rs.getInt("creator_id"), rs.getBoolean("random_questions"),
                    rs.getBoolean("timed"), rs.getBoolean("multiple_pages"), rs.getBoolean("immediate_correction"),
                    rs.getBoolean("practice_mode"), rs.getBoolean("gradable"),
                    rs.getString("quiz_description"), rs.getInt("duration_time"));

        }

        //System.out.println(quiz.getName());
        return quiz;
    }

    public boolean isMultiplePages(int quizId) throws SQLException {
        boolean multiplePages = false;
        String query = "SELECT multiple_pages FROM quiz_table WHERE quiz_id = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, quizId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            multiplePages = rs.getBoolean("multiple_pages");
        }


        return multiplePages;
    }
    public List<Question> getQuestions(int quizId) throws SQLException {
        List<Question> questions = new ArrayList<Question>();
        String query = "SELECT question_id, question, possible_answers, answer, question_type, imageURL FROM question_table WHERE quiz_id = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, quizId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int questionId = rs.getInt("question_id");
            String text = rs.getString("question");
            String possibleAnswers = rs.getString("possible_answers");
            String answer = rs.getString("answer");
            int questionType = rs.getInt("question_type");
            String imageURL = rs.getString("imageURL");

            if (questionType == 1) {
                QuestionResponse question = new QuestionResponse(text, answer);
                questions.add(question);
            }
            if (questionType == 3) {
                MultipleChoice question = new MultipleChoice(text, answer, possibleAnswers.split(";"));
                questions.add(question);
            }
            if (questionType == 4) {
                PictureResponse question = new PictureResponse(text, answer, imageURL);
                questions.add(question);
            }
            if (questionType == 2) {
                FillInTheBlank question = new FillInTheBlank(text, answer);
                questions.add(question);
            }


        }


        return questions;
    }
    public void saveReview(int user_id, int quiz_id, int score, Date date, int rating, String review, String quizName) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = dataSource.getConnection();

            String sql = "INSERT INTO review_table (user_id, quiz_id, quiz_name, score, date, rating, review_text) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user_id);
            stmt.setInt(2, quiz_id);
            stmt.setString(3, quizName);
            stmt.setInt(4, score);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            stmt.setDate(5, sqlDate);
            stmt.setInt(6, rating);
            stmt.setString(7, review);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePracticedField(int userId) throws SQLException {
        Connection connection = dataSource.getConnection();
        String query = "UPDATE user_table SET practiced = TRUE WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        }
    }

    public int getUserIdByName(String friendName) throws SQLException {
        int userId = -1;
        Connection connection = dataSource.getConnection();
        String query = "SELECT user_id FROM user_table WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, friendName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }

    public void sendMail(int fromId, int toId, String type, String message) throws SQLException {
        String query = "INSERT INTO mail_table (from_id, to_id, type, message, date) VALUES (?, ?, ?, ?, NOW())";

        Connection connection = dataSource.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, fromId);
            preparedStatement.setInt(2, toId);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, message);
            preparedStatement.executeUpdate();
        }
    }

    public boolean areFriends(int userId1, int userId2) throws SQLException {
        boolean areFriends = false;
        Connection connection = dataSource.getConnection();
        String query = "SELECT COUNT(*) FROM friend_table WHERE (user_id_1 = ? AND user_id_2 = ?) OR (user_id_1 = ? AND user_id_2 = ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId1);
            preparedStatement.setInt(2, userId2);
            preparedStatement.setInt(3, userId2);
            preparedStatement.setInt(4, userId1);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    areFriends = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return areFriends;
    }

    public int getHighestScore(int quizId) throws SQLException {
        Connection connection = dataSource.getConnection();
        String query = "SELECT MAX(score) as highest_score FROM review_table WHERE quiz_id = ?";

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, quizId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("highest_score");
                }
            }

        return -10000;
    }

    public void updateUserScoredHighest(int userId) throws SQLException {
        Connection connection = dataSource.getConnection();
        String query = "UPDATE user_table SET scored_Highest = TRUE WHERE user_id = ?";

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, userId);
        stmt.executeUpdate();

    }


    public int getNextQuizId() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COALESCE(MAX(quiz_id), 0) + 1  AS next_quiz_id FROM quiz_table;");
        int nextQuizId = 1;
        while(resultSet.next()){
            System.out.println("here");
            nextQuizId = resultSet.getInt("next_quiz_id");
        }

        resultSet.close();
        statement.close();
        connection.close();
        return nextQuizId;
    }

    public void banUser(int user_id, String date, String reason) throws SQLException, ParseException {
        Connection connection = dataSource.getConnection();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO ban_table " +
                "(user_id, expire_date, reason) " +
                "VALUES (?, ?, ?)");

//        SqlDate sqlDate = new SqlDate(utilDate.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date parsedDate = dateFormat.parse(date);
        Timestamp sqlDate = new Timestamp(parsedDate.getTime());

        // Step 2: Convert LocalDate to java.sql.Date
//        Date sqlDate = Date.valueOf(localDate);
        statement.setInt(1,user_id);
        statement.setTimestamp(2,sqlDate);
        statement.setString(3, reason);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }




}
