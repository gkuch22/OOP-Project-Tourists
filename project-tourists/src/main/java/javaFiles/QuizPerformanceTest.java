package javaFiles;

import junit.framework.TestCase;

import java.util.Date;

public class QuizPerformanceTest extends TestCase {

    public void testQuizPerformance1(){
        Date date = new Date();
        QuizPerformance quizPerformance = new QuizPerformanceimpl(1,1,"test1",5,date,3,"");
        assertEquals(1,quizPerformance.getUser_id());
        assertEquals(1,quizPerformance.getQuiz_id());
        assertEquals("test1",quizPerformance.getQuiz_name());
        assertEquals(5,quizPerformance.getScore());
        assertEquals(date,quizPerformance.getDate());
        assertEquals(3,quizPerformance.getRating());
        assertEquals("",quizPerformance.getReviewText());
    }
    public void testQuizPerformance2(){
        Date date = new Date();
        QuizPerformance quizPerformance = new QuizPerformanceimpl(1,1,"test1",5,date,3,"");
        quizPerformance.setUser_id(2);
        quizPerformance.setQuiz_id(2);
        quizPerformance.setQuiz_name("test2");
        quizPerformance.setScore(2);
        quizPerformance.setDate(date);
        quizPerformance.setRating(2);
        quizPerformance.setReviewText("meh");
        assertEquals(2,quizPerformance.getUser_id());
        assertEquals(2,quizPerformance.getQuiz_id());
        assertEquals("test2",quizPerformance.getQuiz_name());
        assertEquals(2,quizPerformance.getScore());
        assertEquals(date,quizPerformance.getDate());
        assertEquals(2,quizPerformance.getRating());
        assertEquals("meh",quizPerformance.getReviewText());
    }

}
