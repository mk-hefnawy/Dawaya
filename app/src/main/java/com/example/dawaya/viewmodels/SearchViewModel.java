package com.example.dawaya.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.models.ProductModel;
import com.example.dawaya.repositories.SearchRepo;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {

    /** Declarations **/
    SearchRepo searchRepo = SearchRepo.getInstance();

    MutableLiveData<ArrayList<ProductModel>> products = new MutableLiveData<>();

    /** Methods **/
    public void sendSearchRequest(String searchKey){
        searchRepo.search(searchKey);
        observeRepoLiveData();
    }
    public void  observeRepoLiveData(){
        searchRepo.productsLiveData.observeForever(new Observer<ArrayList<ProductModel>>() {
            @Override
            public void onChanged(ArrayList<ProductModel> productModels) {
                products.setValue(productModels);
            }
        });
    }

    public MutableLiveData<ArrayList<ProductModel>> getProducts() {
        return products;
    }
    public void setProducts(MutableLiveData<ArrayList<ProductModel>> products) {
        this.products = products;
    }

    public SearchRepo getSearchRepo() {
        return searchRepo;
    }
}