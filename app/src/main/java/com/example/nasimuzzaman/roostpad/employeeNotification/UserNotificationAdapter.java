package com.example.nasimuzzaman.roostpad.employeeNotification;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.pendingRequests.RequestAdapter;

import java.util.List;

/**
 * Created by nasimuzzaman on 11/19/17.
 */

public class UserNotificationAdapter extends RecyclerView.Adapter<UserNotificationAdapter.MyViewHolder> {

    List<UserNotification> notificationList;

    public UserNotificationAdapter(List<UserNotification> notificationList) {
        this.notificationList = notificationList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_notification_resource_file, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserNotification notification = notificationList.get(position);

        holder.replyText.setText("Your request for leave from " + notification.getFromDate().toString() + " to "
                + notification.getToDate().toString() + " is " + notification.getStatus());

        holder.messageBoxText.setText("Your personal holiday left: " + notification.getHoliday());
    }

    @Override
    public int getItemCount() {
        if(notificationList != null) {
            return notificationList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView replyText;
        public TextView messageBoxText;
        private Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            messageBoxText = (TextView) itemView.findViewById(R.id.available_holiday_text);
            replyText = (TextView) itemView.findViewById(R.id.reply_text);
            context = itemView.getContext();
        }
    }
}
