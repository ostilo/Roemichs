package com.elkanah.roemichs.db.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SessionModel {

  @PrimaryKey
  @NonNull
  private String Value;
  private String Text;

  public SessionModel(@NonNull String Value, String Text) {
    this.Value = Value;
    this.Text = Text;
  }

  @NonNull
  public String getValue() {
    return Value;
  }

  public void setValue(@NonNull String Value) {
    this.Value = Value;
  }

  public String getText() {
    return Text;
  }

  public void setText(String Text) {
    this.Text = Text;
  }

  @NonNull
  @Override
  public String toString() {
    return Text;
  }

}
