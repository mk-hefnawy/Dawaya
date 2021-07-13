package com.example.dawaya.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class OrdersTable {
    @PrimaryKey(autoGenerate = true)
    int id;

    String orderId;
    String userId;
    String sendingDateTime;
    String estimatedDateTime;
    String mainCategories;
    String subCategories;


    public OrdersTable(String orderId, String userId, String sendingDateTime, String estimatedDateTime, String mainCategories, String subCategories) {
        this.orderId = orderId;
        this.userId = userId;
        this.sendingDateTime = sendingDateTime;
        this.estimatedDateTime = estimatedDateTime;
        this.mainCategories = mainCategories;
        this.subCategories = subCategories;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSendingDateTime() {
        return sendingDateTime;
    }
    public void setSendingDateTime(String sendingDateTime) {
        this.sendingDateTime = sendingDateTime;
    }

    public String getEstimatedDateTime() {
        return estimatedDateTime;
    }
    public void setEstimatedDateTime(String estimatedDateTime) {
        this.estimatedDateTime = estimatedDateTime;
    }

    public String getMainCategories() {
        return mainCategories;
    }
    public void setMainCategories(String mainCategories) {
        this.mainCategories = mainCategories;
    }

    public String getSubCategories() {
        return subCategories;
    }
    public void setSubCategories(String subCategories) {
        this.subCategories = subCategories;
    }
}
