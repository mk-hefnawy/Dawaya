package com.example.dawaya.viewmodels;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

import com.example.dawaya.repositories.PrescriptionRepo;

public class PrescriptionViewModel extends ViewModel {

    PrescriptionRepo repo = PrescriptionRepo.getInstance();


    public void uploadPrescription(Bitmap bitmap){

        repo.uploadPrescription(bitmap);
    }
}
