package com.elkanah.roemichs.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ParentPaymentModel implements Parcelable {
    private  String date;
    private String description;
    private String amount;
    private int status;

    public ParentPaymentModel(String date, String description, String amount, int status) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.status = status;
    }

    protected ParentPaymentModel(Parcel in) {
        date = in.readString();
        description = in.readString();
        amount = in.readString();
        status = in.readInt();
    }

    public static final Creator<ParentPaymentModel> CREATOR = new Creator<ParentPaymentModel>() {
        @Override
        public ParentPaymentModel createFromParcel(Parcel in) {
            return new ParentPaymentModel(in);
        }

        @Override
        public ParentPaymentModel[] newArray(int size) {
            return new ParentPaymentModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(description);
        dest.writeString(amount);
        dest.writeInt(status);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static List<ParentPaymentModel> getPaymentList(){
        List<ParentPaymentModel> models = new ArrayList<>();
        models.add(new ParentPaymentModel("2/12/20","School Fee","\u20a6 300,000",1));
        models.add(new ParentPaymentModel("1/1/20","Excursion Fee","\u20a6 30,000",0));
        models.add(new ParentPaymentModel("3/2/20","Medical Fee","\u20a6 200,000",1));
//        models.add(new ParentPaymentModel("5/2/20","School","\u20a6 100,000",1));
        models.add(new ParentPaymentModel("8/9/20","Charity","\u20a6 800,000",1));
        models.add(new ParentPaymentModel("4/3/20","Tax","\u20a6 100,000",0));
        return models;
    }
}
