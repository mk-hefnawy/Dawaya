package com.example.dawaya.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.dawaya.R;
import com.example.dawaya.adapters.ProductsAdapter;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.viewmodels.CartViewModel;
import com.example.dawaya.viewmodels.WishListViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

public class WishListActivity extends AppCompatActivity {

    WishListViewModel viewModel;
    CartViewModel cartViewModel;
    RecyclerView recyclerView;
    ProductsAdapter adapter;
    ArrayList<ProductModel> products;

    Button addToCartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        viewModel = new ViewModelProvider(this).get(WishListViewModel.class);
        cartViewModel = CartViewModel.getInstance();

        recyclerView = findViewById(R.id.wish_list_recycler_view);

      /*  viewModel.getAllWishListProducts();
        viewModel.getWishListProducts().observe(this, new Observer<ArrayList<ProductModel>>() {
            @Override
            public void onChanged(ArrayList<ProductModel> productModels) {
                if (productModels.size() > 0){
                    products = productModels;
                    adapter = new ProductsAdapter(products, true, true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(WishListActivity.this));
                    recyclerView.setAdapter(adapter);

                    handleAddToCartAction();
                }
                else {
                    //TODO
                    //your wish list is empty
                }


            }
        });*/

        String allUsersWishListProductsString = SharedPrefs.read(SharedPrefs.WISH_LIST_PRODUCTS,"");

        if (!allUsersWishListProductsString.equals("")) {
            HashMap<String, ArrayList<ProductModel>> allUsersWishListProducts = new Gson().fromJson(allUsersWishListProductsString,
                    new TypeToken<HashMap<String, ArrayList<ProductModel>>>(){}.getType());

            products = allUsersWishListProducts.get(SharedPrefs.USER_ID);

            if (products == null || products.size() == 0) {products = new ArrayList<>();}

            adapter = new ProductsAdapter(products, true, true);
            recyclerView.setLayoutManager(new LinearLayoutManager(WishListActivity.this));
            recyclerView.setAdapter(adapter);
        }
        handleAddToCartAction();

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.v("onSwiped", "Done");
                int position = viewHolder.getAdapterPosition();
                viewModel.deleteProductFromWishList(products.get(position));
                products.remove(position);
                adapter.notifyDataSetChanged();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void handleAddToCartAction() {
        addToCartBtn = findViewById(R.id.add_wish_list_products_to_cart);
        ArrayList<ProductModel> productsToCart = new ArrayList<>();

        addToCartBtn.setOnClickListener(view -> {
            //Check the selected Products
            for (int i = 0 ; i<products.size() ; i++){
                if (products.get(i).getCheckedInPrescriptionProducts()){
                    productsToCart.add(products.get(i));
                }
            }

            //Add them to cart

                cartViewModel.addToCart(productsToCart, "append");

        });


    }
}