package com.example.nasimuzzaman.roostpad.pendingRequests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 11/11/17.
 */

public class Request {

    @SerializedName("name") private String name;
    @SerializedName("id") private int id;
    @SerializedName("email") private String email;
    @SerializedName("from_date") private String fromDate;
    @SerializedName("to_date") private String toDate;
    @SerializedName("days") private int days;
    @SerializedName("message") private String message;
    @SerializedName("seen") private boolean seen;
    @SerializedName("status") private String status;


    public Request(String name, String fromDate, String toDate, int days) {
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.days = days;
    }

    public String getName() {
        return name;
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
