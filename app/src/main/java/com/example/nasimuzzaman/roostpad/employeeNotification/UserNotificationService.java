package com.example.nasimuzzaman.roostpad.employeeNotification;

import com.example.nasimuzzaman.roostpad.pendingRequests.PendingRequestsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by nasimuzzaman on 11/19/17.
 */

public interface UserNotificationService {

    @POST("RoostpadLMS/controller/GetEmployeeNotification.php")
    Call<UserNotificationResponse> showUserNotifications(@Body UserNotificationCredential credential);
}
