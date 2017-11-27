package com.example.nasimuzzaman.roostpad.deleteUser;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 11/14/17.
 */

public class DeleteUserCredential {
    @SerializedName("email") private String email;
    @SerializedName("emailOfAuthor") private String emailOfAuthor;
    @SerializedName("tokenOfAuthor") private String tokenOfAuthor;

    public String getEmailOfAuthor() {
        return emailOfAuthor;
    }

    public void setEmailOfAuthor(String emailOfAuthor) {
        this.emailOfAuthor = emailOfAuthor;
    }

    public String getTokenOfAuthor() {
        return tokenOfAuthor;
    }

    public void setTokenOfAuthor(String tokenOfAuthor) {
        this.tokenOfAuthor = tokenOfAuthor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
