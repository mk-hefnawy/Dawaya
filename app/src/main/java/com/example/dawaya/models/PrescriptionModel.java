package com.example.dawaya.models;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionModel {

    String prescriptionPath;
    ArrayList<ProductModel> prescriptionProducts;

    public PrescriptionModel(String prescriptionPath, ArrayList<ProductModel> prescriptionProducts) {
        this.prescriptionPath = prescriptionPath;
        this.prescriptionProducts = prescriptionProducts;
    }

    public String getPrescriptionPath() {
        return prescriptionPath;
    }

    public void setPrescriptionPath(String prescriptionPath) {
        this.prescriptionPath = prescriptionPath;
    }

    public List<ProductModel> getPrescriptionProducts() {
        return prescriptionProducts;
    }

    public void setPrescriptionProducts(ArrayList<ProductModel> prescriptionProducts) {
        this.prescriptionProducts = prescriptionProducts;
    }
}
