package com.elkanah.roemichs.db;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class NoteModel implements Parcelable {
    private String noteTitle;
    private String noteBody;
    private String teachersname;
    private String WeekNumber;
    private  String subTopic;

    protected NoteModel(Parcel in) {
        noteTitle = in.readString();
        noteBody = in.readString();
        teachersname = in.readString();
        WeekNumber = in.readString();
        subTopic = in.readString();
    }

    public static final Creator<NoteModel> CREATOR = new Creator<NoteModel>() {
        @Override
        public NoteModel createFromParcel(Parcel in) {
            return new NoteModel(in);
        }

        @Override
        public NoteModel[] newArray(int size) {
            return new NoteModel[size];
        }
    };

    public NoteModel(String noteTitle, String noteBody, String teachersname, String weekNumber, String subTopic) {
        this.noteTitle = noteTitle;
        this.noteBody = noteBody;
        this.teachersname = teachersname;
        WeekNumber = weekNumber;
        this.subTopic = subTopic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteTitle);
        dest.writeString(noteBody);
        dest.writeString(teachersname);
        dest.writeString(WeekNumber);
        dest.writeString(subTopic);
    }
    public static List<NoteModel> getNotes(){
        List<NoteModel> noteModels = new ArrayList<>();
        noteModels.add(new NoteModel("Polygon","The arc of a rectangle", "Mr. Paul", "Mar","Getting the Rectangle"));
        noteModels.add(new NoteModel("Osmosis","The arc of a rectangle", "Mr. Paul", "Jan","Getting the Rectangle"));
        noteModels.add(new NoteModel("Arc","The arc of a rectangle", "Mr. Paul", "Feb","Getting the Rectangle"));
        noteModels.add(new NoteModel("Vowels","The arc of a rectangle", "Mr. Paul", "April","Getting the Rectangle"));
        noteModels.add(new NoteModel("Organic Matters","The arc of a rectangle", "Mr. Paul", "Octv","Getting the Rectangle"));
        noteModels.add(new NoteModel("Energy","The arc of a rectangle", "Mr. Paul", "Sep","Getting the Rectangle"));
        noteModels.add(new NoteModel("Polygon","The arc of a rectangle", "Mr. Paul", "Dec","Getting the Rectangle"));
        return  noteModels;

    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public String getTeachersname() {
        return teachersname;
    }

    public void setTeachersname(String teachersname) {
        this.teachersname = teachersname;
    }

    public String getWeekNumber() {
        return WeekNumber;
    }

    public void setWeekNumber(String weekNumber) {
        WeekNumber = weekNumber;
    }

    public String getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(String subTopic) {
        this.subTopic = subTopic;
    }
}
