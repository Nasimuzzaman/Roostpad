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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nasimuzzaman on 11/27/17.
 */

public class RequestDaysAdapter extends RecyclerView.Adapter<RequestDaysAdapter.MyViewHolder> {

    List<RequestDay> requestDays;
    OnHolidayRequestCountChangeCallback callback;

    public RequestDaysAdapter(List<RequestDay> requestDays, OnHolidayRequestCountChangeCallback callback) {
        this.requestDays = requestDays;
        this.callback = callback;
    }

    public List<RequestDay> getRequestDays() {
        return requestDays;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView dayName, dayDate, holidayType;
        CheckBox firstHalf, secondHalf;

        public MyViewHolder(View itemView) {
            super(itemView);

            dayName = (TextView) itemView.findViewById(R.id.dayName);
            dayDate = (TextView) itemView.findViewById(R.id.dayDate);
            holidayType = (TextView) itemView.findViewById(R.id.holidayType);

            firstHalf = (CheckBox) itemView.findViewById(R.id.firstHalf);
            secondHalf = (CheckBox) itemView.findViewById(R.id.secondHalf);
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

        holder.firstHalf.setEnabled(day.isFirstHalfEnabled());
        holder.secondHalf.setEnabled(day.isSecondHalfEnabled());
        holder.firstHalf.setChecked(day.isFirstHalfChecked());
        holder.secondHalf.setChecked(day.isSecondHalfChecked());


        holder.firstHalf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed() && callback != null) {
                    callback.onHolidayRequestCountChange(isChecked);
                    day.setFirstHalfChecked(isChecked);
                    requestDays.set(position, day);
                    notifyItemChanged(position);
                    callback.setRequestDays(requestDays);
                }
            }
        });

        holder.secondHalf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed() && callback != null) {
                    callback.onHolidayRequestCountChange(isChecked);
                    day.setSecondHalfChecked(isChecked);
                    requestDays.set(position, day);
                    notifyItemChanged(position);
                    callback.setRequestDays(requestDays);
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

}
