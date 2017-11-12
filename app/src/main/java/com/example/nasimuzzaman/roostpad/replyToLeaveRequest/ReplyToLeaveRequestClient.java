package com.example.nasimuzzaman.roostpad.replyToLeaveRequest;

import com.example.nasimuzzaman.roostpad.Client;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nasimuzzaman on 11/12/17.
 */

public class ReplyToLeaveRequestClient extends Client<ReplyToLeaveRequestService>{


    @Override
    public ReplyToLeaveRequestService createService() {
        return getRetrofit().create(ReplyToLeaveRequestService.class);
    }
}
