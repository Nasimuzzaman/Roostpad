package com.example.nasimuzzaman.roostpad.pendingRequests;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.gmail.GMailSender;
import com.example.nasimuzzaman.roostpad.replyToLeaveRequest.ReplyToLeaveRequestClient;
import com.example.nasimuzzaman.roostpad.replyToLeaveRequest.ReplyToLeaveRequestCredential;
import com.example.nasimuzzaman.roostpad.replyToLeaveRequest.ReplyToLeaveRequestResponse;
import com.example.nasimuzzaman.roostpad.replyToLeaveRequest.ReplyToLeaveRequestService;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nasimuzzaman on 11/11/17.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {

    List<Request> requestList;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView messageBoxText;
        public TextView applicationText;
        public Button btnAcceptRequest;
        public Button btnRejectRequest;
        private Context context;


        public MyViewHolder(View itemView) {
            super(itemView);
            messageBoxText = (TextView) itemView.findViewById(R.id.messageBox);
            applicationText = (TextView) itemView.findViewById(R.id.applicationText);
            btnAcceptRequest = (Button) itemView.findViewById(R.id.btn_Accept_Request);
            btnRejectRequest = (Button) itemView.findViewById(R.id.btn_Reject_Request);
            context = itemView.getContext();
        }
    }

    public RequestAdapter(List<Request> requestList) {
        this.requestList = requestList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Request request = requestList.get(position);

        holder.applicationText.setText(request.getName() + " apply for leave from " + request.getFromDate() + " to " +
                request.getToDate() + " for total "+request.getDays() + " days");
        holder.messageBoxText.setText(request.getMessage());

        holder.btnAcceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = request.getId();
                String email = request.getEmail();
                int days = request.getDays();
                String status = "accepted";

                final ReplyToLeaveRequestCredential credential = new ReplyToLeaveRequestCredential();
                credential.setId(id);
                credential.setEmail(email);
                credential.setDays(days);
                credential.setStatus(status);

                ReplyToLeaveRequestService service = new ReplyToLeaveRequestClient().createService();
                Call<ReplyToLeaveRequestResponse> call = service.replyToLeaveRequest(credential);
                call.enqueue(new Callback<ReplyToLeaveRequestResponse>() {
                    @Override
                    public void onResponse(Call<ReplyToLeaveRequestResponse> call, Response<ReplyToLeaveRequestResponse> response) {
                        ReplyToLeaveRequestResponse body = response.body();
                        if(body != null) {
                            if(body.getStatusCode() == 200) {
                                // save user info
                                // com.binjar.prefsdroid.Preference.putObject(PrefKeys.USER_INFO, body);
                                // show success message
                                Toast.makeText(holder.context, body.getMessage(), Toast.LENGTH_SHORT);
                                // reply to corresponding employee through mail
                                sendReply(credential, holder.context);
                                // refresh notification page
                                requestList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, requestList.size());
                            } else Toast.makeText(holder.context, body.getError(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ReplyToLeaveRequestResponse> call, Throwable t) {
                        Toast.makeText(holder.context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.btnRejectRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = request.getId();
                String email = request.getEmail();
                int days = request.getDays();
                String status = "rejected";

                final ReplyToLeaveRequestCredential credential = new ReplyToLeaveRequestCredential();
                credential.setId(id);
                credential.setEmail(email);
                credential.setDays(days);
                credential.setStatus(status);

                ReplyToLeaveRequestService service = new ReplyToLeaveRequestClient().createService();
                Call<ReplyToLeaveRequestResponse> call = service.replyToLeaveRequest(credential);
                call.enqueue(new Callback<ReplyToLeaveRequestResponse>() {
                    @Override
                    public void onResponse(Call<ReplyToLeaveRequestResponse> call, Response<ReplyToLeaveRequestResponse> response) {
                        ReplyToLeaveRequestResponse body = response.body();
                        if(body != null) {
                            if(body.getStatusCode() == 200) {
                                // save user info
                                // com.binjar.prefsdroid.Preference.putObject(PrefKeys.USER_INFO, body);
                                // show success message
                                Toast.makeText(holder.context, body.getMessage(), Toast.LENGTH_SHORT);
                                // reply to corresponding employee through mail
                                sendReply(credential , holder.context);
                                // refresh notification page
                                requestList.remove(position);
                                notifyItemRemoved(position);

                            } else Toast.makeText(holder.context, body.getError(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ReplyToLeaveRequestResponse> call, Throwable t) {
                        Toast.makeText(holder.context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //using javax.mail
    private void sendReply(final ReplyToLeaveRequestCredential credential, Context context) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();

        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("roostpaddb@gmail.com", "Kaz123Mig");
                    sender.sendMail("Reply from ROOSTPAD",
                            "Your request has been "+credential.getStatus(),
                            "roostpaddb@gmail.com",
                            credential.getEmail());
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
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
