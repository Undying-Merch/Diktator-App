package com.example.diktatorapp.Classes;

public class Database {

    private String token = "2183c1ad82ed9ec825dab900e3d78378b3c61f5e";

    private String postPerson = "http://10.130.54.25:8000/data/personcreate/";
    private String getCPR = "http://10.130.54.25:8000/data/cpr/?format=json";
    private String getListe = "http://10.130.54.25:8000/data/liste/?format=json";
    private String getPerson = "http://10.130.54.25:8000/data/personappalt/"
            ;
    private String getSettings = "http://10.130.54.25:8000/data/settings/?format=json";
    private String getSusReport = "http://10.130.54.25:8000/data/rjsuspect/";

    private String createZIP = "http://10.130.54.25:8000/data/postnummercreate/";


    public String getCreateZIP() {
        return createZIP;
    }

    public String getToken() {
        return token;
    }

    public String getPostPerson() {
        return postPerson;
    }

    public String getGetCPR() {
        return getCPR;
    }

    public String getGetListe() {
        return getListe;
    }

    public String getGetPerson() {
        return getPerson;
    }

    public String getGetSettings() {
        return getSettings;
    }

    public String getGetSusReport() {
        return getSusReport;
    }
}
