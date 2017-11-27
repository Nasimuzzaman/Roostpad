package com.example.nasimuzzaman.roostpad.pendingRequests;

import com.example.nasimuzzaman.roostpad.request.RequestHolidayCredential;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by nasimuzzaman on 11/11/17.
 */

public interface PendingRequestsService {

    @POST("RoostpadLMS/controller/GetPendingLeaveRequests.php")
    Call<PendingRequestsResponse> showPendingRequests(@Body PendingRequestsCredential pendingRequestsCredential);
}
