package com.example.dawaya.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dawaya.interfaces.ImageUploadInterface;
import com.example.dawaya.models.PrescriptionModel;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.responses.UploadPrescriptionResponse;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.URLs;
import com.example.dawaya.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PrescriptionRepo {


    Context context = App.getAppContext();
    RequestQueue requestQueue = Utils.getRequestQueue(context);

    MutableLiveData<ArrayList<ProductModel>> prescriptionProducts = new MutableLiveData<>();
    MutableLiveData<ArrayList<PrescriptionModel>> prescriptionsLiveData = new MutableLiveData<>();

    private static PrescriptionRepo instance = null;
    public static PrescriptionRepo getInstance() {
        if (instance == null) {
            instance = new PrescriptionRepo();
        }
        return instance;
    }


    public void uploadPrescription(File imageFile, String userId) {
        String baseUrl = URLs.postPrescription;
        Retrofit retrofit = Utils.getRetrofitInstance(baseUrl);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("multipartFile", imageFile.getName(), requestFile);
        //RequestBody userIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), userId);

        ImageUploadInterface imageUploadInterface = retrofit.create(ImageUploadInterface.class);
        Call<UploadPrescriptionResponse> call = imageUploadInterface.uploadImage(body);

        Log.v("----", "repo");
        call.enqueue(new Callback<UploadPrescriptionResponse>() {
            @Override
            public void onResponse(Call<UploadPrescriptionResponse> call, Response<UploadPrescriptionResponse> response) {

                String prescriptionImageUrl = response.body().getPrescriptionId();

                /*Log.v("onResponse", response.body().getPrescriptionId());
                String prescriptionId = response.body().getPrescriptionId();
                getPrescriptionProducts(prescriptionId);*/
            }

            @Override
            public void onFailure(Call<UploadPrescriptionResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    private void getPrescriptionProducts(String prescriptionId) {
        System.out.println("getPrescriptionProducts");
        String url = URLs.getPrescriptionProducts;    // + "/" + prescriptionId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String status = response.getString("status");
                        JSONArray prescriptionProductsJsonArray;
                        JSONObject product;
                        ArrayList<ProductModel> arrayList = new ArrayList<>();

                        if (status.equals("1")) {
                            prescriptionProductsJsonArray = response.getJSONArray("prescription_products");
                            for (int i = 0 ; i<prescriptionProductsJsonArray.length() ; i++){
                                product = prescriptionProductsJsonArray.getJSONObject(i);
                                /*arrayList.add(new ProductModel(product.getString("code"), product.getString("name"),
                                        product.getString("main_category"),product.getString("secondary_category"),
                                        product.getDouble("price"), product.getInt("quantity"), product.getString("position"),
                                        product.getString("imageUrl")));*/
                            }
                            prescriptionProducts.setValue(arrayList);
                        }
                        else {
                                // Wait for an amount of time and resend the request till the status is 1
                            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                ,error -> {

                });

        requestQueue.add(jsonObjectRequest);
    }

    public void getAllPrescriptions(String userId) {
        String url = URLs.getAllPrescriptions + "/" + 9;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonPrescriptions = response.getJSONArray("the result");
                        JSONObject jsonPrescription;

                        JSONArray jsonPrescriptionProducts;
                        JSONObject jsonPrescriptionProduct;
                        ArrayList<PrescriptionModel> prescriptions = new ArrayList<>();


                        for (int i = 0 ; i<jsonPrescriptions.length() ; i++){
                            ArrayList<ProductModel> prescriptionProducts = new ArrayList<>();
                            jsonPrescription = jsonPrescriptions.getJSONObject(i);

                            String prescriptionImageUrl = jsonPrescription.getString("url");
                            String prescriptionId = jsonPrescription.getString("id");
                            String prescriptionSendTime = jsonPrescription.getString("sentTime");
                            String prescriptionReplyTime = jsonPrescription.getString("replyTime");

                            jsonPrescriptionProducts = jsonPrescription.getJSONArray("prescriptsProducts");
                            for (int j = 0 ; j < jsonPrescriptionProducts.length() ; j++){

                                jsonPrescriptionProduct = jsonPrescriptionProducts.getJSONObject(j);

                                String isTaken = jsonPrescriptionProduct.getString("status");
                                String isAlternative = jsonPrescriptionProduct.getString("type");

                                JSONObject id = jsonPrescriptionProduct.getJSONObject("id");

                                prescriptionProducts.add(new ProductModel(id.getString("productCode"), isTaken, isAlternative));
                            }

                            prescriptions.add(new PrescriptionModel(prescriptionId, prescriptionImageUrl, prescriptionSendTime,
                                    prescriptionReplyTime, prescriptionProducts));
                        }

                        prescriptionsLiveData.setValue(prescriptions);

                    } catch (JSONException e) {e.printStackTrace(); }

                },
                error -> {
                    error.printStackTrace();
                });
        requestQueue.add(jsonObjectRequest);
    }

    public MutableLiveData<ArrayList<PrescriptionModel>> getPrescriptionsLiveData() {
        return prescriptionsLiveData;
    }

    public MutableLiveData<ArrayList<ProductModel>> getPrescriptionProducts() {
        return prescriptionProducts;
    }
}