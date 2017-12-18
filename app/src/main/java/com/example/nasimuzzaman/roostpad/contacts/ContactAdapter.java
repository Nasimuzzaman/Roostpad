package com.example.nasimuzzaman.roostpad.contacts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.example.nasimuzzaman.roostpad.updateUser.UpdateUserClient;
import com.example.nasimuzzaman.roostpad.updateUser.UpdateUserCredential;
import com.example.nasimuzzaman.roostpad.updateUser.UpdateUserResponse;
import com.example.nasimuzzaman.roostpad.updateUser.UpdateUserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nasimuzzaman on 11/13/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    List<Contacts> contactsList;
    LoginResponse userInfo;

    public ContactAdapter(List<Contacts> contactsList) {
        this.contactsList = contactsList;
        userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_contacts_resource_file, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Contacts contacts = contactsList.get(position);

        final List<String> spinnerArrayList = new ArrayList<>(
                Arrays.asList("Admin", "CTO", "Employee")
        );

        holder.nameText.setText(contacts.getName());
        holder.emailText.setText(contacts.getEmail());
        holder.contactText.setText(contacts.getContact());
        holder.designationText.setText(contacts.getDesignation());
        holder.roleText.setSelection(spinnerArrayList.indexOf(contacts.getRole()));
        holder.holidayText.setText(""+contacts.getHoliday());

        final List<EditText> editTextList = new ArrayList<>(
                Arrays.asList(holder.nameText, holder.emailText, holder.contactText, holder.designationText, holder.holidayText)
        );

        disableEditText(editTextList);
        holder.roleText.setEnabled(false);
        editTextList.remove(holder.emailText);

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

    @Override
    public int getItemCount() {
        if(contactsList != null) {
            return contactsList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public EditText nameText;
        public EditText emailText;
        public EditText contactText;
        public EditText designationText;
        public Spinner roleText;
        public EditText holidayText;
        public Button btnEdit;
        public Button btnDelete;
        public Button btnUpdate;
        private Context context;


        public MyViewHolder(View itemView) {
            super(itemView);
            nameText = (EditText) itemView.findViewById(R.id.nameText);
            emailText = (EditText) itemView.findViewById(R.id.emailText);
            contactText = (EditText) itemView.findViewById(R.id.contactText);
            designationText = (EditText) itemView.findViewById(R.id.designationText);
            roleText = (Spinner) itemView.findViewById(R.id.roleSpinner);
            holidayText = (EditText) itemView.findViewById(R.id.holidayText);

            btnEdit = (Button) itemView.findViewById(R.id.btn_edit_contact);
            btnDelete = (Button) itemView.findViewById(R.id.btn_delete_contact);
            btnUpdate = (Button) itemView.findViewById(R.id.btn_update_contact);



            context = itemView.getContext();
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
}
