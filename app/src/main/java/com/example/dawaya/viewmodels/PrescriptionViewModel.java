package com.example.dawaya.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.models.PrescriptionModel;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.repositories.PrescriptionRepo;
import com.example.dawaya.repositories.SearchRepo;
import com.example.dawaya.utils.SharedPrefs;

import java.io.File;
import java.util.ArrayList;

public class PrescriptionViewModel extends ViewModel {

    PrescriptionRepo repo = PrescriptionRepo.getInstance();
    SearchRepo searchRepo = SearchRepo.getInstance();

    MutableLiveData<ArrayList<ProductModel>> prescriptionProducts = new MutableLiveData<>();

    MutableLiveData<ArrayList<PrescriptionModel>> prescriptionsLiveData = new MutableLiveData<>();


    MutableLiveData<ArrayList<ProductModel>> singlePrescriptionProductsLiveData = new MutableLiveData<>();
    ArrayList<ProductModel> singlePrescriptionProducts = new ArrayList<>();


    public void uploadPrescription(File imageFile){
        Log.v("------", imageFile.getPath());
        repo.uploadPrescriptionImage(imageFile);
        //observePrescriptionProducts();
    }

    private void observePrescriptionProducts() {
        repo.getPrescriptionProducts().observeForever(new Observer<ArrayList<ProductModel>>() {
            @Override
            public void onChanged(ArrayList<ProductModel> productModels) {
                prescriptionProducts.setValue(productModels);
            }
        });
    }

    public MutableLiveData<ArrayList<ProductModel>> getPrescriptionProducts() {
        return prescriptionProducts;
    }

    public void getAllPrescriptions() {
        repo.getAllPrescriptions(SharedPrefs.read(SharedPrefs.USER_ID, " "));
        repo.getPrescriptionsLiveData().observeForever(new Observer<ArrayList<PrescriptionModel>>() {
            @Override
            public void onChanged(ArrayList<PrescriptionModel> prescriptions) {
                    getAllPrescriptionsProducts(prescriptions, 0, 0);
            }
        });
    }

    private void getAllPrescriptionsProducts(ArrayList<PrescriptionModel> prescriptions, int prescriptionIndex, int productIndex) {
        if (prescriptionIndex == prescriptions.size()){
            prescriptionsLiveData.setValue(prescriptions);
            return;
        }

        getSinglePrescriptionProducts(prescriptions.get(prescriptionIndex), prescriptions.get(prescriptionIndex).getProducts().get(productIndex).getCode(),
                productIndex);
        singlePrescriptionProductsLiveData.observeForever(new Observer<ArrayList<ProductModel>>() {
            @Override
            public void onChanged(ArrayList<ProductModel> products) {
                PrescriptionModel prescription = prescriptions.get(prescriptionIndex);
                for (int i = 0; i<prescription.getProducts().size() && i<prescription.getProducts().size() ; i++){

                    for (int j = i+1 ; j<products.size() && j<prescription.getProducts().size() ; j++){

                        if (prescription.getProducts().get(i).getCode().equals(products.get(j).getCode())){

                            prescription.getProducts().get(i).setName(products.get(j).getName());
                            prescription.getProducts().get(i).setFirstCategory(products.get(j).getFirstCategory());
                            prescription.getProducts().get(i).setSecondCategory(products.get(j).getSecondCategory());
                            prescription.getProducts().get(i).setPosition(products.get(j).getPosition());

                        }
                    }
                }

                getAllPrescriptionsProducts(prescriptions, prescriptionIndex+1, productIndex);
            }
        });

    }

    private void getSinglePrescriptionProducts(PrescriptionModel prescription, String code, int productIndex){

        if (productIndex == prescription.getProducts().size()){
            singlePrescriptionProductsLiveData.setValue(singlePrescriptionProducts);
            return;
        }

        searchRepo.searchByCode(code);
        searchRepo.productSearchedByCode.observeForever(new Observer<ProductModel>() {
            @Override
            public void onChanged(ProductModel productModel) {
                singlePrescriptionProducts.add(productModel);
                if (productIndex+1 == prescription.getProducts().size()){
                    getSinglePrescriptionProducts(prescription, "", productIndex+1);
                }
                else {
                    getSinglePrescriptionProducts(prescription, prescription.getProducts().get(productIndex + 1).getCode(), productIndex + 1);
                }
            }
        });

    }

    public MutableLiveData<ArrayList<PrescriptionModel>> getPrescriptionsLiveData() {
        return prescriptionsLiveData;
    }
}
