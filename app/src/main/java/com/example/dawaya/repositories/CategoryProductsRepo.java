package com.example.dawaya.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

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

        String url = new URLs().getProductsByCategoryURL;
        url += "?sub_category+" + subCategory;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("products");
                    JSONObject productJsonObject;
                    ArrayList<ProductModel> arrayList = new ArrayList<>();
                    for (int i=0 ; i<jsonArray.length(); i++){
                        productJsonObject = jsonArray.getJSONObject(i);
                        arrayList.add(new ProductModel(productJsonObject.getString("product_code"),productJsonObject.getString("name"),
                                productJsonObject.getDouble("unit_price"),productJsonObject.getInt("quantity")));
                    }
                    productsLiveData.setValue(arrayList);
                    Log.v("From Repo", productsLiveData.getValue().get(1).getName());
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
