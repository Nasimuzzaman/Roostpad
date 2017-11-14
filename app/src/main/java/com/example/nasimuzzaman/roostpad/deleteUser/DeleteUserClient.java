package com.example.nasimuzzaman.roostpad.deleteUser;

import com.example.nasimuzzaman.roostpad.Client;

/**
 * Created by nasimuzzaman on 11/14/17.
 */

public class DeleteUserClient extends Client<DeleteUserService> {
    @Override
    public DeleteUserService createService() {
        return getRetrofit().create(DeleteUserService.class);
    }
}
