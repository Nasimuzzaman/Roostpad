package com.example.nasimuzzaman.roostpad.deleteUser;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 11/14/17.
 */

public class DeleteUserCredential {
    @SerializedName("email") private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
