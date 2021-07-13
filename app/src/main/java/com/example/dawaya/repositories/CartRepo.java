package com.example.dawaya.repositories;

import android.content.Context;
import android.util.Log;

import com.example.dawaya.Local.LocalDataBase;
import com.example.dawaya.models.CartProductsTable;
import com.example.dawaya.utils.App;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartRepo {

    private static CartRepo instance;

    public static CartRepo getInstance() {
        if (instance == null) return new CartRepo();
        else return instance;
    }

    public void insertOrderIntoDataBase(CartProductsTable productsTable) {
        LocalDataBase localDataBase = LocalDataBase.getInstance(App.getAppContext());
        localDataBase.mainDao().addProductToCart(productsTable)
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


}