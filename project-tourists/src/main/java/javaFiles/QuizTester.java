package javaFiles;

import junit.framework.TestCase;

public class QuizTester extends TestCase {

    public void test1(){
        Quiz quiz = new QuizImpl(1, "quiz", "tag1,tag2", "easy", 1, false, false, false,
        false, true, true, "desc", 30);

        assertEquals(quiz.getQuiz_id(), 1);
        quiz.setQuiz_id(2);
        assertEquals(quiz.getQuiz_id(), 2);


        assertTrue(quiz.getQuiz_name().equals("quiz"));
        quiz.setQuiz_name("quiz1");
        assertTrue(quiz.getQuiz_name().equals("quiz1"));

        assertTrue(quiz.getDescription().equals("desc"));
        quiz.setDescription("desc1");
        assertTrue(quiz.getDescription().equals("desc1"));

        assertTrue(quiz.getQuizTagsAsString().equals("tag1,tag2"));
        quiz.setQuiz_tag("tag3");
//        assertTrue(quiz.getQuiz_tag().equals("tag3"));

        assertTrue(quiz.getDifficulty().equals("easy"));
        quiz.setDifficulty("hard");
        assertTrue(quiz.getDifficulty().equals("hard"));



        assertEquals(quiz.getCreator_id(), 1);
        quiz.setCreator_id(2);
        assertEquals(quiz.getCreator_id(), 2);

        assertEquals(quiz.isRandom(), false);
        assertEquals(quiz.isTimed(), false);

        assertEquals(quiz.isMultiple_pages(), false);
        quiz.setMultiple_pages(true);
        assertEquals(quiz.isMultiple_pages(), true);

        assertEquals(quiz.isImmediatelyCorrected(), false);

        assertEquals(quiz.isGradable(), true);
        quiz.setGradable(false);
        assertEquals(quiz.isGradable(), false);

        assertEquals(quiz.isPractice_mode(), true);
        quiz.setPractice_mode(false);
        assertEquals(quiz.isPractice_mode(), false);
    }
}
