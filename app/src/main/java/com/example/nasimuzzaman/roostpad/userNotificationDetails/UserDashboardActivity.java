package com.example.nasimuzzaman.roostpad.userNotificationDetails;

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
import com.example.nasimuzzaman.roostpad.contacts.ContactsResponse;
import com.example.nasimuzzaman.roostpad.libraryPackage.BaseActivity;
import com.example.nasimuzzaman.roostpad.libraryPackage.CustomLibrary;
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

public class UserDashboardActivity extends BaseActivity {

    LoginResponse userInfo;

    public EditText nameText;
    public EditText emailText;
    public EditText phoneText;
    public EditText designationText;
    public EditText roleText;
    public EditText holidayText;
    public Button btnEdit;
    public Button btnUpdate;
    public Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);

        nameText = (EditText) findViewById(R.id.profileName);
        emailText = (EditText) findViewById(R.id.profileEmail);
        phoneText = (EditText) findViewById(R.id.profilePhone);
        designationText = (EditText) findViewById(R.id.profileDesignation);
        roleText = (EditText) findViewById(R.id.profileRole);
        holidayText = (EditText) findViewById(R.id.profileHolidayText);

        btnEdit = (Button) findViewById(R.id.btnEditProfile);
        btnUpdate = (Button) findViewById(R.id.btnUpdateProfile);
        btnCancel = (Button) findViewById(R.id.btnCancel);


        nameText.setText(userInfo.getName());
        emailText.setText(userInfo.getEmail());
        phoneText.setText(userInfo.getContact());
        designationText.setText(userInfo.getDesignation());
        roleText.setText(userInfo.getRole());
        holidayText.setText("" + userInfo.getHoliday());

        final List<EditText> editTextList = new ArrayList<>(Arrays.asList(nameText, emailText, phoneText, designationText, roleText, holidayText));
        CustomLibrary.disableEditText(editTextList);

        editTextList.removeAll(Arrays.asList(emailText, designationText, roleText, holidayText));

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomLibrary.enableEditText(editTextList);
                btnUpdate.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomLibrary.openPage(UserDashboardActivity.this, UserDashboardActivity.class);
            }
        });

    }

}
