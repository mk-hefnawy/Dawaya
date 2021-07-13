package com.example.dawaya.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;

import android.os.Bundle;

import com.example.dawaya.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class WebScrapingActivity extends AppCompatActivity {

    ArrayList<String> foodNamesTexts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_scraping);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                scrape();
                for(String name : foodNamesTexts){
                    System.out.println(name);
                }
            }
        };

        Thread webScrapingThread = new Thread(runnable);
        webScrapingThread.start();



    }

    private void scrape() {
        //Connecting
        String url =  "https://www.fouda.com/fp/taxonomy/term/19203";
        try {
            Document document = Jsoup.connect(url).get();
            System.out.println(document.title());

            //Getting the parent Tag
            Elements divs = document.getElementsByClass("col-xs-12 remove-padding product-item");
            //Log.v("---", divs.get(0).text());
            Elements foodNames = new Elements();
            Elements foodPrices = new Elements();
            for(int i = 0 ; i<divs.size(); i++){
                foodNames.add(divs.get(i).children().get(2));
                foodPrices.add(divs.get(i).children().get(5));
            }
            for(int i = 0 ; i<foodNames.size(); i++){
                foodNamesTexts.add(foodNames.get(i).text());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}