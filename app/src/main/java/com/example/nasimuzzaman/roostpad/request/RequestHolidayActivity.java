package com.example.nasimuzzaman.roostpad.request;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.binjar.prefsdroid.Preference;
import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.authentication.LoginActivity;
import com.example.nasimuzzaman.roostpad.authentication.LoginCredential;
import com.example.nasimuzzaman.roostpad.authentication.LoginResponse;
import com.example.nasimuzzaman.roostpad.changePassword.ChangePasswordActivity;
import com.example.nasimuzzaman.roostpad.gmail.GMailSender;
import com.example.nasimuzzaman.roostpad.home.DateCalendarActivity;
import com.example.nasimuzzaman.roostpad.home.HomeActivity;
import com.example.nasimuzzaman.roostpad.home.SetupActivity;
import com.example.nasimuzzaman.roostpad.services.AddUserResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestHolidayActivity extends AppCompatActivity {

    private TextView fromDateInput, toDateInput, totalDayCount;
    private EditText messageInput;
    private Button btnFromDateGoCalender, btnToDateGoCalender, btnRequest, info;
    private TableLayout dates;
    private final int FROM_DATE_REQ = 1;
    private final int TO_DATE_REQ = 2;
    Date from, to;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_holiday);

        fromDateInput = (TextView) findViewById(R.id.parseFromDate);
        toDateInput = (TextView) findViewById(R.id.parseToDate);
        totalDayCount = (TextView) findViewById(R.id.total_day_count);
        messageInput = (EditText) findViewById(R.id.messageBox);
        dates = (TableLayout) findViewById(R.id.dates);

        btnFromDateGoCalender = (Button) findViewById(R.id.btn_from_date_go_calendar);
        btnToDateGoCalender = (Button) findViewById(R.id.btn_to_date_go_calendar);
        btnRequest = (Button) findViewById(R.id.btn_request);
        info = (Button) findViewById(R.id.info);


        //dates.append("Date" + "\t\t" + "Day" + "\t\t" + "Description" + "\n");


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
                if (fromDateInput.getText().toString().trim().length() != 0) {
                    Intent intent = new Intent(RequestHolidayActivity.this, DateCalendarActivity.class);
                    startActivityForResult(intent, TO_DATE_REQ);
                }

            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                System.out.println("HIM");
