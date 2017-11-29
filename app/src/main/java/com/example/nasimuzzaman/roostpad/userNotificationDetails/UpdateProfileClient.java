package com.example.nasimuzzaman.roostpad.userNotificationDetails;

import com.example.nasimuzzaman.roostpad.Client;

/**
 * Created by nasimuzzaman on 11/29/17.
 */

public class UpdateProfileClient extends Client<UpdateProfileService>{
    @Override
    public UpdateProfileService createService() {
        return getRetrofit().create(UpdateProfileService.class);
    }
}
