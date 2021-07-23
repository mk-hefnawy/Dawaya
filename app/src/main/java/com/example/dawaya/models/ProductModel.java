package com.example.dawaya.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class ProductModel {

    public String code;
    public String companyId;
    public String supplyId;
    public String name;
    public String firstCategory;
    public String secondCategory;
    public Double price;
    public Double totalPrice;
    public int quantity;
    public int quantityToBuy = 1;
    public String imageUrl;
    public String position;

    Boolean checkedInPrescriptionProducts = true;
    String isTakenInPrescription;
    String isAlternativeInPrescription;

    public ProductModel(String code, String isTakenInPrescription, String isAlternativeInPrescription) {
        this.code = code;
        this.isTakenInPrescription = isTakenInPrescription;
        this.isAlternativeInPrescription = isAlternativeInPrescription;
    }

    public ProductModel(String code, Double price, Double totalPrice, int quantityToBuy, String companyId, String supplyId ) {
        this.code = code;
        this.price = price;
        this.totalPrice = totalPrice;
        this.quantityToBuy = quantityToBuy;
        this.companyId = companyId;
        this.supplyId = supplyId;
    }

    public ProductModel() {
    }

    public ProductModel(String code, String name, String firstCategory, String secondCategory, String position, String imageUrl) {
        this.code = code;
        this.name = name;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.position = position;
        this.imageUrl = imageUrl;
    }

   /* public ProductModel(String code, String name, String firstCategory, String secondCategory, Double price, int quantity, String position,
                        String imageUrl) {
        this.code = code;
        this.name = name;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.price = price;
        this.quantity = quantity;
        this.position = position;
        this.imageUrl = imageUrl;
    }*/

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFirstCategory() {
        return firstCategory;
    }
    public void setFirstCategory(String firstCategory) {
        this.firstCategory = firstCategory;
    }

    public String getSecondCategory() {
        return secondCategory;
    }
    public void setSecondCategory(String secondCategory) {
        this.secondCategory = secondCategory;
    }

    public int getQuantityToBuy() {
        return quantityToBuy;
    }

    public void setQuantityToBuy(int quantityToBuy) {
        this.quantityToBuy = quantityToBuy;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getCheckedInPrescriptionProducts() {
        return checkedInPrescriptionProducts;
    }

    public void setCheckedInPrescriptionProducts(Boolean checkedInPrescriptionProducts) {
        this.checkedInPrescriptionProducts = checkedInPrescriptionProducts;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(String supplyId) {
        this.supplyId = supplyId;
    }


    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getIsTakenInPrescription() {
        return isTakenInPrescription;
    }

    public void setIsTakenInPrescription(String isTakenInPrescription) {
        this.isTakenInPrescription = isTakenInPrescription;
    }

    public String getIsAlternativeInPrescription() {
        return isAlternativeInPrescription;
    }

    public void setIsAlternativeInPrescription(String isAlternativeInPrescription) {
        this.isAlternativeInPrescription = isAlternativeInPrescription;
    }
}
