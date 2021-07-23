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
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawaya.R;
import com.example.dawaya.adapters.MyOrdersAdapter;
import com.example.dawaya.interfaces.ItemClickInterface;
import com.example.dawaya.models.OrderModel;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.models.TransientProductModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.utils.Utils;
import com.example.dawaya.viewmodels.MyOrdersViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

public class MyOrdersActivity extends AppCompatActivity implements ItemClickInterface {

    Context context = App.getAppContext();
    ItemClickInterface myOrdersRecyclerViewInterface = this;


    ImageView backIcon, cartIcon;
    TextView toolBarTitle;
    RecyclerView recyclerView;

    MyOrdersViewModel viewModel;

    ArrayList<OrderModel> orders;

    //TransientProductModel transientProducts;
    TransientProductModel selectedTransientProducts;

    OrderDetailsFragment orderDetailsFragment;
    Bundle orderDetailsFragmentBundle;

    Boolean addressesReceived = false, productsReceived = false;
    ImageView sadFeedBack, neutralFeedBack, happyFeedBack;
    EditText feedbackMessageET;
    String notificationOrderId = "";

    @RequiresApi(api = Build.VERSION_CODES.O) // for LocalDateTime.of
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        if (getIntent().hasExtra("orderId")){
            notificationOrderId = Objects.requireNonNull(getIntent().getExtras()).getString("orderId");
            Toast.makeText(this, notificationOrderId, Toast.LENGTH_SHORT).show();
        }

        viewModel = new ViewModelProvider(this).get(MyOrdersViewModel.class);
        viewModel.getAllUserOrders(SharedPrefs.USER_ID);

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
        orders = new ArrayList<>();

        observeOrdersLiveData();

    }

    @Override // For Recycler View Item Click Handling
    public void onItemClick(OrderModel order, String code, int index) {
        //viewModel.getAddresses();
        viewModel.getOrderProducts(order, code, index);

      //  observeAddressesResponse();
        observeProductsResponse(order);
         // i called this after products observation as the latter (address) has the logic of starting the fragment

        }

    @Override
    public void onReOrderClicked(String orderId) {

    }


    @Override
    public void onFeedbackClicked(String orderId) {
        //open a dialog with a large edit text to take the feedback
        Dialog feedBackDialog = Utils.getBasicDialog(this, R.layout.dialog_message_feedback);
        feedBackDialog.getWindow().setLayout(700, ViewGroup.LayoutParams.WRAP_CONTENT);
        feedBackDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.rounded_corners));
        feedBackDialog.getWindow().setGravity(Gravity.CENTER);
        feedBackDialog.show();

        handleOkOrCancel(feedBackDialog, orderId);

        //when user clicks ok, i will send what is in the edit text
    }

    private void handleOkOrCancel(Dialog feedBackDialog, String orderId) {
        TextView okBtn = feedBackDialog.findViewById(R.id.message_feedback_ok);
        TextView cancelBtn = feedBackDialog.findViewById(R.id.message_feedback_cancel);

        cancelBtn.setOnClickListener(view -> {feedBackDialog.dismiss();});

        okBtn.setOnClickListener(view -> {

            feedbackMessageET = ((TextInputLayout)feedBackDialog.findViewById(R.id.feedback_message_container)).getEditText();

            //check if the feedback message is not empty
            if (feedbackMessageET.getText() != null && !feedbackMessageET.getText().equals("")){
                System.out.println("HELLO");
                viewModel.sendUserFeedBackMessage(SharedPrefs.read(SharedPrefs.USER_ID, ""), orderId, feedbackMessageET.getText().toString());
                viewModel.getFeedBackStatusLiveData().observe(this, integer -> {
                    if (integer == 1) {
                        Toast.makeText(this, "Feedback was Sent Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, HomeActivity.class));
                    }
                    else {
                        Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }

                    feedBackDialog.dismiss();
                });
            }
        });
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


    /*private void startOrderDetailsFragment(String orderId, TransientProductModel transientProductModel){
        String address = "";
        Bundle bundle = new Bundle();

        for (int i = 0; i< orders.size() ; i++){
            if (orders.get(i).getOrderId().equals(orderId)){
                address = orders.get(i).getOrderAddress();
            }
        }

        bundle.putString("address", address);
        orderDetailsFragment.setProducts(transientProductModel.getProducts());
        orderDetailsFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.order_details_fragment_placeholder, orderDetailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }*/

    private void startOrderDetailsFragment(OrderModel order){

        Bundle bundle = new Bundle();
        String orderString = new Gson().toJson(order);
        bundle.putString("order", orderString);

        orderDetailsFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.order_details_fragment_placeholder, orderDetailsFragment);
        fragmentTransaction.addToBackStack("OrderDetailsFragment");
        fragmentTransaction.commit();
    }

    private void observeOrdersLiveData(){
        viewModel.getOrdersLiveData().observe(this, new Observer<ArrayList<OrderModel>>() {
            @Override
            public void onChanged(ArrayList<OrderModel> orderModels) {

                orders = orderModels;
                MyOrdersAdapter adapter = new MyOrdersAdapter(orders, myOrdersRecyclerViewInterface, notificationOrderId); // takes the interface as 'this' because the activity implements it
                if (!notificationOrderId.equals("")){
                    int orderToGetFeedBackPosition = getOrderToGetFeedBackPosition(orders);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    linearLayoutManager.scrollToPosition(orderToGetFeedBackPosition);
                    recyclerView.setLayoutManager(linearLayoutManager);
                }else {
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                }

                recyclerView.setAdapter(adapter);
            }
        });
    }

    private int getOrderToGetFeedBackPosition(ArrayList<OrderModel> peripherals) {
        int i = 0;
        while (i< peripherals.size()-1){
            if (peripherals.get(i).getOrderId().equals(notificationOrderId)){
                return i+1;
            }
            i++;
        }
        return -1;
    }

    /* private void getAllOrders() {
            LocalDataBase localDataBase = LocalDataBase.getInstance(getApplicationContext());
            localDataBase.mainDao().getOrders()
                    .subscribeOn(Schedulers.computation())
                    .subscribe(new io.reactivex.rxjava3.core.Observer<List<OrdersTable>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull List<OrdersTable> orderModels) {
                            for (int i = 0; i<orderModels.size() ; i++){
                            Log.v(String.valueOf(i), orderModels.get(i).getMainCategories());

                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
        */
    private void observeProductsResponse(OrderModel order) {
        viewModel.getProductsLiveData().observe(this, new Observer<ArrayList<ProductModel>>() {
            @Override
            public void onChanged(ArrayList<ProductModel> products) {


                for (int i = 0; i<products.size() && i<order.getProducts().size() ; i++){

                    for (int j = i+1 ; j<products.size() && j<order.getProducts().size() ; j++){

                        if (order.getProducts().get(i).getCode().equals(products.get(j).getCode())){

                            order.getProducts().get(i).setName(products.get(j).getName());
                            order.getProducts().get(i).setFirstCategory(products.get(j).getFirstCategory());
                            order.getProducts().get(i).setSecondCategory(products.get(j).getSecondCategory());
                            order.getProducts().get(i).setPosition(products.get(j).getPosition());

                        }
                    }
                }
                //productsReceived = true;
                startOrderDetailsFragment(order);
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