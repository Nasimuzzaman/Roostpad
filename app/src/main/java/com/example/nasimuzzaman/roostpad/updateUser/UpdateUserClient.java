package com.example.nasimuzzaman.roostpad.updateUser;

import com.example.nasimuzzaman.roostpad.Client;

/**
 * Created by nasimuzzaman on 11/14/17.
 */

public class UpdateUserClient extends Client<UpdateUserService> {
    @Override
    public UpdateUserService createService() {
        return getRetrofit().create(UpdateUserService.class);
    }
}
