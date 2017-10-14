package com.example.nasimuzzaman.roostpad.authentication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nasimuzzaman on 10/12/17.
 */

public interface AuthenticationService {
    @POST("RoostpadLMS/controller/login.php")
    Call<LoginResponse> login(@Body LoginCredential loginCredential);
}
