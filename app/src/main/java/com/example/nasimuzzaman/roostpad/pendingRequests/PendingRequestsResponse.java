package com.example.nasimuzzaman.roostpad.pendingRequests;

import com.example.nasimuzzaman.roostpad.contacts.Contacts;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nasimuzzaman on 11/11/17.
 */

public class PendingRequestsResponse {

    @SerializedName("requestsList") List<Request> requests;

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}
