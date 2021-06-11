package com.example.dawaya.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.dawaya.models.SignUpModel;
import com.example.dawaya.responses.SignUpResponse;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.URLs;
import com.example.dawaya.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SignUpRepo {

    Context context = App.getAppContext();
    RequestQueue requestQueue = Utils.getRequestQueue(context); // Only one RequestQueue Object
    public MutableLiveData<SignUpResponse> signUpResponseLiveData = new MutableLiveData<>();

    public MutableLiveData<String> currentUserId = new MutableLiveData<>();

    String customerId;

    private static SignUpRepo instance = null;

    public MutableLiveData<SignUpResponse> getSignUpResponseLiveData() {
        return signUpResponseLiveData;
    }

    public static SignUpRepo getInstance(){
        if(instance == null){
            instance = new SignUpRepo();

            instance.signUpResponseLiveData.setValue(new SignUpResponse("", ""));
        }
        return instance;
    }

    public void signUp(final SignUpModel user){
        //final String finalUser = Utils.objToJson(user);
        String url = URLs.signUpUrl;
        //url = url.replaceAll(" ", "%20");

        JSONObject body = new JSONObject();
        try {
            body.put("credit", 10.0);
            body.put("firstName", user.getFirstName());
            body.put("lastName", user.getLastName());
            body.put("email", user.getEmail());
            body.put("password", user.getPassword());
            body.put("gender", user.getGender());
            //body.put("dateOfBirth", user.getDateOfBirth());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String mRequestBody = body.toString();
        Log.v("-------", mRequestBody);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.v("From sign up repo", "Sign up info was sent");
                    JSONObject jsonObject = new JSONObject(response);


                    JSONObject userObject = new JSONObject();

                    //Success
                    if (jsonObject.getString("status").equals("1")){
                        Log.v("Success", "Success");
                        //Getting the user id from server

                        //Notice

                        userObject = jsonObject.getJSONObject("the_customer");
                        customerId = userObject.getString("customerId");

                        /*customerId = jsonObject.getString("customerId");*/


                        //populate the live data with current user id
                        currentUserId.setValue(customerId);

                        //sendPhoneNumber(customerId, user.getPhoneNumber());
                        Log.v("signed up successfully", "true");
                        Toast.makeText(context, "Signed up successfully", Toast.LENGTH_LONG).show();
                    }
                    else if (jsonObject.getString("already_has_an_account").equals("1")){
                        Toast.makeText(context, "You already have an account", Toast.LENGTH_LONG).show();
                    }




                    //Failure
                    else Toast.makeText(context, "Sign up failed", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Error", error.toString());
                error.printStackTrace();
            }

        })
        {
            /*@Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                    params.put("credit", "");
                    params.put("firstName", user.getFirstName());
                    params.put("lastName", user.getLastName());
                    params.put("email", user.getEmail());
                    params.put("password", user.getPassword());
                    params.put("gender", user.getGender());
                    params.put("dateOfBirth", user.getDateOfBirth());
                return params;
            }*/

            @Override
            public byte[] getBody() {
                try {
                    return mRequestBody.getBytes("latin1");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            /*@Override
            public String getBodyContentType() {
                //return "application/json; charset=utf-8";
                //return "application/json; charset=latin1";
                return "application/json";
            }*/
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json; charset=utf-8");
                //headers.put("Content-Type", "application/json; charset=latin-1");
                headers.put("Content-Type", "application/json; charset=latin1");
                return headers;
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    /*public void signUp(final SignUpModel user)  {

        String url = URLs.signUpUrl;
        url = url.replaceAll(" ", "%20");

        JSONObject body = new JSONObject();
        try {
            body.put("credit", 10.0);
            body.put("firstName", user.getFirstName());
            body.put("lastName", user.getLastName());
            body.put("email", user.getEmail());
            body.put("password", user.getPassword());
            body.put("gender", user.getGender());
            body.put("dateOfBirth", user.getDateOfBirth());
        } catch (JSONException e) {
            e.printStackTrace();
        }




        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONObject userObject = new JSONObject();
                try {
                    Log.v("Response", String.valueOf(response.getInt("status")));


                   *//* userObject = response.getJSONObject("the_customer");
                    customerId = userObject.getString("customerId");*//*

                    customerId = response.getString("customerId");


                    //populate the live data with current user id
                    currentUserId.setValue(customerId);

                    //sendPhoneNumber(customerId, user.getPhoneNumber());

                    Log.v("signed up successfully", "true");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        }){
            @Override
            public String getBodyContentType() {
                //return "application/json; charset=utf-8";
                //return "application/json; charset=latin1";
                return "application/json";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json; charset=utf-8");
                //headers.put("Content-Type", "application/json; charset=latin1");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        /*jsonObjectRequest.setShouldCache(false);
        // Wait 30 seconds and don't retry more than once
        //jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*//*
        //jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);
    }*/

    private void sendPhoneNumber(String customerId, String phoneNumber){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.postPhoneNumnerUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Success
                    if (jsonObject.getString("status").equals("0")){
                        Toast.makeText(context, "Signed up successfully",Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(context, "Sign up failed", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.v("phone number sent", "true");
                Log.v("customer id", customerId);
                //Todo anything idk
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("customer_id", customerId);
                params.put("phone_number", phoneNumber);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
