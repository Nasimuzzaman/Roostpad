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
        if(res_id == R.id.action_show_pending_requests) {
            //Toast.makeText(getApplicationContext(), "You select Edit Profile option", Toast.LENGTH_SHORT).show();
            if(userInfo.getRole().toString().equals("CTO")) {
                showPendingRequests();
            } else {
                showUserNotification();
            }
        } else if(res_id == R.id.action_change_password) {
            //Toast.makeText(getApplicationContext(), "You select Change Password option", Toast.LENGTH_SHORT).show();
            showChangePasswordDialogBox();
        } else if(res_id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logged out Successfully", Toast.LENGTH_SHORT).show();
            showLoginPage();
            Preference.remove(PrefKeys.USER_INFO);
        } else if(res_id == R.id.action_home) {
            openHomePage();
        } else if(res_id == R.id.action_setup) {
            showSetupPage();
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

    private void showUserNotification() {
        Intent intent = new Intent(this, UserNotificationActivity.class);
        startActivity(intent);
    }

    private void showPendingRequests() {
        Intent intent = new Intent(this, PendingRequestsActivity.class);
        startActivity(intent);
    }

    private void showChangePasswordDialogBox() {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    private void showSetupPage() {
        Intent intent = new Intent(this, SetupActivity.class);
        startActivity(intent);
    }

    private void showLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        finishAffinity();
        startActivity(intent);
    }

    private void openHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
