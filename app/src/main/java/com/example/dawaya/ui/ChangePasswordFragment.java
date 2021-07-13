package com.example.dawaya.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dawaya.R;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.viewmodels.ChangePasswordViewModel;
import com.example.dawaya.viewmodels.EditProfileViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


public class ChangePasswordFragment extends Fragment {

    ChangePasswordViewModel viewModel;
    TextInputEditText oldPasswordET, newPasswordET, confirmNewPasswordET;
    Button changePassword;
    Boolean isValidData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        viewModel = new ViewModelProvider(this).get(ChangePasswordViewModel.class);
        // Getting Ui Data
        oldPasswordET = view.findViewById(R.id.old_password_field);
        newPasswordET = view.findViewById(R.id.new_password_field);
        confirmNewPasswordET = view.findViewById(R.id.confirm_new_password_field);

        changePassword = view.findViewById(R.id.change_password_button);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickedView) {
                isValidData = validateUiData();
                if (isValidData){
                    getUiData();
                    observeChangePassword(view);
                }
                else Snackbar.make(view, "Confirm Password Does Not Match New Password",Snackbar.LENGTH_SHORT).show();
            }
        });

        return view;
    }



    private Boolean validateUiData() {
        String oldPassword = oldPasswordET.getText().toString();
        String newPassword = newPasswordET.getText().toString();
        return oldPassword.equals(newPassword);
    }

    private void getUiData() {
        String oldPassword = oldPasswordET.getText().toString();
        String newPassword = newPasswordET.getText().toString();
        viewModel.changePassword(oldPassword, newPassword, SharedPrefs.read(SharedPrefs.USER_ID, ""));
    }
    private void observeChangePassword(View view) {
        viewModel.getChangePasswordStatus().observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1){
                    Snackbar.make(view, "Password Changed Successfully", Snackbar.LENGTH_SHORT);
                }
                else Snackbar.make(view, "Password Change Failed", Snackbar.LENGTH_SHORT);
            }
        });
    }
}