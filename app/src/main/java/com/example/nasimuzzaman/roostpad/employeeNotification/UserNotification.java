package com.example.nasimuzzaman.roostpad.employeeNotification;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 11/19/17.
 */

public class UserNotification {

    @SerializedName("id") private int id;
    @SerializedName("email") private String email;
    @SerializedName("from_date") private String fromDate;
    @SerializedName("to_date") private String toDate;
    @SerializedName("days") private int days;
    @SerializedName("message") private String message;
    @SerializedName("seen") private boolean seen;
    @SerializedName("status") private String status;
    @SerializedName("holiday") private int holiday;

    public int getHoliday() {
        return holiday;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public int getDays() {
        return days;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSeen() {
        return seen;
    }

    public String getStatus() {
        return status;
    }
}
