package com.example.nasimuzzaman.roostpad.employeeNotification;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 11/19/17.
 */

public class UserNotification {

    @SerializedName("id") private int id;
    @SerializedName("email") private String email;
    @SerializedName("info") private String info;
    @SerializedName("message") private String message;
    @SerializedName("days") private int days;
    @SerializedName("status") private String status;
    @SerializedName("holiday") private double holiday;

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

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
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
