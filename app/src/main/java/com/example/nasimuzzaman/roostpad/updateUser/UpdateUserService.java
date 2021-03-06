package com.example.nasimuzzaman.roostpad.updateUser;

import com.example.nasimuzzaman.roostpad.services.AddUserResponse;
import com.example.nasimuzzaman.roostpad.services.UserCredential;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nasimuzzaman on 11/14/17.
 */

public interface UpdateUserService {

    @POST("RoostpadLMS/controller/UpdateUser.php")
    Call<UpdateUserResponse> updateUser(@Body UpdateUserCredential updateUserCredential);
}
