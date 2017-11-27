package com.example.nasimuzzaman.roostpad.request;

/**
 * Created by nasimuzzaman on 11/27/17.
 */

public class RequestDay {

    private String dayName, dayDate, holidayType;
    private boolean firstHalfEnabled, secondHalfEnabled, firstHalfChecked, secondHalfChecked;

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDayDate() {
        return dayDate;
    }

    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }

    public String getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(String holidayType) {
        this.holidayType = holidayType;
    }

    public boolean isFirstHalfEnabled() {
        return firstHalfEnabled;
    }

    public void setFirstHalfEnabled(boolean firstHalfEnabled) {
        this.firstHalfEnabled = firstHalfEnabled;
    }

    public boolean isSecondHalfEnabled() {
        return secondHalfEnabled;
    }

    public void setSecondHalfEnabled(boolean secondHalfEnabled) {
        this.secondHalfEnabled = secondHalfEnabled;
    }

    public boolean isFirstHalfChecked() {
        return firstHalfChecked;
    }

    public void setFirstHalfChecked(boolean firstHalfChecked) {
        this.firstHalfChecked = firstHalfChecked;
    }

    public boolean isSecondHalfChecked() {
        return secondHalfChecked;
    }

    public void setSecondHalfChecked(boolean secondHalfChecked) {
        this.secondHalfChecked = secondHalfChecked;
    }
}
