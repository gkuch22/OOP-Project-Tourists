package javaFiles;

public class FillInTheBlank extends Question{
    public FillInTheBlank(String quiestionText, String answer) {
        super(quiestionText, answer);
    }

    public String getQuestionText(){
        return super.getQuestionText();
    }

    public String getAnswer(){
        return super.getAnswer();
    }

    public String[] getAnswers(){
        return answer.split(",");
    }
}
