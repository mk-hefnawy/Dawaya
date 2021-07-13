package com.example.dawaya.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.repositories.PhoneNumbersRepo;

import java.util.ArrayList;

public class PhoneNumbersViewModel extends ViewModel {

    MutableLiveData<ArrayList<String>> fromRepoPhoneNumbersLiveDate = new MutableLiveData<>();
    MutableLiveData<Integer> addPhoneNumberStatus = new MutableLiveData<>();
    PhoneNumbersRepo repo = PhoneNumbersRepo.getInstance();


    public void getPhoneNumbers(String userId){
        repo.getPhoneNumbers(userId);
        observeGetPhoneNumbersResponse();

    }

    private void observeGetPhoneNumbersResponse() {
        repo.getFromServerPhoneNumbersLiveData().observeForever(new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                fromRepoPhoneNumbersLiveDate.setValue(strings);
            }
        });
    }


    public void addPhoneNumber(String userId, String phoneNumber){
        repo.addPhoneNumber(userId, phoneNumber);
        observeAddPhoneNumbersResponse();
    }

    private void observeAddPhoneNumbersResponse() {
        repo.getAddPhoneNumberStatus().observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                addPhoneNumberStatus.setValue(integer);
            }
        });
    }

    public MutableLiveData<ArrayList<String>> getFromRepoPhoneNumbersLiveDate() {
        return fromRepoPhoneNumbersLiveDate;
    }

    public MutableLiveData<Integer> getAddPhoneNumberStatus() {
        return addPhoneNumberStatus;
    }
}
