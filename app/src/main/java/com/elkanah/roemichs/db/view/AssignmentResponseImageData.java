package com.elkanah.roemichs.db.view;

import android.os.Parcel;
import android.os.Parcelable;

import com.elkanah.roemichs.R;


import java.util.ArrayList;
import java.util.List;

public class AssignmentResponseImageData implements Parcelable {

    private String itemImageId;

    private String itemId;

    private String itemLargeImageName;

    private String itemSmallImageName;

    private String itemImageRefCode;

    private String itemImageStatus;

    private int images;

    public AssignmentResponseImageData(int images) {
        this.images = images;
    }

    public AssignmentResponseImageData(String itemImageStatus, int images) {
        this.itemImageStatus = itemImageStatus;
        this.images = images;
    }

    protected AssignmentResponseImageData(Parcel in) {
        itemImageId = in.readString();
        itemId = in.readString();
        itemLargeImageName = in.readString();
        itemSmallImageName = in.readString();
        itemImageRefCode = in.readString();
        itemImageStatus = in.readString();
        images = in.readInt();
    }

    public static final Creator<AssignmentResponseImageData> CREATOR = new Creator<AssignmentResponseImageData>() {
        @Override
        public AssignmentResponseImageData createFromParcel(Parcel in) {
            return new AssignmentResponseImageData(in);
        }

        @Override
        public AssignmentResponseImageData[] newArray(int size) {
            return new AssignmentResponseImageData[size];
        }
    };

    public String getItemImageId() {
        return itemImageId;
    }

    public void setItemImageId(String itemImageId) {
        this.itemImageId = itemImageId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemLargeImageName() {
        return itemLargeImageName;
    }

    public void setItemLargeImageName(String itemLargeImageName) {
        this.itemLargeImageName = itemLargeImageName;
    }

    public String getItemSmallImageName() {
        return itemSmallImageName;
    }

    public void setItemSmallImageName(String itemSmallImageName) {
        this.itemSmallImageName = itemSmallImageName;
    }

    public String getItemImageRefCode() {
        return itemImageRefCode;
    }

    public void setItemImageRefCode(String itemImageRefCode) {
        this.itemImageRefCode = itemImageRefCode;
    }

    public String getItemImageStatus() {
        return itemImageStatus;
    }

    public void setItemImageStatus(String itemImageStatus) {
        this.itemImageStatus = itemImageStatus;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemImageId);
        dest.writeString(itemId);
        dest.writeString(itemLargeImageName);
        dest.writeString(itemSmallImageName);
        dest.writeString(itemImageRefCode);
        dest.writeString(itemImageStatus);
        dest.writeInt(images);
    }

    public static List<AssignmentResponseImageData> getAssignmentImages(){
        List<AssignmentResponseImageData> assignmentModels = new ArrayList<>();
        assignmentModels.add(new AssignmentResponseImageData("This image is for Question two.", R.drawable.dashboardbg));
        assignmentModels.add(new AssignmentResponseImageData("This image is for Question one.", R.drawable.dashboaard));
        assignmentModels.add(new AssignmentResponseImageData("This image is for Question two.", R.drawable.dashboardbg));
        assignmentModels.add(new AssignmentResponseImageData("This image is for Question four.", R.drawable.dashboaard));
        assignmentModels.add(new AssignmentResponseImageData("This image is for Question five.", R.drawable.dashboardbg));
        assignmentModels.add(new AssignmentResponseImageData("This image is for Question six.", R.drawable.dashboaard));
        return assignmentModels;
    }
}
