package com.example.nasimuzzaman.roostpad.pendingRequests;

import com.example.nasimuzzaman.roostpad.Client;

/**
 * Created by nasimuzzaman on 11/11/17.
 */

public class PendingRequestsClient extends Client<PendingRequestsService>{
    @Override
    public PendingRequestsService createService() {
        return getRetrofit().create(PendingRequestsService.class);
    }
}
