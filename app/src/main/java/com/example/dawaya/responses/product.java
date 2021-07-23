package com.example.dawaya.responses;

import com.example.dawaya.responses.id;

public class product {

    id id;
    int quantity;
    Double totalPrice;
    Double unitPrice;

    public product(com.example.dawaya.responses.id id, int quantity, Double totalPrice, Double unitPrice) {
        this.id = id;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.unitPrice = unitPrice;
    }

    public com.example.dawaya.responses.id getId() {
        return id;
    }

    public void setId(com.example.dawaya.responses.id id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
