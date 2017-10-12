package com.example.nasimuzzaman.roostpad.authentication;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 10/12/17.
 */

public class LoginResponse {
    @SerializedName("statusCode") private long statusCode;

    public long getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(long statusCode) {
        this.statusCode = statusCode;
    }
}
