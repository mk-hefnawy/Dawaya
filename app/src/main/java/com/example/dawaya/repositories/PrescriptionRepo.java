package com.example.dawaya.repositories;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.URLs;
import com.example.dawaya.utils.Utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PrescriptionRepo {


    Context context = App.getAppContext();
    RequestQueue requestQueue = Utils.getRequestQueue(context);


    /** Singleton **/
    private static PrescriptionRepo instance = null;
    public static PrescriptionRepo getInstance(){
        if(instance == null){
            instance = new PrescriptionRepo();
        }
        return instance;
    }



    public void uploadPrescription(Bitmap bitmap){

        String encodedImage = Utils.convertImageToString(bitmap);
        Log.v("----", encodedImage);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.postPrescription, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.v("----", response);
                Toast.makeText(context, "The image has been uploaded", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Upload failed", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("image", encodedImage);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
