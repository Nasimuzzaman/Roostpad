package com.example.nasimuzzaman.roostpad.addUser;

import com.example.nasimuzzaman.roostpad.Client;

/**
 * Created by nasimuzzaman on 10/23/17.
 */

public class AddUserClient extends Client<AddUserService>{

    @Override
    public AddUserService createService() {
        return getRetrofit().create(AddUserService.class);
    }
}
