package com.example.dawaya.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.dawaya.R;
import com.example.dawaya.databinding.FragmentEditProfileBinding;
import com.example.dawaya.models.SignUpModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.utils.Utils;
import com.example.dawaya.viewmodels.EditProfileViewModel;
import com.example.dawaya.viewmodels.SignUpViewModel;

import java.util.Date;


public class EditProfileFragment extends Fragment {

    FragmentEditProfileBinding fragmentBinding;
    Context context = App.getAppContext();

   Bundle userData;
   EditProfileViewModel viewModel;
   SignUpModel user = new SignUpModel();
   EditText firstNameET, lastNameET, emailET, phoneNumberET;
   RadioButton genderRadioButton;
   DatePicker datePicker;

   Button saveButton;


    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userData = getArguments();
        user.setFirstName(userData.getString("firstName"));
        user.setLastName(userData.getString("lastName"));
        user.setEmail(userData.getString("email"));
        user.setPhoneNumber(userData.getString("phoneNumber"));
        user.setGender(userData.getString("gender"));
        user.setDateOfBirth(userData.getString("dateOfBirth"));



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        fragmentBinding = FragmentEditProfileBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        fragmentBinding.setLifecycleOwner(this);
        fragmentBinding.setViewModel(viewModel);

       // onBackArrowPressed();

        //why not using data binding? .. because of the need of some processing before sending the data to the  view model
        viewModel.setUser(user);
        saveButton = fragmentBinding.editProfileSaveButton;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Date of birth
                Date dateOfBirth = Utils.getDateFromDatePicker(datePicker);
                String strDateOfBirth = Utils.convertDateToString(dateOfBirth);

                //Gender
                String gender;
                genderRadioButton = fragmentBinding.maleRadioButton;
                if (genderRadioButton.isChecked()){
                    //The user checked male
                    gender = "Male";
                }
                else gender = "Female";
                viewModel.updateUserData(gender,strDateOfBirth);

            }
        });


        //Notice it should be done using two way view binding but i still don't know how it works
        populateFragmentViewsWithUserData();
        observeViewModel();


        return fragmentBinding.getRoot();
    }

    private void populateFragmentViewsWithUserData() {

        //Gender
        if (user.getGender().equals("Male")) {
            genderRadioButton = fragmentBinding.maleRadioButton;
            genderRadioButton.setChecked(true);
        }
        else if (user.getGender().equals("Female")){
            genderRadioButton = fragmentBinding.femaleRadioButton;
            genderRadioButton.setChecked(true);
        }


        //Date of birth
        datePicker = fragmentBinding.datePicker;
        String[] dateComponents = new String[3];
        dateComponents = user.getDateOfBirth().split("-");
        //Log.v("----",dateComponents[2]);
        datePicker.updateDate(Integer.parseInt(dateComponents[2]), Integer.parseInt(dateComponents[1]), Integer.parseInt(dateComponents[0]));

    }

    private void observeViewModel(){
        viewModel.getUpdatedUser().observeForever(new Observer<SignUpModel>() {
            @Override
            public void onChanged(SignUpModel user) {
                SharedPrefs.write(SharedPrefs.USER_ID, user.getUserId());
                SharedPrefs.write(SharedPrefs.FIRST_NAME, user.getFirstName());
                SharedPrefs.write(SharedPrefs.LAST_NAME, user.getLastName());
                SharedPrefs.write(SharedPrefs.EMAIL, user.getEmail());
                SharedPrefs.write(SharedPrefs.PASSWORD, user.getPassword());
                SharedPrefs.write(SharedPrefs.PHONE_NUMBER, user.getPhoneNumber());
                SharedPrefs.write(SharedPrefs.GENDER, user.getGender());
                SharedPrefs.write(SharedPrefs.DATE_OF_BIRTH, user.getDateOfBirth());

                Toast.makeText(context, "Data saved successfully", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });
    }

}