package com.example.nasimuzzaman.roostpad;

import com.example.nasimuzzaman.roostpad.request.RequestDay;

import java.util.List;

/**
 * Created by nasimuzzaman on 11/27/17.
 */

public interface OnHolidayRequestCountChangeCallback {
    void onHolidayRequestCountChange(boolean checked);

    void setRequestDays(List<RequestDay> requestDays);
}
