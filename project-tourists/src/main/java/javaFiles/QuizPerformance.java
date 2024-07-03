package javaFiles;

import java.util.Date;

public interface QuizPerformance {

    void setUser_id(int user_id);
    int getUser_id();

    int getQuiz_id();
    void setQuiz_id(int quiz_id);

    String getQuiz_name();
    void setQuiz_name(String quiz_name);

    int getScore();
    void setScore(int score);

    Date getDate();
    void setDate(Date date);

    int getRating();
    void setRating(int rating);

    String getReviewText();
    void setReviewText(String review);
}
