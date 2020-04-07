package com.elkanah.roemichs.db.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ClassType {
    @PrimaryKey
    @NonNull
    private String Value;
    private String Text;

    public ClassType(@NonNull String value, String text) {
        Value = value;
        Text = text;
    }

    @NonNull
    public String getValue() {
        return Value;
    }

    public void setValue(@NonNull String value) {
        Value = value;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    @NonNull
    @Override
    public String toString() {
        return Text;
    }
}
