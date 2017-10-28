package com.example.nasimuzzaman.roostpad.contacts;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 10/26/17.
 */

public class Contacts {
    @SerializedName("name") private long name;
    @SerializedName("email") private String email;
    @SerializedName("contact") private String contact;

    public long getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }
}
