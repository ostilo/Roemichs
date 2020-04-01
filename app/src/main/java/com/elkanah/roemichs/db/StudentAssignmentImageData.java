package com.elkanah.roemichs.db;

public class StudentAssignmentImageData {
    public String imageID;
    public String assgnID;
    public  int image;

    public StudentAssignmentImageData(int image) {
        this.image = image;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getAssgnID() {
        return assgnID;
    }

    public void setAssgnID(String assgnID) {
        this.assgnID = assgnID;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
