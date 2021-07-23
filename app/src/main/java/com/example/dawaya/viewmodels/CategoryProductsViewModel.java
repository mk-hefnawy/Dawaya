package com.example.dawaya.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.models.ProductModel;
import com.example.dawaya.repositories.CategoryProductsRepo;
import com.example.dawaya.ui.CategoryProductsFragment;

import java.util.ArrayList;

public class CategoryProductsViewModel extends ViewModel {

    CategoryProductsRepo repo = CategoryProductsRepo.getInstance();
    MutableLiveData<ArrayList<ProductModel>> products = new MutableLiveData<>();


    public void getCategoryProducts(String mainCategory, String subCategory){

        repo.sendCategoryProductsRequest(mainCategory, subCategory);
        observeRepoResponse();
    }

    private void observeRepoResponse() {
        repo.getProductsLiveData().observeForever(new Observer<ArrayList<ProductModel>>() {
            @Override
            public void onChanged(ArrayList<ProductModel> productModels) {
                products.setValue(productModels);
                //Log.v("From ViewModel", products.getValue().get(1).getName());
            }
        });
    }

    public MutableLiveData<ArrayList<ProductModel>> getProducts() {
        return products;
    }
}
