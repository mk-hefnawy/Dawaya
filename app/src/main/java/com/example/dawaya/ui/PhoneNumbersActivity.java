package com.example.dawaya.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dawaya.R;
import com.example.dawaya.adapters.PhoneNumbersAdapter;
import com.example.dawaya.interfaces.PhoneNumbersInterface;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.viewmodels.EditPhoneNumbersViewModel;
import com.example.dawaya.viewmodels.PhoneNumbersViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class PhoneNumbersActivity extends AppCompatActivity implements PhoneNumbersInterface {

    PhoneNumbersViewModel viewModel;
    EditPhoneNumbersViewModel editPhoneNumbersViewModel;
    RecyclerView phoneNumbersRecyclerView;
    PhoneNumbersAdapter adapter;

    ExtendedFloatingActionButton addPhoneNumber;

    ArrayList<String> phoneNumbers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_numbers);

        //Getting phone numbers from database
        viewModel = new ViewModelProvider(this).get(PhoneNumbersViewModel.class);
        editPhoneNumbersViewModel = new ViewModelProvider(this).get(EditPhoneNumbersViewModel.class);
        viewModel.getPhoneNumbers(SharedPrefs.read(SharedPrefs.USER_ID, ""));
        observePhoneNumbersLiveData();

        addPhoneNumber = findViewById(R.id.add_phone_number_button);
        addPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPhoneNumberDialogFragment dialogFragment = new AddPhoneNumberDialogFragment();

                dialogFragment.show(getSupportFragmentManager(), "AddPhoneNumberDialogFragment");
            }
        });

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
        adapter = new PhoneNumbersAdapter(phoneNumbers, this);
        phoneNumbersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        phoneNumbersRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onEditPhoneNumberClicked(String phoneNumber) {

    }

    @Override
    public void onDeletePhoneNumberClicked(String phoneNumber) {

    }

    private void showEditPhoneNumberDialog(String oldPhoneNumber) {
        final Dialog editPhoneNumberDialog = new Dialog(this);
        editPhoneNumberDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editPhoneNumberDialog.setContentView(R.layout.dialog_edit_phone_number);

        editPhoneNumberDialog.show();
        editPhoneNumberDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 580);
        editPhoneNumberDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editPhoneNumberDialog.getWindow().getAttributes().windowAnimations = R.style.BottomSheetAnimation;
        editPhoneNumberDialog.getWindow().setGravity(Gravity.BOTTOM);

        //Databinding
        editPhoneNumbersViewModel.setPhoneNumber(oldPhoneNumber);

        editPhoneNumberDialog.findViewById(R.id.edit_phone_number_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPhoneNumber = ((EditText)editPhoneNumberDialog.findViewById(R.id.edit_phone_number_field)).getText().toString();
                editPhoneNumbersViewModel.editPhoneNumber(oldPhoneNumber, newPhoneNumber);
                observeUpdateStatus();
            }
        });

        editPhoneNumberDialog.findViewById(R.id.edit_phone_number_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPhoneNumberDialog.dismiss();
            }
        });
    }
    private void observeUpdateStatus(){
        editPhoneNumbersViewModel.getUpdatePhoneNumberStatus().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1){
                    Toast.makeText(PhoneNumbersActivity.this, "Phone Number Successfully Edited", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PhoneNumbersActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}