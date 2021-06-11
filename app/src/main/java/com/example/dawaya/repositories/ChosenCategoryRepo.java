package com.example.dawaya.repositories;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChosenCategoryRepo {



    /*   here Creat respons of live data of repo *_____*                         */
    Context context = App.getAppContext();
    public MutableLiveData<ArrayList<ProductModel>> productResponseLiveData = new MutableLiveData<>();

    /** Singleton **/
    private static ChosenCategoryRepo instance = null;
    public static ChosenCategoryRepo getInstance(){
        if(instance == null){
            instance = new ChosenCategoryRepo();

            //instance.responseLiveData.setValue(new SignInResponse(false, false, false)); // Hello
        }
        return instance;
    }
    /* Here create a function to return the  live data                            */
    public MutableLiveData<ArrayList<ProductModel>> getProductResponseLiveData() {
        return productResponseLiveData;
    }

    String url = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/getProductsByCategory?sub_category=teeth";

    /** Sending the Request**/

    public void sendProductsRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O) // for now()
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject json = new JSONObject(response);
                    JSONArray jsonArray = json.optJSONArray("Product");
                    JSONObject jsonObject;
                    ArrayList<ProductModel> arrayList = new ArrayList<>();
                    if(json.getInt("Status")==0){
                        //toast
                        Toast.makeText(context, "Data Loaded successful !",
                                Toast.LENGTH_LONG).show();
                    }
                    else{
                        //toast

                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        arrayList.add(new ProductModel("677tf",jsonObject.getString("Fisrt_cata"),jsonObject.getString("secand_cata"),jsonObject.getString("product_name") ,5.0,5));

                    }
                    // Log.v("---------", arrayList.get(1).getOrderTotalPrice().toString());
                    productResponseLiveData.setValue(arrayList);
                    // Log.v("----", peripheralsResponseLiveData.getValue().get(0).getOrderTotalPrice().toString());
                } catch (JSONException e) {
                    Toast.makeText(context, "Error in Connction  !",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue requestQueue = Utils.getRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}











