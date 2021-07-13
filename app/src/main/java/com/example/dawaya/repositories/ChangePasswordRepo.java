package com.example.dawaya.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dawaya.models.AddressModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.URLs;
import com.example.dawaya.utils.Utils;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ChangePasswordRepo {
    /** Singleton **/
    private static ChangePasswordRepo instance = null;
    public static ChangePasswordRepo getInstance(){
        if(instance == null){
            instance = new ChangePasswordRepo();

            //instance.responseLiveData.setValue(new SignInResponse(false, false, false)); // Hello
        }
        return instance;
    }

    Context context = App.getAppContext();
    RequestQueue requestQueue = Utils.getRequestQueue(context);

    MutableLiveData<Integer> changePasswordStatus = new MutableLiveData<>();


    public void changePassword(String oldPassword, String newPassword, String userId){
        String url = URLs.putPassword;
        JSONObject body = new JSONObject();
        try {
            body.put("oldPassword", oldPassword);
            body.put("newPassword", newPassword);
            body.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Parse
                //Set LiveData
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public MutableLiveData<Integer> getChangePasswordStatus() {
        return changePasswordStatus;
    }
}
