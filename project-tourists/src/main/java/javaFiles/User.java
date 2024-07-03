package javaFiles;

import java.util.List;
import java.util.Map;

public interface User {

    int getUser_id();
    void setUser_id(int user_id);

    String getUsername();
    void setUsername(String name);

    boolean isAdmin();
    void setAdminStatus(boolean adminStatus);

    boolean hasPracticed();
    void setPracticed(boolean practiced);

    int getNumberOfCreatedQuizzes();
    void setNumberOfCreatedQuizzes(int count);

    List<Quiz> getTakenQuizzes();
    void setTakenQuizzes(List<Quiz> quizzes);

    boolean scoredHighest();
    void setScoredHighest(boolean scoredHighest);

    String getProfilePhoto();
    void setProfilePhoto(String txt);

    Map<String,Integer> getTagCount();

}
