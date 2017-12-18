package com.example.nasimuzzaman.roostpad.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.binjar.prefsdroid.Preference;
import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.authentication.LoginResponse;
import com.example.nasimuzzaman.roostpad.home.UsersActivity;
import com.example.nasimuzzaman.roostpad.libraryPackage.BaseActivity;
import com.example.nasimuzzaman.roostpad.libraryPackage.CustomLibrary;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsActivity extends BaseActivity {

    private Button users, contacts;
    //private TextView contactView;
    ContactsResponse contactsResponse;
    List<Contacts> contactsLists;
    RecyclerView view;
    RecyclerView.Adapter adapter;
    LoginResponse userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);

        contactsResponse = Preference.getObject(PrefKeys.USER_CONTACTS, ContactsResponse.class);
        contactsLists = contactsResponse.getContacts();

        users = (Button) findViewById(R.id.users);
        contacts = (Button) findViewById(R.id.contacts);
        view = (RecyclerView) findViewById(R.id.contactRecyclerView);
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ContactAdapter(contactsLists);
        view.setAdapter(adapter);

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsActivity.this, UsersActivity.class);
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
                                // go to contact page
                                CustomLibrary.openPage(ContactsActivity.this, ContactsActivity.class);
                            } else Toast.makeText(getApplicationContext(), body.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ContactsResponse> call, Throwable t) {
                        if(contactsLists != null) {
                            // save user info
                            com.binjar.prefsdroid.Preference.putObject(PrefKeys.USER_CONTACTS, contactsLists);
                            // go to contact page
                            CustomLibrary.openPage(ContactsActivity.this, ContactsActivity.class);
                        }
                    }
                });
            }
        });
    }

}
