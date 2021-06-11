package com.example.dawaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dawaya.R;
import com.example.dawaya.interfaces.AddressBookInterface;
import com.example.dawaya.models.AddressModel;

import java.util.ArrayList;

public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.AddressBookViewHolder> {

    ArrayList<AddressModel> addresses = new ArrayList<>();
    AddressBookInterface addressBookInterface;

    public AddressBookAdapter(ArrayList<AddressModel> addresses, AddressBookInterface addressBookInterface) {
        this.addresses = addresses;
        this.addressBookInterface = addressBookInterface;
    }

    @NonNull
    @Override
    public AddressBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext()); //Creating an inflater
        View listItem = layoutInflater.inflate(R.layout.address_list_item,parent,false); //
        AddressBookViewHolder viewHolder = new AddressBookViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressBookViewHolder holder, int position) {

        String postFix;
        holder.county.setText(addresses.get(position).getCounty());
        postFix = "Street: " + addresses.get(position).getStreet();
        holder.street.setText(postFix);
        postFix = "Building Number: " + addresses.get(position).getBuildingNo();
        holder.buildingNumber.setText(postFix);
        postFix = "Floor Number: " + addresses.get(position).getFloorNo();
        holder.floorNumber.setText(postFix);
        postFix = "Apartment Number: " + addresses.get(position).getApartmentNo();
        holder.apartmentNumber.setText(postFix);
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }


    // User-defined class for holding list item view
    public  class AddressBookViewHolder extends RecyclerView.ViewHolder {

        TextView county, street, buildingNumber, floorNumber, apartmentNumber;
        ImageView editBtn, deleteBtn;



        public AddressBookViewHolder(@NonNull View itemView) {
            super(itemView);
            this.county= itemView.findViewById(R.id.county);
            this.street= itemView.findViewById(R.id.street);
            this.buildingNumber= itemView.findViewById(R.id.building_no);
            this.floorNumber= itemView.findViewById(R.id.floor_no);
            this.apartmentNumber= itemView.findViewById(R.id.apartment_no);
            this.editBtn = itemView.findViewById(R.id.addresses_edit_btn);
            this.deleteBtn = itemView.findViewById(R.id.addresses_delete_btn);

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addressBookInterface.onEditAddressClicked(getAdapterPosition());
                }
            });
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addressBookInterface.onDeleteAddressClicked(getAdapterPosition());
                }
            });
        }
    }

}
