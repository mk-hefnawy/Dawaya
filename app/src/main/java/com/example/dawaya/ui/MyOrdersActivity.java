package com.example.dawaya.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawaya.R;
import com.example.dawaya.adapters.MyOrdersAdapter;
import com.example.dawaya.interfaces.ItemClickInterface;
import com.example.dawaya.models.OrderPeripheralsModel;
import com.example.dawaya.models.TransientProductModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.utils.Utils;
import com.example.dawaya.viewmodels.MyOrdersViewModel;

import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity implements ItemClickInterface {

    Context context = App.getAppContext();
    ItemClickInterface myOrdersRecyclerViewInterface = this;


    ImageView backIcon, cartIcon;
    TextView toolBarTitle;
    RecyclerView recyclerView;

    MyOrdersViewModel viewModel;

    ArrayList<OrderPeripheralsModel> peripherals;

    TransientProductModel transientProducts;
    TransientProductModel selectedTransientProducts;

    OrderDetailsFragment orderDetailsFragment;
    Bundle orderDetailsFragmentBundle;

    Boolean addressesReceived = false, productsReceived = false;
    ImageView sadFeedBack, neutralFeedBack, happyFeedBack;

    @RequiresApi(api = Build.VERSION_CODES.O) // for LocalDateTime.of
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        viewModel = new ViewModelProvider(this).get(MyOrdersViewModel.class);
        viewModel.getPeripherals();

        orderDetailsFragment = new OrderDetailsFragment();

       /* *//** Tool Bar Processing **/

        backIcon = findViewById(R.id.back_from_my_orders);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyOrdersActivity.super.onBackPressed();
            }
        });


        /** Recycler View **/
        recyclerView =  findViewById(R.id.orders_recycler_view);
        peripherals = new ArrayList<>();

        observePeripheralsResponse();

    }

    @Override // For Recycler View Item Click Handling
    public void onItemClick(String orderId) {
        //viewModel.getAddresses();
        viewModel.getProducts(orderId);

      //  observeAddressesResponse();
        observeProductsResponse(orderId);
         // i called this after products observation as the latter (address) has the logic of starting the fragment

        }

    @Override
    public void onReOrderClicked(String orderId) {

    }

    @Override
    public void onRateClicked(String orderId) {
        /*
        * First: pop up a dialog to choose their rating
        * Second: call onRateChosen() which will handle the backend stuff
        * */

        Dialog ratingOrderDialog = Utils.getBasicDialog(this, R.layout.rating_dialog);
        ratingOrderDialog.getWindow().setLayout(700, ViewGroup.LayoutParams.WRAP_CONTENT);
        ratingOrderDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.rounded_corners));
        ratingOrderDialog.getWindow().setGravity(Gravity.CENTER);
        ratingOrderDialog.show();
        onRateChosen(orderId, ratingOrderDialog);


    }

    private void onRateChosen(String orderId, Dialog ratingOrderDialog) {
        sadFeedBack = ratingOrderDialog.findViewById(R.id.sad_feedback);
        neutralFeedBack = ratingOrderDialog.findViewById(R.id.neutral_feedback);
        happyFeedBack = ratingOrderDialog.findViewById(R.id.happy_feedback);

        sadFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingOrderDialog.dismiss();
                //Todo Progress Bar
                viewModel.sendUserFeedBack(SharedPrefs.read(SharedPrefs.USER_ID,""), orderId, "sad");
                observeFeedBackStatus();
            }
        });

        neutralFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingOrderDialog.dismiss();
                //Todo Progress Bar
                viewModel.sendUserFeedBack(SharedPrefs.read(SharedPrefs.USER_ID,""), orderId, "neutral");
                observeFeedBackStatus();
            }
        });

        happyFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingOrderDialog.dismiss();
                //Todo Progress Bar
                viewModel.sendUserFeedBack(SharedPrefs.read(SharedPrefs.USER_ID,""), orderId, "happy");
                observeFeedBackStatus();
            }
        });
    }

    private void observeFeedBackStatus() {
        viewModel.getFeedBackStatusLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1){
                    Toast.makeText(MyOrdersActivity.this, "Feedback Sent Successfully", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(MyOrdersActivity.this, "Cannot Send Your Feedback", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void startOrderDetailsFragment(String orderId, TransientProductModel transientProductModel){
        String address = "";
        Bundle bundle = new Bundle();

        for (int i=0 ; i<peripherals.size() ; i++){
            if (peripherals.get(i).getOrderId().equals(orderId)){
                address = peripherals.get(i).getOrderAddress();
            }
        }

        bundle.putString("address", address);
        orderDetailsFragment.setProducts(transientProductModel.getProducts());
        orderDetailsFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.order_details_fragment_placeholder, orderDetailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void observePeripheralsResponse(){
        viewModel.getPrephiralsLiveData().observe(this, new Observer<ArrayList<OrderPeripheralsModel>>() {
            @Override
            public void onChanged(ArrayList<OrderPeripheralsModel> orderPrephiralsModels) {

                peripherals = viewModel.getPrephiralsLiveData().getValue();
                Log.v("from Activity", peripherals.get(0).getOrderTotalPrice().toString());
                MyOrdersAdapter adapter = new MyOrdersAdapter(peripherals, myOrdersRecyclerViewInterface); // takes the interface as 'this' because the activity implements it
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
            }
        });
    }


    private void observeProductsResponse(String orderId) {
        viewModel.getProductsResponseLiveData().observe(this, new Observer<TransientProductModel>() {
            @Override
            public void onChanged(TransientProductModel transientProductModels) {
                transientProducts = new TransientProductModel(viewModel.getProductsResponseLiveData().getValue().getProducts(),
                        viewModel.getProductsResponseLiveData().getValue().getOrderId());
                productsReceived = true;
                startOrderDetailsFragment(orderId, transientProducts);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}