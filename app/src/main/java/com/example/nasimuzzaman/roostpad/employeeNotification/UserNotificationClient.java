package com.example.nasimuzzaman.roostpad.employeeNotification;

import com.example.nasimuzzaman.roostpad.Client;

/**
 * Created by nasimuzzaman on 11/19/17.
 */

public class UserNotificationClient extends Client<UserNotificationService>{
    @Override
    public UserNotificationService createService() {
        return getRetrofit().create(UserNotificationService.class);
    }
}
