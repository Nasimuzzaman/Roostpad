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
import com.example.nasimuzzaman.roostpad.libraryPackage.CustomLibrary;
import com.example.nasimuzzaman.roostpad.pendingRequests.PendingRequestsActivity;
import com.example.nasimuzzaman.roostpad.request.RequestHolidayActivity;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    LoginResponse userInfo;

    private Button requestAHoliday;
    private TextView availableHolidayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int res_id = item.getItemId();
        if(res_id == android.R.id.home) onBackPressed();

        if(res_id == R.id.action_show_pending_requests) {
            if(userInfo.getRole().toString().equals("CTO")) {
                new CustomLibrary().open(this, PendingRequestsActivity.class);
            } else {
                new CustomLibrary().open(this, UserNotificationActivity.class);
            }
        } else if(res_id == R.id.action_change_password) {
            new CustomLibrary().open(this, ChangePasswordActivity.class);
        } else if(res_id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logged out Successfully", Toast.LENGTH_SHORT).show();
            showLoginPage();
            Preference.remove(PrefKeys.USER_INFO);
        } else if(res_id == R.id.action_home) {
            new CustomLibrary().open(this, HomeActivity.class);
        } else if(res_id == R.id.action_setup) {
            new CustomLibrary().open(this, SetupActivity.class);
        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {

        if(userInfo.getRole().toString().equals("Employee")) {
            menu.getItem(1).setVisible(false);
        }

        return true;
    }

    private void showLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        finishAffinity();
        startActivity(intent);
    }
}
