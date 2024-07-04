package javaFiles;

public class MultipleChoice extends Question{
    String[] possibleAnswers = null;
    public MultipleChoice(String quiestionText, String answer,
                          String[] possibleAnswers) {
        super(quiestionText, answer);
        this.possibleAnswers = possibleAnswers;
    }

    @Override
    public String getQuestionText() {
        return super.getQuestionText();
    }

    @Override
    public String getAnswer() {
        return super.getAnswer();
    }

    public String[] getPossibleAnswers(){
        return possibleAnswers;
    }
}
