package com.example.nasimuzzaman.roostpad.holiday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.binjar.prefsdroid.Preference;
import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.authentication.LoginResponse;
import com.example.nasimuzzaman.roostpad.contacts.ContactsActivity;
import com.example.nasimuzzaman.roostpad.contacts.ContactsClient;
import com.example.nasimuzzaman.roostpad.contacts.ContactsResponse;
import com.example.nasimuzzaman.roostpad.contacts.ContactsService;
import com.example.nasimuzzaman.roostpad.home.UsersActivity;
import com.example.nasimuzzaman.roostpad.libraryPackage.Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HolidayActivity extends AppCompatActivity {

    private Button users, contacts, btnAddHoliday, btnSelectHoliday, btnAddNewHoliday;
    LoginResponse userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_holiday);

        users = (Button) findViewById(R.id.users);
        contacts = (Button) findViewById(R.id.contacts);
        btnAddHoliday = (Button) findViewById(R.id.btn_add_holiday);
        btnSelectHoliday = (Button) findViewById(R.id.btn_select_holiday);
        btnAddNewHoliday = (Button) findViewById(R.id.btn_add_new_holiday);
        userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Helper().openPage(getApplicationContext(), UsersActivity.class);
            }
        });

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactsService contactsService = new ContactsClient().createService();
                Call<ContactsResponse> call = contactsService.showContacts();

                call.enqueue(new Callback<ContactsResponse>() {
                    @Override
                    public void onResponse(Call<ContactsResponse> call, Response<ContactsResponse> response) {
                        ContactsResponse body = response.body();

                        if(body != null) {
                            if(body.getStatusCode() == 200) {
                                // save contact info
                                com.binjar.prefsdroid.Preference.putObject(PrefKeys.USER_CONTACTS, body);
                                // show success message
                                Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT);
                                // go to contact page
                                Helper.openPage(getApplicationContext(), ContactsActivity.class);
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

        btnAddHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }
}
