package com.elkanah.roemichs.ui.model;

public class CreateTestModel {
    private int questionNo;
    private String questionText;
    private String questionImgUrl;
    private String answer1Text;
    private String answer1ImgUrl;
    private String answer2Text;
    private String answer2ImgUrl;
    private String answer3Text;
    private String answer3ImgUrl;
    private String answer4Text;
    private String answer4ImgUrl;
    private String answer5Text;
    private String answer5ImgUrl;
    private String correctAnswer;
    private int noOfAnswers;

    public CreateTestModel() {

    }

    public CreateTestModel(int questionNo, String questionText, String questionImgUrl, String answer1Text, String answer1ImgUrl, String answer2Text, String answer2ImgUrl, String answer3Text, String answer3ImgUrl, String answer4Text, String answer4ImgUrl, String answer5Text, String answer5ImgUrl, String correctAnswer, int noOfAnswers) {
        this.questionNo = questionNo;
        this.questionText = questionText;
        this.questionImgUrl = questionImgUrl;
        this.answer1Text = answer1Text;
        this.answer1ImgUrl = answer1ImgUrl;
        this.answer2Text = answer2Text;
        this.answer2ImgUrl = answer2ImgUrl;
        this.answer3Text = answer3Text;
        this.answer3ImgUrl = answer3ImgUrl;
        this.answer4Text = answer4Text;
        this.answer4ImgUrl = answer4ImgUrl;
        this.answer5Text = answer5Text;
        this.answer5ImgUrl = answer5ImgUrl;
        this.correctAnswer = correctAnswer;
        this.noOfAnswers = noOfAnswers;
    }

    public int getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionImgUrl() {
        return questionImgUrl;
    }

    public void setQuestionImgUrl(String questionImgUrl) {
        this.questionImgUrl = questionImgUrl;
    }

    public String getAnswer1Text() {
        return answer1Text;
    }

    public void setAnswer1Text(String answer1Text) {
        this.answer1Text = answer1Text;
    }

    public String getAnswer1ImgUrl() {
        return answer1ImgUrl;
    }

    public void setAnswer1ImgUrl(String answer1ImgUrl) {
        this.answer1ImgUrl = answer1ImgUrl;
    }

    public String getAnswer2Text() {
        return answer2Text;
    }

    public void setAnswer2Text(String answer2Text) {
        this.answer2Text = answer2Text;
    }

    public String getAnswer2ImgUrl() {
        return answer2ImgUrl;
    }

    public void setAnswer2ImgUrl(String answer2ImgUrl) {
        this.answer2ImgUrl = answer2ImgUrl;
    }

    public String getAnswer3Text() {
        return answer3Text;
    }

    public void setAnswer3Text(String answer3Text) {
        this.answer3Text = answer3Text;
    }

    public String getAnswer3ImgUrl() {
        return answer3ImgUrl;
    }

    public void setAnswer3ImgUrl(String answer3ImgUrl) {
        this.answer3ImgUrl = answer3ImgUrl;
    }

    public String getAnswer4Text() {
        return answer4Text;
    }

    public void setAnswer4Text(String answer4Text) {
        this.answer4Text = answer4Text;
    }

    public String getAnswer4ImgUrl() {
        return answer4ImgUrl;
    }

    public void setAnswer4ImgUrl(String answer4ImgUrl) {
        this.answer4ImgUrl = answer4ImgUrl;
    }

    public String getAnswer5Text() {
        return answer5Text;
    }

    public void setAnswer5Text(String answer5Text) {
        this.answer5Text = answer5Text;
    }

    public String getAnswer5ImgUrl() {
        return answer5ImgUrl;
    }

    public void setAnswer5ImgUrl(String answer5ImgUrl) {
        this.answer5ImgUrl = answer5ImgUrl;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getNoOfAnswers() {
        return noOfAnswers;
    }

    public void setNoOfAnswers(int noOfAnswers) {
        this.noOfAnswers = noOfAnswers;
    }

}
