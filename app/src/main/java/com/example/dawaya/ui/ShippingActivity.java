package com.example.dawaya.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dawaya.R;
import com.example.dawaya.adapters.BottomSheetAdapter;
import com.example.dawaya.adapters.ProductsAdapter;
import com.example.dawaya.interfaces.BottomSheetInterface;
import com.example.dawaya.models.AddressModel;
import com.example.dawaya.models.OrderModel;
import com.example.dawaya.models.OrdersTable;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.notifications.FeedBackBroadCastReceiver;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.utils.Utils;
import com.example.dawaya.viewmodels.AddressBookViewModel;
import com.example.dawaya.viewmodels.ShippingViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDateTime;
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
    String timeToViewModel;
    String billId;
    OrdersTable orderRecord;

    View shippingRoot;

    ImageView backButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shiping);

        shippingViewModel = new ViewModelProvider(this).get(ShippingViewModel.class);

        //Back
        backButton = findViewById(R.id.back_from_shipping);
        backButton.setOnClickListener(view -> {
            super.onBackPressed();
        });


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
        //Notice
        /*addressBookViewModel.sendAddressesRequest(SharedPrefs.read(SharedPrefs.USER_ID, ""));
        addressBookViewModel.getAddressesLiveData().observeForever(addressModels -> {
            addresses = addressModels;

            //Notice user cannot press change button unless the addresses have come from the server
            change = findViewById(R.id.shipping_change_address_button);
            change.setOnClickListener(view -> showBottomSheet());
        });*/


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

        placeOrder.setOnClickListener(view -> {
            timeToViewModel = Utils.dateToString(LocalDateTime.now());
            sendOrder(addressToViewModel, totalPriceToViewModel,timeToViewModel, shippingProducts);
            observeBillId();
            observeStatus();

        });

        timeToViewModel = Utils.dateToString(LocalDateTime.now());
        //sendOrder(addressToViewModel, totalPriceToViewModel,timeToViewModel, shippingProducts);
        //observeBillId();
        observeStatus();

    }

    private void observeOrderInfo(){
        shippingViewModel.getOrderLivaData().observe(this, orderModel -> {
            createNotificationIntent(orderModel, 5000);
        });
    }

    private void createNotificationIntent (OrderModel orderModel, long delay) {

        Intent notificationIntent = new Intent(this, FeedBackBroadCastReceiver.class);

        notificationIntent.putExtra("NotificationId", "1" );
        notificationIntent.putExtra("OrderId", orderModel.getOrderId());
        notificationIntent.putExtra("NotificationBody", "Tell us about your experience with our ordering system");
        notificationIntent.putExtra("NotificationTitle","It's Feedback Time!");
        notificationIntent.putExtra("ChannelId","1");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0 , notificationIntent , PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, delay, pendingIntent) ;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendOrder(String address, Double totalPrice, String time, ArrayList<ProductModel> products) {

        shippingViewModel.sendOrder(address, totalPrice, time, products);
    }

    private void observeBillId() {
        shippingViewModel.getBill_id().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                billId = s;
            }
        });
    }
    private void observeStatus(){
        //shippingViewModel.getStatusLiveData().observeForever(this::reactToStatus);
        reactToStatus(1);
    }

    private void reactToStatus(Integer status) {
         shippingRoot = (ConstraintLayout) findViewById(R.id.shipping_root);
        if (status == 1){
            Snackbar.make(shippingRoot, "Order Placed Successfully", Snackbar.LENGTH_SHORT);
            //TODO orderState stuff
            //it has to be ONGOING and after the interval it has to be DONE
            //String userId = SharedPrefs.read(SharedPrefs.USER_ID, "");
            ////

            Log.v("from react to status", "rr");
            observeOrderInfo();

            ////
            /*String userId = "4";
            billId = "5";
            orderRecord = new OrdersTable(billId, userId,  timeToViewModel,"2020-1-1 12:01:20" , "BABY", "FOOD");*/

            //Room Stuff
            //insertOrderIntoDataBase();

            // you can now set a notification for the feedback after a period of time
        }
        else {
            Snackbar.make(shippingRoot, "Something Went Wrong", Snackbar.LENGTH_SHORT);
        }
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