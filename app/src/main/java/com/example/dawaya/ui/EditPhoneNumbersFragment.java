package com.example.dawaya.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dawaya.R;
import com.example.dawaya.adapters.BottomSheetAdapter;
import com.example.dawaya.adapters.PhoneNumbersAdapter;
import com.example.dawaya.interfaces.PhoneNumbersInterface;
import com.example.dawaya.viewmodels.EditPhoneNumbersViewModel;

import java.util.ArrayList;


public class EditPhoneNumbersFragment extends Fragment implements PhoneNumbersInterface {
    Bundle neededData;
    RecyclerView recyclerView;
    PhoneNumbersAdapter adapter;
    ArrayList<String> phoneNumbers = new ArrayList<>();
    EditPhoneNumbersViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_phone_numbers, container, false);

        viewModel = new ViewModelProvider(this).get(EditPhoneNumbersViewModel.class);

        neededData = getArguments();
        for (int i = 0; i<neededData.size(); i++ ){
            phoneNumbers.add(neededData.getString(String.valueOf(i)));
        }

        recyclerView = view.findViewById(R.id.edit_numbers_recycler_view);
        adapter = new PhoneNumbersAdapter(phoneNumbers, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        return view;
    }

    @Override
    public void onEditPhoneNumberClicked(String oldPhoneNumber) {
        showEditPhoneNumberDialog(oldPhoneNumber);
    }

    private void showEditPhoneNumberDialog(String oldPhoneNumber) {
        final Dialog editPhoneNumberDialog = new Dialog(getContext());
        editPhoneNumberDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editPhoneNumberDialog.setContentView(R.layout.dialog_edit_phone_number);

        editPhoneNumberDialog.show();
        editPhoneNumberDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 580);
        editPhoneNumberDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editPhoneNumberDialog.getWindow().getAttributes().windowAnimations = R.style.BottomSheetAnimation;
        editPhoneNumberDialog.getWindow().setGravity(Gravity.BOTTOM);

        //Databinding
        viewModel.setPhoneNumber(oldPhoneNumber);

        editPhoneNumberDialog.findViewById(R.id.edit_phone_number_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPhoneNumber = ((EditText)editPhoneNumberDialog.findViewById(R.id.edit_phone_number_field)).getText().toString();
                viewModel.editPhoneNumber(oldPhoneNumber, newPhoneNumber);
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
        viewModel.getUpdatePhoneNumberStatus().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1){
                    Toast.makeText(getActivity(), "Phone Number Successfully Edited", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onDeletePhoneNumberClicked(String phoneNumber) {
        viewModel.deletePhoneNumber(phoneNumber);
        observeDeleteStatus();
    }
    private void observeDeleteStatus(){
        viewModel.getDeletePhoneNumberStatus().observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1){
                    Toast.makeText(getActivity(), "Phone Number Successfully Deleted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}