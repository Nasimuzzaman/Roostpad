package com.example.nasimuzzaman.roostpad.employeeNotification;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.binjar.prefsdroid.Preference;
import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.authentication.LoginResponse;
import com.example.nasimuzzaman.roostpad.libraryPackage.BaseActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserNotificationActivity extends BaseActivity {

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

                        Toast.makeText(getApplicationContext(), "Called", Toast.LENGTH_SHORT);
                        // save notification list
                        notificationList = body.getNotificationList();

                        adapter = new UserNotificationAdapter(notificationList);
                        recyclerView.setAdapter(adapter);
                    } else Toast.makeText(getApplicationContext(), body.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserNotificationResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Not Called", Toast.LENGTH_SHORT);

                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT);
            }
        });


    }
}
