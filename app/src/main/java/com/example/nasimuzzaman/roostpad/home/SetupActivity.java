package com.example.nasimuzzaman.roostpad.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.example.nasimuzzaman.roostpad.R;

public class SetupActivity extends AppCompatActivity {

    private Button users;
    private Button contacts;
    private Button calendarEvents;
    private Button news;
    private Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        users = (Button) findViewById(R.id.users);
        contacts = (Button) findViewById(R.id.contacts);
        calendarEvents = (Button) findViewById(R.id.calendar_events);
        news = (Button) findViewById(R.id.news);
        settings = (Button) findViewById(R.id.settings);

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetupActivity.this, UsersActivity.class);
                startActivity(intent);
            }
        });

        /*contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetupActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });

        calendarEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetupActivity.this, CalendarEventsActivity.class);
                startActivity(intent);
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetupActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetupActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }
}