package com.example.dawaya.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.models.AddressModel;
import com.example.dawaya.models.OrderPeripheralsModel;
import com.example.dawaya.models.TransientProductModel;
import com.example.dawaya.repositories.MyOrdersRepo;

import java.util.ArrayList;

public class MyOrdersViewModel extends ViewModel {

    MutableLiveData<ArrayList<OrderPeripheralsModel>> peripheralsResponseLiveData = new MutableLiveData<>();

    MutableLiveData<TransientProductModel> productsResponseLiveData = new MutableLiveData<>();
    MutableLiveData<Integer> feedBackStatusLiveData = new MutableLiveData<>();

    MyOrdersRepo myOrdersRepo = MyOrdersRepo.getInstance(); //Singleton

    public MyOrdersViewModel() {
    }

    public MutableLiveData<ArrayList<OrderPeripheralsModel>> getPrephiralsLiveData() {
        return peripheralsResponseLiveData;
    }

    public MutableLiveData<TransientProductModel> getProductsResponseLiveData() {
        return productsResponseLiveData;
    }

    public MutableLiveData<Integer> getFeedBackStatusLiveData() {
        return feedBackStatusLiveData;
    }

    public void sendUserFeedBack(String userId, String orderId, String feedBack){
        myOrdersRepo.sendUserFeedBack(userId, orderId, feedBack);
        observeFeedBackStatus();
    }

    private void observeFeedBackStatus() {
        myOrdersRepo.feedBackStatusLiveData.observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                feedBackStatusLiveData.setValue(integer);
            }
        });
    }


    public void getPeripherals(){
        myOrdersRepo.sendPeripheralsRequest();
        //Logging here causes a null pointer exception
        observePeripheralsLiveData();


    }
    private void observePeripheralsLiveData() {
        myOrdersRepo.peripheralsResponseLiveData.observeForever(new Observer<ArrayList<OrderPeripheralsModel>>() {
            @Override
            public void onChanged(ArrayList<OrderPeripheralsModel> orderPrephiralsModels) {
                updatePeripheralsLiveData();
            }
        });
    }
    private void updatePeripheralsLiveData() {
        peripheralsResponseLiveData.setValue(myOrdersRepo.getPeripheralsResponseLiveData().getValue());

    }


    /*public void getAddresses(){
        myOrdersRepo.sendAddressesRequest();
        observeAddressesLiveData();
    }*/
    /*private void observeAddressesLiveData() {
        myOrdersRepo.addressesResponseLiveData.observeForever(new Observer<ArrayList<AddressModel>>() {
            @Override
            public void onChanged(ArrayList<AddressModel> addressModels) {
                updateAddressesLiveData();
            }
        });
    }
    private void updateAddressesLiveData() {
        addressesResponseLiveData.setValue(myOrdersRepo.getAddressesResponseLiveData().getValue());
    }*/


    public void getProducts(String orderId){
        myOrdersRepo.sendProductsRequest(orderId);
        observeProductsLiveData();
    }
    private void observeProductsLiveData() {
        myOrdersRepo.productsResponseLiveData.observeForever(new Observer<TransientProductModel>() {
            @Override
            public void onChanged(TransientProductModel transientProductModels) {
                Log.d("TAG", "onChanged: " + transientProductModels.getProducts().get(0).getName());
                updateProductsLiveData();
            }
        });
    }
    private void updateProductsLiveData() {
        productsResponseLiveData.setValue(myOrdersRepo.getProductsResponseLiveData().getValue());
    }


}
