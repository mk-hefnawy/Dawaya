package com.example.dawaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dawaya.R;

import java.util.ArrayList;

public class PhoneNumbersAdapter extends RecyclerView.Adapter<PhoneNumbersAdapter.PhNumViewHolder> {

    ArrayList<String> phoneNumbers;

    public PhoneNumbersAdapter(ArrayList<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @NonNull
    @Override
    public PhNumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_number_list_item, parent, false);
        return new PhNumViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull PhNumViewHolder holder, int position) {
            holder.phoneNumber.setText(phoneNumbers.get(position));
    }

    @Override
    public int getItemCount() {
        return phoneNumbers.size();
    }

    public class PhNumViewHolder extends RecyclerView.ViewHolder {
        TextView phoneNumber;
        public PhNumViewHolder(@NonNull View itemView) {
            super(itemView);

            phoneNumber = itemView.findViewById(R.id.phone_number);

        }
    }
}
