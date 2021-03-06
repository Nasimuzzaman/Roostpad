package com.example.nasimuzzaman.roostpad.recoverPassword;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 11/15/17.
 */

public class RecoverPasswordCredential {
    @SerializedName("email") private String email;
    @SerializedName("password") private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
