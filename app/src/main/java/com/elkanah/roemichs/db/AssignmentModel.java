package com.elkanah.roemichs.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.elkanah.roemichs.R;

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
        this.assignImage = assignImage;
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

    public int getAssignImage() {
        return assignImage;
    }

    public void setAssignImage(int assignImage) {
        this.assignImage = assignImage;
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

    public static List<AssignmentModel> getAssignments(){
        List<AssignmentModel> assignmentModels = new ArrayList<>();
        assignmentModels.add(new AssignmentModel("MAR 17, 2020","JUNE 4, 2022","PROF. ADEWOLE", R.drawable.vtwo,"RADIUS OF A CIRCLE","Calculate the radius of a circle given the radius as 7cm, 4cm"));
        assignmentModels.add(new AssignmentModel("JAN 1, 2019","FEB 4, 2022","MR. AYODELE", R.drawable.vtwo,"DIFFUSION","Calculate the radius of a circle given the radius as 7cm, 4cm"));
        assignmentModels.add(new AssignmentModel("MAY 3, 2013","JUNE 4, 2022","MRS. SAMSON", R.drawable.vtwo,"ENERGY","Calculate the radius of a circle given the radius as 7cm, 4cm"));
        assignmentModels.add(new AssignmentModel("FEB 18, 2020","MAR 4, 2022","DR. VICTOR", R.drawable.vtwo,"KINETIC MOTION","Calculate the radius of a circle given the radius as 7cm, 4cm"));
        assignmentModels.add(new AssignmentModel("OCT 12, 2012","NOV 4, 2022","MR. EMMANUEL", R.drawable.vtwo,"POTENTIAL","Calculate the radius of a circle given the radius as 7cm, 4cm"));
        assignmentModels.add(new AssignmentModel("DEC 8, 2019","JAN 4, 2020","DR. DEE", R.drawable.vtwo,"RADIUS OF A TRIANGLE","Calculate the radius of a circle given the radius as 7cm, 4cm"));
        assignmentModels.add(new AssignmentModel("JUN 1, 2019","JUNE 4, 2019","MRS. VIC", R.drawable.vtwo,"RADIUS OF A SQUARE","Calculate the radius of a circle given the radius as 7cm, 4cm"));
        return assignmentModels;


    }

}
