package com.elkanah.roemichs.ui.model;
import java.util.ArrayList;

public class TestQuestionModel {
    public String testID;
    public String questionText;
    public String questionImage;
    public ArrayList<OptiontModel> options;
    public String answer;

    public TestQuestionModel() {
    }

    public TestQuestionModel(String testID, String questionTEXT, String questionIMAGE, ArrayList<OptiontModel> options, String answer) {
        this.testID = testID;
        this.questionText = questionTEXT;
        this.questionImage = questionIMAGE;
        this.options = options;
        this.answer = answer;
    }
}
