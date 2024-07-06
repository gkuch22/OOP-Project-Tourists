package javaFiles;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface Quiz {

    int getQuiz_id();
    void setQuiz_id(int quiz_id);

    String getQuiz_name();
    void setQuiz_name(String quiz_name);

    List<String> getQuiz_tag();
    void setQuiz_tag(String quiz_tag);

    String getDifficulty();
    String getQuizTagsAsString();


    boolean isRandom();

    boolean isImmediatelyCorrected();

    void setDifficulty(String difficulty);

    int getCreator_id();
    void setCreator_id(int creator_id);

    boolean isMultiple_pages();
    void setMultiple_pages(boolean multiple_pages);

    boolean isPractice_mode();
    void setPractice_mode(boolean practice_mode);

    boolean isGradable();

    boolean isRandom();

    boolean isImmediatelyCorrected();

    void setGradable(boolean gradable);

    void addQuestion(Question question);
    ArrayList<Question> getQuestions();

    Date getDate();

}
