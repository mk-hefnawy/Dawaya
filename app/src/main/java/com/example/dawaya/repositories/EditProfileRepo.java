package com.example.dawaya.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dawaya.models.SignUpModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.URLs;
import com.example.dawaya.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class EditProfileRepo {

    Context context = App.getAppContext();
    RequestQueue requestQueue = Utils.getRequestQueue(context);

    MutableLiveData<SignUpModel> updatedUser = new MutableLiveData<>();


    /** Singleton **/
    private static EditProfileRepo instance = null;
    public static EditProfileRepo getInstance(){
        if(instance == null){
            instance = new EditProfileRepo();

        }
        return instance;
    }


    public void updateUser(SignUpModel user) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URLs.updateUserUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("0")){
                        //User data have been updated
                        updatedUser.setValue(user);
                    }
                    Log.v("---",jsonObject.getString("status"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer_id", user.getUserId());
                params.put("first_name", user.getFirstName());
                params.put("last_name", user.getLastName());
                params.put("email", user.getEmail());
                params.put("phone_number", user.getPhoneNumber());
                params.put("gender", user.getGender());
                params.put("date_of_birth", user.getDateOfBirth());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public MutableLiveData<SignUpModel> getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(MutableLiveData<SignUpModel> updatedUser) {
        this.updatedUser = updatedUser;
    }
}
