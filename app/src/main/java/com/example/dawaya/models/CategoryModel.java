package com.example.dawaya.models;

public class CategoryModel {
    String catName;
    String imageUrl;


    public CategoryModel(String catName, String imageUrl) {
        this.catName = catName;
        this.imageUrl = imageUrl;
    }

    public String getCatName() {
        return catName;
    }
    public void setCatName(String catName) {
        this.catName = catName;
    }


    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
