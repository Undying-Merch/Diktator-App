package com.example.diktatorapp.Classes;

public class Database {

    private String token = "10221976ae7eebb749b62cb74de527cd6500697a";

    private String postPerson = "http://10.130.54.25:8000/data/personcreate/";
    private String getCPR = "http://10.130.54.25:8000/data/cpr/?format=json";
    private String getListe = "http://10.130.54.25:8000/data/liste/?format=json";
    private String getPerson = "http://10.130.54.25:8000/data/personappalt/";
    private String getSettings = "http://10.130.54.25:8000/data/settings/?format=json";


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
}
