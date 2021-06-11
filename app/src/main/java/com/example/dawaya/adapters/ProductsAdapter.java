package com.example.dawaya.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dawaya.R;
import com.example.dawaya.interfaces.CartInterface;
import com.example.dawaya.interfaces.ProductsInterface;
import com.example.dawaya.models.ProductModel;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

    ArrayList<ProductModel> products;
    ProductsInterface productsInterface;
    Boolean isCartProduct = false;

    public ProductsAdapter(ArrayList<ProductModel> products, Boolean isCartProduct) {
        this.products = products;
        this.isCartProduct = isCartProduct;

    }

    public ProductsAdapter(ArrayList<ProductModel> products, ProductsInterface productsInterface) {
        this.products = products;
        this.productsInterface = productsInterface;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_product_item,parent,false);

        return new ProductsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        holder.productName.setText(products.get(position).getName());
        holder.productPrice.setText(String.valueOf(products.get(position).getPrice()));
        if (products.get(position).getQuantity() == 0) {
            holder.productQuantity.setText(R.string.out_of_stock);
            holder.productQuantity.setTextColor(Color.parseColor("#BD2032"));
        }else {
            holder.productQuantity.setText(R.string.available);

        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductsViewHolder extends RecyclerView.ViewHolder{
        TextView productName, productPrice, productQuantity, quantity;
        Button addToCartBtn;
        ImageView increaseQuantity, decreaseQuantity;
        LinearLayout quantityContainer;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            addToCartBtn = itemView.findViewById(R.id.add_to_cart_btn);

            quantity = itemView.findViewById(R.id.quantity_text_view);
            increaseQuantity = itemView.findViewById(R.id.increase_quantity);
            decreaseQuantity = itemView.findViewById(R.id.decrease_quantity);

            quantityContainer = itemView.findViewById(R.id.cart_quantity_container);

            addToCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productsInterface.onAddToCartClicked(products.get(getAdapterPosition()).getCode());
                }
            });

            increaseQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currentQuantity = Integer.parseInt(quantity.getText().toString());
                    quantity.setText(String.valueOf(currentQuantity+ 1));
                    products.get(getAdapterPosition()).setQuantityToBuy(currentQuantity+ 1);
                }
            });

            decreaseQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currentQuantity = Integer.parseInt(quantity.getText().toString());
                    quantity.setText(String.valueOf(currentQuantity - 1));
                    products.get(getAdapterPosition()).setQuantityToBuy(currentQuantity - 1);
                }
            });

            if (isCartProduct) {
                addToCartBtn.setVisibility(View.GONE);
                quantityContainer.setVisibility(View.VISIBLE);
            }
        }
    }
}
