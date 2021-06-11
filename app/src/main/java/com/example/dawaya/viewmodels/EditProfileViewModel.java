package com.example.dawaya.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.models.SignUpModel;
import com.example.dawaya.repositories.EditProfileRepo;

public class EditProfileViewModel extends ViewModel {
    SignUpModel user;
    EditProfileRepo editProfileRepo = EditProfileRepo.getInstance();

    MutableLiveData<SignUpModel> updatedUser = new MutableLiveData<>();



    public SignUpModel getUser() {
        return user;
    }
    public void setUser(SignUpModel user) {
        this.user = user;
    }


    public void updateUserData(String gender, String dateOfBirth){

        //For updated gender and date of birth as there is no view binding for these fields
        user.setGender(gender);
        user.setDateOfBirth(dateOfBirth);

        Log.v("viewmodel firstName", user.getFirstName());
        Log.v("viewmodel lastName", user.getLastName());
        Log.v("viewmodel email", user.getEmail());
        Log.v("viewmodel phoneNumber", user.getPhoneNumber());
        Log.v("viewmodel gender", user.getGender());
        Log.v("viewmodel Date", user.getDateOfBirth());


        editProfileRepo.updateUser(user);
        observeRepoResponse();
    }
    private void observeRepoResponse(){
        editProfileRepo.getUpdatedUser().observeForever(new Observer<SignUpModel>() {
            @Override
            public void onChanged(SignUpModel user) {
                updatedUser.setValue(user);
            }
        });
    }

    public MutableLiveData<SignUpModel> getUpdatedUser() {
        return updatedUser;
    }
}
