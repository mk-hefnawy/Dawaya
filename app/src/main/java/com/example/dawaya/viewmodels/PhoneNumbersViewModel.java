package com.example.dawaya.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.repositories.PhoneNumbersRepo;

import java.util.ArrayList;

public class PhoneNumbersViewModel extends ViewModel {

    MutableLiveData<ArrayList<String>> fromRepoPhoneNumbersLiveDate = new MutableLiveData<>();
    PhoneNumbersRepo repo = PhoneNumbersRepo.getInstance();


    public void getPhoneNumbers(String userId){
        repo.getPhoneNumbers(userId);
        observePhoneNumbersResponse();

    }

    private void observePhoneNumbersResponse() {
        repo.getFromServerPhoneNumbersLiveData().observeForever(new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                fromRepoPhoneNumbersLiveDate.setValue(strings);
            }
        });
    }

    public MutableLiveData<ArrayList<String>> getFromRepoPhoneNumbersLiveDate() {
        return fromRepoPhoneNumbersLiveDate;
    }
}
