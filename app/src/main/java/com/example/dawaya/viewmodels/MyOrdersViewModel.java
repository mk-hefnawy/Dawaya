package com.example.dawaya.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.models.FeedBackMessageModel;
import com.example.dawaya.models.OrderModel;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.models.TransientProductModel;
import com.example.dawaya.repositories.MyOrdersRepo;
import com.example.dawaya.repositories.SearchRepo;

import java.util.ArrayList;

public class MyOrdersViewModel extends ViewModel {

    MutableLiveData<ArrayList<OrderModel>> ordersLiveData = new MutableLiveData<>();

    ArrayList<ProductModel> products = new ArrayList<>();
    MutableLiveData<ArrayList<ProductModel>> productsLiveData = new MutableLiveData<>();

   // MutableLiveData<TransientProductModel> productsResponseLiveData = new MutableLiveData<>();
    MutableLiveData<Integer> feedBackStatusLiveData = new MutableLiveData<>();

    MutableLiveData<FeedBackMessageModel> feedBackMessage = new MutableLiveData<>();

    MyOrdersRepo myOrdersRepo = MyOrdersRepo.getInstance();
    SearchRepo searchRepo = SearchRepo.getInstance();

    public MyOrdersViewModel() {
    }


    public void sendUserFeedBackMessage(String userId, String orderId, String feedBackMessage){

        myOrdersRepo.sendUserMessageFeedback(userId, orderId, feedBackMessage);
        observeFeedBackStatus();
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


    public void getAllUserOrders(String userId){
        myOrdersRepo.getAllUserOrders(userId);
        //Logging here causes a null pointer exception
        observeOrdersLiveData();


    }
    private void observeOrdersLiveData() {
        myOrdersRepo.ordersLiveData.observeForever(new Observer<ArrayList<OrderModel>>() {
            @Override
            public void onChanged(ArrayList<OrderModel> orders) {
                ordersLiveData.setValue(orders);
            }
        });
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


    public void getOrderProducts(OrderModel order, String code, int index){

        if (index == order.getProducts().size()){
            productsLiveData.setValue(products);
            return;
        }

       searchRepo.searchByCode(code);
       searchRepo.productSearchedByCode.observeForever(new Observer<ProductModel>() {
           @Override
           public void onChanged(ProductModel product) {
               products.add(product);
               getOrderProducts(order, order.getProducts().get(index+1).getCode(), index+1);
           }
       });
    }
    /*private void observeProductsLiveData() {
        myOrdersRepo.productsResponseLiveData.observeForever(new Observer<TransientProductModel>() {
            @Override
            public void onChanged(TransientProductModel transientProductModels) {
                Log.d("TAG", "onChanged: " + transientProductModels.getProducts().get(0).getName());
                updateProductsLiveData();
            }
        });
    }*/

   /* private void updateProductsLiveData() {
        productsResponseLiveData.setValue(myOrdersRepo.getProductsResponseLiveData().getValue());
    }*/

    public MutableLiveData<ArrayList<ProductModel>> getProductsLiveData() {
        return productsLiveData;
    }

    public MutableLiveData<ArrayList<OrderModel>> getOrdersLiveData() {
        return ordersLiveData;
    }

    public MutableLiveData<Integer> getFeedBackStatusLiveData() {
        return feedBackStatusLiveData;
    }

    public MutableLiveData<FeedBackMessageModel> getFeedBackMessage() {
        return feedBackMessage;
    }
    public void setFeedBackMessage(MutableLiveData<FeedBackMessageModel> feedBackMessage) {
        this.feedBackMessage = feedBackMessage;
    }
}
