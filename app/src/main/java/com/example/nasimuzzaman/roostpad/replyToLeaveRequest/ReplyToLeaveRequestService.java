package com.example.nasimuzzaman.roostpad.replyToLeaveRequest;

import com.example.nasimuzzaman.roostpad.request.RequestHolidayCredential;
import com.example.nasimuzzaman.roostpad.request.RequestHolidayResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nasimuzzaman on 11/12/17.
 */

public interface ReplyToLeaveRequestService {
    @POST("RoostpadLMS/controller/UpdateDBAfterReply.php")
    Call<ReplyToLeaveRequestResponse> replyToLeaveRequest(@Body ReplyToLeaveRequestCredential replyToLeaveRequestCredential);
}
