package com.example.nasimuzzaman.roostpad.services;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.binjar.prefsdroid.Preference;
import com.example.nasimuzzaman.roostpad.authentication.LoginResponse;
import com.example.nasimuzzaman.roostpad.contacts.ContactCredential;
import com.example.nasimuzzaman.roostpad.employeeNotification.UserNotificationActivity;
import com.example.nasimuzzaman.roostpad.libraryPackage.CustomLibrary;
import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.authentication.LoginActivity;
import com.example.nasimuzzaman.roostpad.changePassword.ChangePasswordActivity;
import com.example.nasimuzzaman.roostpad.contacts.ContactsActivity;
import com.example.nasimuzzaman.roostpad.contacts.ContactsClient;
import com.example.nasimuzzaman.roostpad.contacts.ContactsResponse;
import com.example.nasimuzzaman.roostpad.contacts.ContactsService;
import com.example.nasimuzzaman.roostpad.home.HomeActivity;
import com.example.nasimuzzaman.roostpad.home.SetupActivity;
import com.example.nasimuzzaman.roostpad.home.UsersActivity;
import com.example.nasimuzzaman.roostpad.pendingRequests.PendingRequestsActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewUserActivity extends AppCompatActivity {

    private Button users, contacts, add_new_user;
    EditText nameInput, emailInput, phoneInput, designationInput;
    Spinner genderInput, roleInput;
    LoginResponse userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);

        userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);

        users = (Button) findViewById(R.id.users);
        contacts = (Button) findViewById(R.id.contacts);
        add_new_user = (Button) findViewById(R.id.btn_add_new_user);

        nameInput = (EditText) findViewById(R.id.name);
        emailInput = (EditText) findViewById(R.id.email);
        phoneInput = (EditText) findViewById(R.id.phone);
        designationInput = (EditText) findViewById(R.id.designation);
        roleInput = (Spinner) findViewById(R.id.role);
        genderInput = (Spinner) findViewById(R.id.gender);


        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewUserActivity.this, UsersActivity.class);
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

                        if (body != null) {
                            if (body.getStatusCode() == 200) {
                                // save user info
                                com.binjar.prefsdroid.Preference.putObject(PrefKeys.USER_CONTACTS, body);
                                // show success message
                                Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT);
                                // go to setup page
                                showContactsPage();
                            } else
                                Toast.makeText(getApplicationContext(), body.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ContactsResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        add_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nameInput.getText().toString().equals("")) {
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    nameInput.setError("Name cannot be empty");
                    nameInput.startAnimation(shake);
                } else if (emailInput.getText().toString().equals("")) {
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    emailInput.setError("Email address cannot be empty");
                    emailInput.startAnimation(shake);
                } else if (!validate(emailInput.getText().toString())) {
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    emailInput.setError("Email address is not valid");
                    emailInput.startAnimation(shake);
                } else if (phoneInput.getText().toString().equals("")) {
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    phoneInput.setError("Phone cannot be empty");
                    phoneInput.startAnimation(shake);
                } else if (designationInput.getText().toString().equals("")) {
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    designationInput.setError("Designation cannot be empty");
                    designationInput.startAnimation(shake);
                } else {
                    String name = nameInput.getText().toString();
                    final String email = emailInput.getText().toString();
                    String phone = phoneInput.getText().toString();
                    String designation = designationInput.getText().toString();
                    String role = roleInput.getSelectedItem().toString();
                    String gender = genderInput.getSelectedItem().toString();


                    final UserCredential userCredential = new UserCredential();
                    userCredential.setName(name);
                    userCredential.setEmail(email);
                    userCredential.setEmailOfAdder(userInfo.getEmail());
                    userCredential.setTokenOfAdder(userInfo.getToken());
                    userCredential.setContact(phone);
                    userCredential.setPassword(new CustomLibrary().getSaltString());
                    userCredential.setDesignation(designation);
                    userCredential.setRole(role);
                    userCredential.setHoliday(25);
                    userCredential.setGender(gender);

                    AddUserService userService = new AddUserClient().createService();
                    Call<AddUserResponse> call = userService.addUser(userCredential);
                    call.enqueue(new Callback<AddUserResponse>() {
                        @Override
                        public void onResponse(Call<AddUserResponse> call, Response<AddUserResponse> response) {
                            AddUserResponse body = response.body();
                            if (body != null) {
                                if (body.getStatusCode() == 200) {
                                    // show success message
                                    Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT);
                                    // go to setup page
                                    new CustomLibrary().sendPassword(userCredential.getEmail(), userCredential.getPassword());
                                    showSetupPage();
                                } else
                                    Toast.makeText(getApplicationContext(), body.getError(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddUserResponse> call, Throwable t) {
                            Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                            emailInput.setError("User With This Email Already in Exists!");
                            emailInput.startAnimation(shake);
                            Toast.makeText(getApplicationContext(), "User With This Email Already in Exists!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public static boolean validate(String email) {
        Pattern ptr = Pattern.compile("(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*:(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)(?:,\\s*(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*))*)?;\\s*)");

        //System.out.println(email + " is " + (ptr.matcher(email).matches() ? "valid" : "invalid"));

        if(ptr.matcher(email).matches())
            return true;

        return false;
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
        if (res_id == R.id.action_show_pending_requests) {
            //Toast.makeText(getApplicationContext(), "You select Edit Profile option", Toast.LENGTH_SHORT).show();
            if (userInfo.getRole().toString().equals("CTO")) {
                showPendingRequests();
            } else {
                showUserNotification();
            }
        } else if (res_id == R.id.action_change_password) {
            //Toast.makeText(getApplicationContext(), "You select Change Password option", Toast.LENGTH_SHORT).show();
            showChangePasswordDialogBox();
        } else if (res_id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logged out Successfully", Toast.LENGTH_SHORT).show();
            showLoginPage();
            Preference.remove(PrefKeys.USER_INFO);
        } else if (res_id == R.id.action_home) {
            openHomePage();
        } else if (res_id == R.id.action_setup) {
            showSetupPage();
        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (userInfo.getRole().toString().equals("Employee")) {
            menu.getItem(1).setVisible(false);
        }

        return true;
    }

    private void showUserNotification() {
        Intent intent = new Intent(this, UserNotificationActivity.class);
        startActivity(intent);
    }

    private void showPendingRequests() {
        Intent intent = new Intent(this, PendingRequestsActivity.class);
        startActivity(intent);
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

    private void showContactsPage() {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }
}
