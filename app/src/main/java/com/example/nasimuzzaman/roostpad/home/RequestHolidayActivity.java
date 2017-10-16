package com.example.nasimuzzaman.roostpad.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nasimuzzaman.roostpad.R;

public class RequestHolidayActivity extends AppCompatActivity {

    private TextView fromDate;
    private TextView toDate;
    private Button btnFromDateGoCalender;
    private Button btnToDateGoCalender;
    private final int FROM_DATE_REQ = 1;
    private final int TO_DATE_REQ = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_holiday);

        fromDate = (TextView) findViewById(R.id.fromDate);
        toDate = (TextView) findViewById(R.id.toDate);

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
                if(data != null) fromDate.setText(data.getStringExtra("date"));
            } else {
                if(data != null) toDate.setText(data.getStringExtra("date"));
            }
        }
    }
}
