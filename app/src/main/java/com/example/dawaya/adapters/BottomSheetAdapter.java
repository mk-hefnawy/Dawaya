package com.example.dawaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dawaya.R;
import com.example.dawaya.interfaces.BottomSheetInterface;
import com.example.dawaya.models.AddressModel;

import java.util.ArrayList;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.ViewHolder> {

    ArrayList<AddressModel> addresses;
    BottomSheetInterface bottomSheetInterface;

    public BottomSheetAdapter(ArrayList<AddressModel> addresses,  BottomSheetInterface bottomSheetInterface) {
        this.addresses = addresses;
        this.bottomSheetInterface = bottomSheetInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_item, parent, false);
        return new BottomSheetAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String postFix;

        postFix = addresses.get(position).getCounty() + ", " + addresses.get(position).getStreet();
        holder.countyAndStreet.setText(postFix);

        postFix = "Building No: " + addresses.get(position).getBuildingNo();
        holder.building.setText(postFix);

        postFix = "Floor No: " + addresses.get(position).getFloorNo();
        holder.floor.setText(postFix);
        postFix = "Apartment No: " + addresses.get(position).getApartmentNo();
        holder.apartment.setText(postFix);
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView countyAndStreet, building, floor, apartment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countyAndStreet = itemView.findViewById(R.id.addresses_to_choose_from_text_view);
            building = itemView.findViewById(R.id.address_to_choose_building);
            floor = itemView.findViewById(R.id.address_to_choose_floor);
            apartment = itemView.findViewById(R.id.address_to_choose_apartment);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetInterface.onAddressClicked(getAdapterPosition());

                }
            });
        }
    }
}
