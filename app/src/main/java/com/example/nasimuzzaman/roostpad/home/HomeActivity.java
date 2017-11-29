package com.example.nasimuzzaman.roostpad.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.binjar.prefsdroid.Preference;
import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.authentication.LoginActivity;
import com.example.nasimuzzaman.roostpad.authentication.LoginResponse;
import com.example.nasimuzzaman.roostpad.changePassword.ChangePasswordActivity;
import com.example.nasimuzzaman.roostpad.employeeNotification.UserNotificationActivity;
import com.example.nasimuzzaman.roostpad.libraryPackage.BaseActivity;
import com.example.nasimuzzaman.roostpad.libraryPackage.CustomLibrary;
import com.example.nasimuzzaman.roostpad.pendingRequests.PendingRequestsActivity;
import com.example.nasimuzzaman.roostpad.request.RequestHolidayActivity;
import com.example.nasimuzzaman.roostpad.userNotificationDetails.UserDashboardActivity;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    LoginResponse userInfo;

    private Button requestAHoliday;
    private TextView availableHolidayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);

        requestAHoliday = (Button) findViewById(R.id.request_a_holiday);
        availableHolidayText = (TextView) findViewById(R.id.available_holiday_text);

        if(userInfo.getRole().toString().equals("CTO")) {
            availableHolidayText.setVisibility(View.GONE);
            requestAHoliday.setVisibility(View.GONE);
        }

        availableHolidayText.setText(userInfo.getHoliday() + " personal holidays left");

        requestAHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RequestHolidayActivity.class);
                startActivity(intent);
            }
        });
    }

}
