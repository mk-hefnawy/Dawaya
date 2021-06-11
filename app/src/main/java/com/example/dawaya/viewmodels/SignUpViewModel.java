package com.example.dawaya.viewmodels;

import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dawaya.models.SignUpModel;
import com.example.dawaya.repositories.SignUpRepo;
import com.example.dawaya.responses.SignUpResponse;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.Utils;

import java.util.Date;
import java.util.Map;

public class SignUpViewModel extends ViewModel {

    //Context context = App.getAppContext();

    Boolean isValid, error= false;
    Map<String, String> tempSignUpErrors;
    DatePicker datePicker;

    private MutableLiveData<SignUpModel> signUpLiveData = new MutableLiveData<>(); // for view binding
    public MutableLiveData<SignUpModel> userLiveData = new MutableLiveData<>(); // user info (including id)


    SignUpModel signUpModel = new SignUpModel(); //Default constructor




    //Notice No need for something called response live data
    MutableLiveData<SignUpResponse> signUpResponseLiveData = new MutableLiveData<>();
    MutableLiveData<Map<String, String>> signUpErrorsLiveData = new MutableLiveData<>();


    SignUpRepo signUpRepo = SignUpRepo.getInstance();

    public SignUpViewModel() {

        signUpLiveData.setValue(signUpModel);
        signUpResponseLiveData.setValue(new SignUpResponse("", ""));
    }


    public void signUp() {

        Date dateOfBirth = Utils.getDateFromDatePicker(datePicker);
        String strDateOfBirth = Utils.convertDateToString(dateOfBirth);
        signUpModel.setDateOfBirth(strDateOfBirth);
        // signUpLiveData.setValue(signUpModel); // no need for this line
        Log.v("viewModelLive Date", signUpLiveData.getValue().getDateOfBirth());
        Log.v("viewModelLive Gender", signUpLiveData.getValue().getGender());

        signUpModel = signUpLiveData.getValue();
        signUpRepo.signUp(signUpModel);
        //getting user id
        observeRepoResponse();

    }

    private Boolean isUserInputValid(SignUpModel signUpModel) {
        initErrorMap();
        if(signUpModel.getFirstName().isEmpty()){ tempSignUpErrors.put("firstNameError", "Please, enter your first name"); error=true; }
        if(signUpModel.getLastName().isEmpty()){ tempSignUpErrors.put("lastNameError", "Please, enter your last name"); error=true;}
        if(signUpModel.getEmail().isEmpty()){ tempSignUpErrors.put("emailError", "Please, enter your email");error=true; }
        if(signUpModel.getPassword().isEmpty()){ tempSignUpErrors.put("passwordError", "Please, enter a password"); error=true;}
        //if(signUpModel.getPhoneNumber().isEmpty()){ tempSignUpErrors.put("phoneNumberError", "Please, enter your phone number");error=true; }
        if(signUpModel.getGender().isEmpty()){ tempSignUpErrors.put("genderError", "Please, select your gender"); error=true;}

        return !error;
    }

    private void initErrorMap() {
        tempSignUpErrors.put("firstNameError", "");
        tempSignUpErrors.put("lastNameError", "");
        tempSignUpErrors.put("emailError", "");
        tempSignUpErrors.put("passwordError", "");
        tempSignUpErrors.put("phoneNumberError", "");
        tempSignUpErrors.put("genderError", "");
    }

    /*public void getResponse(){
        signUpResponseLiveData.setValue(new SignUpResponse(signUpRepo.getSignUpResponseLiveData().getValue().success,
                                                            signUpRepo.getSignUpResponseLiveData().getValue().alreadyHasAnAccount));
    }*/

    public void observeRepoResponse(){
        signUpRepo.currentUserId.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                signUpModel.setUserId(s);
                Log.v("LastName", signUpModel.getLastName());
                userLiveData.setValue(signUpModel);
            }
        });
    }



    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
    }
    public MutableLiveData<SignUpModel> getSignUpLiveData() {
        return signUpLiveData;
    }
    public void setSignUpLiveData(MutableLiveData<SignUpModel> signUpLiveData) {
        this.signUpLiveData = signUpLiveData;
    }
    public MutableLiveData<Map<String, String>> getSignUpErrorsLiveData() {
        return signUpErrorsLiveData;
    }
    public void setSignUpErrorsLiveData(MutableLiveData<Map<String, String>> signUpErrorsLiveData) {
        this.signUpErrorsLiveData = signUpErrorsLiveData;
    }

    public MutableLiveData<SignUpModel> getUserLiveData() {
        return userLiveData;
    }
}
