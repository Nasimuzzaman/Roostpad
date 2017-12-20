package com.example.nasimuzzaman.roostpad.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.binjar.prefsdroid.Preference;
import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.authentication.AuthenticationClient;
import com.example.nasimuzzaman.roostpad.authentication.AuthenticationService;
import com.example.nasimuzzaman.roostpad.authentication.LoginCredential;
import com.example.nasimuzzaman.roostpad.authentication.LoginResponse;
import com.example.nasimuzzaman.roostpad.deleteUser.DeleteUserClient;
import com.example.nasimuzzaman.roostpad.deleteUser.DeleteUserCredential;
import com.example.nasimuzzaman.roostpad.deleteUser.DeleteUserResponse;
import com.example.nasimuzzaman.roostpad.deleteUser.DeleteUserService;
import com.example.nasimuzzaman.roostpad.libraryPackage.BaseActivity;
import com.example.nasimuzzaman.roostpad.libraryPackage.CustomLibrary;
import com.example.nasimuzzaman.roostpad.updateUser.UpdateUserClient;
import com.example.nasimuzzaman.roostpad.updateUser.UpdateUserCredential;
import com.example.nasimuzzaman.roostpad.updateUser.UpdateUserResponse;
import com.example.nasimuzzaman.roostpad.updateUser.UpdateUserService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactDetailActivity extends BaseActivity implements Serializable{


    ContactsResponse contactsResponse;
    List<Contacts> contactsLists;
    LoginResponse userInfo;

    public EditText nameText;
    public EditText emailText;
    public EditText contactText;
    public EditText designationText;
    public Spinner roleText;
    public EditText holidayText;
    public Button btnEdit;
    public Button btnDelete;
    public Button btnUpdate;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);
        contactsResponse = Preference.getObject(PrefKeys.USER_CONTACTS, ContactsResponse.class);
        contactsLists = contactsResponse.getContacts();

        nameText = (EditText) findViewById(R.id.nameText);
        emailText = (EditText) findViewById(R.id.emailText);
        contactText = (EditText) findViewById(R.id.contactText);
        designationText = (EditText) findViewById(R.id.designationText);
        roleText = (Spinner) findViewById(R.id.roleSpinner);
        holidayText = (EditText) findViewById(R.id.holidayText);

        btnEdit = (Button) findViewById(R.id.btn_edit_contact);
        btnDelete = (Button) findViewById(R.id.btn_delete_contact);
        btnUpdate = (Button) findViewById(R.id.btn_update_contact);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");


        final List<String> spinnerArrayList = new ArrayList<>(
                Arrays.asList("Admin", "CTO", "Employee")
        );

        Contacts contact = new Contacts();

        for (Contacts con: contactsLists) {
            if(con.getEmail().equals(email)) {
                contact = con;
                position = contactsLists.indexOf(con);
            }
        }

        final List<EditText> editTextList = new ArrayList<>(
                Arrays.asList(nameText, emailText, contactText, designationText, holidayText));


        disableEditText(editTextList);
        roleText.setEnabled(false);
        editTextList.remove(emailText);


        if(userInfo.getEmail().equals(email)) {
            btnDelete.setVisibility(View.GONE);
        }


        nameText.setText(contact.getName());
        emailText.setText(contact.getEmail());
        contactText.setText(contact.getContact());
        designationText.setText(contact.getDesignation());
        roleText.setSelection(spinnerArrayList.indexOf(contact.getRole()));
        holidayText.setText(""+contact.getHoliday());


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditText(editTextList);
                roleText.setEnabled(true);
                btnUpdate.setVisibility(View.VISIBLE);
                if(userInfo.getEmail().equals(email)) {
                    btnDelete.setVisibility(View.GONE);
                    roleText.setEnabled(false);
                    holidayText.setEnabled(false);
                    designationText.setEnabled(false);
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UpdateUserCredential credential = new UpdateUserCredential();

                credential.setName(nameText.getText().toString());
                credential.setEmail(emailText.getText().toString());
                credential.setContact(contactText.getText().toString());
                credential.setDesignation(designationText.getText().toString());
                credential.setHoliday(Double.parseDouble(holidayText.getText().toString()));
                credential.setRole(roleText.getSelectedItem().toString());
                credential.setEmailOfAuthor(userInfo.getEmail());
                credential.setTokenOfAuthor(userInfo.getToken());

                UpdateUserService service = new UpdateUserClient().createService();
                Call<UpdateUserResponse> call = service.updateUser(credential);
                call.enqueue(new Callback<UpdateUserResponse>() {
                    @Override
                    public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                        UpdateUserResponse body = response.body();
                        if(body != null) {
                            if(body.getStatusCode() == 200) {
                                //To do
                                updateUserInfo();
                                //Refresh page
                                nameText.setText(credential.getName());
                                emailText.setText(credential.getEmail());
                                contactText.setText(credential.getContact());
                                designationText.setText(credential.getDesignation());
                                roleText.setSelection(spinnerArrayList.indexOf(credential.getRole()));
                                holidayText.setText(""+credential.getHoliday());

                            } else Toast.makeText(ContactDetailActivity.this, body.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateUserResponse> call, Throwable t) {
                        Toast.makeText(ContactDetailActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });


                disableEditText(editTextList);
                roleText.setEnabled(false);
                btnUpdate.setVisibility(View.GONE);
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteUserCredential credential = new DeleteUserCredential();

                credential.setEmail(emailText.getText().toString());
                credential.setEmailOfAuthor(userInfo.getEmail());
                credential.setTokenOfAuthor(userInfo.getToken());

                DeleteUserService service = new DeleteUserClient().createService();
                Call<DeleteUserResponse> call = service.deleteUser(credential);
                call.enqueue(new Callback<DeleteUserResponse>() {
                    @Override
                    public void onResponse(Call<DeleteUserResponse> call, Response<DeleteUserResponse> response) {
                        DeleteUserResponse body = response.body();
                        if(body != null) {
                            if(body.getStatusCode() == 200) {
                                // show success message
                                Toast.makeText(ContactDetailActivity.this, body.getMessage(), Toast.LENGTH_SHORT);
                                contactsLists.remove(position);

                                CustomLibrary.openPage(ContactDetailActivity.this, ContactsActivity.class);

                            } else Toast.makeText(ContactDetailActivity.this, body.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteUserResponse> call, Throwable t) {
                        Toast.makeText(ContactDetailActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void disableEditText(List<EditText> editTextList) {
        for(EditText editText : editTextList) {
            editText.setEnabled(false);
            editText.setClickable(false);
        }
    }

    private void enableEditText(List<EditText> editTextList) {
        for(EditText editText : editTextList) {
            editText.setEnabled(true);
            editText.setClickable(true);
        }
    }

    public void updateUserInfo() {

        String email = userInfo.getEmail().toString();
        String password = userInfo.getPassword().toString();
        LoginCredential credential = new LoginCredential();
        credential.setEmail(email);
        credential.setPassword(password);
        AuthenticationService service = new AuthenticationClient().createService();
        Call<LoginResponse> call = service.login(credential);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse body = response.body();
                if(body != null) {
                    if (body.getStatusCode() == 200) {
                        // save user info
                        com.binjar.prefsdroid.Preference.putObject(PrefKeys.USER_INFO, body);

                        userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);            }
        });
    }
  /*
    holder.btnEdit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            enableEditText(editTextList);
            holder.roleText.setEnabled(true);
            holder.btnUpdate.setVisibility(View.VISIBLE);
        }
    });

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final UpdateUserCredential credential = new UpdateUserCredential();

            credential.setName(holder.nameText.getText().toString());
            credential.setEmail(contacts.getEmail());
            credential.setContact(holder.contactText.getText().toString());
            credential.setDesignation(holder.designationText.getText().toString());
            credential.setHoliday(Double.parseDouble(holder.holidayText.getText().toString()));
            credential.setRole(holder.roleText.getSelectedItem().toString());
            credential.setEmailOfAuthor(userInfo.getEmail());
            credential.setTokenOfAuthor(userInfo.getToken());

            UpdateUserService service = new UpdateUserClient().createService();
            Call<UpdateUserResponse> call = service.updateUser(credential);
            call.enqueue(new Callback<UpdateUserResponse>() {
                @Override
                public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                    UpdateUserResponse body = response.body();
                    if(body != null) {
                        if(body.getStatusCode() == 200) {
                            //To do
                            updateUserInfo();
                            //Refresh page
                            holder.nameText.setText(credential.getName());
                            holder.emailText.setText(credential.getEmail());
                            holder.contactText.setText(credential.getContact());
                            holder.designationText.setText(credential.getDesignation());
                            holder.roleText.setSelection(spinnerArrayList.indexOf(credential.getRole()));
                            holder.holidayText.setText(""+credential.getHoliday());

                        } else Toast.makeText(holder.context, body.getError(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateUserResponse> call, Throwable t) {
                    Toast.makeText(holder.context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });


            disableEditText(editTextList);
            holder.roleText.setEnabled(false);
            holder.btnUpdate.setVisibility(View.GONE);
        }
    });


     holder.btnDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DeleteUserCredential credential = new DeleteUserCredential();

            credential.setEmail(contacts.getEmail());
            credential.setEmailOfAuthor(userInfo.getEmail());
            credential.setTokenOfAuthor(userInfo.getToken());

            DeleteUserService service = new DeleteUserClient().createService();
            Call<DeleteUserResponse> call = service.deleteUser(credential);
            call.enqueue(new Callback<DeleteUserResponse>() {
                @Override
                public void onResponse(Call<DeleteUserResponse> call, Response<DeleteUserResponse> response) {
                    DeleteUserResponse body = response.body();
                    if(body != null) {
                        if(body.getStatusCode() == 200) {
                            // show success message
                            Toast.makeText(holder.context, body.getMessage(), Toast.LENGTH_SHORT);
                            contactsList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, contactsList.size());
                        } else Toast.makeText(holder.context, body.getError(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DeleteUserResponse> call, Throwable t) {
                    Toast.makeText(holder.context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    });

     */
}
