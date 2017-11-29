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

import com.binjar.prefsdroid.Preference;
import com.example.nasimuzzaman.roostpad.PrefKeys;
import com.example.nasimuzzaman.roostpad.R;
import com.example.nasimuzzaman.roostpad.authentication.LoginResponse;
import com.example.nasimuzzaman.roostpad.gmail.GMailSender;
import com.example.nasimuzzaman.roostpad.libraryPackage.CustomLibrary;
import com.example.nasimuzzaman.roostpad.replyToLeaveRequest.ReplyToLeaveRequestClient;
import com.example.nasimuzzaman.roostpad.replyToLeaveRequest.ReplyToLeaveRequestCredential;
import com.example.nasimuzzaman.roostpad.replyToLeaveRequest.ReplyToLeaveRequestResponse;
import com.example.nasimuzzaman.roostpad.replyToLeaveRequest.ReplyToLeaveRequestService;
import com.example.nasimuzzaman.roostpad.userNotificationDetails.UserNotificationDetailsActivity;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nasimuzzaman on 11/11/17.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {

    List<Request> requestList;
    LoginResponse userInfo;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView messageBoxText;
        public Button btnPendingRequests;
        public Button btnAcceptRequest;
        public Button btnRejectRequest;
        private Context context;


        public MyViewHolder(View itemView) {
            super(itemView);
            messageBoxText = (TextView) itemView.findViewById(R.id.messageBox);
            btnPendingRequests = (Button) itemView.findViewById(R.id.btnPendingRequestNotification);
            btnAcceptRequest = (Button) itemView.findViewById(R.id.btn_Accept_Request);
            btnRejectRequest = (Button) itemView.findViewById(R.id.btn_Reject_Request);
            context = itemView.getContext();
        }
    }

    public RequestAdapter(List<Request> requestList) {
        this.requestList = requestList;
        userInfo = Preference.getObject(PrefKeys.USER_INFO, LoginResponse.class);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Request request = requestList.get(position);

        //holder.btnPendingRequests.setText(request.getName() + " apply for leave from " + request.getFromDate() + " to " +
          //      request.getToDate() + " for total "+request.getDays() + " days");

        String btnText = prepareShortStatementForPendingRequestsNotification(request.getInfo());
        btnText = request.getName() + btnText;

        holder.btnPendingRequests.setText(btnText);

        holder.messageBoxText.setText(request.getMessage());

        holder.btnPendingRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.context, UserNotificationDetailsActivity.class);
                intent.putExtra("info", request.getInfo());
                holder.context.startActivity(intent);
            }
        });

        holder.btnAcceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = request.getId();
                String email = request.getEmail();
                double days = request.getDays();
                String status = "accepted";

                final ReplyToLeaveRequestCredential credential = new ReplyToLeaveRequestCredential();
                credential.setId(id);
                credential.setEmail(email);
                credential.setDays(days);
                credential.setStatus(status);
                credential.setEmailOfAuthor(userInfo.getEmail());
                credential.setTokenOfAuthor(userInfo.getToken());

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
                double days = request.getDays();
                String status = "rejected";

                final ReplyToLeaveRequestCredential credential = new ReplyToLeaveRequestCredential();
                credential.setId(id);
                credential.setEmail(email);
                credential.setDays(days);
                credential.setStatus(status);
                credential.setEmailOfAuthor(userInfo.getEmail());
                credential.setTokenOfAuthor(userInfo.getToken());

                ReplyToLeaveRequestService service = new ReplyToLeaveRequestClient().createService();
                Call<ReplyToLeaveRequestResponse> call = service.replyToLeaveRequest(credential);
                call.enqueue(new Callback<ReplyToLeaveRequestResponse>() {
                    @Override
                    public void onResponse(Call<ReplyToLeaveRequestResponse> call, Response<ReplyToLeaveRequestResponse> response) {
                        ReplyToLeaveRequestResponse body = response.body();
                        if(body != null) {
                            if(body.getStatusCode() == 200) {
                                // show success message
                                Toast.makeText(holder.context, body.getMessage(), Toast.LENGTH_SHORT);
                                // reply to corresponding employee through mail
                                sendReply(credential , holder.context);
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
    }

    public String prepareShortStatementForPendingRequestsNotification(String info) {

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

    //using javax.mail
    private void sendReply(final ReplyToLeaveRequestCredential credential, Context context) {

        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("roostpaddb@gmail.com", "Kaz123Mig");
                    sender.sendMail("Reply from ROOSTPAD",
                            "Your request for leave has been "+credential.getStatus()+"\nPlease check notification from your app",
                            "roostpaddb@gmail.com",
                            credential.getEmail());
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
