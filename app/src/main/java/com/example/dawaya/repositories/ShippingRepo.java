package com.example.dawaya.repositories;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.utils.URLs;
import com.example.dawaya.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ShippingRepo {

    /** Singleton **/
    private static ShippingRepo instance = null;
    public static ShippingRepo getInstance(){
        if(instance == null){
            instance = new ShippingRepo();
        }
        return instance;
    }
    MutableLiveData<Integer> wholeOrderStatus = new MutableLiveData<>();
    MutableLiveData<String> bill_id = new MutableLiveData<>();

    Context context = App.getAppContext();
    RequestQueue requestQueue = Utils.getRequestQueue(context);

    @RequiresApi(api = Build.VERSION_CODES.O) // for now()
    public void sendOrder(String address, Double totalPrice,String time, ArrayList<ProductModel> products){
        String url = URLs.postOrderUrl;
        JSONObject body = new JSONObject();
        try {
            body.put("customerId", SharedPrefs.read(SharedPrefs.USER_ID, ""));
            body.put("address", address);
            body.put("totalPrice", totalPrice);
            body.put("time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, body, response -> {
            //Parse the response json object

            try {
                int status = response.getInt("status");

                if (status == 1) {

                    bill_id.setValue(response.getString("bill_id"));
                    wholeOrderStatus.setValue(1);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
            // get the status
                //if status is 1 { get the bill_id}
                    // send the products using that bill_id

        }, error -> {
            error.printStackTrace();
        });
        requestQueue.add(jsonObjectRequest);
    }

    public MutableLiveData<Integer> getWholeOrderStatus() {
        return wholeOrderStatus;
    }

    public MutableLiveData<String> getBill_id() {
        return bill_id;
    }
}
