package com.example.nasimuzzaman.roostpad.changePassword;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.binjar.prefsdroid.Preference;
import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.authentication.LoginResponse;
import com.example.nasimuzzaman.roostpad.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {


    Button submitChangePassword, cancelChangePassword;
    EditText currentPasswordInput, newPasswordInput, reNewPasswordInput;
    LoginResponse userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);

        submitChangePassword = (Button) findViewById(R.id.btn_change_password);
        cancelChangePassword = (Button) findViewById(R.id.btn_cancel_change_password);

        currentPasswordInput = (EditText) findViewById(R.id.current_passwrod);
        newPasswordInput = (EditText) findViewById(R.id.new_password);
        reNewPasswordInput = (EditText) findViewById(R.id.re_type_new_password);


        submitChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentPasswordInput.getText().toString().equals("")) {
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    currentPasswordInput.setError("Please enter current password");
                    currentPasswordInput.startAnimation(shake);
                } else if(!currentPasswordInput.getText().toString().equals(userInfo.getPassword())) {
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    currentPasswordInput.setError("Current password don't match");
                    currentPasswordInput.startAnimation(shake);
                } else if(newPasswordInput.getText().toString().length() < 6) {
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    newPasswordInput.setError("Password length must be at least 6");
                    newPasswordInput.startAnimation(shake);
                } else if (newPasswordInput.getText().toString().equals(reNewPasswordInput.getText().toString())) {
                    String currentPassword = currentPasswordInput.getText().toString();
                    String newPassword = newPasswordInput.getText().toString();

                    ChangePasswordCredential credential = new ChangePasswordCredential();
                    credential.setEmail(userInfo.getEmail());
                    credential.setPassword(currentPassword);
                    credential.setNewPassword(newPassword);

                    ChangePasswordService changePasswordService = new ChangePasswordClient().createService();
                    Call<ChangePasswordResponse> call = changePasswordService.changePassword(credential);

                    call.enqueue(new Callback<ChangePasswordResponse>() {
                        @Override
                        public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                            ChangePasswordResponse body = response.body();

                            if (body != null) {
                                if (body.getStatusCode() == 200) {
                                    // show success message
                                    Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT);
                                    Toast.makeText(getApplicationContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
                                    // go to setup page
                                    showHomePage();
                                } else
                                    Toast.makeText(getApplicationContext(), body.getError(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    reNewPasswordInput.setError("Password don't match");
                    reNewPasswordInput.startAnimation(shake);
                }


            }
        });

        cancelChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePasswordActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int res_id = item.getItemId();
        if(res_id == android.R.id.home) onBackPressed();

        return true;
    }

    private void showHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
