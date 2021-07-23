package com.example.dawaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dawaya.R;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.models.TransientProductModel;

import java.util.ArrayList;

public class OrderDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    ArrayList<ProductModel> products = new ArrayList<>();

    public OrderDetailsAdapter(ArrayList<ProductModel> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details_product_item,parent,false);
            return new OrderDetailsItemViewHolder(itemView);
        }
        else if(viewType == TYPE_HEADER){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details_products_header,parent,false);
            return new OrderDetailsHeaderViewHolder(itemView);
        }

        else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof OrderDetailsHeaderViewHolder){

            ((OrderDetailsHeaderViewHolder) holder).productNameLabel.setText(R.string.product);
            ((OrderDetailsHeaderViewHolder) holder).productPriceLabel.setText(R.string.price);
            ((OrderDetailsHeaderViewHolder) holder).productQuantityLabel.setText(R.string.quantity);
        }
        else if(holder instanceof OrderDetailsItemViewHolder){

            ((OrderDetailsItemViewHolder) holder).productName.setText(products.get(position-1).getName());
            ((OrderDetailsItemViewHolder) holder).productPrice.setText(String.valueOf(products.get(position-1).getPrice()));
            ((OrderDetailsItemViewHolder) holder).productQuantity.setText(String.valueOf(products.get(position-1).getQuantityToBuy()));




        }
    }

    @Override
    public int getItemCount() {
        return products.size()+1; // 1 is for the header
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    class OrderDetailsItemViewHolder extends  RecyclerView.ViewHolder{

        TextView productName, productPrice, productQuantity;

         public OrderDetailsItemViewHolder(@NonNull View itemView) {
             super(itemView);
             productName = itemView.findViewById(R.id.order_details_product_name);
             productPrice = itemView.findViewById(R.id.order_details_product_price);
             productQuantity = itemView.findViewById(R.id.order_details_product_quantity);
         }
     }

    class OrderDetailsHeaderViewHolder extends  RecyclerView.ViewHolder{

        TextView productNameLabel, productPriceLabel, productQuantityLabel;

        public OrderDetailsHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameLabel = itemView.findViewById(R.id.product_name_label);
            productPriceLabel = itemView.findViewById(R.id.product_price_label);
            productQuantityLabel = itemView.findViewById(R.id.product_quantity_label);
        }
    }
}
