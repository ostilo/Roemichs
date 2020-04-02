package com.elkanah.roemichs.ui.model;
import java.util.List;

public class TestQuestionModel {
    public String testID;
    public String questionText;
    public String questionImage;
    public List<OptiontModel> options;
    public String answer;

    public TestQuestionModel(String testID, String questionTEXT, String questionIMAGE, List<OptiontModel> options, String answer) {
        this.testID = testID;
        this.questionText = questionTEXT;
        this.questionImage = questionIMAGE;
        this.options = options;
        this.answer = answer;
    }
}
