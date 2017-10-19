package com.example.nasimuzzaman.roostpad.home;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.binjar.prefsdroid.Preference;
import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.authentication.LoginActivity;

import java.text.ParseException;
import java.util.Date;

public class RequestHolidayActivity extends AppCompatActivity {

    private EditText fromDate;
    private EditText toDate;
    private Button btnFromDateGoCalender;
    private Button btnToDateGoCalender;
    private final int FROM_DATE_REQ = 1;
    private final int TO_DATE_REQ = 2;
    Date from, to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_holiday);

        fromDate = (EditText) findViewById(R.id.parseFromDate);
        toDate = (EditText) findViewById(R.id.parseToDate);

        btnFromDateGoCalender = (Button) findViewById(R.id.btn_from_date_go_calendar);
        btnToDateGoCalender = (Button) findViewById(R.id.btn_to_date_go_calendar);

        btnFromDateGoCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequestHolidayActivity.this, DateCalendarActivity.class);
                startActivityForResult(intent, FROM_DATE_REQ);
            }
        });

        btnToDateGoCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequestHolidayActivity.this, DateCalendarActivity.class);
                startActivityForResult(intent, TO_DATE_REQ);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == FROM_DATE_REQ) {
                if(data != null){
                    btnFromDateGoCalender.setText(data.getStringExtra("date"));
                    from = getDateFromString(data.getStringExtra("date"));
                    fromDate.setText(getDayNameFromDate(from));
                }
            } else {
                if(data != null){
                    btnToDateGoCalender.setText(data.getStringExtra("date"));
                    to = getDateFromString(data.getStringExtra("date"));
                    toDate.setText(getDayNameFromDate(to));
                }
            }
        }
    }

    protected Date getDateFromString(String dateString) {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    private int getDayFromDate(Date date) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(java.util.Calendar.DAY_OF_WEEK);
        return day;
    }

    private String getDayNameFromDate(Date date) {
        java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("EEEE");
        return dateFormat.format(date);
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
