package com.example.nasimuzzaman.roostpad.request;

import com.example.nasimuzzaman.roostpad.Client;

/**
 * Created by nasimuzzaman on 10/30/17.
 */

public class RequestHolidayClient extends Client<RequestHolidayService>{

    @Override
    public RequestHolidayService createService() {
        return getRetrofit().create(RequestHolidayService.class);
    }
}
