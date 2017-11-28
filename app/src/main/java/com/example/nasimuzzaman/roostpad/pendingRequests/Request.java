package com.example.nasimuzzaman.roostpad.pendingRequests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 11/11/17.
 */

public class Request {

    @SerializedName("name") private String name;
    @SerializedName("id") private int id;
    @SerializedName("email") private String email;
    @SerializedName("info") private String info;
    @SerializedName("message") private String message;
    @SerializedName("days") private double days;
    @SerializedName("status") private String status;
    @SerializedName("holiday") private double holiday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDays() {
        return days;
    }

    public void setDays(double days) {
        this.days = days;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getHoliday() {
        return holiday;
    }

    public void setHoliday(double holiday) {
        this.holiday = holiday;
    }
}
