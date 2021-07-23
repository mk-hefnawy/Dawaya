package com.example.dawaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dawaya.R;
import com.example.dawaya.interfaces.ItemClickInterface;
import com.example.dawaya.models.OrderModel;


import java.util.ArrayList;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyOrdersViewHolder> {

    ArrayList<OrderModel> orders;
    ItemClickInterface anInterface;
    String notificationOrderId = "";


    // added another param to the constructor, the interface
    public MyOrdersAdapter(ArrayList<OrderModel> orders, ItemClickInterface anInterface) {
        this.orders = orders;
        this.anInterface = anInterface;
    }

    public MyOrdersAdapter(ArrayList<OrderModel> orders, ItemClickInterface anInterface, String notificationOrderId) {
        this.orders = orders;
        this.anInterface = anInterface;
        this.notificationOrderId = notificationOrderId;
    }

    @NonNull
    @Override
    public MyOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_list_item, parent, false);
        return new MyOrdersViewHolder(listItem);
    }
    @Override
    public void onBindViewHolder(@NonNull MyOrdersViewHolder holder, int position) {

        if (!notificationOrderId.equals("")){
            if (orders.get(position).getOrderId().equals(notificationOrderId)){
                holder.orderTime.setText(orders.get(position).getOrderDate());
                String str;
                str = String.valueOf(orders.get(position).getOrderTotalPrice()) + " EGP";
                holder.orderTotalPrice.setText(str);
            }
            else {
                holder.itemView.setVisibility(View.GONE);

                // to make other list views have 0 size
                ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                params.height = 0;
                params.width = 0;
                holder.itemView.setLayoutParams(params);
            }
        }
        else {
        //Section Time
        holder.orderTime.setText(orders.get(position).getOrderDate());
        String orderIdText = "Order Id: " + orders.get(position).getOrderId();
        holder.orderId.setText(orderIdText);

        //Section Price
        String str;
        str = String.valueOf(orders.get(position).getOrderTotalPrice()) + " EGP";
        holder.orderTotalPrice.setText(str);
    }}
    @Override
    public int getItemCount() {
        /*if (!notificationOrderId.equals("")){
            return 1;
        }*/
        return orders.size();
    }

     class MyOrdersViewHolder extends RecyclerView.ViewHolder {

        TextView orderTime, orderTotalPrice, orderId, reOrder, rate, feedBack;
        ImageView orderImage;
        LinearLayout peripheralsView;


        public MyOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            this.orderId = itemView.findViewById(R.id.order_id);
            this.orderTime = itemView.findViewById(R.id.order_time);
            this.orderTotalPrice = itemView.findViewById(R.id.order_total_price);
            this.orderImage = itemView.findViewById(R.id.order_image);

            this.reOrder = itemView.findViewById(R.id.orders_reorder);
            this.rate = itemView.findViewById(R.id.order_rate);
            this.peripheralsView = itemView.findViewById(R.id.peripherals);
            this.feedBack = itemView.findViewById(R.id.order_feedback);

            //Handling Click Action
            peripheralsView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    anInterface.onItemClick(orders.get(getAdapterPosition()), orders.get(getAdapterPosition()).getProducts().get(0).getCode(),
                            0);
                }
            });

            reOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //TODO el mafrod a2om fat7 saf7t el shipping bel ma3lomat elly fe el order el 2deem
                    anInterface.onReOrderClicked(orders.get(getAdapterPosition()).getOrderId());
                }
            });
            rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    anInterface.onRateClicked(orders.get(getAdapterPosition()).getOrderId());
                }
            });

            feedBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    anInterface.onFeedbackClicked(orders.get(getAdapterPosition()).getOrderId());
                }
            });
        }
    }
}
