package javaFiles;

import javafx.util.Pair;
import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class DBManagerTESTER extends TestCase {

    private DBManager dbManager;
    private BasicDataSource dataSource;
    {
        try {
            dbManager = new DBManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void init(){
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/tourists");
        dataSource.setUsername("root");
        dataSource.setPassword("rootroot");
    }

    private void addQuizzes() throws SQLException {
        init();
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO login_table (user_id, username, password) VALUES (1, 'nick', 'rume123')");
        statement.executeUpdate("INSERT INTO user_table (user_id, username, is_admin) VALUES (1, 'nick', FALSE)");
        statement.executeUpdate("INSERT INTO quiz_table (quiz_id, quiz_name, quiz_tag, difficulty, creator_id, date_created, multiple_pages, practice_mode, gradable) VALUES (1, 'Basic Math Quiz', 'math;3grade', 'Easy', 1, NOW(), FALSE, TRUE, TRUE)");
        statement.executeUpdate("INSERT INTO quiz_table (quiz_id, quiz_name, quiz_tag, difficulty, creator_id, date_created, multiple_pages, practice_mode, gradable) VALUES (2, 'english Quiz', 'english;3grade', 'Medium', 1, NOW(), FALSE, TRUE, TRUE)");
        statement.close();
        connection.close();
    }

    private void removeQuizzes() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM quiz_table WHERE 1=1");
        statement.executeUpdate("DELETE FROM user_table WHERE 1=1");
        statement.executeUpdate("DELETE FROM login_table WHERE 1=1");
        statement.close();
        connection.close();
    }

    public void testGetQuizes() throws SQLException {
        addQuizzes();
        List<Quiz> quizzes = dbManager.getQuizzes();
        Quiz quiz = quizzes.get(0);
        assertEquals("Basic Math Quiz", quiz.getQuiz_name());
        assertEquals("math", quiz.getQuiz_tag().get(0));
        assertEquals("Easy", quiz.getDifficulty());
        assertEquals(1, quiz.getCreator_id());

        quiz = quizzes.get(1);
        assertEquals("english Quiz", quiz.getQuiz_name());
        assertEquals("english", quiz.getQuiz_tag().get(0));
        assertEquals("Medium", quiz.getDifficulty());
        assertEquals(1, quiz.getCreator_id());

        removeQuizzes();
    }

    private void addReviews() throws SQLException{
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO review_table (user_id, quiz_id, score, date, rating, review_text) VALUES (1, 1, 70, '2024-02-03', 4, 'good quiz, really enjoyed it!')");
        statement.executeUpdate("INSERT INTO review_table (user_id, quiz_id, score, date, rating, review_text) VALUES (1, 2, 80, '2024-02-03', 5, 'wooow')");
        statement.close();
        connection.close();
    }

    private void removeReviews() throws SQLException{
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM review_table WHERE 1=1");
        statement.close();
        connection.close();
    }

    public void testGetStatMap() throws SQLException {
        addQuizzes();
        addReviews();
        Map<Integer, Pair<Integer, Integer>> statsMap = dbManager.getStatMap();
        int count = statsMap.get(1).getKey();
        int score = statsMap.get(1).getValue();
        assertEquals(1, count);
        assertEquals(70, score);

        count = statsMap.get(2).getKey();
        score = statsMap.get(2).getValue();
        assertEquals(1, count);
        assertEquals(80, score);

        removeReviews();
        removeQuizzes();
    }

    public void testGetFilteredQuizzes() throws SQLException {
        addQuizzes();
        List<Quiz> filteredQuizzes = dbManager.getFilteredQuizzes("Easy", "math", "None", "");
        assertEquals(1, filteredQuizzes.size());
        Quiz quiz = filteredQuizzes.get(0);
        assertEquals("Basic Math Quiz", quiz.getQuiz_name());
        assertEquals("math", quiz.getQuiz_tag().get(0));
        assertEquals("Easy", quiz.getDifficulty());
        assertEquals(1, quiz.getCreator_id());
        removeQuizzes();
    }

    private void addPeople() throws SQLException {
        init();
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO login_table (user_id, username, password) VALUES (1, 'nick', 'rume123')");
        statement.executeUpdate("INSERT INTO login_table (user_id, username, password) VALUES (2, 'john', 'rume123')");
        statement.executeUpdate("INSERT INTO login_table (user_id, username, password) VALUES (3, 'bonk', 'rume123')");

        statement.executeUpdate("INSERT INTO user_table (user_id, username, is_admin) VALUES (1, 'nick', FALSE)");
        statement.executeUpdate("INSERT INTO user_table (user_id, username, is_admin) VALUES (2, 'john', FALSE)");
        statement.executeUpdate("INSERT INTO user_table (user_id, username, is_admin) VALUES (3, 'bonk', FALSE)");

        statement.executeUpdate("INSERT INTO friend_table (user_id_1, user_id_2) VALUES (1, 2)");
        statement.executeUpdate("INSERT INTO friend_table (user_id_1, user_id_2) VALUES (3, 1)");

        statement.close();
        connection.close();
    }

    private void removePeople() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM friend_table WHERE 1=1");
        statement.executeUpdate("DELETE FROM user_table WHERE 1=1");
        statement.executeUpdate("DELETE FROM login_table WHERE 1=1");
        statement.close();
        connection.close();
    }

    public void testGetFriends() throws SQLException {
        addPeople();
        List<User> friends = dbManager.getFriends(1);
        assertEquals(2, friends.size());
        assertEquals(2, friends.get(0).getUser_id());
        assertEquals(3, friends.get(1).getUser_id());
        removePeople();
    }

    public void testGetIdByUsername() throws SQLException {
        addPeople();
        int id1 = dbManager.getIdByUsername("nick");
        assertEquals(1, id1);

        int id3 = dbManager.getIdByUsername("bonk");
        assertEquals(3, id3);
        removePeople();
    }

    public void testGetUsernameById() throws SQLException {
        addPeople();
        String username = dbManager.getUsernameById(1);
        assertEquals("nick", username);

        username = dbManager.getUsernameById(2);
        assertEquals("john", username);

        removePeople();
    }

    private void addMessages() throws SQLException{
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO mail_table (from_id, to_id, type, message, date) VALUES (1, 2, 'textmessage', 'gamarjoba', NOW())");
        statement.executeUpdate("INSERT INTO mail_table (from_id, to_id, type, message, date) VALUES (2, 1, 'textmessage', 'gagimarjos', NOW())");

        statement.close();
        connection.close();
    }

    private int removeMessages() throws SQLException{
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        int num = statement.executeUpdate("DELETE FROM mail_table WHERE 1=1");
        statement.close();
        connection.close();
        return num;
    }

    public void testGetMessages() throws SQLException{
        addPeople();
        addMessages();
        List<Message> messages = dbManager.getMessages(1,"nick", "john");
        assertEquals(2, messages.size());

        Message message = messages.get(0);
        assertEquals(1, message.getFromId());
        assertEquals(2, message.getToId());
        assertEquals("textmessage", message.getType());
        assertEquals("gamarjoba", message.getContext());

        message = messages.get(1);
        assertEquals(2, message.getFromId());
        assertEquals(1, message.getToId());
        assertEquals("textmessage", message.getType());
        assertEquals("gagimarjos", message.getContext());

        removeMessages();
        removePeople();
    }

    public void testSaveMessageToDatabase() throws SQLException{
        addPeople();
        dbManager.saveMessageToDatabase(1, 2, "gamarjoba", new Timestamp(System.currentTimeMillis()));
        int num = removeMessages();
        assertEquals(1, num);
        removePeople();
    }

    private void addUsers() throws SQLException {
        init();
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO login_table (user_id, username, password) VALUES (1, 'nick', 'rume123')");
        statement.executeUpdate("INSERT INTO login_table (user_id, username, password) VALUES (2, 'john', 'rume123')");
        statement.executeUpdate("INSERT INTO login_table (user_id, username, password) VALUES (3, 'bonk', 'rume123')");

        statement.executeUpdate("INSERT INTO user_table (user_id, username, is_admin) VALUES (1, 'nick', FALSE)");
        statement.executeUpdate("INSERT INTO user_table (user_id, username, is_admin) VALUES (2, 'john', FALSE)");
        statement.executeUpdate("INSERT INTO user_table (user_id, username, is_admin) VALUES (3, 'bonk', FALSE)");

        statement.close();
        connection.close();
    }

    private void removeUsers() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM user_table WHERE 1=1");
        statement.executeUpdate("DELETE FROM login_table WHERE 1=1");
        statement.close();
        connection.close();
    }

    private void addRequests() throws SQLException{
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO mail_table (from_id, to_id, type, message, date) VALUES (2, 1, 'friendrequest', 'temp', NOW())");
        statement.executeUpdate("INSERT INTO mail_table (from_id, to_id, type, message, date) VALUES (3, 1, 'friendrequest', 'temp', NOW())");
        statement.close();
        connection.close();
    }

    public void testGetFriendRequests() throws SQLException{
        addUsers();
        addRequests();
        List<Integer> requests = dbManager.getFriendRequests(1);
        assertEquals(2, requests.size());
        int id = requests.get(0);
        assertEquals(2, id);

        id = requests.get(1);
        assertEquals(3, id);

        removeMessages();
        removeUsers();
    }

    public void testRemoveFriendRequest() throws SQLException{
        addUsers();
        addRequests();
        dbManager.removeFriendRequest(2, 1);
        List<Integer> requests = dbManager.getFriendRequests(1);
        assertEquals(1, requests.size());

        int id = requests.get(0);
        assertEquals(3, id);

        removeMessages();
        removeUsers();
    }

    public void testAddFriendCouple() throws SQLException{
        addUsers();
        dbManager.addFriendCouple(1, 2);

        List<User> friends = dbManager.getFriends(1);
        assertEquals(1, friends.size());
        assertEquals(2, friends.get(0).getUser_id());

        removePeople();
    }

    private void addChallenges() throws SQLException{
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO mail_table (from_id, to_id, type, message, date) VALUES (2, 1, 'challenge', '1', NOW())");
        statement.executeUpdate("INSERT INTO mail_table (from_id, to_id, type, message, date) VALUES (3, 1, 'challenge', '1', NOW())");

        statement.close();
        connection.close();
    }

    public void testGetChallenges() throws SQLException{
        addPeople();
        addChallenges();
        List<Message> challenges = dbManager.getChallenges(1);
        assertEquals(2, challenges.size());

        Message challenge = challenges.get(0);
        assertEquals(2, challenge.getFromId());
        assertEquals(1, challenge.getToId());
        assertEquals("challenge", challenge.getType());
        assertEquals("1", challenge.getContext());

        challenge = challenges.get(1);
        assertEquals(3, challenge.getFromId());
        assertEquals(1, challenge.getToId());
        assertEquals("challenge", challenge.getType());
        assertEquals("1", challenge.getContext());

        removeMessages();
        removePeople();
    }

    public void testGetQuizName() throws SQLException{
        addQuizzes();
        String name = dbManager.getQuizName(1);
        assertEquals("Basic Math Quiz", name);

        name = dbManager.getQuizName(2);
        assertEquals("english Quiz", name);

        removeQuizzes();
    }

    public void testRemoveChallengeRequest() throws SQLException{
        addPeople();
        addChallenges();
        dbManager.removeChallengeRequest(2, 1, 1);
        List<Message> challenges = dbManager.getChallenges(1);
        assertEquals(1, challenges.size());

        Message challenge = challenges.get(0);
        assertEquals(3, challenge.getFromId());
        assertEquals(1, challenge.getToId());
        assertEquals("challenge", challenge.getType());
        assertEquals("1", challenge.getContext());

        removeMessages();
        removePeople();
    }





}
