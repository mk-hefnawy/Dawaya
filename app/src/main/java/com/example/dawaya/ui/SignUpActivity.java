package com.example.dawaya.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dawaya.R;
import com.example.dawaya.databinding.ActivitySignUpBinding;
import com.example.dawaya.models.SignUpModel;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.utils.Utils;
import com.example.dawaya.viewmodels.SignUpViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {

    SignUpViewModel viewModel;
    TextInputLayout firstNameLayout;
    RadioGroup genderRadioGroup;
    RadioButton selectedGender;

    Button signUpButton;
    DatePicker datePicker;

    ActivitySignUpBinding activitySignUpBinding; // kant final

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPrefs.init();


        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        activitySignUpBinding.setViewModel(viewModel);
        activitySignUpBinding.setLifecycleOwner(this);

        signUpButton = activitySignUpBinding.signUpButton;
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignUpClicked();
            }
        });
        observeUserLiveData();


        // No data binding
        observeGender();



        /*viewModel.getSignUpErrorsLiveData().observe(this, new Observer<Map<String, String>>() {
            @Override
            public void onChanged(Map<String, String> stringStringMap) {
                firstNameLayout = findViewById(R.id.first_name_container);
                firstNameLayout.setError(viewModel.getSignUpErrorsLiveData().getValue().get("firstNameError"));
            }
        });*/

        }

    private void observeUserLiveData() {
        viewModel.userLiveData.observeForever(new Observer<SignUpModel>() {
            @Override
            public void onChanged(SignUpModel signUpModel) {

                Utils.createUserSession(signUpModel);


                startHomeActivity();
            }
        });
    }

    private void onSignUpClicked() {
        //Date picker stuff
        datePicker = activitySignUpBinding.datePicker;
        viewModel.setDatePicker(datePicker);

        //Gender stuff


        //View model method invoking
        viewModel.signUp();

    }

    private void observeGender() {
        genderRadioGroup = activitySignUpBinding.genderRadioGroup;
        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectedGender = findViewById(i);
                viewModel.getSignUpLiveData().getValue().setGender(selectedGender.getText().toString());
            }
        });
    }




    private void startHomeActivity() {
        startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
    }
}
