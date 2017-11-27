package com.example.nasimuzzaman.roostpad.pendingRequests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 11/27/17.
 */

public class PendingRequestsCredential {
    @SerializedName("email") private String email;
    @SerializedName("token") private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
