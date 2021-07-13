package com.example.dawaya.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_products")
public class CartProductsTable {

    @PrimaryKey(autoGenerate = true)
    int id;
    String code;
    String name;
    Double price;
    int quantity;

    public CartProductsTable(String code, String name, Double price, int quantity) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
