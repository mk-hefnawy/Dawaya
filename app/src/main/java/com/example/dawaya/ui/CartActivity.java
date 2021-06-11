package com.example.dawaya.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawaya.R;
import com.example.dawaya.adapters.ProductsAdapter;
import com.example.dawaya.interfaces.CartInterface;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.viewmodels.CartViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    Context context = App.getAppContext();

    ArrayList<ProductModel> cartProducts;
    RecyclerView recyclerView;
    CartViewModel viewModel;
    ProductsAdapter adapter;
    Boolean isCartProduct = true;

    Button proceed;
    ImageView backFromCart;

    LinearLayout emptyCartContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        viewModel = CartViewModel.getInstance();
        //viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        recyclerView = findViewById(R.id.cart_recycler_view);

        cartProducts = viewModel.getCartProducts().getValue();
        emptyCartContainer = findViewById(R.id.empty_cart_image_container);



        proceed = findViewById(R.id.proceed_btn);
        proceed.setVisibility(View.GONE);

        if (!cartProducts.isEmpty()){
            emptyCartContainer.setVisibility(View.GONE);
            proceed.setVisibility(View.VISIBLE);
            adapter = new ProductsAdapter(cartProducts, isCartProduct);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        //Back Button
        backFromCart = findViewById(R.id.back_from_my_cart);
        backFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartActivity.super.onBackPressed();
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onProceedButtonClicked();
            }
        });


    }

    private void onProceedButtonClicked() {

        if (cartProducts.size() != 0) {
            Intent intent = new Intent(CartActivity.this, ShippingActivity.class);
            String cartProductsString = new Gson().toJson(cartProducts);
            intent.putExtra("cartProductsString", cartProductsString);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "No items in your cart", Toast.LENGTH_SHORT).show();
        }
    }




    public ArrayList<ProductModel> getCartProducts() {
        return cartProducts;
    }
    public void setCartProducts(ArrayList<ProductModel> cartProducts) {
        this.cartProducts = cartProducts;
    }

}