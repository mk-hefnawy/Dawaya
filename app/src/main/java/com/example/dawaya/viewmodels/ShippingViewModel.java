package com.example.dawaya.viewmodels;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.models.AddressModel;
import com.example.dawaya.models.OrderModel;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.repositories.ShippingRepo;

import java.util.ArrayList;

public class ShippingViewModel extends ViewModel {

    ShippingRepo shippingRepo = ShippingRepo.getInstance();

    MutableLiveData<Integer> statusLiveData = new MutableLiveData<>();
    MutableLiveData<String> bill_id = new MutableLiveData<>();


    MutableLiveData<OrderModel> orderLivaData = new MutableLiveData<>();
    OrderModel order = new OrderModel();

    @RequiresApi(api = Build.VERSION_CODES.O) // now()
    public void sendOrder(String address, Double totalPrice, String time, ArrayList<ProductModel> products){

        order.setOrderAddress(address);
        order.setOrderTotalPrice(totalPrice);
        order.setOrderDate(time);
        order.setProducts(products);

        shippingRepo.sendOrder(address, totalPrice,time,  products);
        observeBillId();
        observeWholeOrderStatus();
    }

    private void observeBillId() {
        shippingRepo.getBill_id().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                order.setOrderId(s);
                bill_id.setValue(s);

            }
        });
    }

    private void observeWholeOrderStatus() {
        shippingRepo.getWholeOrderStatus().observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                statusLiveData.setValue(integer);
                //to send the order info to the activity
                orderLivaData.setValue(order);
            }
        });
    }

    public MutableLiveData<Integer> getStatusLiveData() {
        return statusLiveData;
    }

    public MutableLiveData<String> getBill_id() {
        return bill_id;
    }

    public MutableLiveData<OrderModel> getOrderLivaData() {
        return orderLivaData;
    }
}
