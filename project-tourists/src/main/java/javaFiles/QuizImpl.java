package javaFiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class QuizImpl implements Quiz{
    private int quiz_id;
    private String quiz_name;
    private String quiz_tag;
    private String difficulty;
    private int creator_id;
    private boolean multiple_pages;
    private boolean practice_mode;
    private boolean gradable;
    private boolean isRandom;
    private boolean isTimed;
    private boolean immediatelyCorrected;
    private Date date_created;
    private ArrayList<Question> questions = new ArrayList<Question>();

    public QuizImpl(int quizId, String quizName, String quizTag, String difficulty, int creatorId,
                        boolean multiplePages, boolean practiceMode, boolean gradable, Date dateCreated) {
        this.quiz_id = quizId;
        this.quiz_name = quizName;
        this.quiz_tag = quizTag;
        this.difficulty = difficulty;
        this.creator_id = creatorId;
        this.multiple_pages = multiplePages;
        this.practice_mode = practiceMode;
        this.gradable = gradable;
        this.date_created = dateCreated;
    }


    public QuizImpl(int quiz_id, String quizName, String quizTag, String difficulty, int creatorId, boolean isRandom, boolean isTimed, boolean multiplePages,
                        boolean immediatelyCorrected, boolean practiceMode, boolean gradable) {
        this.quiz_id = quiz_id;
        this.quiz_name = quizName;
        this.quiz_tag = quizTag;
        this.difficulty = difficulty;
        this.creator_id = creatorId;
        this.isRandom = isRandom;
        this.isTimed = isTimed;
        this.multiple_pages = multiplePages;
        this.immediatelyCorrected = immediatelyCorrected;
        this.practice_mode = practiceMode;
        this.gradable = gradable;
    }


    @Override
    public int getQuiz_id() {
        return quiz_id;
    }


    @Override
    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
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
    public List<String> getQuiz_tag() {
        List<String> tags = Arrays.asList(quiz_tag.split(";"));
        return tags;
    }
    @Override
    public String getQuizTagsAsString(){
        return quiz_tag;
    }

    @Override
    public void setQuiz_tag(String quiz_tag) {
        this.quiz_tag = quiz_tag;
    }

    @Override
    public String getDifficulty() {
        return difficulty;
    }

    @Override
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public int getCreator_id() {
        return creator_id;
    }

    @Override
    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }

    @Override
    public boolean isMultiple_pages() {
        return multiple_pages;
    }

    @Override
    public void setMultiple_pages(boolean multiple_pages) {
        this.multiple_pages = multiple_pages;
    }

    @Override
    public boolean isPractice_mode() {
        return practice_mode;
    }

    @Override
    public void setPractice_mode(boolean practice_mode) {
        this.practice_mode = practice_mode;
    }

    @Override
    public boolean isGradable() {
        return gradable;
    }

    @Override
    public boolean isRandom() {
        return isRandom;
    }

    @Override
    public boolean isImmediatelyCorrected() {
        return immediatelyCorrected;
    }



    @Override
    public void setGradable(boolean gradable) {
        this.gradable = gradable;
    }

    @Override
    public Date getDate(){
        return this.date_created;
    }

    @Override
    public void addQuestion(Question question){
        questions.add(question);
    }
    @Override
    public ArrayList<Question> getQuestions(){
        return questions;
    }

}
