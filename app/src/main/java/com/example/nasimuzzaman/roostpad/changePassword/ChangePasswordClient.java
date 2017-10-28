package com.example.nasimuzzaman.roostpad.changePassword;

import com.example.nasimuzzaman.roostpad.Client;

/**
 * Created by nasimuzzaman on 10/26/17.
 */

public class ChangePasswordClient extends Client<ChangePasswordService> {

    @Override
    public ChangePasswordService createService() {
        return getRetrofit().create(ChangePasswordService.class);
    }
}
