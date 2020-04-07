package com.elkanah.roemichs.ui.model;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class TestQuestionModel implements Parcelable {
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

    protected TestQuestionModel(Parcel in) {
        testID = in.readString();
        questionText = in.readString();
        questionImage = in.readString();
        answer = in.readString();
    }

    public static final Creator<TestQuestionModel> CREATOR = new Creator<TestQuestionModel>() {
        @Override
        public TestQuestionModel createFromParcel(Parcel in) {
            return new TestQuestionModel(in);
        }

        @Override
        public TestQuestionModel[] newArray(int size) {
            return new TestQuestionModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(testID);
        dest.writeString(questionText);
        dest.writeString(questionImage);
        dest.writeString(answer);
    }
}
