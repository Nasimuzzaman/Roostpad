package com.example.nasimuzzaman.roostpad.libraryPackage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.example.nasimuzzaman.roostpad.gmail.GMailSender;
import com.example.nasimuzzaman.roostpad.request.RequestDay;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by nasimuzzaman on 11/15/17.
 */

public final class CustomLibrary {

    public void open(Context context, Class aClass) {
        Intent intent = new Intent(context, aClass);
        context.startActivity(intent);
    }

    public static void openPage(Context context, Class aClass) {
        Intent intent = new Intent(context, aClass);
        context.startActivity(intent);
    }

    /*
    * Take a date string as dd/mm/yyyy format and return month-date i.e. Nov-5
    * */
    public static String getMonthNameWithDate(String dateString, int flag) {
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

    public static void disableEditText(List<EditText> editTextList) {
        for(EditText editText : editTextList) {
            editText.setEnabled(false);
            editText.setClickable(false);
        }
    }

    public static void enableEditText(List<EditText> editTextList) {
        for(EditText editText : editTextList) {
            editText.setEnabled(true);
            editText.setClickable(true);
        }
    }

    public static String getHolidayHalf(int flag) {
        if (flag == 1) {
            return "(1sh half)";
        } else if (flag == 2) {
            return "(2nd half)";
        } else
            return "";
    }

    public String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 9) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    //using javax.mail
    public void sendPassword(final String recipientsEmail, final String newPassword) {
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("roostpaddb@gmail.com", "Kaz123Mig");
                    sender.sendMail("Notification from ROOSTPAD",
                            "Your Roostpad account has been created. Your password is '"+ newPassword + '"',
                            "roostpaddb@gmail.com",
                            recipientsEmail);
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }

    public List<RequestDay> decodeInfo(String info) {

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

    public static String prepareShortStatementForPendingRequestsNotification(String info) {

        String statement = " request for personal holiday on ";
        String[] lines = info.split(System.getProperty("line.separator"));

        String line = lines[0];
        String[] data = line.split("\\s+");
        String dateString = data[0];
        int flag = Integer.parseInt(data[1]);
        statement = statement + CustomLibrary.getMonthNameWithDate(dateString, flag);


        if (lines.length > 1) {
            statement = statement.substring(0, statement.length() - 1);
            line = lines[lines.length-1];
            data = line.split("\\s+");
            dateString = data[0];
            flag = Integer.parseInt(data[1]);
            statement = statement + " to " + CustomLibrary.getMonthNameWithDate(dateString, flag);

        }

        return statement.substring(0, statement.length() - 1);
    }
}
