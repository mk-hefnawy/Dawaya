package com.example.dawaya.viewmodels;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.Local.LocalDataBase;
import com.example.dawaya.models.CartProductsTable;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.repositories.CartRepo;
import com.example.dawaya.repositories.MyOrdersRepo;
import com.example.dawaya.utils.App;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartViewModel extends ViewModel {

    CartRepo repo = CartRepo.getInstance();
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

    public Boolean addProductToCart(Context context, ProductModel product) {

        ArrayList<ProductModel> accumulator = new ArrayList<>();

        repo.insertOrderIntoDataBase(new CartProductsTable(product.getCode(), product.getName(), product.getPrice(), product.getQuantity()));
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


  /*  private void getAllCartProductsFromDataBase(Context context){
        LocalDataBase localDataBase = LocalDataBase.getInstance(context);
        Completable.fromRunnable(() -> {
             localDataBase.mainDao().getAllCartProducts()
                    ;
        });


    }*/
}