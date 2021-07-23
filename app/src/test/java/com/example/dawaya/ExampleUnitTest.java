package com.example.dawaya;

import android.util.Log;

import androidx.collection.ArraySet;

import com.example.dawaya.models.ProductModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


public class ExampleUnitTest {
  @Test
    public void test(){
        String dateTime = "2020-04-13 13:03:50";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime dateTime1 = LocalDateTime.parse(dateTime, formatter);
        System.out.println(dateTime1.toString().replace('T', ' '));
    }

    @Test
    public void testGson(){
        ProductModel productModel = new ProductModel("code", "name",
        "mainCategory", "secondaryCategory",
                10.0, 10, "", "");


        String productString = new Gson().toJson(productModel);
        System.out.println(productString);

        ProductModel product = new Gson().fromJson(productString, ProductModel.class);
        System.out.println("ProductName" + " " + product.getName());
    }






}