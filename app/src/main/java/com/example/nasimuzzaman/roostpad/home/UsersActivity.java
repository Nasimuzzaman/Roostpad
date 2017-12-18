package com.example.nasimuzzaman.roostpad.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.binjar.prefsdroid.Preference;
import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.addUser.AddNewUserActivity;
import com.example.nasimuzzaman.roostpad.authentication.LoginResponse;
import com.example.nasimuzzaman.roostpad.contacts.ContactCredential;
import com.example.nasimuzzaman.roostpad.contacts.Contacts;
import com.example.nasimuzzaman.roostpad.contacts.ContactsClient;
import com.example.nasimuzzaman.roostpad.contacts.ContactsResponse;
import com.example.nasimuzzaman.roostpad.contacts.ContactsService;
import com.example.nasimuzzaman.roostpad.libraryPackage.BaseActivity;
import com.example.nasimuzzaman.roostpad.libraryPackage.CustomLibrary;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersActivity extends BaseActivity {

    private Button users;
    private Button contacts;
    private Button addNewUser;
    LoginResponse userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        users = (Button) findViewById(R.id.users);
        contacts = (Button) findViewById(R.id.contacts);
        addNewUser = (Button) findViewById(R.id.add_new_user);
        userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);


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

                ContactCredential credential = new ContactCredential();
                credential.setEmail(userInfo.getEmail());
                credential.setToken(userInfo.getToken());

                ContactsService contactsService = new ContactsClient().createService();
                Call<ContactsResponse> call = contactsService.showContacts(credential);

                call.enqueue(new Callback<ContactsResponse>() {
                    @Override
                    public void onResponse(Call<ContactsResponse> call, Response<ContactsResponse> response) {
                        ContactsResponse body = response.body();

                        if(body != null) {
                            if(body.getStatusCode() == 200) {
                                // save user info
                                com.binjar.prefsdroid.Preference.putObject(PrefKeys.USER_CONTACTS, body);
                                // show success message
                                Toast.makeText(UsersActivity.this, body.getMessage(), Toast.LENGTH_SHORT);
                                // go to setup page
                                CustomLibrary.openPage(UsersActivity.this, Contacts.class);
                            } else Toast.makeText(getApplicationContext(), body.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ContactsResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
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

}
