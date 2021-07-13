package com.example.dawaya.Local;

import androidx.room.Dao;
import androidx.room.FtsOptions;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dawaya.models.CartProductsTable;
import com.example.dawaya.models.OrderModel;
import com.example.dawaya.models.OrdersTable;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface CartProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addProductToCart(CartProductsTable product);

/*
    @Query("SELECT * FROM cart_products")
    Observable<List<CartProductsTable>> getAllCartProducts();

    @Query("UPDATE cart_products SET quantity = (:newQuantity) WHERE code = (:code)")
    Completable updateCartProductQuantity(String code, int newQuantity);*/

    /*@Query("SELECT * FROM orders WHERE userId = (:userId) ORDER By id DESC LIMIT 1;")
    Observable<OrdersTable> getLastOrder(String userId);*/
}
