package com.example.dawaya.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.dawaya.R;
import com.example.dawaya.adapters.PhoneNumbersAdapter;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.viewmodels.PhoneNumbersViewModel;

import java.util.ArrayList;

public class PhoneNumbersActivity extends AppCompatActivity {

    PhoneNumbersViewModel viewModel;
    RecyclerView phoneNumbersRecyclerView;
    PhoneNumbersAdapter adapter;

    ArrayList<String> phoneNumbers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_numbers);

        //Getting phone numbers from database
        viewModel = new ViewModelProvider(this).get(PhoneNumbersViewModel.class);
        viewModel.getPhoneNumbers(SharedPrefs.read(SharedPrefs.USER_ID, ""));
        observePhoneNumbersLiveData();



    }

    private void observePhoneNumbersLiveData() {
        viewModel.getFromRepoPhoneNumbersLiveDate().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                phoneNumbers = strings;
                populateRecyclerView();
            }
        });
    }

    private void populateRecyclerView() {
        phoneNumbersRecyclerView = findViewById(R.id.phone_numbers_recycler_view);
        adapter = new PhoneNumbersAdapter(phoneNumbers);
        phoneNumbersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        phoneNumbersRecyclerView.setAdapter(adapter);
    }
}