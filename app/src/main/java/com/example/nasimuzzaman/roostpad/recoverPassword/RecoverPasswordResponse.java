package com.example.nasimuzzaman.roostpad.recoverPassword;

import com.example.nasimuzzaman.roostpad.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 11/15/17.
 */

public class RecoverPasswordResponse extends BaseResponse {
    @SerializedName("id") private long id;
    @SerializedName("email") private String email;
    @SerializedName("role") private String role;
    @SerializedName("designation") private String designation;
    @SerializedName("holiday") private int holiday;
    @SerializedName("gender") private String gender;
    @SerializedName("token") private String token;
    @SerializedName("name") private String name;
    @SerializedName("emailOfCTO") private String emailOfCTO;

    public String getGender() {
        return gender;
    }

    public String getCtoEmail() {
        return emailOfCTO;
    }

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

    public String isGender() {
        return gender;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }
}
