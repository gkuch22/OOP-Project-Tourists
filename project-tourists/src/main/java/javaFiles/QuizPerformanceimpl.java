package javaFiles;

import java.util.Date;

public class QuizPerformanceimpl implements QuizPerformance{

    int user_id;
    int quiz_id;
    String quiz_name;
    int score;
    Date date;
    int rating;
    String review;

    public QuizPerformanceimpl(int user_id,int quiz_id,String quiz_name,int score,Date date,int rating,String review){
        this.user_id=user_id;
        this.quiz_id=quiz_id;
        this.quiz_name=quiz_name;
        this.score=score;
        this.date=date;
        this.rating=rating;
        this.review=review;
    }


    @Override
    public int getUser_id() {
        return user_id;
    }

    @Override
    public void setUser_id(int user_id) {
        this.user_id=user_id;
    }

    @Override
    public int getQuiz_id() {
        return quiz_id;
    }

    @Override
    public void setQuiz_id(int quiz_id) {
        this.quiz_id=quiz_id;
    }

    @Override
    public String getQuiz_name() {
        return quiz_name;
    }

    @Override
    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score=score;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date=date;
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public void setRating(int rating) {
        this.rating=rating;
    }

    @Override
    public String getReviewText() {
        return review;
    }

    @Override
    public void setReviewText(String review) {
        this.review=review;
    }
}
