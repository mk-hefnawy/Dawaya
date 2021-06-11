package com.example.dawaya.models;

import java.util.ArrayList;

public class TransientProductModel {

    ArrayList<ProductModel> products;
    String orderId;

    public TransientProductModel(ArrayList<ProductModel> products, String orderId) {
        this.products = products;
        this.orderId = orderId;
    }

    public ArrayList<ProductModel> getProducts() {
        return products;
    }
    public void setProducts(ArrayList<ProductModel> productModel) {
        this.products = productModel;
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
