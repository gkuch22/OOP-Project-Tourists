package javaFiles;

public class QuestionResponse extends Question{
    public QuestionResponse(String quiestionText, String answer) {
        super(quiestionText, answer);
    }


    public String getQuestionText(){
        return super.getQuestionText();
    }

    public String getAnswer(){
        return super.getAnswer();
    }
}
