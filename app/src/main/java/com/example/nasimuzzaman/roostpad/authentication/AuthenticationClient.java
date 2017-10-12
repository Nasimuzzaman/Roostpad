package com.example.nasimuzzaman.roostpad.authentication;

/**
 * Created by nasimuzzaman on 10/12/17.
 */

public class AuthenticationClient extends Client<AuthenticationService> {
    @Override
    public AuthenticationService createService() {
        return getRetrofit().create(AuthenticationService.class);
    }
}
