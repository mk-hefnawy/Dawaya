package com.example.dawaya.models;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class OrderPeripheralsModel {

    String orderId;
    Double orderTotalPrice;
    String orderDate;
    String orderState;
    String orderAddress;


    public OrderPeripheralsModel(String orderId, Double orderTotalPrice, String orderDate, String orderState, String orderAddress) {
        this.orderId = orderId;
        this.orderTotalPrice = orderTotalPrice;
        this.orderDate = orderDate;
        this.orderState = orderState;
        this.orderAddress = orderAddress;
    }

    public Double getOrderTotalPrice() {
        return orderTotalPrice;
    }
    public void setOrderTotalPrice(Double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }
}
