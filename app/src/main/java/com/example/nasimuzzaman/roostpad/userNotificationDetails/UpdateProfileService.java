package com.example.nasimuzzaman.roostpad.userNotificationDetails;

import com.example.nasimuzzaman.roostpad.updateUser.UpdateUserCredential;
import com.example.nasimuzzaman.roostpad.updateUser.UpdateUserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nasimuzzaman on 11/29/17.
 */

public interface UpdateProfileService {
    @POST("RoostpadLMS/controller/updateProfile.php")
    Call<UpdateUserResponse> updateProfile(@Body UpdateUserCredential updateUserCredential);
}
