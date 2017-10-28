package com.example.nasimuzzaman.roostpad.contacts;

import com.example.nasimuzzaman.roostpad.Client;

/**
 * Created by nasimuzzaman on 10/26/17.
 */

public class ShowContactsClient extends Client<ShowContactsService>{
    @Override
    public ShowContactsService createService() {
        return getRetrofit().create(ShowContactsService.class);
    }
}
