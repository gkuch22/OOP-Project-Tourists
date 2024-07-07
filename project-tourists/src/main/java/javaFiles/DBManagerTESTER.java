package javaFiles;

import javafx.util.Pair;
import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.Arrays;
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

    private void addQuizzesMore() throws SQLException {
        init();
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO login_table (user_id, username, password) VALUES (2, 'john', 'rume123')");
        statement.executeUpdate("INSERT INTO user_table (user_id, username, is_admin) VALUES (2, 'john', FALSE)");
        statement.executeUpdate("INSERT INTO quiz_table (quiz_id, quiz_name, quiz_tag, difficulty, creator_id, date_created, multiple_pages, practice_mode, gradable, immediate_correction, random_questions,timed, duration_time) VALUES (3, 'english Quiz', 'english;3grade', 'Medium', 2, NOW(), TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, 1)");
        statement.close();
        connection.close();
    }

    private void addQuestions() throws SQLException {
        init();
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO question_table (quiz_id, question, possible_answers, answer, question_type, imageURL) VALUES (1, 'What is 2+2?', '2;3;4;5', '4', 3, '')");
        statement.executeUpdate("INSERT INTO question_table (quiz_id, question, possible_answers, answer, question_type, imageURL) VALUES (1, 'What is 3+5?', '6;7;8;9', '8', 3, '')");
        statement.executeUpdate("INSERT INTO question_table (quiz_id, question, possible_answers, answer, question_type, imageURL) VALUES (2, '----, my name is slim shady', '', 'hello', 2, '')");
        statement.executeUpdate("INSERT INTO question_table (quiz_id, question, possible_answers, answer, question_type, imageURL) VALUES (2, 'are you gay?', '', 'yes', 1, '')");
        statement.executeUpdate("INSERT INTO question_table (quiz_id, question, possible_answers, answer, question_type, imageURL) VALUES (2, 'What is this a picture of?', '', 'legend', 4, 'https://i.imgur.com/mdHqY9n.png')");
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
        init();
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

        statement.executeUpdate("INSERT INTO review_table(user_id, quiz_id, quiz_name,score, date, review_text, rating) VALUES (1,1,'ez',10,NOW(),'',10);");
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
        List<Pair<String,String>> achievements = dbManager.getAchievements(temp);
        assertEquals("supreme",achievements.get(0).getKey());
        assertEquals("wowzers",achievements.get(1).getKey());

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


    public void testGetQuiz() throws SQLException {
        addQuizzes();

        Quiz quiz1 = dbManager.getQuiz(1);
        assertNotNull(quiz1);
        assertEquals(1, quiz1.getQuiz_id());
        assertEquals("Basic Math Quiz", quiz1.getQuiz_name());
        assertEquals(Arrays.asList("math", "3grade"), quiz1.getQuiz_tag());
        assertEquals("Easy", quiz1.getDifficulty());
        assertEquals(1, quiz1.getCreator_id());

        Quiz quiz2 = dbManager.getQuiz(2);
        assertNotNull(quiz2);
        assertEquals(2, quiz2.getQuiz_id());
        assertEquals("english Quiz", quiz2.getQuiz_name());
        assertEquals(Arrays.asList("english", "3grade"), quiz2.getQuiz_tag());
        assertEquals("Medium", quiz2.getDifficulty());
        assertEquals(1, quiz2.getCreator_id());
    }

    public void testIsMultiplePages() throws SQLException {
        addQuizzes();
        addQuizzesMore();

        boolean isMultiplePagesQuiz1 = dbManager.isMultiplePages(1);
        assertFalse(isMultiplePagesQuiz1);

        boolean isMultiplePagesQuiz2 = dbManager.isMultiplePages(3);
        assertTrue(isMultiplePagesQuiz2);
    }

    public void testGetQuestions() throws SQLException {
        addQuizzes();
        addQuestions();

        List<Question> questionsQuiz1 = dbManager.getQuestions(1);
        assertNotNull(questionsQuiz1);
        assertEquals(2, questionsQuiz1.size());

        assertTrue(questionsQuiz1.get(0) instanceof MultipleChoice);
        assertEquals("What is 2+2?", questionsQuiz1.get(0).getQuestionText());
        assertEquals("4", questionsQuiz1.get(0).getAnswer());

        List<Question> questionsQuiz2 = dbManager.getQuestions(2);
        assertNotNull(questionsQuiz2);
        assertEquals(3, questionsQuiz2.size());

        assertTrue(questionsQuiz2.get(0) instanceof FillInTheBlank);
        assertEquals("----, my name is slim shady", questionsQuiz2.get(0).getQuestionText());
        assertEquals("hello", questionsQuiz2.get(0).getAnswer());

        assertTrue(questionsQuiz2.get(1) instanceof QuestionResponse);
        assertEquals("are you gay?", questionsQuiz2.get(1).getQuestionText());
        assertEquals("yes", questionsQuiz2.get(1).getAnswer());

        assertTrue(questionsQuiz2.get(2) instanceof PictureResponse);
        assertEquals("What is this a picture of?", questionsQuiz2.get(2).getQuestionText());
        assertEquals("legend", questionsQuiz2.get(2).getAnswer());
    }




    public void testSaveReview() throws SQLException {
        addQuizzes();
        addReviews();

        int userId = 2;
        int quizId = 1;
        int score = 85;
        long timeTaken = 3600;
        Date date = new Date(1111, 11, 1);
        int rating = 4;
        String review = "Good quiz!";
        String quizName = "Basic Math Quiz";

        dbManager.saveReview(userId, quizId, score, timeTaken, date, rating, review, quizName);

        Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM review_table WHERE user_id = ? AND quiz_id = ?");
        stmt.setInt(1, userId);
        stmt.setInt(2, quizId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            assertEquals(userId, rs.getInt("user_id"));
            assertEquals(quizId, rs.getInt("quiz_id"));
            assertEquals(score, rs.getInt("score"));
            assertEquals(timeTaken, rs.getLong("timeTaken"));
            assertEquals(new java.sql.Date(date.getTime()), rs.getDate("date"));
            assertEquals(rating, rs.getInt("rating"));
            assertEquals(review, rs.getString("review_text"));
            assertEquals(quizName, rs.getString("quiz_name"));
        } else {
            throw new AssertionError("Review not found in database");
        }

        rs.close();
        stmt.close();
        connection.close();
    }
    public void testUpdatePracticedField() throws SQLException {
        addQuizzes();

        int userId = 1;


        Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT practiced FROM user_table WHERE user_id = ?");
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        assertTrue(rs.next());
        assertFalse(rs.getBoolean("practiced"));


        dbManager.updatePracticedField(userId);


        rs = stmt.executeQuery();
        assertTrue(rs.next());
        assertTrue(rs.getBoolean("practiced"));

        rs.close();
        stmt.close();
        connection.close();
    }

    public void testGetUserIdByName() throws SQLException {
        addQuizzes();
        String username = "nick";
        int expectedUserId = 1;

        int actualUserId = dbManager.getUserIdByName(username);
        assertEquals(expectedUserId, actualUserId);

        username = "john";
        expectedUserId = 2;

        actualUserId = dbManager.getUserIdByName(username);
        assertEquals(expectedUserId, actualUserId);

        username = "nonexistent";
        expectedUserId = -1;

        actualUserId = dbManager.getUserIdByName(username);
        assertEquals(expectedUserId, actualUserId);
    }

    public void testSendMail() throws SQLException {
        addQuizzes();
        int fromId = 1;
        int toId = 2;
        String type = "challenge";
        String message = "come if you dare";

        dbManager.sendMail(fromId, toId, type, message);

        Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM mail_table WHERE from_id = ? AND to_id = ? AND type = ? AND message = ?");
        stmt.setInt(1, fromId);
        stmt.setInt(2, toId);
        stmt.setString(3, type);
        stmt.setString(4, message);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            assertEquals(fromId, rs.getInt("from_id"));
            assertEquals(toId, rs.getInt("to_id"));
            assertEquals(type, rs.getString("type"));
            assertEquals(message, rs.getString("message"));
            assertNotNull(rs.getTimestamp("date")); // Check that the date is not null
        } else {
            throw new AssertionError("Mail not found in database");
        }

        rs.close();
        stmt.close();
        connection.close();
    }

    public void testAreFriends() throws SQLException {
        addQuizzes();
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO friend_table (user_id_1, user_id_2) VALUES (1, 2)");
        statement.close();
        connection.close();

        assertTrue(dbManager.areFriends(1, 2));
        assertTrue(dbManager.areFriends(2, 1));
        assertFalse(dbManager.areFriends(1, 3));
        assertFalse(dbManager.areFriends(2, 3));
    }

    public void testGetHighestScore() throws SQLException {
        addQuizzes();
        addReviews();
        int quizId = 1;
        int expectedHighestScore = 85;

        int actualHighestScore = dbManager.getHighestScore(quizId);
        assertEquals(expectedHighestScore, actualHighestScore);

        quizId = 2;
        expectedHighestScore = 80;

        actualHighestScore = dbManager.getHighestScore(quizId);
        assertEquals(expectedHighestScore, actualHighestScore);

        quizId = 3;
        expectedHighestScore = 0;

        actualHighestScore = dbManager.getHighestScore(quizId);
        assertEquals(expectedHighestScore, actualHighestScore);
    }

    public void testUpdateUserScoredHighest() throws SQLException {
        addQuizzes();
        int userId = 1;


        Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT scored_Highest FROM user_table WHERE user_id = ?");
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        assertTrue(rs.next());
        assertFalse(rs.getBoolean("scored_Highest"));


        dbManager.updateUserScoredHighest(userId);


        rs = stmt.executeQuery();
        assertTrue(rs.next());
        assertTrue(rs.getBoolean("scored_Highest"));

        rs.close();
        stmt.close();
        connection.close();
    }
    public void testGetSiteTagData() throws SQLException {
        addTestUserData();

        Map<String,Integer> tagData = dbManager.getSiteTagData();
        assertEquals((Integer) 2,tagData.get("history"));
        assertEquals((Integer) 1,tagData.get("math"));
        assertEquals((Integer) 1,tagData.get("english"));

        removeTestUserData();
    }

    public void testGetUserCount() throws SQLException {
        addTestUserData();

        int cnt = dbManager.getUserCount();
        assertEquals(4,cnt);

        removeTestUserData();
    }

    public void testgetSiteActivity() throws SQLException {
        addTestUserData();

        Map<String,Integer> activityData = dbManager.getSiteActivity();
        assertEquals((Integer) 3,activityData.get("bendo"));

        removeTestUserData();
    }

    public void testgetAverageRating() throws SQLException {
        addTestUserData();

        double avg = dbManager.getAverageRating();
        assertEquals(10.0,avg);

        removeTestUserData();
    }
    public void testgetHighPerformanceQuizzes() throws SQLException {
        addTestUserData();

        List<Pair<String,Double>> avg = dbManager.getHighPerformanceQuizzes();
        assertEquals(3,avg.size());

        removeTestUserData();
    }

    public void testgetAdminQuizzesCount() throws SQLException {
        addTestUserData();

        int activityData = dbManager.getAdminQuizzesCount();
        assertEquals(0,activityData);

        removeTestUserData();
    }

    public void testgetBanCount() throws SQLException {
        addTestUserData();

        int banCount = dbManager.getBanCount();
        assertEquals(0,banCount);

        removeTestUserData();
    }

    public void testgetAchievementDescription() throws SQLException {
        addTestUserData();

        String banCount = dbManager.getAchievementDescription("supreme");
        assertEquals(null,banCount);

        removeTestUserData();

    }

}
