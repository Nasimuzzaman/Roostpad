package com.example.nasimuzzaman.roostpad.contacts;

import com.example.nasimuzzaman.roostpad.Client;

/**
 * Created by nasimuzzaman on 10/26/17.
 */

public class ContactsClient extends Client<ContactsService>{
    @Override
    public ContactsService createService() {
        return getRetrofit().create(ContactsService.class);
    }
}
