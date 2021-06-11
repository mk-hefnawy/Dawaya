package com.example.dawaya.models;

public class ProductModel {
    String code;
    String name;
    String firstCategory;
    String secondCategory;
    Double price;
    int quantity;
    int quantityToBuy = 1;
    //int imageResourceId;


    public ProductModel(String code, String name, Double price, int quantity) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public ProductModel(String code, String name, String firstCategory, String secondCategory, Double price, int quantity) {
        this.code = code;
        this.name = name;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.price = price;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFirstCategory() {
        return firstCategory;
    }
    public void setFirstCategory(String firstCategory) {
        this.firstCategory = firstCategory;
    }

    public String getSecondCategory() {
        return secondCategory;
    }
    public void setSecondCategory(String secondCategory) {
        this.secondCategory = secondCategory;
    }

    public int getQuantityToBuy() {
        return quantityToBuy;
    }

    public void setQuantityToBuy(int quantityToBuy) {
        this.quantityToBuy = quantityToBuy;
    }
}
