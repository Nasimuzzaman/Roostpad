package com.example.nasimuzzaman.roostpad.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.authentication.LoginCredential;
import com.example.nasimuzzaman.roostpad.changePassword.ChangePasswordClient;
import com.example.nasimuzzaman.roostpad.changePassword.ChangePasswordCredential;
import com.example.nasimuzzaman.roostpad.changePassword.ChangePasswordResponse;
import com.example.nasimuzzaman.roostpad.changePassword.ChangePasswordService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {


    Button submitChangePassword, cancelChangePassword;
    EditText currentPasswordInput, newPasswordInput, reTypeNewPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        submitChangePassword = (Button) findViewById(R.id.btn_change_password);
        cancelChangePassword = (Button) findViewById(R.id.btn_cancel_change_password);

        currentPasswordInput = (EditText) findViewById(R.id.current_passwrod);
        newPasswordInput = (EditText) findViewById(R.id.new_password);
        reTypeNewPasswordInput = (EditText) findViewById(R.id.re_type_new_password);

        submitChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPassword = currentPasswordInput.getText().toString();
                String newPassword = newPasswordInput.getText().toString();

                ChangePasswordCredential credential = new ChangePasswordCredential();
                LoginCredential loginCredential = new LoginCredential();

                credential.setEmail(loginCredential.getEmail());
                credential.setPassword(currentPassword);
                credential.setNewPassword(newPassword);

                ChangePasswordService changePasswordService = new ChangePasswordClient().createService();
                Call<ChangePasswordResponse> call = changePasswordService.changePassword(credential);

                call.enqueue(new Callback<ChangePasswordResponse>() {
                    @Override
                    public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                        ChangePasswordResponse body = response.body();

                        if(body != null) {
                            if(body.getStatusCode() == 200) {
                                // save user info
                                com.binjar.prefsdroid.Preference.putObject(PrefKeys.USER_INFO, body);
                                // show success message
                                Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT);
                                // go to setup page
                                showHomePage();
                            } else Toast.makeText(getApplicationContext(), body.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        cancelChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePasswordActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        /*submitChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPassword = currentPasswordInput.getText().toString();
                String newPassword = newPasswordInput.getText().toString();

                ChangePasswordCredential credential = new ChangePasswordCredential();
                LoginCredential loginCredential = new LoginCredential();

                credential.setEmail(loginCredential.getEmail());
                credential.setPassword(currentPassword);
                credential.setNewPassword(newPassword);

                ChangePasswordService changePasswordService = new ChangePasswordClient().createService();
                Call<ChangePasswordResponse> call = changePasswordService.changePassword(credential);

                call.enqueue(new Callback<ChangePasswordResponse>() {
                    @Override
                    public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                        ChangePasswordResponse body = response.body();

                        if(body != null) {
                            if(body.getStatusCode() == 200) {
                                // save user info
                                com.binjar.prefsdroid.Preference.putObject(PrefKeys.USER_INFO, body);
                                // show success message
                                Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT);
                                // go to setup page
                                showHomePage();
                            } else Toast.makeText(getApplicationContext(), body.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });*/
    }

    private void showHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
