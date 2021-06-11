package com.example.dawaya.models;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.stream.Stream;

public class CartAddedModel extends ArrayList<CartAddedModel> {
    public String name;
    public Float price;
    public int quantity;
    public int raminitem ;

    public CartAddedModel(String name, Float price, int quantity,int raminitem) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.raminitem=raminitem;
    }

    public int getRaminitem() {
        return raminitem;
    }

    public void setRaminitem(int raminitem) {
        this.raminitem = raminitem;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;


    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public Stream<CartAddedModel> stream() {
        return null;
    }
}
