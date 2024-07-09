package javaFiles;

import javafx.util.Pair;
import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class QuizTester extends TestCase {

    private DBManager dbManager;
    private BasicDataSource dataSource;
    {
        try {
            dbManager = new DBManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void test(){
        Quiz quiz = new QuizImpl(1, "name", "tag", "hard", 1, false, false, false,
        true, true, false, "desc", 30);
        quiz.setQuiz_id(2);
        assertEquals(true, quiz.getQuiz_id() == 2);
        assertEquals("tag", quiz.getQuiz_tag().get(0));
    }




}
