package com.example.nasimuzzaman.roostpad.pendingRequests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 11/11/17.
 */

public class Request {

    @SerializedName("name") private String name;
    @SerializedName("id") private int id;
    @SerializedName("email") private String email;
    @SerializedName("fromDate") private String fromDate;
    @SerializedName("toDate") private String toDate;
    @SerializedName("days") private String days;
    @SerializedName("message") private String message;
    @SerializedName("seen") private boolean seen;
    @SerializedName("status") private String status;



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

    public String getDays() {
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
