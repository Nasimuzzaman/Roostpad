package com.example.nasimuzzaman.roostpad.employeeNotification;

import com.example.nasimuzzaman.roostpad.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nasimuzzaman on 11/19/17.
 */

public class UserNotificationResponse extends BaseResponse {

    @SerializedName("notificationList") List<UserNotification> notificationList;

    public List<UserNotification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<UserNotification> notificationList) {
        this.notificationList = notificationList;
    }
}
