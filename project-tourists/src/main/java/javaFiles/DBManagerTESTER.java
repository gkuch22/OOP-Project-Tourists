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

    public void addTestUserData() throws SQLException {
        init();
        removeTestUserData();
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO login_table VALUES (1,'bendo','1234');");
        statement.executeUpdate("INSERT INTO login_table VALUES (2,'Quaggy','1234');");
        statement.executeUpdate("INSERT INTO login_table VALUES (3,'Joe','1234');");
        statement.executeUpdate("INSERT INTO login_table VALUES (4,'Bob','1234');");

        statement.executeUpdate("INSERT INTO user_table VALUES (1,'bendo',false,false,30,true,'https://i.pinimg.com/736x/64/5f/d9/645fd98adba55582c6851985779fcb0e.jpg');");
        statement.executeUpdate("INSERT INTO user_table VALUES (2,'Quaggy',false,false,20,true,'https://www.watchmojo.com/uploads/thumbs720/Fi-T-Top10-Family-Guy-Characters_I2B8Z1-720p30-1.jpg');");
        statement.executeUpdate("INSERT INTO user_table VALUES (3,'Joe',false,false,14,true,'https://media.entertainmentearth.com/assets/images/fe9f5fc5d21e4c338652f08b5f86b0caxl.jpg');");
        statement.executeUpdate("INSERT INTO user_table VALUES (4,'Bob',false,false,14,true,'https://media.entertainmentearth.com/assets/images/fe9f5fc5d21e4c338652f08b5f86b0caxl.jpg');");

        statement.executeUpdate("INSERT INTO achievement_table(achievement_id, achievement, num_created) VALUES (1,'supreme',10);");
        statement.executeUpdate("Insert into achievement_table(achievement_id,achievement,num_created) Values (2,'wowzers',20);");

        statement.executeUpdate("INSERT INTO quiz_table(quiz_id,quiz_name,quiz_tag,difficulty,creator_id) VALUES (1,'ez','history','easy',1);");
        statement.executeUpdate("INSERT INTO quiz_table(quiz_id,quiz_name,quiz_tag,difficulty,creator_id) VALUES (2,'mid','english;history','medium',1);");
        statement.executeUpdate("INSERT INTO quiz_table(quiz_id,quiz_name,quiz_tag,difficulty,creator_id) VALUES (3,'hard','math','hard',1);");

        statement.executeUpdate("INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (1,1,'ez',10,NOW(),'');");
        statement.executeUpdate("INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (1,2,'mid',8,NOW(),'');");
        statement.executeUpdate("INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text) VALUES (1,3,'hard',5,NOW(),'');");

        statement.executeUpdate("INSERT INTO friend_table(user_id_1, user_id_2) VALUES (1,2);");
        statement.executeUpdate("INSERT INTO friend_table(user_id_1, user_id_2) VALUES (1,3);");
        statement.executeUpdate("INSERT INTO friend_table(user_id_1, user_id_2) VALUES (2,3);");

        statement.close();
        connection.close();
    }

    public void removeTestUserData() throws SQLException {
        init();
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM login_table WHERE 1=1");
        statement.executeUpdate("DELETE FROM achievement_table WHERE 1=1");
        statement.close();
        connection.close();
    }

    public void testGetUserCreatedQuizzes() throws SQLException {
        addTestUserData();

        User temp = dbManager.getUserData(1);
        List<Quiz> quizzes = dbManager.getUserCreatedQuizzes(temp);
        assertEquals("ez",quizzes.get(0).getQuiz_name());
        assertEquals("mid",quizzes.get(1).getQuiz_name());
        assertEquals("hard",quizzes.get(2).getQuiz_name());

        removeTestUserData();
    }

    public void testGetAchievments() throws SQLException {
        addTestUserData();

        User temp = dbManager.getUserData(1);
        List<String> achievements = dbManager.getAchievements(temp);
        assertEquals("supreme",achievements.get(0));
        assertEquals("wowzers",achievements.get(1));

        removeTestUserData();
    }

    public void testDeleteUser() throws SQLException {
        addTestUserData();

        dbManager.deleteUser(1);
        List<User> friends = dbManager.getFriends(dbManager.getUserData(2).getUser_id());
        assertEquals(1,friends.size());

        removeTestUserData();
    }

    public void testPartialUsernameSearch() throws SQLException {
        addTestUserData();

        List<String> users = dbManager.searchUsersByPartialUsername("q");
        assertEquals(1,users.size());

        removeTestUserData();
    }

    public void testAdminPromotion() throws SQLException {
        addTestUserData();

        dbManager.promoteUserToAdmin(1);
        assertTrue(dbManager.getUserData(1).isAdmin());

        removeTestUserData();
    }

    public void testAdminDemotion() throws SQLException {
        addTestUserData();

        dbManager.promoteUserToAdmin(1);
        dbManager.demoteUserFromAdmin(1);
        assertFalse(dbManager.getUserData(1).isAdmin());

        removeTestUserData();
    }

    public void testUserQuizzes() throws SQLException {
        addTestUserData();

        List<QuizPerformance> quizzes = dbManager.getUserQuizzes(1);
        assertEquals(10,quizzes.get(0).getScore());
        assertEquals(8,quizzes.get(1).getScore());
        assertEquals(5,quizzes.get(2).getScore());

        removeTestUserData();
    }

    public void testUniqueUserQuizzes() throws SQLException {
        addTestUserData();

        List<Integer> quizzes = dbManager.getUniqueUserQuizzes(1);
        assertEquals(3,quizzes.size());

        removeTestUserData();
    }

    public void testUnfriend() throws SQLException {
        addTestUserData();

        dbManager.unfriend(1,2);
        List<User> friends = dbManager.getFriends(1);
        assertEquals(1,friends.size());

        removeTestUserData();
    }

    public void testUpdateProfilePicture() throws SQLException {
        addTestUserData();

        dbManager.updateProfilePicture(1,"https://i.natgeofe.com/k/75ac774d-e6c7-44fa-b787-d0e20742f797/giant-panda-eating_2x3.jpg");
        User user = dbManager.getUserData(1);
        assertEquals("https://i.natgeofe.com/k/75ac774d-e6c7-44fa-b787-d0e20742f797/giant-panda-eating_2x3.jpg",user.getProfilePhoto());

        removeTestUserData();
    }

    public void testFriendRequest() throws SQLException {
        addTestUserData();

        dbManager.sendFriendRequest(1,4);
        List<Integer> reqs = dbManager.getFriendRequests(4);
        assertEquals(1,reqs.size());

        removeTestUserData();
    }

    public void testSearchIfUserExists() throws SQLException {
        addTestUserData();

        boolean val = dbManager.userExists("Joe");
        assertTrue(val);
        val = dbManager.userExists("Johnathan");
        assertFalse(val);

        removeTestUserData();
    }



}
