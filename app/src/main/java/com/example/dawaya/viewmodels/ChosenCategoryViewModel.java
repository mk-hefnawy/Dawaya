package com.example.dawaya.viewmodels;

import com.example.dawaya.models.ProductModel;

import java.util.ArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.repositories.ChosenCategoryRepo;

public class ChosenCategoryViewModel extends ViewModel {


        MutableLiveData<ArrayList<ProductModel>> productsResponseLiveData = new MutableLiveData<>();
        ChosenCategoryRepo myproductRepo = ChosenCategoryRepo.getInstance(); //Singleton


        public MutableLiveData<ArrayList<ProductModel>> getProductsVMLiveData() {
            return productsResponseLiveData;
        }
        public void getProductsByCategory(){
            myproductRepo.sendProductsRequest();
            //Logging here causes a null pointer exception
            observeRepoResponse();
        }

        private void observeRepoResponse() {
            myproductRepo.productResponseLiveData.observeForever(new Observer<ArrayList<ProductModel>>() {
                @Override
                public void onChanged(ArrayList<ProductModel> orderPrephiralsModels) {
                    getResponse();
                }
            });
        }
        private void getResponse() {
            this.productsResponseLiveData.setValue(myproductRepo.getProductResponseLiveData().getValue());

        }
    }

