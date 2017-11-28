package com.example.nasimuzzaman.roostpad.employeeNotification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.binjar.prefsdroid.Preference;
import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.authentication.LoginActivity;
import com.example.nasimuzzaman.roostpad.authentication.LoginResponse;
import com.example.nasimuzzaman.roostpad.changePassword.ChangePasswordActivity;
import com.example.nasimuzzaman.roostpad.home.HomeActivity;
import com.example.nasimuzzaman.roostpad.home.SetupActivity;
import com.example.nasimuzzaman.roostpad.pendingRequests.PendingRequestsActivity;

import java.util.List;

public class UserNotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    LoginResponse userInfo;
    List<UserNotification> notificationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notification);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);

        recyclerView = (RecyclerView) findViewById(R.id.rview_user_notification);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserNotificationCredential credential = new UserNotificationCredential();
        credential.setEmail(userInfo.getEmail());
        credential.setToken(userInfo.getToken());

        UserNotificationService service = new UserNotificationClient().createService();
        Call<UserNotificationResponse> call = service.showUserNotifications(credential);
        call.enqueue(new Callback<UserNotificationResponse>() {
            @Override
            public void onResponse(Call<UserNotificationResponse> call, Response<UserNotificationResponse> response) {
                UserNotificationResponse body = response.body();
                if(body != null) {
                    if(body.getStatusCode() == 200) {
                        // show success message
                        Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT);
                        // save notification list
                        notificationList = body.getNotificationList();

                        adapter = new UserNotificationAdapter(notificationList);
                        recyclerView.setAdapter(adapter);
                    } else Toast.makeText(getApplicationContext(), body.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserNotificationResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
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
