package com.example.nasimuzzaman.roostpad.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.nasimuzzaman.roostpad.R;

public class UsersActivity extends AppCompatActivity {

    private Button users;
    private Button contacts;
    private Button calendarEvents;
    private Button news;
    private Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
    }
}
