package com.example.diktatorapp.Classes;

public class Settings {
    private int pointThreshold;
    private int startUpPoints;

    public Settings() {
    }

    public Settings(int pointThreshold, int startUpPoints) {
        this.pointThreshold = pointThreshold;
        this.startUpPoints = startUpPoints;
    }

    public int getPointThreshold() {
        return pointThreshold;
    }

    public int getStartUpPoints() {
        return startUpPoints;
    }

    public void setPointThreshold(int pointThreshold) {
        this.pointThreshold = pointThreshold;
    }

    public void setStartUpPoints(int startUpPoints) {
        this.startUpPoints = startUpPoints;
    }
}
