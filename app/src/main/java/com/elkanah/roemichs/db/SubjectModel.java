package com.elkanah.roemichs.db;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class SubjectModel implements Parcelable {
    private String subjectTitle;
    private String noteTitle;
    private int noteID;
    private String noteBody;

    public SubjectModel(String subjectTitle, String noteTitle, int noteID, String noteBody) {
        this.subjectTitle = subjectTitle;
        this.noteTitle = noteTitle;
        this.noteID = noteID;
        this.noteBody = noteBody;
    }

    protected SubjectModel(Parcel in) {
        subjectTitle = in.readString();
        noteTitle = in.readString();
        noteID = in.readInt();
        noteBody = in.readString();
    }

    public static final Creator<SubjectModel> CREATOR = new Creator<SubjectModel>() {
        @Override
        public SubjectModel createFromParcel(Parcel in) {
            return new SubjectModel(in);
        }

        @Override
        public SubjectModel[] newArray(int size) {
            return new SubjectModel[size];
        }
    };

    public SubjectModel(String subjectTitle, int noteID) {
        this.subjectTitle = subjectTitle;
        this.noteID = noteID;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subjectTitle);
        dest.writeString(noteTitle);
        dest.writeInt(noteID);
        dest.writeString(noteBody);
    }
    public static List<SubjectModel> mySubjects(){
        List<SubjectModel> models = new ArrayList<>();
        models.add(new SubjectModel("Mathematics", 1));
        models.add(new SubjectModel("English", 1));
        models.add(new SubjectModel("Civic Education", 2));
        models.add(new SubjectModel("Biology", 3));
        models.add(new SubjectModel("Business Studies", 4));
        models.add(new SubjectModel("Yoruba", 3));
        models.add(new SubjectModel("French", 6));
        models.add(new SubjectModel("Chinese", 1));
        models.add(new SubjectModel("Chemistry", 2));
        models.add(new SubjectModel("Physics", 3));
        models.add(new SubjectModel("Agricultural Science", 1));
        models.add(new SubjectModel("Business Studies", 3));
        models.add(new SubjectModel("Yoruba", 1));
        models.add(new SubjectModel("French", 2));
        models.add(new SubjectModel("Chinese", 1));
        return models;

    }

    public static List<String> getColors(){
        List<String> colors = new ArrayList<String>();
        colors.add("#310053");
        colors.add("#6c2067");
        colors.add("#D85B15");
        colors.add("#D34718");
        return colors;
    }

}
