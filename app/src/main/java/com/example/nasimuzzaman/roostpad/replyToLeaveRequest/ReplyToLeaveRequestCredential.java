package com.example.nasimuzzaman.roostpad.replyToLeaveRequest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 11/12/17.
 */

public class ReplyToLeaveRequestCredential {
    @SerializedName("id") private int id;
    @SerializedName("email") private String email;
    @SerializedName("days") private int days;
    @SerializedName("status") private String status;

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
}
