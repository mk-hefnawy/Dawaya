package com.example.dawaya.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.models.AddressModel;
import com.example.dawaya.repositories.AddressBookRepo;

public class AddressViewModel extends ViewModel {

    AddressBookRepo repo = AddressBookRepo.getInstance();


    public MutableLiveData<AddressModel> addressLiveData = new MutableLiveData<>();
    public MutableLiveData<AddressModel> editAddressLiveData = new MutableLiveData<>();

    public MutableLiveData<Integer> statusLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> editStatusLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> deleteStatusLiveData = new MutableLiveData<>();

    AddressModel addressModel = new AddressModel();

    public AddressViewModel() {
        this.addressLiveData.setValue(addressModel);
    }

    //Getters and Setters
    public MutableLiveData<AddressModel> getAddressLiveData() {
        return addressLiveData;
    }
    public void setAddressLiveData(MutableLiveData<AddressModel> addressLiveData) {
        this.addressLiveData = addressLiveData;
    }




    public void addAddress(String customerId){
        String address = addressLiveData.getValue().getCounty() + ',' +
                addressLiveData.getValue().getStreet() + ',' +
                addressLiveData.getValue().getBuildingNo() + ',' +
                addressLiveData.getValue().getFloorNo() + ',' +
                addressLiveData.getValue().getApartmentNo();
        Log.v("ViewModel", address);

        repo.addAddress(address, customerId);
        observeStatus();
    }

    public void editAddress(String customerId, String oldAddress){
        String address = editAddressLiveData.getValue().getCounty() + ',' +
                editAddressLiveData.getValue().getStreet() + ',' +
                editAddressLiveData.getValue().getBuildingNo() + ',' +
                editAddressLiveData.getValue().getFloorNo() + ',' +
                editAddressLiveData.getValue().getApartmentNo();
        Log.v("ViewModel", address);

        repo.editAddress(oldAddress, address, customerId);
        observePutStatus();
    }

    public void deleteAddress(String customerId, String oldAddress){
        repo.deleteAddress(oldAddress, customerId);
        observeDeleteStatus();
    }

    private void observeDeleteStatus() {
        repo.deleteStatusLiveData.observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                deleteStatusLiveData.setValue(integer);
            }
        });
    }

    private void observePutStatus() {
        repo.putStatusLiveData.observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                editStatusLiveData.setValue(integer);
            }
        });
    }

    private void observeStatus() {
        repo.postStatusLiveData.observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                statusLiveData.setValue(integer);
            }
        });
    }


}
