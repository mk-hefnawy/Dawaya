package com.example.dawaya.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.models.ProductModel;
import com.example.dawaya.repositories.MyOrdersRepo;

import java.util.ArrayList;

public class CartViewModel extends ViewModel {

    ArrayList<ProductModel> initial = new ArrayList<>();
    MutableLiveData<ArrayList<ProductModel>> cartProducts = new MutableLiveData<>();


    /** Singleton **/
    private static CartViewModel instance = null;
    public static CartViewModel getInstance(){
        if(instance == null){
            instance = new CartViewModel();
        }
        return instance;
    }
    private CartViewModel(){
        cartProducts.setValue(initial);
    }

    public MutableLiveData<ArrayList<ProductModel>> getCartProducts() {
        return cartProducts;
    }

    public Boolean addProductToCart(ProductModel product) {

        ArrayList<ProductModel> accumulator = new ArrayList<>();

        if (!cartProducts.getValue().isEmpty()) {
            Log.v("CartViewModel", "array is not empty" );
            if (cartProducts.getValue().contains(product)) {
                Log.v("CartViewModel", "repeated" );
                return true;
            }
            else {
                Log.v("CartViewModel", "not repeated" );
                accumulator = cartProducts.getValue();
                accumulator.add(product);
                cartProducts.setValue(accumulator);
                Log.v("CartViewModel", cartProducts.getValue().get(0).getName());
                Log.v("CartViewModel", String.valueOf(cartProducts.getValue().size()));
                return false;
            }

        } else {
            Log.v("CartViewModel", "array is empty" );
            accumulator.add(product);
            cartProducts.setValue(accumulator);
            Log.v("CartViewModel", cartProducts.getValue().get(0).getName());
            Log.v("CartViewModel", String.valueOf(cartProducts.getValue().size()));
            Log.v("CartViewModel", "Done" );
            return false;
        }
    }
}