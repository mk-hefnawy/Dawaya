package com.example.dawaya.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionModel {

    String id;
    String url;
    String sendTime;
    String replyTime;
    Bitmap bitmap;
    ArrayList<ProductModel> products;
    Boolean areProductsAttached = true;

    public PrescriptionModel(String id, String url, String sendTime, String replyTime, ArrayList<ProductModel> products) {
        this.url = url;
        this.products = products;
        this.id = id;
        this.sendTime = sendTime;
        this.replyTime = replyTime;
    }

    public PrescriptionModel(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.products = new ArrayList<>(); // for handling null pointer exception
        this.areProductsAttached = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAreProductsAttached(Boolean areProductsAttached) {
        this.areProductsAttached = areProductsAttached;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductModel> products) {
        this.products = products;
    }

    public Boolean getAreProductsAttached() {
        return areProductsAttached;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }
}
