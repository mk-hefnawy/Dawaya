package com.example.dawaya.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dawaya.R;
import com.example.dawaya.adapters.ProductsAdapter;
import com.example.dawaya.interfaces.ProductsInterface;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.viewmodels.CartViewModel;
import com.example.dawaya.viewmodels.SearchViewModel;
import com.example.dawaya.viewmodels.WishListViewModel;

import java.util.ArrayList;


public class SearchFrag extends Fragment implements ProductsInterface {
    Context context = App.getAppContext();

    String searchKey;
    EditText searchBar;

    SearchViewModel searchViewModel;
    CartViewModel cartViewModel = CartViewModel.getInstance();
    WishListViewModel wishListViewModel;

    ArrayList<ProductModel> searchProducts = new ArrayList<>();

    FragmentManager fragmentManager;



    public SearchFrag() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        wishListViewModel = new ViewModelProvider(this).get(WishListViewModel.class);
        fragmentManager = getParentFragmentManager();




    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayoutRoot = inflater.inflate(R.layout.fragment_search, container, false);



        /*searchBar = fragmentLayoutRoot.findViewById(R.id.search_view);
        searchBar.setOnFocusChangeListener(null);*/
        observeViewModelLiveData(fragmentLayoutRoot);
        return fragmentLayoutRoot;
    }

    private void observeViewModelLiveData(View root) {
        searchViewModel.getProducts().observeForever(new Observer<ArrayList<ProductModel>>() {
            @Override
            public void onChanged(ArrayList<ProductModel> productModels) {
                searchProducts = productModels;
                inflateRecyclerView(root, searchProducts);
            }
        });
    }

    private void inflateRecyclerView(View root, ArrayList<ProductModel> products) {
        RecyclerView recyclerView = root.findViewById(R.id.search_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ProductsAdapter adapter = new ProductsAdapter(products, SearchFrag.this);
        recyclerView.setAdapter(adapter);
    }


    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
        searchViewModel.sendSearchRequest(searchKey);

    }

    //TODO mlhash lazma
    public void setSearchBar(EditText searchBar) {
        this.searchBar = searchBar;
    }


    @Override
    public void onAddToCartClicked(ProductModel product) {
        Boolean alreadyInCart = false;

        //NOTICE a bug when an item at the end is clicked

        //viewModel = new ViewModelProvider(this).get(CategoryProductsViewModel.class);

        ArrayList<ProductModel> productToCart = new ArrayList<>();
        productToCart.add(product);
        alreadyInCart = cartViewModel.addToCart(productToCart, "append");

        /*int size = searchProducts.size();

        for (int i = 0; i < size; i++) {
            if (searchProducts.get(i).getCode().equals(productCode)) {
                ArrayList<ProductModel> productToCart = new ArrayList<>();
                productToCart.add(searchProducts.get(i));
                alreadyInCart = cartViewModel.addToCart(productToCart, "append");
                Log.v("OK", "OK");
            }

        }
        if (alreadyInCart)
            Toast.makeText(context, "Already in Cart", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onFavouriteClicked(ProductModel product) {
        ArrayList<ProductModel> productModel = new ArrayList<>();
        productModel.add(product);
        wishListViewModel.addToWishList(productModel, "append");
    }
}