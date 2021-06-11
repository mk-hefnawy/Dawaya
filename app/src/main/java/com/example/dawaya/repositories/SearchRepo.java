package com.example.dawaya.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.URLs;
import com.example.dawaya.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchRepo {
    Context context = App.getAppContext();
    private static SearchRepo instance = null;

    String searchUrl = new URLs().searchUrl;
    RequestQueue requestQueue = Utils.getRequestQueue(context);

    public MutableLiveData<ArrayList<ProductModel>> productsLiveData = new MutableLiveData<>();

    public static SearchRepo getInstance(){
        if(instance == null){
            instance = new SearchRepo();

            //instance.responseLiveData.setValue(new SignInResponse(false, false, false)); // Hello
        }
        return instance;
    }


    public void search(String searchKey){
        searchUrl += "/" + searchKey;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, searchUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject productJsonObject;
                    ArrayList<ProductModel> arrayList = new ArrayList<>();
                    for (int i=0 ; i<jsonArray.length(); i++){
                        productJsonObject = jsonArray.getJSONObject(i);
                        arrayList.add(new ProductModel(productJsonObject.getString("code"),productJsonObject.getString("name"),
                                0.0,0));
                    }
                    productsLiveData.setValue(arrayList);
                    //Log.v("----", String.valueOf(jsonObject.getInt("status")));
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

}
