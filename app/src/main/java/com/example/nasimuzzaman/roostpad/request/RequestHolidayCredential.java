package com.example.nasimuzzaman.roostpad.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 10/30/17.
 */

public class RequestHolidayCredential {
    @SerializedName("email") private String email;
    @SerializedName("fromDate") private String fromDate;
    @SerializedName("toDate") private String toDate;
    @SerializedName("days") private int days;
    @SerializedName("message") private String message;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RequestHolidayCredential{" +
                "email='" + email + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", days=" + days +
                ", message='" + message + '\'' +
                '}';
    }
}
