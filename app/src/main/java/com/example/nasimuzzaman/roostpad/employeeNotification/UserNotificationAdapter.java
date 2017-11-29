package com.example.nasimuzzaman.roostpad.employeeNotification;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.libraryPackage.CustomLibrary;
import com.example.nasimuzzaman.roostpad.request.RequestDay;
import com.example.nasimuzzaman.roostpad.userNotificationDetails.UserNotificationDetailsActivity;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by nasimuzzaman on 11/19/17.
 */

public class UserNotificationAdapter extends RecyclerView.Adapter<UserNotificationAdapter.MyViewHolder> {

    List<UserNotification> notificationList;
    List<RequestDay> requestDays;
    CustomLibrary library;

    public UserNotificationAdapter(List<UserNotification> notificationList) {
        this.notificationList = notificationList;
        this.requestDays = new ArrayList<>();
        this.library = new CustomLibrary();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_notification_resource_file, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final UserNotification notification = notificationList.get(position);
        requestDays = library.decodeInfo(notification.getInfo());
        //String btnText = prepareStatementForUserNotification(notification.getInfo());
        String btnText = prepareShortStatementForUserNotification(notification.getInfo());

        btnText = btnText + " is " + notification.getStatus();
        holder.btnReply.setText(btnText);
        holder.messageText.setText(notification.getMessage());

        holder.btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //library.open(holder.context, UserNotificationDetailsActivity.class);
                Intent intent = new Intent(holder.context, UserNotificationDetailsActivity.class);
                intent.putExtra("info", notification.getInfo());
                holder.context.startActivity(intent);
            }
        });

    }

    public String prepareStatementForUserNotification(String info) {

        String statement = "Your request for personal holiday on ";
        String[] lines = info.split(System.getProperty("line.separator"));

        for (String line : lines) {
            String[] data = line.split("\\s+");
            String dateString = data[0];
            int flag = Integer.parseInt(data[1]);

            statement = statement + getMonthNameWithDate(dateString, flag);
        }

        return statement.substring(0, statement.length() - 1);
    }

    public String prepareShortStatementForUserNotification(String info) {

        String statement = "Your request for personal holiday on ";
        String[] lines = info.split(System.getProperty("line.separator"));

        String line = lines[0];
        String[] data = line.split("\\s+");
        String dateString = data[0];
        int flag = Integer.parseInt(data[1]);
        statement = statement + getMonthNameWithDate(dateString, flag);


        if (lines.length > 1) {
            statement = statement.substring(0, statement.length() - 1);
            line = lines[lines.length-1];
            data = line.split("\\s+");
            dateString = data[0];
            flag = Integer.parseInt(data[1]);
            statement = statement + " to " + getMonthNameWithDate(dateString, flag);

        }

        return statement.substring(0, statement.length() - 1);
    }

    public String getHolidayHalf(int flag) {
        if (flag == 1) {
            return "(1sh half)";
        } else if (flag == 2) {
            return "(2nd half)";
        } else
            return "";
    }

    /*
    * Take a date string as dd/mm/yyyy format and return month-date i.e. Nov-5
    * */
    public String getMonthNameWithDate(String dateString, int flag) {
        String monthName = "";

        String[] data = dateString.split("/");
        if (data.length == 3) {
            int day = Integer.parseInt(data[0]);
            int monthNumber = Integer.parseInt(data[1]);
            int year = Integer.parseInt(data[2]);

            DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
            String[] months = dateFormatSymbols.getShortMonths();
            if (monthNumber > 0 && monthNumber < 13) {
                monthName = monthName + months[monthNumber - 1] + " " + day + getHolidayHalf(flag) + ", " + year + ",";
            }
        }

        return monthName;
    }

    public String getMonth(String actualDate) {
        SimpleDateFormat month_date = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date date = null;
        try {
            date = sdf.parse(actualDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String month_name = month_date.format(date);

        return month_name;
    }

    @Override
    public int getItemCount() {
        if (notificationList != null) {
            return notificationList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button btnReply;
        public TextView messageText;
        public Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            btnReply = (Button) itemView.findViewById(R.id.btnReply);
            messageText = (TextView) itemView.findViewById(R.id.textMessage);
            context = itemView.getContext();
        }
    }
}
