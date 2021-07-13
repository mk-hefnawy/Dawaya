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

    ArrayList<OrderModel> peripherals = new ArrayList<>();
    ItemClickInterface anInterface;
    String notificationOrderId = "";


    // added another param to the constructor, the interface
    public MyOrdersAdapter(ArrayList<OrderModel> peripherals , ItemClickInterface anInterface) {
        this.peripherals  = peripherals ;
        this.anInterface = anInterface;
    }

    public MyOrdersAdapter(ArrayList<OrderModel> peripherals, ItemClickInterface anInterface, String notificationOrderId) {
        this.peripherals = peripherals;
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
            if (peripherals.get(position).getOrderId().equals(notificationOrderId)){
                holder.orderTime.setText(peripherals .get(position).getOrderDate());
                String str;
                str = String.valueOf(peripherals .get(position).getOrderTotalPrice()) + " EGP";
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
        holder.orderTime.setText(peripherals .get(position).getOrderDate());

        //Section Price
        String str;
        str = String.valueOf(peripherals .get(position).getOrderTotalPrice()) + " EGP";
        holder.orderTotalPrice.setText(str);
    }}
    @Override
    public int getItemCount() {
        /*if (!notificationOrderId.equals("")){
            return 1;
        }*/
        return peripherals.size();
    }

     class MyOrdersViewHolder extends RecyclerView.ViewHolder {

        TextView orderTime, orderTotalPrice, orderId, reOrder, rate;
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

            //Handling Click Action
            peripheralsView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    anInterface.onItemClick(peripherals.get(getAdapterPosition()).getOrderId());
                }
            });

            reOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //TODO el mafrod a2om fat7 saf7t el shipping bel ma3lomat elly fe el order el 2deem
                    anInterface.onReOrderClicked(peripherals.get(getAdapterPosition()).getOrderId());
                }
            });
            rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    anInterface.onRateClicked(peripherals.get(getAdapterPosition()).getOrderId());
                }
            });
        }
    }
}
