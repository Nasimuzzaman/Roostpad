package com.example.nasimuzzaman.roostpad.contacts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nasimuzzaman.roostpad.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nasimuzzaman on 11/13/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    List<Contacts> contactsList;

    public ContactAdapter(List<Contacts> contactsList) {
        this.contactsList = contactsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_contacts_resource_file, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contacts contacts = contactsList.get(position);

        final List<String> spinnerArrayList = new ArrayList<>(
                Arrays.asList("Admin", "CTO", "Employee")
        );

        holder.nameText.setText(contacts.getName());
        holder.emailText.setText(contacts.getEmail());
        holder.contactText.setText(contacts.getContact());
        holder.designationText.setText(contacts.getDesignation());
        holder.roleText.setSelection(spinnerArrayList.indexOf(contacts.getRole()));
        holder.holidayText.setText(""+contacts.getHoliday());


    }

    @Override
    public int getItemCount() {
        if(contactsList != null) {
            return contactsList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public EditText nameText;
        public EditText emailText;
        public EditText contactText;
        public EditText designationText;
        public Spinner roleText;
        public EditText holidayText;
        public Button btnEdit;
        public Button btnDelete;
        public Button btnUpdate;
        private Context context;


        public MyViewHolder(View itemView) {
            super(itemView);
            nameText = (EditText) itemView.findViewById(R.id.nameText);
            emailText = (EditText) itemView.findViewById(R.id.emailText);
            contactText = (EditText) itemView.findViewById(R.id.contactText);
            designationText = (EditText) itemView.findViewById(R.id.designationText);
            roleText = (Spinner) itemView.findViewById(R.id.roleSpinner);
            holidayText = (EditText) itemView.findViewById(R.id.holidayText);

            btnEdit = (Button) itemView.findViewById(R.id.btn_edit_contact);
            btnDelete = (Button) itemView.findViewById(R.id.btn_delete_contact);
            btnUpdate = (Button) itemView.findViewById(R.id.btn_update_contact);

            final List<EditText> editTextList = new ArrayList<>(
                    Arrays.asList(nameText, emailText, contactText, designationText, holidayText)
            );

            disableEditText(editTextList);
            roleText.setEnabled(false);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enableEditText(editTextList);
                    roleText.setEnabled(true);
                    btnUpdate.setVisibility(View.VISIBLE);
                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disableEditText(editTextList);
                    roleText.setEnabled(false);
                }
            });

            context = itemView.getContext();
        }

        private void disableEditText(List<EditText> editTextList) {
            for(EditText editText : editTextList) {
                editText.setEnabled(false);
                editText.setClickable(false);
            }
        }

        private void enableEditText(List<EditText> editTextList) {
            for(EditText editText : editTextList) {
                editText.setEnabled(true);
                editText.setClickable(true);
            }
        }
    }
}
