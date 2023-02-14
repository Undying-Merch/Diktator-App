package com.example.diktatorapp.Classes;

import java.math.BigInteger;

public class Persons {
    private String name;
    private String address;
    private String mail;
    private int phone;
    private int zip;
    private int points;
    private String cpr;
    private String userName;
    private String password;

    public Persons() {
    }

    public Persons(String name, String address, String mail, int phone, int zip, int points, String cpr, String userName, String password) {
        this.name = name;
        this.address = address;
        this.mail = mail;
        this.phone = phone;
        this.zip = zip;
        this.points = points;
        this.cpr = cpr;
        this.userName = userName;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
