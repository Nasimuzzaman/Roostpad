package com.example.nasimuzzaman.roostpad.recoverPassword;

import com.example.nasimuzzaman.roostpad.Client;

/**
 * Created by nasimuzzaman on 11/15/17.
 */

public class RecoverPasswordClient extends Client<RecoverPasswordService> {
    @Override
    public RecoverPasswordService createService() {
        return getRetrofit().create(RecoverPasswordService.class);
    }
}
