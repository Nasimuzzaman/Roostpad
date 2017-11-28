package com.example.nasimuzzaman.roostpad.userNotificationDetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.nasimuzzaman.roostpad.OnHolidayRequestCountChangeCallback;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.libraryPackage.CustomLibrary;
import com.example.nasimuzzaman.roostpad.request.RequestDay;
import com.example.nasimuzzaman.roostpad.request.RequestDaysAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserNotificationDetailsActivity extends AppCompatActivity implements OnHolidayRequestCountChangeCallback{

    RecyclerView rviewUserNotification;
    RecyclerView.Adapter adapter;
    List<RequestDay> requestDays;
    CustomLibrary library;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notification_details);

        rviewUserNotification = (RecyclerView) findViewById(R.id.rviewUserNotificationDetail);
        requestDays = new ArrayList<>();
        library = new CustomLibrary();

        String info = getIntent().getStringExtra("info");
        requestDays = library.decodeInfo(info);

        rviewUserNotification.setHasFixedSize(true);
        rviewUserNotification.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RequestDaysAdapter(requestDays, this);
        rviewUserNotification.setAdapter(adapter);
    }

    @Override
    public void onHolidayRequestCountChange(boolean checked) {

    }

    @Override
    public void setRequestDays(List<RequestDay> requestDays) {

    }
}
