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
import android.widget.EditText;
import android.widget.TextView;

import com.example.dawaya.databinding.ActivitySignInBinding;
import com.example.dawaya.models.SignInModel;
import com.example.dawaya.models.SignUpModel;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.utils.Utils;
import com.example.dawaya.viewmodels.SignInViewModel;
import com.example.dawaya.R;
import com.google.android.material.textfield.TextInputEditText;


public class SignInActivity extends AppCompatActivity {


    ActivitySignInBinding signInBinding;
    SignInViewModel viewModel;
    TextView goToSignUp;

    @Override
    protected void onStart() {
        super.onStart();
        // if so, go to Home Screen
        isUserLoggedIn();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        signInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        signInBinding.setViewModel(viewModel);
        signInBinding.setLifecycleOwner(this);

        viewModel.getSignInLiveData().observe(this, new Observer<SignInModel>() {
            @Override
            public void onChanged(SignInModel signInModel) {
                viewModel.setSignInModel(signInModel);
            }
        });

        Button signIn = signInBinding.signInButton;
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText editText = findViewById(R.id.sign_in_email_field);
                viewModel.signIn();
            }
        });

        //Shared Preferences Initialization
        SharedPrefs.init();

        // Don't have an account ?
        goToSignUp = findViewById(R.id.go_to_sign_up);
        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignUpActivity();
            }
        });

        // Successful Sign In
        viewModel.getUserLiveData().observe(this, new Observer<SignUpModel>() {
            @Override
            public void onChanged(SignUpModel user) {

                Utils.createUserSession(user);
                startHomeActivity();
                Log.v("--------", "Done");
                }
            });





    }

    private void startSignUpActivity() {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }
    private void startHomeActivity(){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    private void isUserLoggedIn(){
        if( ! (SharedPrefs.sp.getString(SharedPrefs.USER_ID, "").equals("")) ){
            startHomeActivity();
        }
    }
}