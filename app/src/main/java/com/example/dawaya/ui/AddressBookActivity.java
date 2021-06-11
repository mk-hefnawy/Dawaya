package com.example.dawaya.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawaya.R;
import com.example.dawaya.adapters.AddressBookAdapter;
import com.example.dawaya.interfaces.AddressBookInterface;
import com.example.dawaya.models.AddressModel;
import com.example.dawaya.utils.App;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.viewmodels.AddressViewModel;
import com.example.dawaya.viewmodels.AddressBookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AddressBookActivity extends AppCompatActivity implements AddressBookInterface{

    Context context = App.getAppContext();
    //ViewModel
    AddressBookViewModel viewModel;
    AddressViewModel addressViewModel;
    AddAddressFragment addAddressFragment = new AddAddressFragment();

    ArrayList<AddressModel> addresses = new ArrayList<>();
    //ListView addressesListView;
    RecyclerView addressesRecyclerView;

    //For Tool bar
    Toolbar toolbar;
    //Toolbar toolbar = findViewById(R.id.tool_bar);  Finding a view before onCreate causes a problem in instantiating the activity
    ImageView backIcon, cartIcon;
    TextView toolBarTitle;
    FloatingActionButton addAddressFab;

    String userId;
    //AddressBookInterface addressBookInterface;

    TextInputLayout countyEdit;
    TextInputLayout streetEdit ;
    TextInputLayout buildingNoEdit ;
    TextInputLayout floorNoEdit;
    TextInputLayout apartmentNoEdit;

    TextView editAddressOk, editAddressCancel, deleteAddressYes, deleteAddressNo;

    @RequiresApi(api = Build.VERSION_CODES.M) //for getColor
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        viewModel = new ViewModelProvider(this).get(AddressBookViewModel.class);

        userId = SharedPrefs.read(SharedPrefs.USER_ID, "");
        viewModel.sendAddressesRequest(userId);


        /*addressBookInterface = new AddressBookInterface() {
            @Override
            public void onEditAddressClicked(int pos) {
                //Todo
                // Pop up dialog to edit the address
                Toast.makeText(context, "Edit Pressed", Toast.LENGTH_SHORT).show();
                showEditAddressDialog(pos);

                //the data in the dialog will come from the address clicked
                //the new input data will be send to the server to update it
            }
        };


*/

        viewModel.getAddressesLiveData().observe(this, new Observer<ArrayList<AddressModel>>() {
            @Override
            public void onChanged(ArrayList<AddressModel> addressModels) {
                addresses = viewModel.getAddressesLiveData().getValue();
                AddressBookAdapter adapter = new AddressBookAdapter(addresses, AddressBookActivity.this);

                //Address Book RecyclerView
                addressesRecyclerView = findViewById(R.id.addresses_recycler_view);

                addressesRecyclerView.setHasFixedSize(true);
                addressesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext())); //Notice App context
                addressesRecyclerView.setAdapter(adapter);
            }
        });

        //Section
        // Add Address Handling
        addAddressFab = findViewById(R.id.add_address_fab);
        handleAddingAddress();


        //Section
        // Tool Bar customizing

       /* toolbar = findViewById(R.id.tool_bar);
        *//*setSupportActionBar(toolbar);
        //toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);*//*

        toolBarTitle = findViewById(R.id.tool_bar_title);
        toolBarTitle.setText(R.string.my_addresses);


        backIcon.setImageResource(R.drawable.baseline_arrow_back_ios_24);*/
        backIcon = findViewById(R.id.back_from_my_addresses);
        handleUpAction();

       /* cartIcon = findViewById(R.id.action_cart);
        cartIcon.setVisibility(View.GONE);*/


    }



    private void onCancelClicked(Dialog editAddressDialog) {
        editAddressDialog.dismiss();
    }

    private void onOkClicked(String oldAddress) {
        addressViewModel = new AddressViewModel();
        addressViewModel.editAddress(SharedPrefs.read(SharedPrefs.USER_ID, ""), oldAddress);
        observeEditStatus();

    }

    private void observeEditStatus() {
        addressViewModel.editStatusLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1){
                    Toast.makeText(AddressBookActivity.this, "Address Edited Successfully", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(AddressBookActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String populateEditLayout(int pos, Dialog editAddressDialog) {

        String oldAddress = getOldAddress(pos);

        /*LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.edit_address_dialog,null);*/

         countyEdit = editAddressDialog.findViewById(R.id.edit_county_container);
         streetEdit = editAddressDialog.findViewById(R.id.edit_street_container);
         buildingNoEdit = editAddressDialog.findViewById(R.id.edit_building_no_container);
         floorNoEdit = editAddressDialog.findViewById(R.id.edit_floor_no_container);
         apartmentNoEdit = editAddressDialog.findViewById(R.id.edit_apartment_no_container);


         String [] splittedAddress = new String[5];
         splittedAddress = oldAddress.split(",");

        countyEdit.getEditText().setText(splittedAddress[0]);
        streetEdit.getEditText().setText(splittedAddress[1]);
        buildingNoEdit.getEditText().setText(splittedAddress[2]);
        floorNoEdit.getEditText().setText(splittedAddress[3]);
        apartmentNoEdit.getEditText().setText(splittedAddress[4]);

        return oldAddress;
    }

    private void handleAddingAddress() {
        addAddressFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddAddressFragment();
            }
        });
    }

    private void startAddAddressFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
         //addAddressFab.setVisibility(View.GONE);//Notice the fab does not disappear idk the reason, so i have to set the visibility to gone
        fragmentTransaction.replace(R.id.add_address_fragment_placeholder, addAddressFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void handleUpAction() {
            backIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (addAddressFragment.isVisible()){
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.popBackStack();}
                    else AddressBookActivity.super.onBackPressed();

                }
            });
    }

    private void startHomeActivity() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    @Override
    public void onEditAddressClicked(int pos) {
        showEditAddressDialog(pos);

    }

    @Override
    public void onDeleteAddressClicked(int pos) {
        showDeleteAddressDialog(pos);
    }

    private void showEditAddressDialog(int pos) {

        final Dialog editAddressDialog = new Dialog(this);
        editAddressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editAddressDialog.setContentView(R.layout.edit_address_dialog);

        String oldAddress = populateEditLayout(pos, editAddressDialog);

        editAddressDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        editAddressDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.rounded_corners));
        editAddressDialog.getWindow().setGravity(Gravity.CENTER);

        editAddressDialog.show();

        editAddressOk = editAddressDialog.findViewById(R.id.edit_address_ok);
        editAddressOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOkClicked(oldAddress);
            }
        });
        editAddressCancel = editAddressDialog.findViewById(R.id.edit_address_cancel);
        editAddressCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancelClicked(editAddressDialog);
            }
        });

    }
    private void showDeleteAddressDialog(int pos) {
        final Dialog deleteAddressDialog = new Dialog(this);
        deleteAddressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        deleteAddressDialog.setContentView(R.layout.delete_address_dialog);

        String oldAddress = getOldAddress(pos);

        deleteAddressYes = deleteAddressDialog.findViewById(R.id.delete_address_yes);
        deleteAddressYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onYesClicked(oldAddress);
            }
        });
        deleteAddressNo = deleteAddressDialog.findViewById(R.id.delete_address_no);
        deleteAddressNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNoClicked(deleteAddressDialog);
            }
        });
    }

    private void onNoClicked(Dialog deleteAddressDialog) {
        deleteAddressDialog.dismiss();
    }

    private void onYesClicked(String oldAddress) {
        addressViewModel = new AddressViewModel();
        addressViewModel.deleteAddress(SharedPrefs.read(SharedPrefs.USER_ID, ""), oldAddress);
        observeDeleteStatus();
    }

    private void observeDeleteStatus() {
        addressViewModel.deleteStatusLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1){
                    Toast.makeText(AddressBookActivity.this, "Address Deleted Successfully", Toast.LENGTH_SHORT).show();

                }
                else Toast.makeText(AddressBookActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getOldAddress(int pos){
        String county = addresses.get(pos).getCounty();
        Log.v("--", county);
        String street = addresses.get(pos).getStreet();
        String buildingNo = addresses.get(pos).getBuildingNo();
        String floorNo = addresses.get(pos).getFloorNo();
        String apartmentNo = addresses.get(pos).getApartmentNo();

        return county + ',' + street + ',' + buildingNo + ',' + floorNo + ',' + apartmentNo;
    }
}