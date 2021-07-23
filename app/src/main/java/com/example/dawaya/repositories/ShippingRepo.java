package com.example.dawaya.repositories;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dawaya.dummy.ApiInterface;
import com.example.dawaya.responses.PostBillProductsResponse;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.responses.id;
import com.example.dawaya.responses.product;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.utils.URLs;
import com.example.dawaya.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    public void sendOrder(String address, Double totalPrice, String time, ArrayList<ProductModel> products){

        String url = URLs.postOrderUrl;
        JSONObject body = new JSONObject();
        try {
            //Integer.parseInt(SharedPrefs.read(SharedPrefs.USER_ID, ""))
            body.put("customerId", 7);
            body.put("customerAddress", "masr al gadeda,al khalefa al maamon, 11, 3, 33");
            body.put("totalPrice", totalPrice);
            body.put("time", "2021-04-12T15:00:00.000+00:00");

            body.put("billType", "mobile");
            body.put("billState", "on_HOLD");
            body.put("deliveryFee", 1.0);
            //body.put("phoneNumber", 15236511);
            body.put("deliveryFeedback", 0);
            body.put("employeeFeedback", 0);
            body.put("pharmacyFeedback", 0);
            body.put("userFeedback", 0);
            body.put("prescriptionOrNot", 0);
            body.put("userFeedback", 0);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, body, response -> {
            //Parse the response json object

            try {
                int status = response.getInt("status");

                if (status == 1) {

                    String orderId = response.getString("billId");
                    bill_id.setValue(orderId);

                    sendOrderProducts(orderId, products);

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

    public void sendOrderProducts(String orderId, ArrayList<ProductModel> orderProducts){

        String url = URLs.postOrderProducts;

       /* JSONArray products = new JSONArray();
        JSONObject product = new JSONObject();
        JSONObject id = new JSONObject();

        for (int i = 0; i<orderProducts.size(); i++){
            try {
               // id.put("billId", orderId);
                id.put("billId", 100);
               // id.put("productCode", orderProducts.get(i).getCode());
                id.put("productCode", 792);
               // id.put("companyId", orderProducts.get(i).getCompanyId());
                id.put("companyId", 1);
                //id.put("supplyId", orderProducts.get(i).getSupplyId());
                id.put("supplyId", 1001);

                product.put("id", id);
                product.put("quantity", 5);
                product.put("totalPrice", 100.0);
                product.put("unitPrice", 20.0);

                products.put(product);




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        //Log.v("PRODUCTS", );

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, products,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject theResponse = response.getJSONObject(0);
                            Log.v("BillProducts", String.valueOf(theResponse.getInt("status")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },


                error -> {
                });

        requestQueue.add(jsonArrayRequest);*/

        ArrayList<product> products = new ArrayList<>();
        product product;
        id id;

        for (int i = 0 ; i<orderProducts.size() ; i++){

                id = new id(100, 1059, 1, 1001);
                product = new product(id, orderProducts.get(i).getQuantityToBuy(), orderProducts.get(i).getTotalPrice(), orderProducts.get(i).getPrice());
                products.add(product);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<List<PostBillProductsResponse>> call = apiInterface.storePost(products);
        call.enqueue(new Callback<List<PostBillProductsResponse>>() {
            @Override
            public void onResponse(Call<List<PostBillProductsResponse>> call, Response<List<PostBillProductsResponse>> response) {
                Log.v("PostBills", String.valueOf(response.body().get(0).getStatus()));
                Log.v("PostBillsResponse", String.valueOf(response));
            }

            @Override
            public void onFailure(Call<List<PostBillProductsResponse>> call, Throwable t) {
                    t.printStackTrace();
            }
        });



    }

    public MutableLiveData<Integer> getWholeOrderStatus() {
        return wholeOrderStatus;
    }

    public MutableLiveData<String> getBill_id() {
        return bill_id;
    }
}
