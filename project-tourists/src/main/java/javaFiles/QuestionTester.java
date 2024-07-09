package javaFiles;

import junit.framework.TestCase;

import java.util.Arrays;

public class QuestionTester extends TestCase {

    public void test1(){
        Question question = new Question("q1", "a1");
        assertTrue(question.getQuestionText().equals("q1"));
        assertTrue(question.getAnswer().equals("a1"));
    }

    public void test2(){
        QuestionResponse question = new QuestionResponse("q2","a2");
        assertTrue(question.getQuestionText().equals("q2"));
        assertTrue(question.getAnswer().equals("a2"));
    }

    public void test3(){
        String[] strs = new String[]{"a1", "a2"};
        MultipleChoice question = new MultipleChoice("q2","a2", strs);
        assertTrue(question.getQuestionText().equals("q2"));
        assertTrue(question.getAnswer().equals("a2"));
        assertTrue(question.getPossibleAnswersAsString().equals("a1;a2"));
        assertTrue(question.getPossibleAnswers()[0].equals("a1"));
        assertTrue(question.getPossibleAnswers()[1].equals("a2"));
    }

    public void test4(){
        PictureResponse question = new PictureResponse("q2","a2", "url");
        assertTrue(question.getQuestionText().equals("q2"));
        assertTrue(question.getAnswer().equals("a2"));
        assertTrue(question.getImageURL().equals("url"));
    }

    public void test5(){
        FillInTheBlank question = new FillInTheBlank("q2","a2,a3");
        assertTrue(question.getQuestionText().equals("q2"));
        assertTrue(question.getAnswer().equals("a2;a3"));
        assertTrue(question.getAnswers()[0].equals("a2"));
        assertTrue(question.getAnswers()[1].equals("a3"));
    }

}
