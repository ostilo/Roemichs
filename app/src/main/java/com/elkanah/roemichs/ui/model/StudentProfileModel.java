package com.elkanah.roemichs.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

public class StudentProfileModel implements Parcelable {
    private String student_id;
    private String surname;
    private String first_name;
    private String student_class;
    private String profile_pic_url;
    private String studentAccount;

    public StudentProfileModel() {
    }



    public StudentProfileModel(String student_id, String surname, String first_name, String student_class) {
        this.student_id = student_id;
        this.surname = surname;
        this.first_name = first_name;
        this.student_class = student_class;
    }

    protected StudentProfileModel(Parcel in) {
        student_id = in.readString();
        surname = in.readString();
        first_name = in.readString();
        student_class = in.readString();
        profile_pic_url = in.readString();
        studentAccount = in.readString();
    }

    public StudentProfileModel(String student_id, String surname, String first_name, String student_class, String profile_pic_url, String studentAccount) {
        this.student_id = student_id;
        this.surname = surname;
        this.first_name = first_name;
        this.student_class = student_class;
        this.profile_pic_url = profile_pic_url;
        this.studentAccount = studentAccount;
    }

    public StudentProfileModel(String student_id, String surname, String first_name, String student_class, String studentAccount) {
        this.student_id = student_id;
        this.surname = surname;
        this.first_name = first_name;
        this.student_class = student_class;
        this.studentAccount = studentAccount;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(student_id);
        dest.writeString(surname);
        dest.writeString(first_name);
        dest.writeString(student_class);
        dest.writeString(profile_pic_url);
        dest.writeString(studentAccount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StudentProfileModel> CREATOR = new Creator<StudentProfileModel>() {
        @Override
        public StudentProfileModel createFromParcel(Parcel in) {
            return new StudentProfileModel(in);
        }

        @Override
        public StudentProfileModel[] newArray(int size) {
            return new StudentProfileModel[size];
        }
    };

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getStudent_class() {
        return student_class;
    }

    public void setStudent_class(String student_class) {
        this.student_class = student_class;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }




    public String getStudentAccount() {
        return studentAccount;
    }

    public void setStudentAccount(String studentAccount) {
        this.studentAccount = studentAccount;
    }
}
