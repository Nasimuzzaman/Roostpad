package com.example.nasimuzzaman.roostpad.employeeNotification;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 11/19/17.
 */

public class UserNotificationCredential {
    @SerializedName("email") private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
