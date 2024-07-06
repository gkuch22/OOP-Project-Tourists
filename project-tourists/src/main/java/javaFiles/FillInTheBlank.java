package javaFiles;

public class FillInTheBlank extends Question{
    public FillInTheBlank(String quiestionText, String answer) {
        super(quiestionText, answer);
    }

    public String getQuestionText(){
        return super.getQuestionText();
    }

    public String getAnswer(){
        String answers = super.getAnswer();
        answers  = answers.replaceAll("\\s*,\\s*", ",");
        answers = answers.replace(',',';');
        System.out.println(answers);
        return answers;
    }

    public String[] getAnswers(){
        String answers = super.getAnswer();
        answers  = answers.replaceAll("\\s*,\\s*", ",");
        return answers.split(",");
    }
}
