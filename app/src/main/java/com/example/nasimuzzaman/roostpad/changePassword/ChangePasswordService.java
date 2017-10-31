package com.example.nasimuzzaman.roostpad.changePassword;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nasimuzzaman on 10/26/17.
 */

public interface ChangePasswordService {

    @POST("RoostpadLMS/controller/ChangePassword.php")
    Call<ChangePasswordResponse> changePassword(@Body ChangePasswordCredential changePasswordCredential);
}
