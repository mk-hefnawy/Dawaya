package com.example.dawaya.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.dawaya.R;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.viewmodels.PhoneNumbersViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class AddPhoneNumberDialogFragment extends DialogFragment {

    TextInputLayout addPhoneNumberContainer;
    TextView addPhoneNumberCancel, addPhoneNumberOk;

    PhoneNumbersViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_add_phone_number, container, false);

        viewModel = new ViewModelProvider(this).get(PhoneNumbersViewModel.class);

        addPhoneNumberContainer = view.findViewById(R.id.add_phone_number_container);
        addPhoneNumberCancel = view.findViewById(R.id.add_phone_number_cancel);
        addPhoneNumberOk = view.findViewById(R.id.add_phone_number_ok);

        // Click Listeners
        addPhoneNumberCancel.setOnClickListener(view1 -> {getDialog().dismiss();});
        addPhoneNumberOk.setOnClickListener(view1 -> {
            String phoneNumber = addPhoneNumberContainer.getEditText().getText().toString();
            if(!phoneNumber.equals("")){
                viewModel.addPhoneNumber(SharedPrefs.read(SharedPrefs.USER_ID, " "), phoneNumber);
                viewModel.getAddPhoneNumberStatus().observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer == 1) {
                            getDialog().dismiss();
                            Toast.makeText(getContext(), "Phone Number was Added Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else  Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else Toast.makeText(getActivity(), "Please Enter your Phone Number", Toast.LENGTH_SHORT).show();


        });
        return view;
    }

}
