package com.example.dawaya.interfaces;

import com.example.dawaya.models.OrderModel;

public interface ItemClickInterface {

    void onItemClick(OrderModel order, String code, int index);
    void onReOrderClicked(String orderId);
    void onRateClicked(String orderId);
    void onFeedbackClicked(String orderId);

}
