package com.example.nasimuzzaman.roostpad.deleteUser;

import com.example.nasimuzzaman.roostpad.updateUser.UpdateUserCredential;
import com.example.nasimuzzaman.roostpad.updateUser.UpdateUserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nasimuzzaman on 11/14/17.
 */

public interface DeleteUserService {

    @POST("RoostpadLMS/controller/DeleteUser.php")
    Call<DeleteUserResponse> updateUser(@Body DeleteUserCredential deleteUserCredential);
}
