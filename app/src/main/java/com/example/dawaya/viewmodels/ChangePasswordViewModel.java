package com.example.dawaya.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.repositories.ChangePasswordRepo;

public class ChangePasswordViewModel extends ViewModel {

    MutableLiveData<Integer> changePasswordStatus = new MutableLiveData<>();
    ChangePasswordRepo repo = ChangePasswordRepo.getInstance();

    public void changePassword(String oldPassword, String newPassword, String userId){
        repo.changePassword(oldPassword, newPassword, userId);
        observeChangePasswordStatus();

    }

    private void observeChangePasswordStatus() {
        repo.getChangePasswordStatus().observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                changePasswordStatus.setValue(integer);
            }
        });
    }

    public MutableLiveData<Integer> getChangePasswordStatus() {
        return changePasswordStatus;
    }
}
