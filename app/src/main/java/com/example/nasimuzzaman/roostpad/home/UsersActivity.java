package com.example.nasimuzzaman.roostpad.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.example.nasimuzzaman.roostpad.R;

public class UsersActivity extends AppCompatActivity {

    private Button users;
    private Button contacts;
    private Button addNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        users = (Button) findViewById(R.id.users);
        contacts = (Button) findViewById(R.id.contacts);
        addNewUser = (Button) findViewById(R.id.add_new_user);

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsersActivity.this, UsersActivity.class);
                startActivity(intent);
            }
        });

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsersActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });

        addNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsersActivity.this, AddNewUserActivity.class);
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
}
