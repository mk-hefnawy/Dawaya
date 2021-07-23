package com.example.dawaya.dummy;

import com.example.dawaya.responses.PostBillProductsResponse;
import com.example.dawaya.responses.product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

   /* //Resource Technique
    @GET("/posts/{id}") // El Ba2y b3d el BaseUrl
    public Call<DummyModel> getPost(@Path("id") int postId); //El Response Hykon hwa El DummyModel


    @GET("/comments")
    public Call<ArrayList<CommentModel>> getComments(@Query("postId") String postId);*/


   // lamma kont 7att /BillsProductsList bas kant 3amla mo4kla
    @POST("/BillsProduct/BillsProductsList")
    public Call<List<PostBillProductsResponse>> storePost(@Body ArrayList<product> postProducts);


  /* @POST("/posts")
    public Call<DummyModel> storePostMap(@Body HashMap<Object, Object> map);*/
}
