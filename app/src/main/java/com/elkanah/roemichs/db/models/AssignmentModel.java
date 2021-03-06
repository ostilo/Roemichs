package com.elkanah.roemichs.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.db.view.AssignmentResponseImageData;

import java.util.ArrayList;
import java.util.List;

public class AssignmentModel implements Parcelable {
    private String postDate;
    private String submissionDate;
    private  String teachersName;
    private int assignImage;
    private String assgnTitle;
    private String asshnBody;


    public AssignmentModel(String postDate, String submissionDate, String teachersName, int assignImage, String assgnTitle, String asshnBody) {
        this.postDate = postDate;
        this.submissionDate = submissionDate;
        this.teachersName = teachersName;
        this.assgnTitle = assgnTitle;
        this.asshnBody = asshnBody;
    }


    protected AssignmentModel(Parcel in) {
        postDate = in.readString();
        submissionDate = in.readString();
        teachersName = in.readString();
        assignImage = in.readInt();
        assgnTitle = in.readString();
        asshnBody = in.readString();
    }

    public static final Creator<AssignmentModel> CREATOR = new Creator<AssignmentModel>() {
        @Override
        public AssignmentModel createFromParcel(Parcel in) {
            return new AssignmentModel(in);
        }

        @Override
        public AssignmentModel[] newArray(int size) {
            return new AssignmentModel[size];
        }
    };

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getTeachersName() {
        return teachersName;
    }

    public void setTeachersName(String teachersName) {
        this.teachersName = teachersName;
    }



    public String getAssgnTitle() {
        return assgnTitle;
    }

    public void setAssgnTitle(String assgnTitle) {
        this.assgnTitle = assgnTitle;
    }

    public String getAsshnBody() {
        return asshnBody;
    }

    public void setAsshnBody(String asshnBody) {
        this.asshnBody = asshnBody;
    }



    public static List<AssignmentModel> getAssignments(){
        List<AssignmentModel> assignmentModels = new ArrayList<>();
        assignmentModels.add(new AssignmentModel("MAR 17, 2020","2/4/2020","PROF. ADEWOLE", R.drawable.vtwo,"CoronaVirus in the World","Calculate the radius of a circle given the radius as 7cm, 4cm\nCalculate the radius of a circle given the radius as 7cm, 4cm\nCalculate the radius of a circle given the radius as 7cm, 4cm\n\nCalculate the radius of a circle given the radius as 7cm, 4cm\nCalculate the radius of a circle given the radius as 7cm, 4cm\n"));
        assignmentModels.add(new AssignmentModel("JAN 1, 2019","1/3/2022","MR. AYODELE", R.drawable.vtwo,"Diffusion in Water","Calculate the radius of a circle given the radius as 7cm, 4cmCalculate the radius of a circle given the radius as 7cm, 4cm\nCalculate the radius of a circle given the radius as 7cm, 4cm\n\nCalculate the radius of a circle given the radius as 7cm, 4cm\nCalculate the radius of a circle given the radius as 7cm, 4cm\n"));
        assignmentModels.add(new AssignmentModel("MAY 3, 2013","3/4/2022","MRS. SAMSON", R.drawable.vtwo,"Kinetic Energy","Calculate the radius of a circle given the radius as 7cm, 4cm\nCalculate the radius of a circle given the radius as 7cm, 4cm\nCalculate the radius of a circle given the radius as 7cm, 4cm\n\nCalculate the radius of a circle given the radius as 7cm, 4cm\nCalculate the radius of a circle given the radius as 7cm, 4cm\n"));
        return assignmentModels;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(postDate);
        dest.writeString(submissionDate);
        dest.writeString(teachersName);
        dest.writeInt(assignImage);
        dest.writeString(assgnTitle);
        dest.writeString(asshnBody);
    }

    public int getAssignImage() {
        return assignImage;
    }

    public void setAssignImage(int assignImage) {
        this.assignImage = assignImage;
    }
}
