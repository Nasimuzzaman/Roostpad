package com.example.nasimuzzaman.roostpad.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;

import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.libraryPackage.BaseActivity;

/**
 * Created by nasimuzzaman on 10/14/17.
 */

public class CalendarActivity extends BaseActivity{

    private static final String TAG = "CalendarActivity";
    private CalendarView calendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = (i1+1) + "/" + i2 + "/" + i;
                Log.d(TAG, "onSelectedDayChange: mm/dd/yyyy: " + date);

                Intent intent = new Intent(CalendarActivity.this, HomeActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }
}
