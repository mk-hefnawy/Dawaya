package com.example.dawaya.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dawaya.R;
import com.example.dawaya.adapters.ProductsAdapter;
//import com.example.dawaya.adapters.SubProductsAdapter;
import com.example.dawaya.interfaces.ProductsInterface;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.viewmodels.CartViewModel;
import com.example.dawaya.viewmodels.CategoryProductsViewModel;
import com.example.dawaya.viewmodels.WishListViewModel;

import java.util.ArrayList;


public class CategoryProductsFragment extends Fragment implements ProductsInterface {

    Context context = App.getAppContext();


    RecyclerView productsRecyclerView;
    ProductsAdapter adapter;
    ArrayList<ProductModel> products = new ArrayList<>();

    CategoryProductsViewModel viewModel;
    CartViewModel cartViewModel = CartViewModel.getInstance();
    WishListViewModel wishListViewModel;
    //SubCategpryClickInterface anInterface;

    String mainCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category_products, container, false);

        //Binding the view model
        viewModel = new ViewModelProvider(this).get(CategoryProductsViewModel.class);
        wishListViewModel = new ViewModelProvider(this).get(WishListViewModel.class);

        mainCategory = getArguments().getString("mainCategory");
        String subCategory = getArguments().getString("subCategory");

        //Log.v("From Fragment", products.get(1).getName());

        productsRecyclerView = view.findViewById(R.id.category_products_fragment_recyclerview);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ProductsAdapter(products, this);
        productsRecyclerView.setAdapter(adapter);

        return view;
    }


    public void setProducts(ArrayList<ProductModel> products) {
        this.products = products;
    }

    @Override
    public void onAddToCartClicked(ProductModel product) {
        Boolean alreadyInCart = false;
        //viewModel = new ViewModelProvider(this).get(CategoryProductsViewModel.class);
        int size = products.size();
        for (int i = 0; i < size; i++) {
            if (products.get(i).getCode().equals(product.getCode())) {
                ArrayList<ProductModel> productToCart = new ArrayList<>();
                productToCart.add(products.get(i));
                alreadyInCart = cartViewModel.addToCart(productToCart, "append");
                Log.v("OK", "OK");
            }
        }
        if (alreadyInCart)
            Toast.makeText(context, "Already in Cart", Toast.LENGTH_SHORT).show();
        //ProductModel toCartProduct = viewModel
    }

    @Override
    public void onFavouriteClicked(ProductModel product) {
        ArrayList<ProductModel> productModel = new ArrayList<>();
        productModel.add(product);

        wishListViewModel.addToWishList(productModel, "append");
    }

}
