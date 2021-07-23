package com.example.dawaya.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dawaya.R;
import com.example.dawaya.adapters.MyOrdersAdapter;
import com.example.dawaya.adapters.OrderDetailsAdapter;
import com.example.dawaya.models.AddressModel;
import com.example.dawaya.models.OrderModel;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.models.TransientProductModel;
import com.example.dawaya.utils.App;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderDetailsFragment extends Fragment {
    Context context = App.getAppContext();


    ArrayList<ProductModel> products = new ArrayList<>();
    String orderId, address;
    String [] addressHolder = new String[5];
    TextView addressCounty ,addressStreet, addressBuildingNo, addressFloorNo, addressApartmentNo;

    RecyclerView recyclerView;


    public OrderDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         //orderId = this.getArguments().getString("orderId");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order_details, container, false);

        String orderString = this.getArguments().getString("order");
        OrderModel order = new Gson().fromJson(orderString, OrderModel.class);


        products = order.getProducts();
        address = order.getOrderAddress();

        for (int i=0 ; i<address.split(",").length ; i++){
            addressHolder[i] = address.split(",")[i];
        }

        addressCounty  = view.findViewById(R.id.address_county);
        addressStreet  = view.findViewById(R.id.address_street);
        addressBuildingNo  = view.findViewById(R.id.address_building_no);
        addressFloorNo  = view.findViewById(R.id.address_floor_no);
        addressApartmentNo  = view.findViewById(R.id.address_apartment_no);

        String postFix;

        addressCounty.setText(addressHolder[0]);

        postFix = "Street: " + addressHolder[1];
        addressStreet.setText(postFix);

        postFix = "Building No: " + addressHolder[2];
        addressBuildingNo.setText(postFix);

        postFix = "Floor No: " + addressHolder[3];
        addressFloorNo.setText(postFix);

        postFix = "Apartment No: " + addressHolder[4];
        addressApartmentNo.setText(postFix);


       //Recycler View Logic
        OrderDetailsAdapter adapter = new OrderDetailsAdapter(products); // takes the interface as 'this' because the activity implements it
        recyclerView = view.findViewById(R.id.order_details_products_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        return view;

    }


}