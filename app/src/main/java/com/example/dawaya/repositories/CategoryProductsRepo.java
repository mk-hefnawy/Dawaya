package com.example.dawaya.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.URLs;
import com.example.dawaya.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryProductsRepo {

    Context context = App.getAppContext();
    RequestQueue requestQueue = Utils.getRequestQueue(context);

    MutableLiveData<ArrayList<ProductModel>> productsLiveData = new MutableLiveData<>();


    /** Singleton **/
    private static CategoryProductsRepo instance = null;
    public static CategoryProductsRepo getInstance(){
        if(instance == null){
            instance = new CategoryProductsRepo();
        }
        return instance;
    }

    public void sendCategoryProductsRequest(String mainCategory, String subCategory){

        String url = URLs.getProductsByCategoryURL;
        url += "/" + mainCategory + "/" + subCategory;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        requestQueue.add(stringRequest);
    }

    public MutableLiveData<ArrayList<ProductModel>> getProductsLiveData() {
        return productsLiveData;
    }
}
