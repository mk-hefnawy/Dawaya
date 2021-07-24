package com.example.dawaya.interfaces;

import com.example.dawaya.responses.UploadPrescriptionResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ImageUploadInterface {

    @Multipart
    @POST("/blob/upload")
    public Call<UploadPrescriptionResponse> uploadImage(@Part MultipartBody.Part image);
}
