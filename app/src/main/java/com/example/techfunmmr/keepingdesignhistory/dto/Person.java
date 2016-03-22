package com.example.techfunmmr.keepingdesignhistory.dto;

import android.graphics.Bitmap;

/**
 * Created by techfunmmr on 2/15/2016.
 */
public class Person {

    private int personId;

    private Bitmap personPhoto;

    private String personName;

    private String personPhoneNo;

    private String personEmail;

    private String personAddress;

    public Person() {}

    public Person(int personId, Bitmap personPhoto, String personName, String personPhoneNo, String personEmail, String personAddress) {
        this.personId = personId;
        this.personPhoto = personPhoto;
        this.personName = personName;
        this.personPhoneNo = personPhoneNo;
        this.personEmail = personEmail;
        this.personAddress = personAddress;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public Bitmap getPersonPhoto() {
        return personPhoto;
    }

    public void setPersonPhoto(Bitmap personPhoto) {
        this.personPhoto = personPhoto;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonPhoneNo() {
        return personPhoneNo;
    }

    public void setPersonPhoneNo(String personPhoneNo) {
        this.personPhoneNo = personPhoneNo;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getPersonAddress() {
        return personAddress;
    }

    public void setPersonAddress(String personAddress) {
        this.personAddress = personAddress;
    }
}
