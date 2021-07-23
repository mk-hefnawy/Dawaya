package com.example.dawaya.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;


import com.example.dawaya.models.ProductModel;
import com.example.dawaya.utils.App;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
/*

public class CartRepo {


    LiveData<List<ProductModel>> productsToCart = new MutableLiveData<>();

    ArrayList<ProductModel> tempProductsToCart = new ArrayList<>();

    private static CartRepo instance;

    public static CartRepo getInstance() {
        if (instance == null) return new CartRepo();
        else return instance;
    }


    public void insertCartProductIntoDataBase(ProductModel product) {

        localDataBase.mainDao().addProductToCart(product)
        .subscribeOn(Schedulers.computation())
        .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.v("onSubscribe", "Done");
            }

            @Override
            public void onComplete() {
                Log.v("onComplete", "Done");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.v("onError", "Done");
                e.printStackTrace();
            }
        });

    }

    public void getAllCartProducts(){
        */
/*LiveDataReactiveStreams.fromPublisher(localDataBase.mainDao().getAllCartProducts()).observeForever(new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                productsToCart.setValue(productModels);
                Log.v("getAllonChanged", productsToCart.getValue().get(0).getName());
            }
        });*//*


        */
/*localDataBase.mainDao().getAllCartProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ProductModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                            Log.v("onSubscribeGet", "Ok");
                    }

                    @Override
                    public void onSuccess(@NonNull List<ProductModel> productModels) {
                        Log.v("onSuccess", productModels.get(0).getName());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                });*//*


        productsToCart = localDataBase.mainDao().getAllCartProducts();
        Log.v("getAllCartProducts", productsToCart.getValue().get(0).getName());


}
    public void deleteAllCartProducts() {
        localDataBase.mainDao().deleteAllCartProducts()
        .subscribeOn(Schedulers.computation())
        .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.v("DeleteAll", "All Rows are deleted");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }
        });
    }

    public LiveData<List<ProductModel>> getProductsToCart() {
        return productsToCart;
    }
}*/
