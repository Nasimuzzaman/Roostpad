package com.example.nasimuzzaman.roostpad.pendingRequests;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nasimuzzaman on 11/11/17.
 */

public interface PendingRequestsService {

    @GET("RoostpadLMS/controller/GetPendingLeaveRequests.php")
    Call<PendingRequestsResponse> showPendingRequests();
}
