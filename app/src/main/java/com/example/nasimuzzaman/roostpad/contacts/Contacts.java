package com.example.nasimuzzaman.roostpad.contacts;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 10/26/17.
 */

public class Contacts {
    @SerializedName("name") private String name;
    @SerializedName("id") private int id;
    @SerializedName("email") private String email;
    @SerializedName("contact") private String contact;
    @SerializedName("days") private int days;
    @SerializedName("designation") private String designation;
    @SerializedName("role") private String role;
    @SerializedName("holiday") private int holiday;

    public int getId() {
        return id;
    }

    public int getDays() {
        return days;
    }

    public String getDesignation() {
        return designation;
    }

    public int getHoliday() {
        return holiday;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }
}
