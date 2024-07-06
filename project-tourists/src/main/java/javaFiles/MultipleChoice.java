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
        String answers = getPossibleAnswersAsString();

        return answers.split(";");
    }

    public String getPossibleAnswersAsString(){
        String answ = "";
        for(int i = 0; i < possibleAnswers.length; i++){
            answ += possibleAnswers[i];
            if(i != possibleAnswers.length - 1){
                answ += ';';
            }
        }
        answ = answ.replaceAll("\\s*;\\s*", ";");
        System.out.println(answ);
        return answ;
    }
}
