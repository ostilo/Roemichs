package com.elkanah.roemichs.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OptiontModel implements Parcelable {
    public String value;
    public String type;

    public OptiontModel(String value, String type) {
        this.value = value;
        this.type = type;
    }

    protected OptiontModel(Parcel in) {
        value = in.readString();
        type = in.readString();
    }

    public static final Creator<OptiontModel> CREATOR = new Creator<OptiontModel>() {
        @Override
        public OptiontModel createFromParcel(Parcel in) {
            return new OptiontModel(in);
        }

        @Override
        public OptiontModel[] newArray(int size) {
            return new OptiontModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
        dest.writeString(type);
    }
}
