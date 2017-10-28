package com.example.nasimuzzaman.roostpad.contacts;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nasimuzzaman on 10/26/17.
 */

public class ContactsResponse {
    @SerializedName("contactList") List<Contacts> contacts;

    public List<Contacts> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contacts> contacts) {
        this.contacts = contacts;
    }
}
