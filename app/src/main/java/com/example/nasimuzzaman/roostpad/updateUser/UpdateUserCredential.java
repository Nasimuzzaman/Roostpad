package com.example.nasimuzzaman.roostpad.updateUser;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 11/14/17.
 */

public class UpdateUserCredential {
    @SerializedName("name") private String name;
    @SerializedName("email") private String email;
    @SerializedName("contact") private String contact;
    @SerializedName("designation") private String designation;
    @SerializedName("role") private String role;
    @SerializedName("holiday") private int holiday;
    @SerializedName("emailOfAuthor") private String emailOfAuthor;
    @SerializedName("tokenOfAuthor") private String tokenOfAuthor;

    public String getEmailOfAuthor() {
        return emailOfAuthor;
    }

    public void setEmailOfAuthor(String emailOfAuthor) {
        this.emailOfAuthor = emailOfAuthor;
    }

    public String getTokenOfAuthor() {
        return tokenOfAuthor;
    }

    public void setTokenOfAuthor(String tokenOfAuthor) {
        this.tokenOfAuthor = tokenOfAuthor;
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

    public int getHoliday() {
        return holiday;
    }

    public void setHoliday(int holiday) {
        this.holiday = holiday;
    }
}
