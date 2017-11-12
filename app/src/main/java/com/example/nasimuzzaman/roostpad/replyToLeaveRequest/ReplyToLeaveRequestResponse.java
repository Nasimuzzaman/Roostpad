package com.example.nasimuzzaman.roostpad.replyToLeaveRequest;

import com.example.nasimuzzaman.roostpad.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 11/12/17.
 */

public class ReplyToLeaveRequestResponse extends BaseResponse{
    @SerializedName("id") private int id;
    @SerializedName("email") private String email;
    @SerializedName("days") private int days;
    @SerializedName("status") private String status;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public int getDays() {
        return days;
    }

    public String getStatus() {
        return status;
    }
}
