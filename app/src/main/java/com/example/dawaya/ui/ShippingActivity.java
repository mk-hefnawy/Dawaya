package com.example.dawaya.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawaya.R;
import com.example.dawaya.adapters.BottomSheetAdapter;
import com.example.dawaya.adapters.ProductsAdapter;
import com.example.dawaya.interfaces.BottomSheetInterface;
import com.example.dawaya.models.AddressModel;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.viewmodels.AddressBookViewModel;
import com.example.dawaya.viewmodels.ShippingViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ShippingActivity extends AppCompatActivity implements BottomSheetInterface {

    TextView chosenAddress, totalPrice;
    ImageView change;
    Button placeOrder;
    RecyclerView bottomSheetRecyclerView;
    BottomSheetAdapter bottomSheetAdapter;
    ArrayList<AddressModel> addresses = new ArrayList<>();


    RecyclerView shippingProductsRecyclerView;
    ProductsAdapter shippingProductsAdapter;

    AddressBookViewModel addressBookViewModel;
    ShippingViewModel shippingViewModel;

    //Stuff to be sent to ViewModel

    String addressToViewModel;
    Double totalPriceToViewModel;
    ArrayList<ProductModel> shippingProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shiping);

        SharedPrefs.init();

        shippingViewModel = new ViewModelProvider(this).get(ShippingViewModel.class);

        //Shipping products

        String shippingProductsString = getIntent().getStringExtra("cartProductsString");
        //new TypeToken<ArrayList<ProductModel>>(){}.getType()  // to avoid making a wrapper class
        shippingProducts = new Gson().fromJson(shippingProductsString,  new TypeToken<ArrayList<ProductModel>>(){}.getType());

        //Recycler View
        shippingProductsAdapter = new ProductsAdapter(shippingProducts, true);
        shippingProductsRecyclerView = findViewById(R.id.shipping_products_recycler_view);
        shippingProductsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        shippingProductsRecyclerView.setAdapter(shippingProductsAdapter);


        //Chosen Address TextView
        chosenAddress = findViewById(R.id.shipping_chosen_address_text_view);
        String address = SharedPrefs.read(SharedPrefs.ADDRESS,"");
        chosenAddress.setText(address);


        //Todo if there is no address in that account , the user should add their address first
        // disable place order button // change edit button to add button


        //Change-button Click Listener
        addressBookViewModel = new AddressBookViewModel();
        addressBookViewModel.sendAddressesRequest(SharedPrefs.read(SharedPrefs.USER_ID, ""));
        addressBookViewModel.getAddressesLiveData().observeForever(new Observer<ArrayList<AddressModel>>() {
            @Override
            public void onChanged(ArrayList<AddressModel> addressModels) {
                addresses = addressModels;

                //Notice user cannot press change button unless the addresses have come from the server
                change = findViewById(R.id.shipping_change_address_button);
                change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showBottomSheet();
                    }
                });
            }
        });


        // Total Price TextView
        totalPrice = findViewById(R.id.shipping_total_price);
        Double price = 0.0;
        for (int i = 0 ; i < shippingProducts.size(); i++){
            price += shippingProducts.get(i).getPrice();
        }
        totalPrice.setText(String.valueOf(price));


        // Place Order Button
        placeOrder = findViewById(R.id.place_order_btn);
        // Preparing Data
        addressToViewModel = chosenAddress.getText().toString();
        totalPriceToViewModel = Double.valueOf(totalPrice.getText().toString());
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOrder();
            }
        });



    }

    private void sendOrder() {

    }

    private void showBottomSheet() {

        final Dialog bottomSheet = new Dialog(this);
        bottomSheet.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bottomSheet.setContentView(R.layout.bottom_sheet);

        bottomSheetAdapter = new BottomSheetAdapter(addresses, this);
        bottomSheetRecyclerView = bottomSheet.findViewById(R.id.addresses_to_choose_from_recycler_view);
        bottomSheetRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bottomSheetRecyclerView.setAdapter(bottomSheetAdapter);

        bottomSheet.show();
        bottomSheet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 580);
        bottomSheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheet.getWindow().getAttributes().windowAnimations = R.style.BottomSheetAnimation;

        bottomSheet.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onAddressClicked(int pos) {
        String address = addresses.get(pos).getCounty() + ',' +
                addresses.get(pos).getStreet() + ',' +
                addresses.get(pos).getBuildingNo() + ',' +
                addresses.get(pos).getFloorNo() + ',' +
                addresses.get(pos).getApartmentNo();

        SharedPrefs.write(SharedPrefs.ADDRESS, address);
        chosenAddress.setText(SharedPrefs.read(SharedPrefs.ADDRESS, ""));
    }


    public void setShippingProducts(ArrayList<ProductModel> shippingProducts) {
        this.shippingProducts = shippingProducts;
    }
}