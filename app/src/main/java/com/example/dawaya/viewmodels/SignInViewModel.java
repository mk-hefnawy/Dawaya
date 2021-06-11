package com.example.dawaya.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.models.SignInModel;
import com.example.dawaya.models.SignUpModel;
import com.example.dawaya.repositories.SignInRepo;
import com.example.dawaya.responses.SignUpResponse;

public class SignInViewModel extends ViewModel {

    private MutableLiveData<SignInModel> signInLiveData = new MutableLiveData<>();
    MutableLiveData<SignUpModel> userLiveData = new MutableLiveData<>();

    SignInRepo myRepo = SignInRepo.getInstance(); //Singleton

    SignInModel signInModel = new SignInModel();

    public SignInViewModel() {
        //Notice without this line below the data will not come from the view binding idk why
        signInLiveData.setValue(signInModel);
    }

    public void setSignInModel(SignInModel signInModel) {
        this.signInModel = signInModel;
    }

    public MutableLiveData<SignInModel> getSignInLiveData() {

        return signInLiveData;
    }
    public void setSignInLiveData(MutableLiveData<SignInModel> signInLiveData) {
        this.signInLiveData = signInLiveData;
    }

    public MutableLiveData<SignUpModel> getUserLiveData() {
        return userLiveData;
    }

    public void signIn() {

        signInModel = signInLiveData.getValue();
        myRepo.signIn(signInModel);

        observeStatus();


    }

    private void observeStatus() {
        myRepo.status.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("1")){
                    observeRepoResponse();
                }
            }
        });
    }


    public void observeRepoResponse(){
        myRepo.userLiveDataRepo.observeForever(new Observer<SignUpModel>() {
            @Override
            public void onChanged(SignUpModel user) {

               userLiveData.setValue(user);
            }
        });
    }
}
