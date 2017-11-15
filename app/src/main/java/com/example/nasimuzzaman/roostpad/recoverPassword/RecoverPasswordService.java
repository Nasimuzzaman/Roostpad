package com.example.nasimuzzaman.roostpad.recoverPassword;

import com.example.nasimuzzaman.roostpad.authentication.LoginCredential;
import com.example.nasimuzzaman.roostpad.authentication.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nasimuzzaman on 11/15/17.
 */

public interface RecoverPasswordService {
    @POST("RoostpadLMS/controller/RecoverPassword.php")
    Call<RecoverPasswordResponse> recoverPassword(@Body RecoverPasswordCredential recoverPasswordCredential);
}
