package com.example.nasimuzzaman.roostpad.request;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.binjar.prefsdroid.Preference;
import com.example.nasimuzzaman.roostpad.OnHolidayRequestCountChangeCallback;
import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.authentication.LoginResponse;
import com.example.nasimuzzaman.roostpad.gmail.GMailSender;
import com.example.nasimuzzaman.roostpad.home.DateCalendarActivity;
import com.example.nasimuzzaman.roostpad.home.HomeActivity;
import com.example.nasimuzzaman.roostpad.libraryPackage.BaseActivity;
import com.example.nasimuzzaman.roostpad.libraryPackage.CustomLibrary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestHolidayActivity extends BaseActivity implements OnHolidayRequestCountChangeCallback {

    private TextView fromDateInput, toDateInput, totalDayCount;
    private EditText messageInput;
    private Button btnFromDateGoCalender, btnToDateGoCalender, btnRequest;
    private final int FROM_DATE_REQ = 1;
    private final int TO_DATE_REQ = 2;
    Date from, to;
    LoginResponse userInfo;
    double days = 0;
    double existingDays = 0;
    List<RequestDay> requestDays;

    RecyclerView rviewDays;
    RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_holiday);

        userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);

        fromDateInput = (TextView) findViewById(R.id.parseFromDate);
        toDateInput = (TextView) findViewById(R.id.parseToDate);
        totalDayCount = (TextView) findViewById(R.id.total_day_count);
        messageInput = (EditText) findViewById(R.id.messageBox);

        btnFromDateGoCalender = (Button) findViewById(R.id.btn_from_date_go_calendar);
        btnToDateGoCalender = (Button) findViewById(R.id.btn_to_date_go_calendar);
        btnRequest = (Button) findViewById(R.id.btn_request);

        rviewDays = (RecyclerView) findViewById(R.id.rview_days);
        rviewDays.setHasFixedSize(true);
        rviewDays.setLayoutManager(new LinearLayoutManager(this));

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

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

                if(userInfo.getHoliday() < days) {
                    Toast toast = Toast.makeText(getApplicationContext(), "You don't have sufficient holidays left", Toast.LENGTH_LONG);
                    TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                    v.setTextColor(Color.BLUE);
                    v.setTextSize(18);
                    toast.show();
                } else if(days == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please select at least half a day", Toast.LENGTH_LONG);
                    TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                    v.setTextColor(Color.BLUE);
                    v.setTextSize(18);
                    toast.show();
                } else if (isOnlyFromDateSelected() || (isBothValidDateSelected() && isValidDateSelected())) {

                    btnFromDateGoCalender.setError(null);
                    btnToDateGoCalender.setError(null);

                    if (messageInput.getText().toString().equals("")) {
                        messageInput.setError("Please leave a message");
                        messageInput.startAnimation(shake);
                    } else {

                        final RequestHolidayCredential credential = new RequestHolidayCredential();
                        credential.setEmail(userInfo.getEmail());
                        credential.setToken(userInfo.getToken());
                        credential.setInfo(prepareInfo(requestDays));
                        credential.setMessage(messageInput.getText().toString());
                        credential.setDays(days);
                        credential.setStatus("pending");

                        //To Do
                        //sendMessage(credential);

                        RequestHolidayService requestHolidayService = new RequestHolidayClient().createService();
                        Call<RequestHolidayResponse> call = requestHolidayService.requestHoliday(credential);
                        call.enqueue(new Callback<RequestHolidayResponse>() {
                            @Override
                            public void onResponse(Call<RequestHolidayResponse> call, Response<RequestHolidayResponse> response) {
                                RequestHolidayResponse body = response.body();
                                if(body != null) {
                                    if(body.getStatusCode() == 200) {
                                        // save user info
                                        com.binjar.prefsdroid.Preference.putObject(PrefKeys.USER_CONTACTS, body);
                                        // show success message
                                        Toast.makeText(getApplicationContext(), body.getMessage(), Toast.LENGTH_SHORT);
                                        // go to next page
                                        new CustomLibrary().open(getApplicationContext(), HomeActivity.class);
                                    } else Toast.makeText(getApplicationContext(), body.getError(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<RequestHolidayResponse> call, Throwable t) {

                            }
                        });
                    }


                } else if (isBothValidDateSelected() && !isValidDateSelected()) {
                    btnFromDateGoCalender.setError("");
                    btnFromDateGoCalender.startAnimation(shake);
                    btnToDateGoCalender.setError("");
                    btnToDateGoCalender.startAnimation(shake);
                } else {
                    btnFromDateGoCalender.setError("");
                    btnFromDateGoCalender.startAnimation(shake);
                }
            }
        });

    }

    public boolean isOnlyFromDateSelected() {
        if (fromDateInput.getText().toString().trim().length() != 0 && toDateInput.getText().toString().trim().length() == 0) {
            return true;
        }
        return false;
    }

    public boolean isBothValidDateSelected() {
        if (fromDateInput.getText().toString().trim().length() != 0 &&
                toDateInput.getText().toString().trim().length() != 0) {
            return true;
        }
        return false;
    }


    public boolean isValidDateSelected() {

        Date date1 = getDateFromString(btnFromDateGoCalender.getText().toString());
        Date date2 = getDateFromString(btnToDateGoCalender.getText().toString());

        if (date1.before(date2) || date1.compareTo(date2) == 0) {
            return true;
        }

        return false;
    }

    public String prepareInfo( List<RequestDay> requestDayList) {
        /*
        *
        * Seperate date and holiday halves by <space>
        * Seperate new line by <new line>
        *
        * */
        String info = "";
        if(requestDayList != null)
        for (RequestDay day: requestDayList) {
            info = info + day.getDayDate() + " " + getHolidayHalves(day) + "\n";
        }

        return info;
    }

    private List<RequestDay> decodeInfo(String info) {

        List<RequestDay> requestDays = new ArrayList<>();
        String[] lines = info.split(System.getProperty("line.separator"));

        for (String line : lines) {
            String[] data = line.split("\\s+");
            String dateString = data[0];
            int holidayHalves = Integer.parseInt(data[1]);
            Date date = getDateFromString(dateString);

            RequestDay newDay = new RequestDay();
            newDay.setDayName(getShortDayName(date));
            newDay.setDayDate(dateString);
            if (isOfficialHoliday(date)) {
                newDay.setHolidayType("Official");
                newDay.setFirstHalfChecked(false);
                newDay.setSecondHalfChecked(false);
                newDay.setFirstHalfEnabled(false);
                newDay.setSecondHalfEnabled(false);
            } else {
                newDay.setHolidayType("Personal");
                if(holidayHalves == 1) {
                    newDay.setFirstHalfChecked(true);
                    newDay.setSecondHalfChecked(false);
                    newDay.setFirstHalfEnabled(false);
                    newDay.setSecondHalfEnabled(false);
                } else if(holidayHalves == 2) {
                    newDay.setFirstHalfChecked(false);
                    newDay.setSecondHalfChecked(true);
                    newDay.setFirstHalfEnabled(false);
                    newDay.setSecondHalfEnabled(false);
                } else if(holidayHalves == 3) {
                    newDay.setFirstHalfChecked(true);
                    newDay.setSecondHalfChecked(true);
                    newDay.setFirstHalfEnabled(false);
                    newDay.setSecondHalfEnabled(false);
                } else {
                    newDay.setFirstHalfChecked(false);
                    newDay.setSecondHalfChecked(false);
                    newDay.setFirstHalfEnabled(false);
                    newDay.setSecondHalfEnabled(false);
                }
            }
            requestDays.add(newDay);
        }

        return requestDays;
    }

    public int getHolidayHalves(RequestDay day) {
        /*
        * Official holiday - 0
        * First half only - 1
        * Second half only - 2
        * Two halves - 3
        * */

        if(day.getHolidayType().equals("Official")) {
            return 0;
        } else if(!day.isFirstHalfChecked() && !day.isSecondHalfChecked()) {
            return 0;
        } else if(day.isFirstHalfChecked() && day.isSecondHalfChecked()) {
            return 3;
        } else if(day.isFirstHalfChecked() && !day.isSecondHalfChecked()) {
            return 1;
        } else
            return 2;
    }


    public void onDateSelect() {
        if (fromDateInput.getText().toString().trim().length() != 0 && toDateInput.getText().toString().trim().length() == 0) {
            btnToDateGoCalender.setError(null);
            btnFromDateGoCalender.setError(null);
            List<RequestDay> dayList = getDateList(btnFromDateGoCalender.getText().toString(), btnFromDateGoCalender.getText().toString());
            requestDays = dayList;
            setDetailInfo();
            adapter = new RequestDaysAdapter(dayList, RequestHolidayActivity.this);
            rviewDays.setAdapter(adapter);
        } else if (fromDateInput.getText().toString().trim().length() != 0 && toDateInput.getText().toString().trim().length() != 0) {
            if (isValidDateSelected()) {
                btnToDateGoCalender.setError(null);
                btnFromDateGoCalender.setError(null);
                List<RequestDay> dayList = getDateList(btnFromDateGoCalender.getText().toString(), btnToDateGoCalender.getText().toString());
                requestDays = dayList;
                setDetailInfo();
                adapter = new RequestDaysAdapter(dayList, RequestHolidayActivity.this);
                rviewDays.setAdapter(adapter);
            } else {
                Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                btnToDateGoCalender.setError("Invalid Selection");
                btnToDateGoCalender.startAnimation(shake);
            }
        }
    }

    public void setDetailInfo() {
        double remainingDays = userInfo.getHoliday() - days;
        double selectedDays = days + existingDays;
        totalDayCount.setText(selectedDays + " days\n");
        totalDayCount.append(existingDays + " existing days\n");
        totalDayCount.append(days + " days\n");
        totalDayCount.append(remainingDays + " days");
    }


    //using javax.mail
    private void sendMessage(final RequestHolidayCredential credential) {
        final ProgressDialog dialog = new ProgressDialog(RequestHolidayActivity.this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        final String subject = "";/*userInfo.getName() + " apply for holiday from " + credential.getFromDate() +
                " to " + credential.getToDate() + " for total " + credential.getDays() + " days.";*/

        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("roostpaddb@gmail.com", "Kaz123Mig");
                    sender.sendMail(subject,
                            credential.getMessage(),
                            "roostpaddb@gmail.com",
                            userInfo.getCtoEmail());
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }

    private List<RequestDay> getDateList(String start, String end) {

        List<RequestDay> requestDays = new ArrayList<>();
        days = 0.0;
        existingDays = 0.0;

        Date startDate = getDateFromString(start);
        Date endDate = getDateFromString(end);
        for (Date date = startDate; date.before(getNextDate(endDate)); date = getNextDate(date)) {
            RequestDay day = new RequestDay();
            day.setDayName(getShortDayName(date));
            day.setDayDate(formatDate(date));
            if (isOfficialHoliday(date)) {
                day.setHolidayType("Official");
                day.setFirstHalfChecked(false);
                day.setSecondHalfChecked(false);
                day.setFirstHalfEnabled(false);
                day.setSecondHalfEnabled(false);
                existingDays += 1.0;
            } else {
                days += 1.0;
                day.setHolidayType("Personal");
                day.setFirstHalfEnabled(true);
                day.setSecondHalfEnabled(true);
                day.setFirstHalfChecked(true);
                day.setSecondHalfChecked(true);
            }
            requestDays.add(day);
        }

        //totalDayCount.setText("Actual Holiday Requested: " + days);

        return requestDays;
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
                    onDateSelect();
                }
            } else {
                if (data != null) {
                    btnToDateGoCalender.setText(data.getStringExtra("date"));
                    to = getDateFromString(data.getStringExtra("date"));
                    toDateInput.setText(getDayNameFromDate(to));
                    onDateSelect();
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

    private String getShortDayName(Date date) {
        java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("EEEE");
        String dayName = dateFormat.format(date);
        return dayName.substring(0, Math.min(dayName.length(), 3));
    }

    private boolean isOfficialHoliday(Date date) {
        if (getShortDayName(date).equals("Sat") || getShortDayName(date).equals("Fri")) {
            return true;
        }

        return false;
    }

    @Override
    public void onHolidayRequestCountChange(boolean checked) {
        if (checked) {
            days += 0.5;
        } else {
            days -= 0.5;
        }
        double remainingDays = userInfo.getHoliday() - days;
        double selectedDays = days + existingDays;
        totalDayCount.setText(selectedDays + " days\n");
        totalDayCount.append(existingDays + " existing days\n");
        totalDayCount.append(days + " days\n");
        totalDayCount.append(remainingDays + " days");
    }



    @Override
    public void setRequestDays(List<RequestDay> requestDays) {
        this.requestDays = requestDays;
    }
}
