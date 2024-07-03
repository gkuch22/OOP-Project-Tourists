package javaFiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserImpl implements User{

    int user_id;
    String username;
    boolean isAdmin;
    boolean hasPracticed;
    int numCreatedQuizzes;
    boolean scoredHighest;
    String profilePhoto;
    List<Quiz> takenQuizzes;

    public UserImpl(int user_id,String username,boolean isAdmin,boolean hasPracticed,int numCreatedQuizzes,boolean scoredHighest,String profilePhoto,List<Quiz> quizesTaken){
        this.user_id=user_id;
        this.username=username;
        this.isAdmin=isAdmin;
        this.hasPracticed=hasPracticed;
        this.numCreatedQuizzes=numCreatedQuizzes;
        this.scoredHighest=scoredHighest;
        this.profilePhoto=profilePhoto;
        this.takenQuizzes=quizesTaken;
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
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String name) {
        this.username=name;
    }

    @Override
    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public void setAdminStatus(boolean adminStatus) {
        this.isAdmin=adminStatus;
    }

    @Override
    public boolean hasPracticed() {
        return hasPracticed;
    }

    @Override
    public void setPracticed(boolean practiced) {
        this.hasPracticed=practiced;
    }

    @Override
    public int getNumberOfCreatedQuizzes() {
        return numCreatedQuizzes;
    }

    @Override
    public void setNumberOfCreatedQuizzes(int count) {
        this.numCreatedQuizzes=count;
    }

    @Override
    public List<Quiz> getTakenQuizzes() {
        return takenQuizzes;
    }

    @Override
    public void setTakenQuizzes(List<Quiz> quizzes) {
        takenQuizzes=quizzes;
    }

    @Override
    public boolean scoredHighest() {
        return scoredHighest;
    }

    @Override
    public void setScoredHighest(boolean scoredHighest) {
        this.scoredHighest=scoredHighest;
    }

    @Override
    public String getProfilePhoto() {
        return profilePhoto;
    }

    @Override
    public void setProfilePhoto(String txt) {
        profilePhoto=txt;
    }

    @Override
    public Map<String, Integer> getTagCount() {
        Map<String,Integer> result = new HashMap<String,Integer>();
        for(Quiz quiz : takenQuizzes) {
            List<String> tags = quiz.getQuiz_tag();
            for (String tag : tags) {
                if (result.containsKey(tag)) {
                    int prevCnt = result.get(tag);
                    result.remove(tag);
                    result.put(tag, prevCnt + 1);
                } else {
                    result.put(tag, 1);
                }
            }
        }
        return result;
    }


}
