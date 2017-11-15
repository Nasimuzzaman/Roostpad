package com.example.nasimuzzaman.roostpad.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.binjar.prefsdroid.Preference;
import com.example.nasimuzzaman.roostpad.libraryPackage.CustomLibrary;
import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.gmail.GMailSender;
import com.example.nasimuzzaman.roostpad.home.HomeActivity;
import com.example.nasimuzzaman.roostpad.recoverPassword.RecoverPasswordClient;
import com.example.nasimuzzaman.roostpad.recoverPassword.RecoverPasswordCredential;
import com.example.nasimuzzaman.roostpad.recoverPassword.RecoverPasswordResponse;
import com.example.nasimuzzaman.roostpad.recoverPassword.RecoverPasswordService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;
    Button loginBtn;
    Button btnForgotPassword, btnRecoverPassword;
    EditText recoveryEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class) != null) {
            openHomePage();
        }
        emailInput = (EditText) findViewById(R.id.email);
        passwordInput = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login);
        btnForgotPassword = (Button) findViewById(R.id.btn_forgot_password);
        btnRecoverPassword = (Button) findViewById(R.id.btn_recover_password);
        recoveryEmail = (EditText) findViewById(R.id.recover_email);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                LoginCredential credential = new LoginCredential();
                credential.setEmail(email);
                credential.setPassword(password);
                AuthenticationService service = new AuthenticationClient().createService();
                Call<LoginResponse> call = service.login(credential);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse body = response.body();
                        if(body != null) {
                            if (body.getStatusCode() == 200) {
                                // save user info
                                com.binjar.prefsdroid.Preference.putObject(PrefKeys.USER_INFO, body);
                                // show login success message
                                Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT).show();
                                // goto next page
                                openHomePage();
                            } else Toast.makeText(getApplicationContext(), body.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoveryEmail.setVisibility(View.VISIBLE);
                recoveryEmail.setEnabled(true);
                recoveryEmail.setClickable(true);
                btnRecoverPassword.setVisibility(View.VISIBLE);
            }
        });

        btnRecoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RecoverPasswordCredential credential = new RecoverPasswordCredential();
                credential.setEmail(recoveryEmail.getText().toString());
                credential.setPassword(new CustomLibrary().getSaltString());

                RecoverPasswordService service = new RecoverPasswordClient().createService();
                Call<RecoverPasswordResponse> call = service.recoverPassword(credential);
                call.enqueue(new Callback<RecoverPasswordResponse>() {
                    @Override
                    public void onResponse(Call<RecoverPasswordResponse> call, Response<RecoverPasswordResponse> response) {
                        RecoverPasswordResponse body = response.body();
                        if (body.getStatusCode() == 200) {
                            // show login success message
                            Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT).show();
                            // goto next page
                            sendNewPassword(credential.getEmail(), credential.getPassword());
                            showLoginPage();
                        } else Toast.makeText(getApplicationContext(), body.getError(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<RecoverPasswordResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "This email does not exist in the server!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //using javax.mail
    private void sendNewPassword(final String recipientsEmail, final String newPassword) {
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("roostpaddb@gmail.com", "Kaz123Mig");
                    sender.sendMail("Notification from ROOSTPAD",
                            "Your password has been reset as "+ newPassword,
                            "roostpaddb@gmail.com",
                            recipientsEmail);
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }

    private void openHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void showLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        finishAffinity();
        startActivity(intent);
    }
}
