package javaFiles;

public class Question {
    private String questionText = null;
    public String answer = null;
    public Question(String quiestionText, String answer){
        this.questionText = quiestionText;
        this.answer = answer;
    }

    public String getQuestionText(){
        return questionText;
    }

    public String getAnswer(){
        return answer;
    }

}
