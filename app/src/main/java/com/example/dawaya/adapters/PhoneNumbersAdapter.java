package com.example.dawaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dawaya.R;
import com.example.dawaya.interfaces.PhoneNumbersInterface;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class PhoneNumbersAdapter extends RecyclerView.Adapter<PhoneNumbersAdapter.PhNumViewHolder> {

    ArrayList<String> phoneNumbers;
    PhoneNumbersInterface anInterface;

    public PhoneNumbersAdapter(ArrayList<String> phoneNumbers,  PhoneNumbersInterface anInterface) {
        this.phoneNumbers = phoneNumbers;
        this.anInterface = anInterface;
    }

    @NonNull
    @Override
    public PhNumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_phone_numbers_recycler_view_item, parent, false);
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
        Chip editPhoneNumber, deletePhoneNumber;
        public PhNumViewHolder(@NonNull View itemView) {
            super(itemView);

            phoneNumber = itemView.findViewById(R.id.phone_number);
            editPhoneNumber = itemView.findViewById(R.id.update_phone_number);
            deletePhoneNumber = itemView.findViewById(R.id.delete_phone_number);

            editPhoneNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    anInterface.onEditPhoneNumberClicked(phoneNumbers.get(getAdapterPosition()));
                }
            });

            deletePhoneNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    anInterface.onDeletePhoneNumberClicked(phoneNumbers.get(getAdapterPosition()));
                }
            });

        }
    }
}
