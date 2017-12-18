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
import android.widget.LinearLayout;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nasimuzzaman on 11/13/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> implements Serializable{

    List<Contacts> contactsList;
    LoginResponse userInfo;
    private Context context;

    public ContactAdapter(List<Contacts> contactsList, Context context) {
        this.contactsList = contactsList;
        this.context = context;
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


        /*final List<String> spinnerArrayList = new ArrayList<>(
                Arrays.asList("Admin", "CTO", "Employee")
        );*/

        holder.btnName.setText(contacts.getName());

        /*final List<EditText> editTextList = new ArrayList<>(
                Arrays.asList(holder.nameText, holder.emailText, holder.contactText, holder.designationText, holder.holidayText)
        );

        disableEditText(editTextList);
        holder.roleText.setEnabled(false);
        editTextList.remove(holder.emailText);*/

        holder.btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContactDetailActivity.class);
                intent.putExtra("email", contacts.getEmail());
                context.startActivity(intent);
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
        public Button btnName;


        public MyViewHolder(View itemView) {
            super(itemView);
            btnName = (Button) itemView.findViewById(R.id.Contact_Name);
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
