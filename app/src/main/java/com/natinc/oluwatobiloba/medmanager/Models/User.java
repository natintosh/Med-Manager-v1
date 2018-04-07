package com.natinc.oluwatobiloba.medmanager.Models;

/**
 * Created by oluwatobiloba on 3/30/18.
 */

public class User {

    private String name;
    private String email;
    private String gender;
    private String phoneNumber;
    private String profileUrl;

    // Default empty contructor for firebase

    public User() {
    }


    public User(String name, String email, String gender, String phoneNumber, String profileUrl) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.profileUrl = profileUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
