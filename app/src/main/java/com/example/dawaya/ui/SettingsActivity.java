package com.example.dawaya.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.dawaya.R;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.viewmodels.PhoneNumbersViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    RelativeLayout editProfile, changePassword, editPhoneNumbers ,changeLanguage;

    EditProfileFragment editProfileFragment;
    ChangePasswordFragment changePasswordFragment;
    EditPhoneNumbersFragment editPhoneNumbersFragment;

    PhoneNumbersViewModel viewModel;

    ImageView backFromSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        viewModel = new ViewModelProvider(this).get(PhoneNumbersViewModel.class);

        editProfile = findViewById(R.id.edit_profile_container);
        changePassword = findViewById(R.id.change_password_container);
        editPhoneNumbers = findViewById(R.id.edit_phone_number_container);
        changeLanguage = findViewById(R.id.change_language_container);

        editProfileFragment = new EditProfileFragment();
        changePasswordFragment = new ChangePasswordFragment();
        editPhoneNumbersFragment = new EditPhoneNumbersFragment();

        onBackArrowPressed();
        onSettingsItemClicked();
    }

    private void onBackArrowPressed() {
        backFromSettings = findViewById(R.id.back_from_settings);
        backFromSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.super.onBackPressed();
            }
        });

    }
    private void onSettingsItemClicked(){

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEditProfileFragment();
            }
        });


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChangePasswordFragment();
            }
        });

        editPhoneNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEditPhoneNumbersFragment();
            }
        });


        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLanguageDialog();
            }
        });
    }


    private void startEditPhoneNumbersFragment() {
        Bundle neededData = new Bundle();
        viewModel.getPhoneNumbers(SharedPrefs.read(SharedPrefs.USER_ID, " "));
        viewModel.getFromRepoPhoneNumbersLiveDate().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> phoneNumbers) {
                for (int i =0; i<phoneNumbers.size(); i++){
                    neededData.putString(String.valueOf(i), phoneNumbers.get(i));
                }
                editPhoneNumbersFragment.setArguments(neededData);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.settings_fragment,editPhoneNumbersFragment);
                ft.addToBackStack("EditPhoneNumbersFragment");
                ft.commit();
            }
        });

        //get phone numbers from server



    }

    private void startChangePasswordFragment() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.settings_fragment,changePasswordFragment);
        ft.addToBackStack("ChangePasswordFragment");
        ft.commit();
    }

    private void startEditProfileFragment() {
        //SharedPrefs.init();
        Bundle neededData = new Bundle();
        neededData.putString("firstName", SharedPrefs.read(SharedPrefs.FIRST_NAME, "User"));
        neededData.putString("lastName", SharedPrefs.read(SharedPrefs.LAST_NAME, "User"));
        neededData.putString("email", SharedPrefs.read(SharedPrefs.EMAIL, "User"));
        //Todo handle more than one phone number
        //neededData.putString("phoneNumber", SharedPrefs.read(SharedPrefs.PHONE_NUMBER, "Phone Number"));
        neededData.putString("gender", SharedPrefs.read(SharedPrefs.GENDER, "Gender"));
        neededData.putString("dateOfBirth", SharedPrefs.read(SharedPrefs.DATE_OF_BIRTH, "Date"));

        editProfileFragment.setArguments(neededData);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.settings_fragment,editProfileFragment);
        ft.addToBackStack("EditProfileFragment");
        ft.commit();
    }

    private void showLanguageDialog() {

    }
    @Override
    public void onBackPressed() {
        if (editProfileFragment.isVisible()){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack();
        }
        else super.onBackPressed();
    }

}