package com.example.dawaya.repositories;

import android.content.Context;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.URLs;
import com.example.dawaya.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class EditPhoneNumbersRepo {
    Context context = App.getAppContext();
    RequestQueue requestQueue = Utils.getRequestQueue(context);

    MutableLiveData<Integer> updatePhoneNumberStatus = new MutableLiveData<>();
    MutableLiveData<Integer> deletePhoneNumberStatus = new MutableLiveData<>();

    /** Singleton **/
    private static EditPhoneNumbersRepo instance = null;
    public static EditPhoneNumbersRepo getInstance(){
        if(instance == null){
            instance = new EditPhoneNumbersRepo();
        }
        return instance;
    }


    public void editPhoneNumber(String userId, String oldPhoneNumber, String newPhoneNumber) {
        String url = URLs.updatePhoneNumbersUrl;
        JSONObject body = new JSONObject();
        try {
            body.put("theCustomerId", userId);
            body.put("theCustomerPhoneold", oldPhoneNumber);
            body.put("theCustomerPhonenew", newPhoneNumber);
        } catch (JSONException e) {

        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("status") == 1){
                        // Successful PhoneNumber Edit
                        updatePhoneNumberStatus.setValue(1);
                    }
                    else {
                        updatePhoneNumberStatus.setValue(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                updatePhoneNumberStatus.setValue(0);
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
    public void deletePhoneNumber(String userId, String phoneNumber){
        String url = URLs.deletePhoneNumbersUrl;
        url += "/" + userId + "/" + phoneNumber;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 1){
                        deletePhoneNumberStatus.setValue(1);
                    }
                    else {
                        deletePhoneNumberStatus.setValue(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                deletePhoneNumberStatus.setValue(0);
            }
        });

        requestQueue.add(stringRequest);
    }






    public MutableLiveData<Integer> getUpdatePhoneNumberStatus() {
        return updatePhoneNumberStatus;
    }

    public MutableLiveData<Integer> getDeletePhoneNumberStatus() {
        return deletePhoneNumberStatus;
    }
}
