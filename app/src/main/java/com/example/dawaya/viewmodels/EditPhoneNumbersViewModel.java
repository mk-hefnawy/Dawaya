package com.example.dawaya.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.repositories.EditPhoneNumbersRepo;
import com.example.dawaya.utils.SharedPrefs;

public class EditPhoneNumbersViewModel extends ViewModel {
    String phoneNumber;

    EditPhoneNumbersRepo repo = EditPhoneNumbersRepo.getInstance();

    MutableLiveData<Integer> updatePhoneNumberStatus = new MutableLiveData<>();
    MutableLiveData<Integer> deletePhoneNumberStatus = new MutableLiveData<>();

    public void editPhoneNumber(String oldPhoneNumber, String newPhoneNumber){
        repo.editPhoneNumber(SharedPrefs.read(SharedPrefs.USER_ID, " "), oldPhoneNumber, newPhoneNumber);
        observeUpdateStatus();
    }



    public void deletePhoneNumber(String phoneNumber){
        repo.deletePhoneNumber(SharedPrefs.read(SharedPrefs.USER_ID, " "), phoneNumber);
        observeDeleteStatus();
    }

    private void observeUpdateStatus() {
        repo.getUpdatePhoneNumberStatus().observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                updatePhoneNumberStatus.setValue(integer);
            }
        });
    }

    private void observeDeleteStatus() {
        repo.getDeletePhoneNumberStatus().observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                deletePhoneNumberStatus.setValue(integer);
            }
        });
    }


    public MutableLiveData<Integer> getUpdatePhoneNumberStatus() {
        return updatePhoneNumberStatus;
    }

    public MutableLiveData<Integer> getDeletePhoneNumberStatus() {
        return deletePhoneNumberStatus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
