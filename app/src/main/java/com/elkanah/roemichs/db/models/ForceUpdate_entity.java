package com.elkanah.roemichs.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ForceUpdate_entity implements Parcelable {
  @PrimaryKey
  private int UpdateID;
  private double Version;
  private String UpdateType;
  private int UpdateStatus; // // 1 is (there is update), 2 is (it has been downloaded), 3 is (it has been installed)
  private String Link;

  public ForceUpdate_entity(int UpdateID, double Version, String UpdateType, int UpdateStatus, String Link) {
    this.UpdateID = UpdateID;
    this.Version = Version;
    this.UpdateType = UpdateType;
    this.UpdateStatus = UpdateStatus;
    this.Link = Link;
  }

  protected ForceUpdate_entity(Parcel in) {
    UpdateID = in.readInt();
    Version = in.readDouble();
    UpdateType = in.readString();
    UpdateStatus = in.readInt();
    Link = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(UpdateID);
    dest.writeDouble(Version);
    dest.writeString(UpdateType);
    dest.writeInt(UpdateStatus);
    dest.writeString(Link);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ForceUpdate_entity> CREATOR = new Creator<ForceUpdate_entity>() {
    @Override
    public ForceUpdate_entity createFromParcel(Parcel in) {
      return new ForceUpdate_entity(in);
    }

    @Override
    public ForceUpdate_entity[] newArray(int size) {
      return new ForceUpdate_entity[size];
    }
  };

  public int getUpdateID() {
    return UpdateID;
  }

  public void setUpdateID(int updateID) {
    UpdateID = updateID;
  }

  public double getVersion() {
    return Version;
  }

  public void setVersion(double version) {
    Version = version;
  }

  public String getUpdateType() {
    return UpdateType;
  }

  public void setUpdateType(String updateType) {
    UpdateType = updateType;
  }

  public int getUpdateStatus() {
    return UpdateStatus;
  }

  public void setUpdateStatus(int updateStatus) {
    UpdateStatus = updateStatus;
  }

  public String getLink() {
    return Link;
  }

  public void setLink(String link) {
    Link = link;
  }
}
