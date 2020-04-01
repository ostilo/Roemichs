package com.elkanah.roemichs.ui.model;

public class NoticeBoardModel {
    public String title;
    public String date;
    public String announcer;
    public String description;

    public NoticeBoardModel(String title, String date, String announcer, String description) {
        this.title = title;
        this.date = date;
        this.announcer = announcer;
        this.description = description;
    }
}
