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

import com.bumptech.glide.Glide;
import com.example.dawaya.R;
import com.example.dawaya.interfaces.CartInterface;
import com.example.dawaya.interfaces.ProductsInterface;
import com.example.dawaya.models.ProductModel;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

    ArrayList<ProductModel> products;
    ProductsInterface productsInterface;
    Boolean isCartProduct = false;
    Boolean isPrescriptionProduct = false;

    public ProductsAdapter(ArrayList<ProductModel> products, Boolean isCartProduct) {
        this.products = products;
        this.isCartProduct = isCartProduct;

    }

    public ProductsAdapter(ArrayList<ProductModel> products, ProductsInterface productsInterface) {
        this.products = products;
        this.productsInterface = productsInterface;
    }

    public ProductsAdapter(ArrayList<ProductModel> products, Boolean isCartProduct, ProductsInterface productsInterface) {
        this.products = products;
        this.isCartProduct = isCartProduct;
        this.productsInterface = productsInterface;
    }

    public ProductsAdapter(ArrayList<ProductModel> products, Boolean isCartProduct ,Boolean isPrescriptionProduct) {
        this.products = products;
        this.isPrescriptionProduct = isPrescriptionProduct;
        this.isCartProduct = isCartProduct;
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

        if (isPrescriptionProduct) {
            holder.favBtn.setVisibility(View.GONE);
            holder.checkBtn.setVisibility(View.VISIBLE);
            holder.quantityContainer.setVisibility(View.GONE);
        }

        if (products.get(position).getImageUrl() != null && !products.get(position).getImageUrl().equals("")){
            Glide.with(holder.itemView.getContext()).load(products.get(position).getImageUrl())
                    .into((holder.productImage));
        }

        if (products.get(position).getQuantity() == 0) {
            holder.productQuantity.setText(R.string.out_of_stock);
            holder.productQuantity.setTextColor(Color.parseColor("#BD2032"));
        }else {
            holder.productQuantity.setText(R.string.available);

        }
    }

    @Override
    public int getItemCount() {
        if (products.isEmpty()){
            return 0;
        }
        return products.size();
    }

    class ProductsViewHolder extends RecyclerView.ViewHolder{
        TextView productName, productPrice, productQuantity, quantity;
        Button addToCartBtn;
        ImageView increaseQuantity, decreaseQuantity, productImage, checkBtn, favBtn;
        LinearLayout quantityContainer, productContainer;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            addToCartBtn = itemView.findViewById(R.id.add_to_cart_btn);

            quantity = itemView.findViewById(R.id.quantity_text_view);
            increaseQuantity = itemView.findViewById(R.id.increase_quantity);
            decreaseQuantity = itemView.findViewById(R.id.decrease_quantity);
            productImage = itemView.findViewById(R.id.product_image);

            quantityContainer = itemView.findViewById(R.id.cart_quantity_container);
            productContainer = itemView.findViewById(R.id.product_container);
            checkBtn = itemView.findViewById(R.id.check_btn);
            favBtn = itemView.findViewById(R.id.fav_btn);


            favBtn.setOnClickListener(view -> {
                productsInterface.onFavouriteClicked(products.get(getAdapterPosition()));
            });

            addToCartBtn.setOnClickListener(view -> productsInterface.onAddToCartClicked(products.get(getAdapterPosition())));

            increaseQuantity.setOnClickListener(view -> {
                int currentQuantity = Integer.parseInt(quantity.getText().toString());
                quantity.setText(String.valueOf(currentQuantity+ 1));
                products.get(getAdapterPosition()).setQuantityToBuy(currentQuantity+ 1);
            });

            decreaseQuantity.setOnClickListener(view -> {
                int currentQuantity = Integer.parseInt(quantity.getText().toString());
                quantity.setText(String.valueOf(currentQuantity - 1));
                products.get(getAdapterPosition()).setQuantityToBuy(currentQuantity - 1);
            });

            productContainer.setOnClickListener(view -> {
            if (checkBtn.getTag().equals("checked")){
                checkBtn.setTag("unchecked");
                checkBtn.setImageResource(R.drawable.outline_radio_button_unchecked_black_36);
                products.get(getAdapterPosition()).setCheckedInPrescriptionProducts(false);
            }
            else {
                checkBtn.setTag("checked");
                checkBtn.setImageResource(R.drawable.outline_check_circle_black_36);
                products.get(getAdapterPosition()).setCheckedInPrescriptionProducts(true);
            }

            });

            if (isCartProduct) {
                addToCartBtn.setVisibility(View.GONE);
                quantityContainer.setVisibility(View.VISIBLE);
            }
        }
    }
}
