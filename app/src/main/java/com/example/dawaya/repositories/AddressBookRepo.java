package com.example.dawaya.repositories;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.dawaya.models.AddressModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.URLs;
import com.example.dawaya.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddressBookRepo {

    Context context = App.getAppContext();
    RequestQueue requestQueue = Utils.getRequestQueue(context);
    public MutableLiveData<ArrayList<AddressModel>> responseLiveData = new MutableLiveData<>();

    public MutableLiveData<Integer> postStatusLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> putStatusLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> deleteStatusLiveData = new MutableLiveData<>();

    /** Singleton **/
    private static AddressBookRepo instance = null;
    public static AddressBookRepo getInstance(){
        if(instance == null){
            instance = new AddressBookRepo();

            //instance.responseLiveData.setValue(new SignInResponse(false, false, false)); // Hello
        }
        return instance;
    }

    public void getAddresses(String userId) {
        //String url = URLs.getAddressUrl + "?user_id=" + userId;
        String url = URLs.getAddressUrl + "/" + userId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject, id, wholeJsonObj;
                    wholeJsonObj = new JSONObject(response);
                    String status = wholeJsonObj.getString("status");

                    if (status.equals("1")){

                        JSONArray jsonArray = wholeJsonObj.getJSONArray("the_customer_addresses:");
                        String concatenatedAddress;
                        String [] splittedAddress = new String[5];
                        ArrayList<AddressModel> arrayList = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getJSONObject("id");
                            concatenatedAddress = id.getString("address");
                            splittedAddress = concatenatedAddress.split(",");

                            if (splittedAddress.length>1) {
                                arrayList.add(new AddressModel(splittedAddress[0], splittedAddress[1],
                                        splittedAddress[2], splittedAddress[3], splittedAddress[4]));
                            }
                        }
                        responseLiveData.setValue(arrayList);



                    }

                    //Log.v("------", responseLiveData.getValue().get(0).getStreet());
            } catch (JSONException e) {
                    e.printStackTrace();
                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        });


        requestQueue.add(stringRequest);
    }

    /*public void addAddress(){
        Log.v("Hello From addAddress", "hello");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, addAddressUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.v("AddAddressResponse", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("county", "Helwan");
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }*/
    public void addAddress(String address, String customerId){

        String url = URLs.addAddressUrl;
        JSONObject subBody = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            subBody.put("customerId", Integer.valueOf(customerId));
            subBody.put("address", address);

            body.put("id", subBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v("---", body.toString());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, body, response -> {

            try {
                int status = response.getInt("states");
                //Success
                if (status == 1){
                    postStatusLiveData.setValue(1);
                    Log.v("---", "Success - Adding Address");
                }
                else {
                    postStatusLiveData.setValue(0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.v("---", "Failure - Adding Address");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
            }
        });

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void editAddress(String oldAddress, String address, String customerId){

    JSONObject body = new JSONObject();

    try {

        body.put("theCustomerId", customerId);
        body.put("theCustomerAddressOld", oldAddress);
        body.put("theCustomerAddressNew", address);
    }
    catch (JSONException e){
        e.printStackTrace();
    }

    JsonObjectRequest jsonObjectRequest =  new JsonObjectRequest(Request.Method.PUT, URLs.editAddressUrl, body, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {

            try {
                //success
                if (response.getInt("states") == 1){
                    putStatusLiveData.setValue(1);
                }

                else  {
                    putStatusLiveData.setValue(0);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });

            requestQueue.add(jsonObjectRequest);
    }

    public MutableLiveData<ArrayList<AddressModel>> getResponseLiveData() {
        return responseLiveData;
    }

    public void deleteAddress(String oldAddress, String customerId) {
        String url = URLs.deleteAddressUrl + "/" + customerId + "/" + oldAddress;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject res = new JSONObject(response);

                    if (res.getInt("states") == 1){
                        deleteStatusLiveData.setValue(1);
                    }
                    else {
                        deleteStatusLiveData.setValue(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }


}
