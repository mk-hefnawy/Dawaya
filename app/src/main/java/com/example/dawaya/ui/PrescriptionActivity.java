package com.example.dawaya.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dawaya.R;
import com.example.dawaya.adapters.PrescriptionAdapter;
import com.example.dawaya.interfaces.PrescriptionInterface;
import com.example.dawaya.models.PrescriptionModel;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.utils.Utils;
import com.example.dawaya.viewmodels.CartViewModel;
import com.example.dawaya.viewmodels.PrescriptionViewModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class PrescriptionActivity extends AppCompatActivity implements PrescriptionInterface {


    ImageView upload;

    PrescriptionViewModel viewModel;
    CartViewModel cartViewModel;

    final int CODE_GALLERY_REQUEST = 999;
    final int CODE_CAMERA_REQUEST = 888;

    Uri photoURI;
    File photoFile;

    Uri galleryPhotoUri;
    File galleryPhotoFile;

    String currentPhotoPath;
    PrescriptionAdapter adapter;
    ArrayList<PrescriptionModel> prescriptions;
    ArrayList<ProductModel> prescriptionProducts;
    RecyclerView prescriptionsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        viewModel = new ViewModelProvider(this).get(PrescriptionViewModel.class);
        cartViewModel = CartViewModel.getInstance();
        prescriptions = new ArrayList<>();
        prescriptionProducts = new ArrayList<>();
        prescriptionsRecyclerView = findViewById(R.id.prescriptions_recycler_view);
        adapter = new PrescriptionAdapter(prescriptions, this);

        prescriptionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        prescriptionsRecyclerView.setAdapter(adapter);



        // Getting all prescriptions
        viewModel.getAllPrescriptions();
        viewModel.getPrescriptionsLiveData().observe(this, new Observer<ArrayList<PrescriptionModel>>() {
            @Override
            public void onChanged(ArrayList<PrescriptionModel> prescriptionModels) {
                //Show these prescriptions in the recycler view
                prescriptions.clear();
                prescriptions.addAll(prescriptionModels);

                adapter.notifyDataSetChanged();
            }
        });



        upload = findViewById(R.id.choose_an_image);
        upload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                // opens dialog to choose either camera or gallery
                Dialog cameraOrGallery = Utils.getBasicDialog(PrescriptionActivity.this, R.layout.dialog_camera_or_gallery);
                cameraOrGallery.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                cameraOrGallery.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(PrescriptionActivity.this, R.drawable.rounded_corners));
                cameraOrGallery.getWindow().setGravity(Gravity.CENTER);
                cameraOrGallery.show();

                cameraOrGallery.findViewById(R.id.prescription_use_camera).setOnClickListener(view1 -> {
                    // ask for permission
                    letUserUseTheCamera();
                  //  getCameraAccessPermission();
                    cameraOrGallery.dismiss();

                    //upload the image
                    //return to activity
                });
                cameraOrGallery.findViewById(R.id.prescription_use_gallery).setOnClickListener(view1 -> {
                    letUserChooseImage();
                    cameraOrGallery.dismiss();
                });
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void dispatchTakePictureIntent(String type) {
        Intent takePictureIntent;
        if (type.equals("camera")) {
             takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go

                try {
                    photoFile = createImageFile();
                    Log.v("photoFile", photoFile.toString());
                } catch (IOException ex) {
                    // Error occurred while creating the File

                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    photoURI = FileProvider.getUriForFile(this,
                            "com.example.dawaya.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, CODE_CAMERA_REQUEST);
                }
            }
        }
        else if (type.equals("gallery")){
            takePictureIntent = new Intent(Intent.ACTION_PICK);
            takePictureIntent.setType("image/*");
            try { galleryPhotoFile = createImageFile();
            Log.v("galleryPhotoFile", galleryPhotoFile.getPath());}
            catch (IOException e) { e.printStackTrace();}

            if (galleryPhotoFile != null){
                Log.v("galleryPhotoFile", "NotNull");
                galleryPhotoUri = FileProvider.getUriForFile(this, "com.example.dawaya.fileprovider", galleryPhotoFile);
                Log.v("galleryPhotoUri", galleryPhotoUri.getPath());
            }
                //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, galleryPhotoUri);
                startActivityForResult(takePictureIntent, CODE_GALLERY_REQUEST);
            }
        }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        System.out.println("StorageDir " + storageDir);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void letUserChooseImage() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CODE_GALLERY_REQUEST);
    }
    private void letUserUseTheCamera() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CODE_GALLERY_REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

               dispatchTakePictureIntent("gallery");

            }
            else Toast.makeText(this, "No Permission", Toast.LENGTH_LONG).show();

            return;
        }

        //CAMERA
        if (requestCode == CODE_CAMERA_REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                dispatchTakePictureIntent("camera");

            }else Toast.makeText(this, "No Permission", Toast.LENGTH_LONG).show();
            return;
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null){

            // getting the uri from the gallery app
            Uri uri = data.getData();
            showThePrescriptionFromGallery(uri);

        }
       else if (requestCode == CODE_CAMERA_REQUEST ){
              if (resultCode == RESULT_OK) {
               System.out.println("RESULT OK");
               //Why didn't i use the data intent ? because camera returns a non-null intent only in case of passing back a thumbnail
                  showThePrescription();
           }
              else if (resultCode == RESULT_CANCELED){
                  System.out.println("RESULT CANCELLED");}
           }
       else {
            System.out.println("ELSE");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showThePrescription() {
        Bitmap imageBitmap;
        try {
            imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);

            ArrayList<PrescriptionModel> newPrescriptions = new ArrayList<>();
            newPrescriptions.add(new PrescriptionModel(imageBitmap));
            int insertIndex = prescriptions.size();
            prescriptions.addAll(insertIndex, newPrescriptions);

            adapter.notifyItemRangeInserted(insertIndex, 1);

            System.out.println(photoURI);
            //InputStream inputStream = getContentResolver().openInputStream(photoURI);


            sendThePrescriptionToTheServer(photoFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showThePrescriptionFromGallery(Uri uri){
        Bitmap imageBitmap;
        try {
            imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            Log.v("Bitmap", imageBitmap.toString());

            ArrayList<PrescriptionModel> newPrescriptions = new ArrayList<>();
            newPrescriptions.add(new PrescriptionModel(imageBitmap));
            int insertIndex = prescriptions.size();
            prescriptions.addAll(insertIndex, newPrescriptions);
            //adapter = new PrescriptionAdapter(prescriptions);
            adapter.notifyItemRangeInserted(insertIndex, 1);


            sendThePrescriptionToTheServer(galleryPhotoFile);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendThePrescriptionToTheServer(File imageFile) {
        System.out.println("ProductsAreAboutToArrive");
        viewModel.uploadPrescription(imageFile);
        viewModel.getPrescriptionProducts().observe(this, new Observer<ArrayList<ProductModel>>() {
            @Override
            public void onChanged(ArrayList<ProductModel> products) {
                System.out.println("ProductsArrived");
                insertProductsIntoTheAdapter(products);
            }
        });
    }

    private void insertProductsIntoTheAdapter(ArrayList<ProductModel> products) {
        prescriptions.get(prescriptions.size()-1).setProducts(products);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onAddToCartClicked(int position) {
        // get selected products
        ArrayList<ProductModel> selectedProducts = new ArrayList<>();
        PrescriptionModel prescription = prescriptions.get(position);

        //loop over the products of current prescription to get selected products
        for (int i = 0; i<prescription.getProducts().size(); i++){
            if (prescription.getProducts().get(i).getCheckedInPrescriptionProducts()){
                selectedProducts.add(prescription.getProducts().get(i));
            }
        }

            cartViewModel.addToCart(selectedProducts, "append");
            System.out.println(selectedProducts.get(0).getName());


    }
}