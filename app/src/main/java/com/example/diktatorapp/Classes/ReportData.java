package com.example.diktatorapp.Classes;


import java.util.Date;

public class ReportData {

    private String suspect;
    private String accusser;
    private String report;
    private String time;

    public ReportData() {
    }

    public ReportData(String suspect, String accusser, String report, String time) {
        this.suspect = suspect;
        this.accusser = accusser;
        this.report = report;
        this.time = time;
    }

    public String getSuspect() {
        return suspect;
    }

    public String getAccusser() {
        return accusser;
    }

    public String getReport() {
        return report;
    }

    public String getTime() {
        return time;
    }
}
