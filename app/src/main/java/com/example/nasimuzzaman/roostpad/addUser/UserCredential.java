package com.example.nasimuzzaman.roostpad.addUser;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 10/23/17.
 */

public class UserCredential {
    @SerializedName("name") private String name;
    @SerializedName("email") private String email;
    @SerializedName("emailOfAdder") private String emailOfAdder;
    @SerializedName("tokenOfAdder") private String tokenOfAdder;
    @SerializedName("contact") private String contact;
    @SerializedName("password") private String password;
    @SerializedName("designation") private String designation;
    @SerializedName("role") private String role;
    @SerializedName("holiday") private double holiday;
    @SerializedName("gender") private String gender;

    public String getEmailOfAdder() {
        return emailOfAdder;
    }

    public void setEmailOfAdder(String emailOfAdder) {
        this.emailOfAdder = emailOfAdder;
    }

    public String getTokenOfAdder() {
        return tokenOfAdder;
    }

    public void setTokenOfAdder(String tokenOfAdder) {
        this.tokenOfAdder = tokenOfAdder;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getHoliday() {
        return holiday;
    }

    public void setHoliday(double holiday) {
        this.holiday = holiday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
