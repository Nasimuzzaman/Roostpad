package com.example.nasimuzzaman.roostpad.authentication;

import com.example.nasimuzzaman.roostpad.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 10/12/17.
 */

public class LoginResponse extends BaseResponse {
    @SerializedName("id") private long id;
    @SerializedName("email") private String email;
    @SerializedName("role") private String role;
    @SerializedName("designation") private String designation;
    @SerializedName("holiday") private int holiday;
    @SerializedName("gender") private boolean gender;
    @SerializedName("token") private String token;
    @SerializedName("name") private String name;

    public long getId() {
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

    public boolean isGender() {
        return gender;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }
}
