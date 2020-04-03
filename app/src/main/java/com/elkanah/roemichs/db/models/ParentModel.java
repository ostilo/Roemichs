package com.elkanah.roemichs.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.elkanah.roemichs.ui.model.StudentProfileModel;

import java.util.ArrayList;
import java.util.List;

public class ParentModel implements Parcelable {
    private String name;
    private  String acctBalance;
    private String childrenID;
    private StudentProfileModel modelList;

    public ParentModel(String name, String acctBalance, String childrenID, StudentProfileModel modelList) {
        this.name = name;
        this.acctBalance = acctBalance;
        this.childrenID = childrenID;
        this.modelList = modelList;
    }

    public ParentModel(StudentProfileModel modelList) {
        this.modelList = modelList;
    }

    public ParentModel(String name, String acctBalance, StudentProfileModel modelList) {
        this.name = name;
        this.acctBalance = acctBalance;
        this.modelList = modelList;
    }


    protected ParentModel(Parcel in) {
        name = in.readString();
        acctBalance = in.readString();
        childrenID = in.readString();
        modelList = in.readParcelable(StudentProfileModel.class.getClassLoader());
    }

    public static final Creator<ParentModel> CREATOR = new Creator<ParentModel>() {
        @Override
        public ParentModel createFromParcel(Parcel in) {
            return new ParentModel(in);
        }

        @Override
        public ParentModel[] newArray(int size) {
            return new ParentModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcctBalance() {
        return acctBalance;
    }

    public void setAcctBalance(String acctBalance) {
        this.acctBalance = acctBalance;
    }

    public String getChildrenID() {
        return childrenID;
    }

    public void setChildrenID(String childrenID) {
        this.childrenID = childrenID;
    }

    public StudentProfileModel getModelList() {
        return modelList;
    }

    public void setModelList(StudentProfileModel modelList) {
        this.modelList = modelList;
    }


    public  static List<ParentModel> getParentModel(){
        List<ParentModel> models = new ArrayList<>();
        models.add(new ParentModel("MR. ADEWOLE","#30,000", new StudentProfileModel("1","ADEWOLE","JOHNSON","SS1","#10,000")));
        models.add(new ParentModel("PROF. AYODEJI","#400,000",new StudentProfileModel("2","AYOMIDE","AYODEJI","JSS1","#20,000")));
        models.add(new ParentModel("PROF. PAUL","#400,000",new StudentProfileModel("3","PAUL","EMMANUEL","SS3","40,000")));

        return models;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(acctBalance);
        dest.writeString(childrenID);
        dest.writeParcelable(modelList, flags);
    }
}
