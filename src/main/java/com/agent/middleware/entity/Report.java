package com.agent.middleware.entity;

import java.util.Date;


public class Report {
    private int id;
    private String firstname;
    private String lastname;
    private String street;
    private String city;
    private Date date;

    public Report() {

    }

    public Report(int id, String firstname, String lastname, String street, String city, Date date) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.street = street;
        this.city = city;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
