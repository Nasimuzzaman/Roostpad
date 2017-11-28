package com.example.nasimuzzaman.roostpad.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 10/30/17.
 */

public class RequestHolidayCredential {
    @SerializedName("email") private String email;
    @SerializedName("token") private String token;
    @SerializedName("info") private String info;
    @SerializedName("message") private String message;
    @SerializedName("days") private double days;
    @SerializedName("status") private String status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getDays() {
        return days;
    }

    public void setDays(double days) {
        this.days = days;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
