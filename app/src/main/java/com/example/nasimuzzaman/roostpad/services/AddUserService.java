package com.example.nasimuzzaman.roostpad.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nasimuzzaman on 10/23/17.
 */

public interface AddUserService {

    @POST("RoostpadLMS/controller/AddUser.php")
    Call<AddUserResponse> addUser(@Body UserCredential userCredential);

}
