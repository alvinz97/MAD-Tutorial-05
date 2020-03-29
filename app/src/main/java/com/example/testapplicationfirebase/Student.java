package com.example.testapplicationfirebase;

public class Student {
    private String sId;
    private String name;
    private String address;
    private Integer contact;

    public Student() {
    }

    public String getsId() {
        return sId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Integer getContact() {
        return contact;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact(Integer contact) {
        this.contact = contact;
    }
}
