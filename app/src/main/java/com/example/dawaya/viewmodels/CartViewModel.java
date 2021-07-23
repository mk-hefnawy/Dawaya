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
import java.util.List;

public class CartViewModel extends ViewModel {


    ArrayList<ProductModel> initial = new ArrayList<>();
    MutableLiveData<ArrayList<ProductModel>> cartProducts = new MutableLiveData<>();

    private static CartViewModel instance = null;
    public static CartViewModel getInstance(){
        if(instance == null){
            instance = new CartViewModel();
        }
        return instance;
    }
    private CartViewModel(){
        cartProducts.setValue(initial);

    }

    public Boolean addToCart(ArrayList<ProductModel> products, String appendOrOverWrite) {

        ArrayList<ProductModel> accumulator = new ArrayList<>();
       // repo.insertCartProductIntoDataBase(product);
        /*if (!cartProducts.getValue().isEmpty()) {
            Log.v("CartViewModel", "array is not empty" );
            if (cartProducts.getValue().contains(product)) {
                Log.v("CartViewModel", "repeated" );
                return true;
            }
            else {
                Log.v("CartViewModel", "not repeated" );
                accumulator = cartProducts.getValue();
                accumulator.add(product);
                cartProducts.setValue(accumulator);

                Log.v("CartViewModel", cartProducts.getValue().get(0).getName());
                Log.v("CartViewModel", String.valueOf(cartProducts.getValue().size()));
                return false;
            }
        }
        else {
            Log.v("CartViewModel", "array is empty" );
            accumulator.add(product);
            cartProducts.setValue(accumulator);
            Log.v("CartViewModel", cartProducts.getValue().get(0).getName());
            Log.v("CartViewModel", String.valueOf(cartProducts.getValue().size()));
            Log.v("CartViewModel", "Done" );
            return false;
        }*/

        //TODO Redundancy Check

        if (appendOrOverWrite.equals("overwrite")){

            String allUsersWishListProductsString = SharedPrefs.read(SharedPrefs.CART_PRODUCTS, "");

            if (!allUsersWishListProductsString.equals("")) {
                HashMap<String, ArrayList<ProductModel>> allUsersWishLists = new Gson().fromJson(allUsersWishListProductsString,
                        new TypeToken<HashMap<String, ArrayList<ProductModel>>>() {
                        }.getType());

                allUsersWishLists.put(SharedPrefs.USER_ID, products);
                String updatedCartProducts = new Gson().toJson(allUsersWishLists);
                SharedPrefs.write(SharedPrefs.CART_PRODUCTS, updatedCartProducts);

            }
            else {
                HashMap<String, ArrayList<ProductModel>> newMap = new HashMap<>();
                newMap.put(SharedPrefs.USER_ID, products);
                String updatedCartProducts = new Gson().toJson(newMap);
                SharedPrefs.write(SharedPrefs.CART_PRODUCTS, updatedCartProducts);
            }

            return true;
        }

        else if (appendOrOverWrite.equals("append")) {
            getAllCartProducts();
            cartProducts.observeForever(new Observer<ArrayList<ProductModel>>() {
                @Override
                public void onChanged(ArrayList<ProductModel> productModels) {
                    productModels.addAll(products);
                    addToCart(productModels, "overwrite");

                }
            });
            return true;
        }
return true;
    }

    public void getAllCartProducts(){

        String allUsersWishListProductsString = SharedPrefs.read(SharedPrefs.CART_PRODUCTS, "");

        if (!allUsersWishListProductsString.equals("")) {

            HashMap<String, ArrayList<ProductModel>> allUsersWishLists = new Gson().fromJson(allUsersWishListProductsString,
                    new TypeToken<HashMap<String, ArrayList<ProductModel>>>() {
                    }.getType());

            ArrayList<ProductModel> products = allUsersWishLists.get(SharedPrefs.USER_ID);

            if (products != null && products.size() > 0) {
                //ArrayList<ProductModel> products = new Gson().fromJson(productsString, new TypeToken<ArrayList<ProductModel>>(){}.getType());
                cartProducts.setValue(products);
            } else cartProducts.setValue(new ArrayList<>());
        }

        else cartProducts.setValue(new ArrayList<>());
    }

    public void deleteProductFromCart(ProductModel product){
        String allUsersWishListProductsString = SharedPrefs.read(SharedPrefs.CART_PRODUCTS, "");

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
        addToCart(products, "overwrite");
    }


  public MutableLiveData<ArrayList<ProductModel>> getCartProducts() {
      return cartProducts;
  }
}