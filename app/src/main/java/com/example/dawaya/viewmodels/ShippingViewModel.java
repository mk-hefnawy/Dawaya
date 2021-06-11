package com.example.dawaya.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.dawaya.models.AddressModel;
import com.example.dawaya.models.ProductModel;

import java.util.ArrayList;

public class ShippingViewModel extends ViewModel {

    AddressModel uiShippingAddress;
    ArrayList<ProductModel> uiShippingProducts;
    
    public void sendOrder(String address, Double totalPrice, ArrayList<ProductModel> products){

    }
}
