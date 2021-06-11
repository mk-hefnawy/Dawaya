package com.example.dawaya.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.dawaya.R;
import com.example.dawaya.databinding.FragmentAddAddressBinding;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.viewmodels.AddressViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class AddAddressFragment extends Fragment {

    Context context = App.getAppContext();
    FragmentAddAddressBinding binding;
    AddressViewModel viewModel;

    TextInputEditText countyEt, streetET, buildingNoET, floorNoET, apartmentNoET;
    Button addAddressButton;
    String customerId, address;

    ArrayList<TextInputEditText> fields = new ArrayList<>();

    public AddAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAddAddressBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(AddressViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        customerId = SharedPrefs.read("USER_ID","");

        onSaveButtonClicked();

        return binding.getRoot();
    }

    private void onSaveButtonClicked() {
        addAddressButton = binding.addAddressButton;
        addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (assertNoInputErrors()) {
                    getStringAddress();
                    viewModel.addAddress(customerId);
                    observeStatus();

                }
            }
        });
    }

    private void getStringAddress() {
         /*address =  viewModel.getAddressLiveData().getValue().getCounty() + ',' +
                    viewModel.getAddressLiveData().getValue().getStreet() + ',' +
                    viewModel.getAddressLiveData().getValue().getBuildingNo() + ',' +
                    viewModel.getAddressLiveData().getValue().getFloorNo() + ',' +
                    viewModel.getAddressLiveData().getValue().getApartmentNo();*/

         address = binding.countyField.getText().toString()+ ',' +
                   binding.streetField.getText().toString()+ ',' +
                   binding.buildingNoField.getText().toString()+ ',' +
                   binding.floorNoField.getText().toString()+ ',' +
                   binding.apartmentNoField.getText().toString() ;
    }

    private void observeStatus() {
        viewModel.statusLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1){
                    Toast.makeText(getActivity(), "Address added successfully", Toast.LENGTH_SHORT).show();
                    if (SharedPrefs.read(SharedPrefs.ADDRESS, "").equals("ADDRESS")){
                        SharedPrefs.write(SharedPrefs.ADDRESS, address);
                    }

                }
            }
        });
    }

    private boolean assertNoInputErrors() {
        countyEt = binding.countyField;
        streetET = binding.streetField;
        buildingNoET = binding.buildingNoField;
        floorNoET = binding.floorNoField;
        apartmentNoET = binding.apartmentNoField;

        fields.add(countyEt);
        fields.add(streetET);
        fields.add(buildingNoET);
        fields.add(floorNoET);
        fields.add(apartmentNoET);

        ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.red));

        if (TextUtils.equals(countyEt.getText(), "")) {
            Toast.makeText(context,"Enter your county name", Toast.LENGTH_SHORT).show();
            countyEt.requestFocus();
            //countyEt.setBackgroundTintList(colorStateList);
            //resetOthers(countyEt);
            return false;
        }

        if (TextUtils.equals(streetET.getText(), "")) {
            Toast.makeText(context,"Enter your street name", Toast.LENGTH_SHORT).show();
            streetET.requestFocus();
            //streetET.setBackgroundTintList(colorStateList);
            //resetOthers(streetET);
            return false;
        }

        if (TextUtils.equals(buildingNoET.getText(), "")) {
            Toast.makeText(context,"Enter your building number", Toast.LENGTH_SHORT).show();
            buildingNoET.requestFocus();
            //buildingNoET.setBackgroundTintList(colorStateList);
            //resetOthers(buildingNoET);
            return false;
        }

        if (TextUtils.equals(floorNoET.getText(), "")) {
            Toast.makeText(context,"Enter your floor number", Toast.LENGTH_SHORT).show();
            floorNoET.requestFocus();
            //floorNoET.setBackgroundTintList(colorStateList);
            //resetOthers(floorNoET);
            return false;
        }

        if (TextUtils.equals(apartmentNoET.getText(), "")) {
            Toast.makeText(context,"Enter your apartment number", Toast.LENGTH_SHORT).show();
            apartmentNoET.requestFocus();
            //apartmentNoET.setBackgroundTintList(colorStateList);
            //resetOthers(apartmentNoET);
            return false;
        }
            return true;

    }

    private void resetOthers(TextInputEditText field) {
        for (int i =0 ; i<fields.size(); i++){
            if (fields.get(i).getId() == field.getId() ) continue;
            fields.get(i).setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

    }
}