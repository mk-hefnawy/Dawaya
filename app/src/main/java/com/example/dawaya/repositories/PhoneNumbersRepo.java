package com.example.dawaya.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.URLs;
import com.example.dawaya.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class PhoneNumbersRepo {
    Context context = App.getAppContext();
    RequestQueue requestQueue = Utils.getRequestQueue(context);
    /** Singleton **/
    private static PhoneNumbersRepo instance = null;
    public static PhoneNumbersRepo getInstance(){
        if(instance == null){
            instance = new PhoneNumbersRepo();
        }
        return instance;
    }

    MutableLiveData<ArrayList<String>> fromServerPhoneNumbersLiveData = new MutableLiveData<>();

    public void getPhoneNumbers(String userId){
        String url = URLs.getPhoneNumbersUrl + "/" + userId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;
                JSONArray jsonArray;
                JSONObject phoneNumberObj;
                String phoneNumber;
                ArrayList <String> phoneNumbers = new ArrayList<>();

                try {
                     jsonObject = new JSONObject(response);
                     jsonArray = jsonObject.getJSONArray("the_customer_phones:");

                    for(int i =0 ; i<jsonArray.length(); i++){
                         phoneNumberObj = jsonArray.getJSONObject(i);
                         phoneNumber = phoneNumberObj.getString("phoneNumber");
                         phoneNumbers.add(phoneNumber);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                fromServerPhoneNumbersLiveData.setValue(phoneNumbers);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);

    }

    public MutableLiveData<ArrayList<String>> getFromServerPhoneNumbersLiveData() {
        return fromServerPhoneNumbersLiveData;
    }
}
