package com.example.nasimuzzaman.roostpad.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.libraryPackage.BaseActivity;

import java.util.Calendar;
import java.util.Date;

public class DateCalendarActivity extends BaseActivity {

    private static final String TAG = "DateCalendarActivity";
    private CalendarView dateCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_calendar);

        dateCalendarView = (CalendarView) findViewById(R.id.dateCalendarView);

        Calendar calendar = Calendar.getInstance();
        dateCalendarView.setMinDate(calendar.getTimeInMillis());

        dateCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i2 + "/" + (i1+1) + "/" + i;
                Log.d(TAG, "onSelectedDayChange: mm/dd/yyyy: " + date);

                Intent intent = new Intent();
                intent.putExtra("date", date);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
