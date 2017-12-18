package com.example.nasimuzzaman.roostpad.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.binjar.prefsdroid.Preference;
import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.authentication.AuthenticationClient;
import com.example.nasimuzzaman.roostpad.authentication.AuthenticationService;
import com.example.nasimuzzaman.roostpad.authentication.LoginCredential;
import com.example.nasimuzzaman.roostpad.authentication.LoginResponse;
import com.example.nasimuzzaman.roostpad.libraryPackage.BaseActivity;
import com.example.nasimuzzaman.roostpad.request.RequestHolidayActivity;
import com.rey.material.widget.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    LoginResponse userInfo;

    private FloatingActionButton requestAHoliday;
    private TextView availableHolidayText, holidatLeftText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);

        updateUserInfo();

        requestAHoliday = (FloatingActionButton) findViewById(R.id.request_a_holiday);
        availableHolidayText = (TextView) findViewById(R.id.available_holiday_text);
        holidatLeftText = (TextView) findViewById(R.id.holiday_left_text);

        if(userInfo.getRole().toString().equals("CTO")) {
            availableHolidayText.setVisibility(View.GONE);
            holidatLeftText.setVisibility(View.GONE);
            requestAHoliday.setVisibility(View.GONE);
        }

        double decimal = userInfo.getHoliday();
        String holidayText = decimal + "";
        if ((int) decimal == decimal) holidayText = (int) decimal + "";

        availableHolidayText.setText(holidayText);

        requestAHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RequestHolidayActivity.class);
                startActivity(intent);
            }
        });
    }

    public void updateUserInfo() {

        String email = userInfo.getEmail().toString();
        String password = userInfo.getPassword().toString();
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

                        userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);
                    } else Toast.makeText(getApplicationContext(), body.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);            }
        });
    }

}
