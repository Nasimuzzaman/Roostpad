package com.example.nasimuzzaman.roostpad.pendingRequests;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nasimuzzaman.roostpad.R;

import java.util.List;

/**
 * Created by nasimuzzaman on 11/11/17.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {

    List<Request> requestList;


    public class MyViewHolder extends RecyclerView.ViewHolder{

//        public TextView nameText;
//        public TextView fromDateText;
//        public TextView toDateText;
//        public TextView daysText;
        public TextView messageBoxText;
        public TextView applicationText;


        public MyViewHolder(View itemView) {
            super(itemView);
//            nameText = (TextView) itemView.findViewById(R.id.nameText);
//            fromDateText = (TextView) itemView.findViewById(R.id.fromDateText);
//            toDateText = (TextView) itemView.findViewById(R.id.toDateText);
//            daysText = (TextView) itemView.findViewById(R.id.daysText);
            messageBoxText = (TextView) itemView.findViewById(R.id.messageBox);
            applicationText = (TextView) itemView.findViewById(R.id.applicationText);

        }
    }

    public RequestAdapter(List<Request> requestList) {
        this.requestList = requestList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Request request = requestList.get(position);
//        holder.nameText.setText(request.getName() + " apply for leave from ");
//        holder.fromDateText.setText(request.getFromDate() + " to ");
//        holder.toDateText.setText(request.getToDate() );
//        holder.daysText.setText(" for total "+request.getDays() + " days");
        holder.applicationText.setText(request.getName() + " apply for leave from " + request.getFromDate() + " to " +
                request.getToDate() + " for total "+request.getDays() + " days");
        holder.messageBoxText.setText(request.getMessage());
    }

    @Override
    public int getItemCount() {
        if(requestList != null)
            return requestList.size();

        return 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pending_requests_resource_file, parent, false);

        return new MyViewHolder(view);
    }
}
