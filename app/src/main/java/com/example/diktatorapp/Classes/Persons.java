package com.example.diktatorapp.Classes;

public class Persons {
    private String name;
    private String address;
    private int phone;
    private int zip;
    private int points;
    private int cpr;
    private String password;

    public Persons() {
    }

    public Persons(String name, String address, int phone, int zip, int points, int cpr, String password) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.zip = zip;
        this.points = points;
        this.cpr = cpr;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getCpr() {
        return cpr;
    }

    public void setCpr(int cpr) {
        this.cpr = cpr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
