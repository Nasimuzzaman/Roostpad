package com.example.nasimuzzaman.roostpad.pendingRequests;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.binjar.prefsdroid.Preference;
import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.authentication.LoginActivity;
import com.example.nasimuzzaman.roostpad.authentication.LoginResponse;
import com.example.nasimuzzaman.roostpad.changePassword.ChangePasswordActivity;
import com.example.nasimuzzaman.roostpad.contacts.ContactsResponse;
import com.example.nasimuzzaman.roostpad.employeeNotification.UserNotificationActivity;
import com.example.nasimuzzaman.roostpad.home.HomeActivity;
import com.example.nasimuzzaman.roostpad.home.SetupActivity;
import com.example.nasimuzzaman.roostpad.libraryPackage.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class PendingRequestsActivity extends BaseActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    LoginResponse userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_requests);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);

        recyclerView = (RecyclerView) findViewById(R.id.rviewPendingRequest);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        PendingRequestsCredential credential = new PendingRequestsCredential();
        credential.setEmail(userInfo.getEmail());
        credential.setToken(userInfo.getToken());

        PendingRequestsService requestsService = new PendingRequestsClient().createService();
        retrofit2.Call<PendingRequestsResponse> call = requestsService.showPendingRequests(credential);
        call.enqueue(new Callback<PendingRequestsResponse>() {
            @Override
            public void onResponse(retrofit2.Call<PendingRequestsResponse> call, Response<PendingRequestsResponse> response) {
                PendingRequestsResponse body = response.body();
                if(body != null) {
                    if(body.getStatusCode() == 200) {
                        // show success message
                        Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT);

                        //Toast.makeText(getApplicationContext(), ""+body.getRequests().size(), Toast.LENGTH_SHORT).show();

                        adapter = new RequestAdapter(body.getRequests());
                        recyclerView.setAdapter(adapter);


                    } else Toast.makeText(getApplicationContext(), body.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<PendingRequestsResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
