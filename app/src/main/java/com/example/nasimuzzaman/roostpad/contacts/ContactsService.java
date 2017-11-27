package com.example.nasimuzzaman.roostpad.contacts;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by nasimuzzaman on 10/26/17.
 */

public interface ContactsService {

    @POST("RoostpadLMS/controller/ShowContacts.php")
    Call<ContactsResponse> showContacts(@Body ContactCredential contactCredential);
}
