package javaFiles;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.time.LocalDate.now;

public class UserTests extends TestCase {


    public void test1(){
        List<Quiz> quizzes = new ArrayList<>();
        Date date = new Date();
        Quiz quiz1 = new QuizImpl(1,"quiz1","history;math","medium",1,false,true,false,date);
        Quiz quiz2 = new QuizImpl(2,"quiz2","chemistry;math","hard",1,true,true,false,date);
        quizzes.add(quiz1);
        quizzes.add(quiz2);
        User user1 = new UserImpl(1,"tester1",false,true,10,true,"https://i.pinimg.com/736x/5e/de/74/5ede7463e5eef20dcc48559df08fcfde.jpg",quizzes);
        assertEquals(1,user1.getUser_id());
        assertEquals("tester1",user1.getUsername());
        assertFalse(user1.isAdmin());
        assertTrue(user1.hasPracticed());
        assertEquals(10,user1.getNumberOfCreatedQuizzes());
        assertTrue(user1.scoredHighest());
        assertEquals("https://i.pinimg.com/736x/5e/de/74/5ede7463e5eef20dcc48559df08fcfde.jpg",user1.getProfilePhoto());
        List<Quiz> comp = user1.getTakenQuizzes();
        assertEquals(1,comp.get(0).getQuiz_id());
        assertEquals(2,comp.get(1).getQuiz_id());

        Map<String,Integer> tagcount = user1.getTagCount();
        assertEquals((Integer) 1,tagcount.get("history"));
        assertEquals((Integer) 2,tagcount.get("math"));
        assertEquals((Integer) 1,tagcount.get("chemistry"));
    }

    public void test2(){
        List<Quiz> quizzes = new ArrayList<>();
        Date date = new Date();
        Quiz quiz1 = new QuizImpl(1,"quiz1","history;math","medium",1,false,true,false,date);
        quizzes.add(quiz1);
        Quiz quiz2 = new QuizImpl(2,"quiz2","chemistry;math","hard",1,true,true,false,date);
        quizzes.add(quiz2);
        User user1 = new UserImpl(1,"tester1",false,true,10,true,"https://i.pinimg.com/736x/5e/de/74/5ede7463e5eef20dcc48559df08fcfde.jpg",quizzes);
        user1.setUser_id(2);
        user1.setUsername("tester2");
        user1.setAdminStatus(true);
        user1.setPracticed(false);
        user1.setNumberOfCreatedQuizzes(5);
        user1.setScoredHighest(false);
        user1.setProfilePhoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4gPPpJEQwNdzZV7_i5nVv1uNqL7Ogb9Gwxw&s");

        quizzes.remove(0);
        user1.setTakenQuizzes(quizzes);

        assertEquals(2,user1.getUser_id());
        assertEquals("tester2",user1.getUsername());
        assertTrue(user1.isAdmin());
        assertFalse(user1.hasPracticed());
        assertEquals(5,user1.getNumberOfCreatedQuizzes());
        assertFalse(user1.scoredHighest());
        assertEquals("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4gPPpJEQwNdzZV7_i5nVv1uNqL7Ogb9Gwxw&s",user1.getProfilePhoto());
        List<Quiz> comp = user1.getTakenQuizzes();
        assertEquals(2,comp.get(0).getQuiz_id());

        Map<String,Integer> tagcount = user1.getTagCount();
        assertEquals((Integer) 1,tagcount.get("math"));
        assertEquals((Integer) 1,tagcount.get("chemistry"));
    }

}
