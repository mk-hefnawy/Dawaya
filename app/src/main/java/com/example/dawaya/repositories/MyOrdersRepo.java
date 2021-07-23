package com.example.dawaya.repositories;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.dawaya.models.AddressModel;
import com.example.dawaya.models.OrderModel;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.models.TransientProductModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.URLs;
import com.example.dawaya.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyOrdersRepo {

    Context context = App.getAppContext();
    RequestQueue requestQueue = Utils.getRequestQueue(context);

    public MutableLiveData<ArrayList<OrderModel>> ordersLiveData = new MutableLiveData<>();
    public MutableLiveData<ArrayList<AddressModel>> addressesResponseLiveData = new MutableLiveData<>();
    public MutableLiveData<TransientProductModel> productsResponseLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> feedBackStatusLiveData = new MutableLiveData<>();

    TransientProductModel transientProductModel;

    /** Singleton **/
    private static MyOrdersRepo instance = null;
    public static MyOrdersRepo getInstance(){
        if(instance == null){
            instance = new MyOrdersRepo();

            //instance.responseLiveData.setValue(new SignInResponse(false, false, false)); // Hello
        }
        return instance;
    }

    public MutableLiveData<ArrayList<OrderModel>> getOrdersLiveData() {
        return ordersLiveData;
    }

    public MutableLiveData<ArrayList<AddressModel>> getAddressesResponseLiveData() {
        return addressesResponseLiveData;
    }

    public MutableLiveData<TransientProductModel> getProductsResponseLiveData() {
        return productsResponseLiveData;
    }

    String getAddressesUrl = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/addresses";
    String getProductsUrl = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/getOrderProducts";

    /** Sending the Request**/

    public void getAllUserOrders(String userId) {
        String url = URLs.getOrdersUrl + "/" + "26";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O) // for now()
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject theResponse = new JSONObject(response);
                    JSONArray jsonOrdersArray = theResponse.getJSONArray("the_customer_bills");
                    JSONObject jsonOrder;

                    JSONArray jsonBillProducts;

                    ArrayList<OrderModel> orders =  new ArrayList<>();


                    for (int i = 0 ; i<jsonOrdersArray.length() ; i++) {
                        jsonOrder = jsonOrdersArray.getJSONObject(i);

                        //theBill = jsonOrder.getJSONObject("theBill");

                        // getting orders without products
                        orders.add(new OrderModel(jsonOrder.getString("billId"), jsonOrder.getDouble("totalPrice"),
                                jsonOrder.getString("time"), jsonOrder.getString("billState"),
                                jsonOrder.getString("customerAddress")));


                        jsonBillProducts = jsonOrder.getJSONArray("billsProducts");
                        JSONObject jsonProduct, id;
                        ArrayList<ProductModel> products = new ArrayList<>();

                        for (int j = 0; j < jsonBillProducts.length(); j++) {

                            jsonProduct = jsonBillProducts.getJSONObject(j);
                            id = jsonProduct.getJSONObject("id");

                            products.add(new ProductModel(id.getString("productCode"), jsonProduct.getDouble("unitPrice"),
                                    jsonProduct.getDouble("totalPrice"), jsonProduct.getInt("quantity"), id.getString("companyId"),
                                    id.getString("supplyId")));

                        }

                        orders.get(i).setProducts(products);
                    }

                    ordersLiveData.setValue(orders);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

               /* try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject;
                    ArrayList<OrderModel> arrayList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        arrayList.add(new OrderModel( jsonObject.getString("order_id"), jsonObject.getDouble("total_price"),
                                jsonObject.getString("order_time"), jsonObject.getString("order_state"),
                                jsonObject.getString("order_address")));

                    }
                    Log.v("---------", arrayList.get(1).getOrderTotalPrice().toString());
                    peripheralsResponseLiveData.setValue(arrayList);
                    Log.v("----", peripheralsResponseLiveData.getValue().get(0).getOrderTotalPrice().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);
    }

  /*  public void getOrderProducts(OrderModel order){

        String url = getProductsUrl + "?orderId=" + orderId;

        ArrayList<ProductModel> products = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("products");

                    JSONObject productJsonObject;

                    for (int i = 0; i<jsonArray.length(); i++){
                        productJsonObject = jsonArray.getJSONObject(i);
                        *//*products.add(new ProductModel(productJsonObject.getString("code"),productJsonObject.getString("name"),"",
                                    "", productJsonObject.getDouble("price"),productJsonObject.getInt("quantity"), "",
                                ""));*//*
                        }
                    transientProductModel = new TransientProductModel(products, orderId);

                    } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                productsResponseLiveData.setValue(transientProductModel);
                Log.v("Hellooooo", productsResponseLiveData.getValue().getProducts().get(0).getName());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }*/

    public void sendUserFeedBack(String userId, String orderId, String feedBack) {
        String url = URLs.postFeedBackUrl;
        JSONObject body = new JSONObject();
        try {
            body.put("customerId", userId);
            body.put("billId", orderId);
            body.put("userFeedBack", feedBack);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, body,
                response -> {
            //Status Stuff
            try {
                if (response.getInt("status") == 1){
                    feedBackStatusLiveData.setValue(1);
                }
                else {
                    feedBackStatusLiveData.setValue(0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
                error.printStackTrace();
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void sendUserMessageFeedback(String userId, String orderId, String feedBack) {
        String url = URLs.postMessageFeedBackUrl;
        JSONObject body = new JSONObject();
        try {
            body.put("customerId", userId);
            body.put("billId", orderId);
            body.put("userFeedBackMessage", feedBack);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, body,
                response -> {
                    try {
                        if (response.getInt("status") == 1){
                            feedBackStatusLiveData.setValue(1);
                        }
                        else {
                            feedBackStatusLiveData.setValue(0);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                error -> {
            error.printStackTrace();
                });

        requestQueue.add(jsonObjectRequest);
    }
}
