package javaFiles;

public class PictureResponse extends Question{
    private String imageURL = null;
    public PictureResponse(String quiestionText, String answer, String imgURL) {
        super(quiestionText, answer);
        this.imageURL = imgURL;
    }

    @Override
    public String getQuestionText() {
        return super.getQuestionText();
    }

    @Override
    public String getAnswer() {
        return super.getAnswer();
    }

    public String getImageURL(){
        return imageURL;
    }
}
