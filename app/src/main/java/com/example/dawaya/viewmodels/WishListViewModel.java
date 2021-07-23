package com.example.dawaya.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.models.ProductModel;
import com.example.dawaya.utils.SharedPrefs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

public class WishListViewModel extends ViewModel {

    MutableLiveData<ArrayList<ProductModel>> wishListProducts = new MutableLiveData<>();

    public void addToWishList(ArrayList<ProductModel> products, String appendOrOverWrite){

        if (appendOrOverWrite.equals("overwrite")) {


            String allUsersWishListProductsString = SharedPrefs.read(SharedPrefs.WISH_LIST_PRODUCTS, "");

            if (!allUsersWishListProductsString.equals("")) {
                HashMap<String, ArrayList<ProductModel>> allUsersWishLists = new Gson().fromJson(allUsersWishListProductsString,
                        new TypeToken<HashMap<String, ArrayList<ProductModel>>>() {
                        }.getType());

                allUsersWishLists.put(SharedPrefs.USER_ID, products);

                String updatedWishLists = new Gson().toJson(allUsersWishLists);
                SharedPrefs.write(SharedPrefs.WISH_LIST_PRODUCTS, updatedWishLists);
            }
            else {
                HashMap<String, ArrayList<ProductModel>> newMap = new HashMap<>();
                newMap.put(SharedPrefs.USER_ID, products);
                String updatedWishLists = new Gson().toJson(newMap);
                SharedPrefs.write(SharedPrefs.WISH_LIST_PRODUCTS, updatedWishLists);
            }
           //getAllWishListProducts();

        }

        else if (appendOrOverWrite.equals("append")){
            // get
            getAllWishListProducts();
            wishListProducts.observeForever(new Observer<ArrayList<ProductModel>>() {
                @Override
                public void onChanged(ArrayList<ProductModel> productModels) {
                    productModels.addAll(products);
                    addToWishList(productModels, "overwrite");
                }
            });

        }
    }

    public void getAllWishListProducts(){
        String allUsersWishListProductsString = SharedPrefs.read(SharedPrefs.WISH_LIST_PRODUCTS, "");

        if (!allUsersWishListProductsString.equals("")) {

            HashMap<String, ArrayList<ProductModel>> allUsersWishLists = new Gson().fromJson(allUsersWishListProductsString,
                    new TypeToken<HashMap<String, ArrayList<ProductModel>>>() {
                    }.getType());

            ArrayList<ProductModel> products = allUsersWishLists.get(SharedPrefs.USER_ID);

            if (products != null && products.size() > 0) {
                //ArrayList<ProductModel> products = new Gson().fromJson(productsString, new TypeToken<ArrayList<ProductModel>>(){}.getType());
                wishListProducts.setValue(products);
            } else wishListProducts.setValue(new ArrayList<>());
        }

        else {
            wishListProducts.setValue(new ArrayList<>());
        }
    }


    public void deleteProductFromWishList(ProductModel product){
        String allUsersWishListProductsString = SharedPrefs.read(SharedPrefs.WISH_LIST_PRODUCTS, "");

        HashMap<String, ArrayList<ProductModel>> allUsersWishLists = new Gson().fromJson(allUsersWishListProductsString,
                new TypeToken<HashMap<String, ArrayList<ProductModel>>>(){}.getType());

        ArrayList<ProductModel> products = allUsersWishLists.get(SharedPrefs.USER_ID);
        //products.remove(product); // makanetsh btemsa7 m3rf4 leh
        for (int i = 0 ; i<products.size(); i++){
            if (products.get(i).getCode().equals(product.getCode())){
                products.remove(i);
            }
        }
        //Overwrite
        addToWishList(products, "overwrite");

    }

    public MutableLiveData<ArrayList<ProductModel>> getWishListProducts() {
        return wishListProducts;
    }
}
