package com.example.dawaya.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.repositories.SearchRepo;
import com.example.dawaya.utils.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class SearchViewModel extends ViewModel {

    /** Declarations **/
    SearchRepo searchRepo = SearchRepo.getInstance();

    MutableLiveData<ArrayList<ProductModel>> products = new MutableLiveData<>();
    MutableLiveData<ProductModel> productSearchedByCode = new MutableLiveData<>();

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


    public void getProductByCode(String code){
            searchRepo.searchByCode(code);
            observeProductSearchedByCode();

    }

    private void observeProductSearchedByCode() {
        searchRepo.productSearchedByCode.observeForever(new Observer<ProductModel>() {
            @Override
            public void onChanged(ProductModel productModel) {
                productSearchedByCode.setValue(productModel);
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