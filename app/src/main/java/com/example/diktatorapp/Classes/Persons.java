package com.example.diktatorapp.Classes;

import java.math.BigInteger;

public class Persons {
    private int id;
    private String name;
    private String address;
    private String mail;
    private int phone;
    private int zip;
    private int worksector;
    private int points;
    private String cpr;
    private String userName;
    private String password;

    public Persons() {
    }

    public Persons(int id, String name, String address, String mail, int phone, int zip, int worksector, int points, String cpr, String userName, String password) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mail = mail;
        this.phone = phone;
        this.zip = zip;
        this.worksector = worksector;
        this.points = points;
        this.cpr = cpr;
        this.userName = userName;
        this.password = password;
    }

    public int getWorksector() {
        return worksector;
    }

    public void setWorksector(int worksector) {
        this.worksector = worksector;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
