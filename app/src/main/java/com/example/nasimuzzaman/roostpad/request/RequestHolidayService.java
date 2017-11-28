package com.example.nasimuzzaman.roostpad.request;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nasimuzzaman on 10/30/17.
 */

public interface RequestHolidayService {
    @POST("RoostpadLMS/controller/requestHoliday.php")
    Call<RequestHolidayResponse> requestHoliday(@Body RequestHolidayCredential requestHolidayCredential);
}
