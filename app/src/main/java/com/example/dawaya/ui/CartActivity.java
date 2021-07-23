package com.example.dawaya.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.example.dawaya.interfaces.ProductsInterface;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.viewmodels.CartViewModel;
import com.example.dawaya.viewmodels.WishListViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

public class CartActivity extends AppCompatActivity implements ProductsInterface {

    Context context = App.getAppContext();

    CartViewModel viewModel;
    WishListViewModel wishListViewModel;

    ArrayList<ProductModel> cartProducts;
    RecyclerView recyclerView;

    ProductsAdapter adapter;
    Boolean isCartProduct = true;

    Button proceed, deleteAll;
    ImageView backFromCart;

    LinearLayout emptyCartContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        viewModel = CartViewModel.getInstance();
        wishListViewModel = new ViewModelProvider(this).get(WishListViewModel.class);
        recyclerView = findViewById(R.id.cart_recycler_view);

        emptyCartContainer = findViewById(R.id.empty_cart_image_container);
        //deleteAll = findViewById(R.id.delete_all);
        proceed = findViewById(R.id.proceed_btn);
        backFromCart = findViewById(R.id.back_from_my_cart);


        //Getting cart products


        /*viewModel.getAllCartProductsFromDataBase();
        viewModel.getCartProducts().observe(this, new Observer<ArrayList<ProductModel>>() {
            @Override
            public void onChanged(ArrayList<ProductModel> productModels) {
                cartProducts = productModels;
                System.out.println("onChanged" + productModels.get(0).getImageUrl());
                emptyCartContainer.setVisibility(View.GONE);
                proceed.setVisibility(View.VISIBLE);
                adapter = new ProductsAdapter(cartProducts, isCartProduct);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));

            }
        });*/
        SharedPrefs.init();
        String cartProductsString = SharedPrefs.read(SharedPrefs.CART_PRODUCTS,"");
        if (!cartProductsString.equals("")) {

            HashMap<String, ArrayList<ProductModel>> cartProductsMap = new Gson().fromJson(cartProductsString,
                    new TypeToken<HashMap<String, ArrayList<ProductModel>>>() {
                    }.getType());

            cartProducts = cartProductsMap.get(SharedPrefs.USER_ID);

            if (cartProducts == null || cartProducts.size() == 0) {cartProducts = new ArrayList<>();}

            emptyCartContainer.setVisibility(View.GONE);
            proceed.setVisibility(View.VISIBLE);
            adapter = new ProductsAdapter(cartProducts, true, true);
            recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
            recyclerView.setAdapter(adapter);
        }
        else {
            cartProducts = new ArrayList<>();
            emptyCartContainer.setVisibility(View.VISIBLE);
            proceed.setVisibility(View.GONE);
        }

        //Back Button
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


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.v("onSwiped", "Done");
                int position = viewHolder.getAdapterPosition();
                viewModel.deleteProductFromCart(cartProducts.get(position));
                cartProducts.remove(position);
                adapter.notifyDataSetChanged();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

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

    @Override
    public void onAddToCartClicked(ProductModel product) {

    }

    @Override
    public void onFavouriteClicked(ProductModel product) {
        ArrayList<ProductModel> productModel = new ArrayList<>();
        productModel.add(product);
        wishListViewModel.addToWishList(productModel, "append");
    }
}