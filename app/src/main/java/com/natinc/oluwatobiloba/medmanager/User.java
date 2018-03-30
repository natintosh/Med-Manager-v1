package com.natinc.oluwatobiloba.medmanager;

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

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProfileUrl() {
        return profileUrl;
    }
}
