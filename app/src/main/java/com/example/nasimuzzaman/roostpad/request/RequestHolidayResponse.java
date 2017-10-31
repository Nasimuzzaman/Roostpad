package com.example.nasimuzzaman.roostpad.request;

import com.example.nasimuzzaman.roostpad.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 10/30/17.
 */

public class RequestHolidayResponse extends BaseResponse{
    @SerializedName("email") private String email;
    @SerializedName("fromDate") private String fromDate;
    @SerializedName("toDate") private String toDate;
    @SerializedName("days") private int days;
//    @SerializedName("message") private String message;
//    @SerializedName("seen") private boolean seen;
//    @SerializedName("status") private String status;

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

//    public String getMessage() {
//        return message;
//    }

//    public boolean isSeen() {
//        return seen;
//    }
//
//    public String getStatus() {
//        return status;
//    }
}
