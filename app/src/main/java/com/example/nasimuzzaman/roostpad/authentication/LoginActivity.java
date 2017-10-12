package com.example.nasimuzzaman.roostpad.authentication;

import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nasimuzzaman.roostpad.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailInput = (EditText) findViewById(R.id.email);
        passwordInput = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login);

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
                        if(body.getStatusCode() == 200) {
                            // Show home page and save user info
                            com.binjar.prefsdroid.Preference.putObject(PrefKeys.USER_INFO, body);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        // Show error message
                    }
                });
            }
        });
    }
}
