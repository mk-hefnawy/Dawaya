package com.example.dawaya.models;

public class CategoryModel {
    String catName;
    int imageResourceId;





    public CategoryModel(String catName, int imageResourceId) {
        this.catName = catName;
        this.imageResourceId = imageResourceId;
    }

    public String getCatName() {
        return catName;
    }
    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }


}
