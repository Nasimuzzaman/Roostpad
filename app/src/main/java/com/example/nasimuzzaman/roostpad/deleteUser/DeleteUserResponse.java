package com.example.nasimuzzaman.roostpad.deleteUser;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 11/14/17.
 */

public class DeleteUserResponse {

    @SerializedName("id") private int id;
    @SerializedName("email") private String email;
    @SerializedName("role") private String role;
    @SerializedName("designation") private String designation;
    @SerializedName("holiday") private int holiday;
    @SerializedName("gender") private String gender;
    @SerializedName("token") private String token;
    @SerializedName("name") private String name;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getDesignation() {
        return designation;
    }

    public int getHoliday() {
        return holiday;
    }

    public String getGender() {
        return gender;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }
}
