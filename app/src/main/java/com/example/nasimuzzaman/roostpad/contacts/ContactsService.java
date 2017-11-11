package com.example.nasimuzzaman.roostpad.contacts;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nasimuzzaman on 10/26/17.
 */

public interface ContactsService {

    @GET("RoostpadLMS/controller/ShowContacts.php")
    Call<ContactsResponse> showContacts();
}
