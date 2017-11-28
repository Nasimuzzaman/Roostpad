package com.example.nasimuzzaman.roostpad.request;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.nasimuzzaman.roostpad.OnHolidayRequestCountChangeCallback;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.contacts.ContactAdapter;
import com.example.nasimuzzaman.roostpad.libraryPackage.CustomLibrary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by nasimuzzaman on 11/27/17.
 */

public class RequestDaysAdapter extends RecyclerView.Adapter<RequestDaysAdapter.MyViewHolder> {

    List<RequestDay> requestDays;
    List<Boolean> flag;
    OnHolidayRequestCountChangeCallback callback;

    public RequestDaysAdapter(List<RequestDay> requestDays, OnHolidayRequestCountChangeCallback callback) {
        this.requestDays = requestDays;
        flag = new ArrayList<>(requestDays.size());
        //initializeFlag();
        this.callback = callback;
    }

    private void initializeFlag() {
        for (int i = 0; i < flag.size(); i++) {
            flag.set(i, true);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView dayName, dayDate, holidayType;
        CheckBox firstHalf, secondHalf;
        private Context context;

        public MyViewHolder(View itemView) {
            super(itemView);

            dayName = (TextView) itemView.findViewById(R.id.dayName);
            dayDate = (TextView) itemView.findViewById(R.id.dayDate);
            holidayType = (TextView) itemView.findViewById(R.id.holidayType);

            firstHalf = (CheckBox) itemView.findViewById(R.id.firstHalf);
            secondHalf = (CheckBox) itemView.findViewById(R.id.secondHalf);

            context = itemView.getContext();
        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.days_resource_file, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final RequestDay day = requestDays.get(position);

        holder.dayName.setText(day.getDayName());
        holder.dayDate.setText(day.getDayDate());
        holder.holidayType.setText(day.getHolidayType());

//        holder.firstHalf.setOnCheckedChangeListener(null);
//        holder.secondHalf.setOnCheckedChangeListener(null);

        holder.firstHalf.setEnabled(day.isFirstHalfEnabled());
        holder.secondHalf.setEnabled(day.isSecondHalfEnabled());
        holder.firstHalf.setChecked(day.isFirstHalfChecked());
        holder.secondHalf.setChecked(day.isSecondHalfChecked());


        holder.firstHalf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed() && callback != null) {
                    //holder.firstHalf.setChecked(isChecked);
                    callback.onHolidayRequestCountChange(isChecked);
                    day.setFirstHalfChecked(isChecked);
                    requestDays.set(position, day);
                    notifyItemChanged(position);
                }
            }
        });

        holder.secondHalf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed() && callback != null) {
                    //holder.secondHalf.setChecked(isChecked);
                    callback.onHolidayRequestCountChange(isChecked);
                    day.setSecondHalfChecked(isChecked);
                    requestDays.set(position, day);
                    notifyItemChanged(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (requestDays != null) {
            return requestDays.size();
        }
        return 0;
    }
/*

    private String getDayNameFromDate(Date date) {
        java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("EEEE");
        String dayName = dateFormat.format(date);
        return dayName.substring(0, Math.min(dayName.length(), 3));
    }

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String format = formatter.format(date);

        return format;
    }

    private boolean isOfficialHoliday(Date date) {
        if (getDayNameFromDate(date).equals("Sat") || getDayNameFromDate(date).equals("Fri")) {
            return true;
        }

        return false;
    }
*/


}
