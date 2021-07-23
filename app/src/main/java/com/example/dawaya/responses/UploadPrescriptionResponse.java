package com.example.dawaya.responses;

public class UploadPrescriptionResponse {

    String prescriptionImageUrl;

    public String getPrescriptionId() {
        return prescriptionImageUrl;
    }

    public void setPrescriptionId(String prescriptionImageUrl) {
        this.prescriptionImageUrl = prescriptionImageUrl;
    }
}
