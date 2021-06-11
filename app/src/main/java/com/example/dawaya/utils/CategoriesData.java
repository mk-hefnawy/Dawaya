package com.example.dawaya.utils;

import java.util.ArrayList;

public class CategoriesData {

    static CategoriesData instance = null;
    public static CategoriesData getInstance(){
        if (instance == null){
            instance =  new CategoriesData();
        }
        return instance;}

     Boolean alreadyFilled = false;

     //An ArrayList for each mainCategory
     ArrayList<String> oral = new ArrayList<>();
     ArrayList<String> respiratory = new ArrayList<>();
     ArrayList<String> eye = new ArrayList<>();
     ArrayList<String> baby = new ArrayList<>();

    private  void prepareData(){
        oral.add("Toothpaste");
        oral.add("Mouthwash");

        respiratory.add("Allergy");
        respiratory.add("Mucolytic");

        eye.add("Lenses Solution");
        eye.add("Antibiotics");

        baby.add("Food");
        baby.add("Diapers");
        baby.add("Shampoo");

        alreadyFilled = true;

    }

    public  void getReady(){
        if (!alreadyFilled) {
            prepareData();

        }
    }

    public  ArrayList<String> getCategories(String categoryName) {
        ArrayList<String> categories = new ArrayList<>();
        switch (categoryName) {

            case "Oral Care":
                categories = oral;
                break;

            case "Respiratory":
                categories = respiratory;
                break;

            case "Eye":
                categories = eye;
                break;

            case "Baby Care":
                categories = baby;
                break;
            default:
                categories = null;


        }
        return categories;

    }

}