//                System.out.println("000000 "+new LoginResponse().getName());



                if (fromDateInput.getText().toString().trim().length() != 0 && toDateInput.getText().toString().trim().length() != 0) {
                    dates.removeAllViews();
                    showDatesInfo(btnFromDateGoCalender.getText().toString(), btnToDateGoCalender.getText().toString());
                    totalDayCount.setText("Actual Holiday Requested: "+getTotalHolidayRequested(btnFromDateGoCalender.getText().toString(), btnToDateGoCalender.getText().toString()));
                }
            }
        });


        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String startDate = btnFromDateGoCalender.getText().toString();
                String endDate = btnToDateGoCalender.getText().toString();
                int numberOfDays = getTotalHolidayRequested(startDate, endDate);
                String leaveMessage = messageInput.getText().toString();


                final RequestHolidayCredential credential = new RequestHolidayCredential();
                credential.setEmail("nasimuzzaman.iit.du@gmail.com");
                credential.setFromDate(startDate);
                credential.setToDate(endDate);
                credential.setDays(numberOfDays);
                credential.setMessage(leaveMessage);

                //sendEmail(credential);
                sendMessage(credential);

                RequestHolidayService requestHolidayService = new RequestHolidayClient().createService();
                Call<RequestHolidayResponse> call = requestHolidayService.requestHoliday(credential);

                call.enqueue(new Callback<RequestHolidayResponse>() {
                    @Override
                    public void onResponse(Call<RequestHolidayResponse> call, Response<RequestHolidayResponse> response) {
                        RequestHolidayResponse body = response.body();
                        if(body != null) {
                            if(body.getStatusCode() == 200) {
                                // save user info
                                com.binjar.prefsdroid.Preference.putObject(PrefKeys.USER_INFO, body);
                                // show success message
                                Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT);
                                // go to home page
                                // openHomePage();
                            } else Toast.makeText(getApplicationContext(), body.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RequestHolidayResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }


    //using javax.mail
    private void sendMessage(final RequestHolidayCredential credential) {
        final ProgressDialog dialog = new ProgressDialog(RequestHolidayActivity.this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        final String subject = credential.getEmail() + " apply for holiday from " + credential.getFromDate() +
                " to " + credential.getToDate();

        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("roostpaddb@gmail.com", "Kaz123Mig");
                    sender.sendMail(subject,
                            credential.getMessage(),
                            "roostpaddb@gmail.com",
                            credential.getEmail());
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }

    //using inten
    private void sendEmail(RequestHolidayCredential credential) {
        Intent email = new Intent(Intent.ACTION_SEND);

        String subject = credential.getEmail() + " apply for holiday from " + credential.getFromDate() +
                " to " + credential.getToDate();

        email.setData(Uri.parse("mailto:"));
        String[] mto = {"nasimuzzaman.iit.du@gmail.com", "bsse0602@iit.du.ac.bd"};
        email.putExtra(Intent.EXTRA_EMAIL, mto);
        email.putExtra(Intent.EXTRA_CC, "nasimuzzaman.iit.du@gmail.com");
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, credential.getMessage());
        //email.putExtra(Intent.EXTRA_);
        email.setType("message/rfc822");

        Intent chooser = Intent.createChooser(email, "Send Email");
        startActivity(chooser);

//        startActivity(Intent.createChooser(email, "Send mail..."));


//        email.setType("message/rfc822");
//
//        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    private void showDatesInfo(String start, String end) {

        TableRow row = new TableRow(this);

        TextView view1 = new TextView(this.getApplicationContext());
        TextView view2 = new TextView(this.getApplicationContext());
        TextView view3 = new TextView(this.getApplicationContext());

        view1.setText("Date");
        row.addView(view1);
        view2.setText("Day");
        row.addView(view2);
        view3.setText("Holiday");
        row.addView(view3);

        dates.addView(row);

        Date startDate = getDateFromString(start);
        Date endDate = getDateFromString(end);

        for (Date date = startDate; date.before(getNextDate(endDate)); date = getNextDate(date)) {

            row = new TableRow(this);

            view1 = new TextView(this.getApplicationContext());
            view2 = new TextView(this.getApplicationContext());
            view3 = new TextView(this.getApplicationContext());

            view1.setText(formatDate(date));
            row.addView(view1);
            view2.setText(getDayNameFromDate(date));
            row.addView(view2);
            view3.setText(getDateInfo(date));
            row.addView(view3);

            dates.addView(row);
        }
    }

    public int getTotalHolidayRequested(String start, String end) {
        Date startDate = getDateFromString(start);
        Date endDate = getDateFromString(end);
        int count = 0;

        for (Date date = startDate; date.before(getNextDate(endDate)); date = getNextDate(date)) {
            if(getDateInfo(date).equals("Personal")) {
                count++;
            }
        }

        return count;
    }

    private String getDateInfo(Date date) {
        if (getDayNameFromDate(date).equals("Saturday") || getDayNameFromDate(date).equals("Friday")) {
            return "Official";
        }

        return "Personal";
    }

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String format = formatter.format(date);

        return format;
    }


    public static Date getNextDate(Date curDate) {

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == FROM_DATE_REQ) {
                if (data != null) {
                    btnFromDateGoCalender.setText(data.getStringExtra("date"));
                    from = getDateFromString(data.getStringExtra("date"));
                    fromDateInput.setText(getDayNameFromDate(from));
                }
            } else {
                if (data != null) {
                    btnToDateGoCalender.setText(data.getStringExtra("date"));
                    to = getDateFromString(data.getStringExtra("date"));
                    toDateInput.setText(getDayNameFromDate(to));
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
        if (res_id == R.id.action_edit_profile) {
            Toast.makeText(getApplicationContext(), "You select Edit Profile option", Toast.LENGTH_SHORT).show();
        } else if (res_id == R.id.action_change_password) {
            Toast.makeText(getApplicationContext(), "You select Change Password option", Toast.LENGTH_SHORT).show();
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