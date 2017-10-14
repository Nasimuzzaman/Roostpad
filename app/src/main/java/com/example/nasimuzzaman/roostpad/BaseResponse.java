package com.example.nasimuzzaman.roostpad;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 10/14/17.
 */

public class BaseResponse {
    @SerializedName("statusCode") private long statusCode;
    @SerializedName("error") private String error;
    @SerializedName("message") private String message;

    public long getStatusCode() {
        return statusCode;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
