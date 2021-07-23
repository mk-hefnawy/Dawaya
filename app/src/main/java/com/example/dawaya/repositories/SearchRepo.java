package com.example.dawaya.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.URLs;
import com.example.dawaya.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchRepo {
    Context context = App.getAppContext();
    private static SearchRepo instance = null;


    RequestQueue requestQueue = Utils.getRequestQueue(context);

    public MutableLiveData<ArrayList<ProductModel>> productsLiveData = new MutableLiveData<>();
    public MutableLiveData<ProductModel> productSearchedByCode = new MutableLiveData<>();

    public static SearchRepo getInstance(){
        if(instance == null){
            instance = new SearchRepo();

            //instance.responseLiveData.setValue(new SignInResponse(false, false, false)); // Hello
        }
        return instance;
    }


    public void search(String searchKey){
        String searchUrl = URLs.searchUrl + "/" + searchKey;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, searchUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArrayResponse = new JSONArray(response);
                    JSONObject jsonProduct;
                    ProductModel product;
                    JSONArray supplyProducts;
                    JSONObject supplyProduct;

                    ArrayList<ProductModel> products = new ArrayList<>();

                   for (int i = 0 ; i<jsonArrayResponse.length() ; i++){
                        jsonProduct = jsonArrayResponse.getJSONObject(i);
                        // Populate the product model with products table info first
                       product = new ProductModel(jsonProduct.getString("code"), jsonProduct.getString("name"),
                               jsonProduct.getString("mainCategory"),jsonProduct.getString("secondaryCategory"),
                                jsonProduct.getString("position"), "");

                       supplyProducts = jsonProduct.getJSONArray("supplyProducts");
                       supplyProduct = supplyProducts.getJSONObject(0);

                       Double price = supplyProduct.getDouble("productPrice");
                       int quantity = supplyProduct.getInt("remainedQuantity");

                       //Adding price and quantity
                       product.setPrice(price);
                       product.setQuantity(quantity);

                       JSONObject id = supplyProduct.getJSONObject("id");

                       String companyId = id.getString("companyId");
                       String supplyId = id.getString("supplyId");

                       product.setCompanyId(companyId);
                       product.setSupplyId(supplyId);

                       products.add(product);
                   }

                   productsLiveData.setValue(products);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }

    public void searchByCode(String code){
        String url = URLs.searchByCodeUrl + "/" + code;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {

                    ProductModel product;
                    JSONArray supplyProducts;
                    JSONObject supplyProduct;
                    try {
                        product = new ProductModel(response.getString("code"), response.getString("name"),
                                response.getString("mainCategory"),response.getString("secondaryCategory"),
                                response.getString("position"), "");

                        supplyProducts = response.getJSONArray("supplyProducts");
                        if (supplyProducts.length() > 0) {
                            supplyProduct = supplyProducts.getJSONObject(0);

                            Double price = supplyProduct.getDouble("productPrice");
                            int quantity = supplyProduct.getInt("remainedQuantity");

                            //Adding price and quantity
                            product.setPrice(price);
                            product.setQuantity(quantity);

                            JSONObject id = supplyProduct.getJSONObject("id");

                            String companyId = id.getString("companyId");
                            String supplyId = id.getString("supplyId");

                            product.setCompanyId(companyId);
                            product.setSupplyId(supplyId);
                        }
                        productSearchedByCode.setValue(product);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                });

        requestQueue.add(jsonObjectRequest);
    }

}
