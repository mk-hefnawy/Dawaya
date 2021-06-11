package com.example.dawaya.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.models.AddressModel;
import com.example.dawaya.repositories.AddressBookRepo;

import java.util.ArrayList;

public class AddressBookViewModel extends ViewModel {

    MutableLiveData<ArrayList<AddressModel>> addressesLiveData = new MutableLiveData<>();

    AddressBookRepo addressBookRepo = AddressBookRepo.getInstance(); //Singleton

    public void sendAddressesRequest(String userId){
        addressBookRepo.getAddresses(userId);
        observeRepoResponse();
    }

   /* public void sendAddAddressRequest(){
        addressBookRepo.addAddress();
    }*/

    private void observeRepoResponse() {
        addressBookRepo.responseLiveData.observeForever(new Observer<ArrayList<AddressModel>>() {
            @Override
            public void onChanged(ArrayList<AddressModel> addressModels) {
                updateViewModelLiveData();
            }
        });
    }

    private void updateViewModelLiveData() {
        addressesLiveData.setValue(addressBookRepo.getResponseLiveData().getValue());
    }




    public MutableLiveData<ArrayList<AddressModel>> getAddressesLiveData() {
        return addressesLiveData;
    }
}
