package com.example.nasimuzzaman.roostpad.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.binjar.prefsdroid.Preference;
import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.authentication.LoginActivity;
import com.example.nasimuzzaman.roostpad.changePassword.ChangePasswordActivity;

public class ContactsActivity extends AppCompatActivity {

    private Button users, contacts;
    private TextView contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        users = (Button) findViewById(R.id.users);
        contacts = (Button) findViewById(R.id.contacts);
        contactList = (TextView) findViewById(R.id.contact_list);

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
                /*ContactsResponse contactsResponse = new ContactsResponse();
                List<Contacts> contacts = contactsResponse.getContacts();

                for (int i=0; i<contacts.size(); i++) {
                    Contacts contact = contacts.get(i);
                    contactList.setText("");
                    contactList.append("Name: " + contact.getName());
                    contactList.append("Email: " + contact.getEmail());
                    contactList.append("Phone: " + contact.getContact());
                }*/

                Intent intent = new Intent(ContactsActivity.this, ContactsActivity.class);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int res_id = item.getItemId();
        if(res_id == R.id.action_edit_profile) {
            Toast.makeText(getApplicationContext(), "You select Edit Profile option", Toast.LENGTH_SHORT).show();
        } else if(res_id == R.id.action_change_password) {
            Toast.makeText(getApplicationContext(), "You select Change Password option", Toast.LENGTH_SHORT).show();
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
