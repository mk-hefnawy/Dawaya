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
import com.example.dawaya.models.SignInModel;
import com.example.dawaya.models.SignUpModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.URLs;
import com.example.dawaya.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInRepo {
    Context context = App.getAppContext();
    public MutableLiveData<SignUpModel> userLiveDataRepo = new MutableLiveData<>();
    public MutableLiveData<String> havingAnAccountLiveDataRepo = new MutableLiveData<>();

    public MutableLiveData<String> status = new MutableLiveData<>();

    RequestQueue requestQueue = Utils.getRequestQueue(context);

    private static SignInRepo instance = null;

    public static SignInRepo getInstance(){
        if(instance == null){
            instance = new SignInRepo();

        }
        return instance;
    }


    public void signIn(SignInModel  user)  {

        String signInUrl = new URLs().signInUrl;
        //signInUrl += "?email=" + user.getEmail() + "&password=" + user.getPassword();
        signInUrl += "/" + user.getEmail() + "/" + user.getPassword();
        Log.v("URL", signInUrl);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, signInUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    // Success Sign in
                    if (jsonObject.getString("status").equals("1")){
                        status.setValue("1");
                        JSONObject userObject = jsonObject.getJSONObject("the_customer");

                        SignUpModel signedInUser = new SignUpModel(userObject.getString("customerId"),userObject.getString("firstName"),
                                userObject.getString("lastName"), user.getEmail() ,user.getPassword(),
                        "",userObject.getString("gender"),userObject.getString("dateOfBirth"));
                        userLiveDataRepo.setValue(signedInUser);
                        Toast.makeText(context, "ٍSigned in successfully", Toast.LENGTH_LONG).show();
                    }
                     else if (jsonObject.getString("having_an_account").equals("0")){
                        status.setValue("0");
                        havingAnAccountLiveDataRepo.setValue("0");
                        Toast.makeText(context, "You don't have an account, register first", Toast.LENGTH_LONG).show();
                    }
                     else if (jsonObject.getString("correct_password").equals("0")){
                        status.setValue("0");
                        Toast.makeText(context, "Incorrect Password", Toast.LENGTH_LONG).show();
                    }

                    else {
                        status.setValue("0");
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();


                    }

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
