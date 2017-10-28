package com.example.nasimuzzaman.roostpad.changePassword;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 10/26/17.
 */

public class ChangePasswordCredential {
    @SerializedName("email") private String email;
    @SerializedName("password") private String password;
    @SerializedName("newPassword") private String newPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
