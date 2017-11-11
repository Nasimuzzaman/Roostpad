package com.example.nasimuzzaman.roostpad.pendingRequests;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nasimuzzaman.roostpad.R;

/**
 * Created by nasimuzzaman on 11/11/17.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView nameText;
        public TextView fromDateText;
        public TextView toDateText;
        public TextView daysText;

        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
}
