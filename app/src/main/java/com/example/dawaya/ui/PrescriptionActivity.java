package com.example.dawaya.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.dawaya.R;
import com.example.dawaya.adapters.PrescriptionAdapter;
import com.example.dawaya.models.PrescriptionModel;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.viewmodels.PrescriptionViewModel;

import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PrescriptionActivity extends AppCompatActivity {

    ImageView prescriptionImage;
    ImageView upload;

    Bitmap bitmap;

    PrescriptionViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        viewModel = new ViewModelProvider(this).get(PrescriptionViewModel.class);


        ArrayList<PrescriptionModel> prescriptions = new ArrayList<>();

        ArrayList<ProductModel> prescriptionProducts1 = new ArrayList<>();
        ArrayList<ProductModel> prescriptionProducts2 = new ArrayList<>();

        ProductModel productModel1 = new ProductModel("code1", " name1",  10.0, 5);
        ProductModel productModel2 = new ProductModel("code2", " name2",  10.0, 6);
        ProductModel productModel3 = new ProductModel("code3", " name3",  10.0, 7);
        ProductModel productModel4 = new ProductModel("code4", " name4",  10.0, 8);

        ProductModel productModel11 = new ProductModel("code11", " name11",  10.0, 55);
        ProductModel productModel22 = new ProductModel("code22", " name22",  10.0, 66);
        ProductModel productModel33 = new ProductModel("code33", " name33",  10.0, 77);
        ProductModel productModel44 = new ProductModel("code44", " name44",  10.0, 88);

        prescriptionProducts1.add(productModel1);
        prescriptionProducts1.add(productModel2);
        prescriptionProducts1.add(productModel3);
        prescriptionProducts1.add(productModel4);

        prescriptionProducts2.add(productModel11);
        prescriptionProducts2.add(productModel22);
        prescriptionProducts2.add(productModel33);
        prescriptionProducts2.add(productModel44);

        PrescriptionModel prescriptionModel1 = new PrescriptionModel("https://i.picsum.photos/id/898/200/300.jpg?hmac=t4CBtj0-seR5dcy3U9f3RETPJo3tVYgUSvwcMV-cL-o",prescriptionProducts1);
        PrescriptionModel prescriptionModel2 = new PrescriptionModel("https://i.picsum.photos/id/428/200/300.jpg?hmac=yZnpqAvuXjLW6NjhE0OFa2GwK6XcNLPBIrI3yr4yFsk",prescriptionProducts2);

        prescriptions.add(prescriptionModel1);
        prescriptions.add(prescriptionModel2);

        RecyclerView recyclerView = findViewById(R.id.prescriptions_recycler_view);
        PrescriptionAdapter adapter = new PrescriptionAdapter(prescriptions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




/*


        bitmap = null;
        String filename = getIntent().getStringExtra("prescription");
        try {
            FileInputStream is = this.openFileInput(filename);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        /*prescriptionImage = findViewById(R.id.prescription_placeholder);
        Glide.with(this).load("https://www.tutorialspoint.com/images/tp-logo-diamond.png").into(prescriptionImage);*/

        //prescriptionImage.setImageBitmap(bitmap);

        /*View view = LayoutInflater.from(this).inflate(R.layout.prescription_place_holder,null);
        RelativeLayout relativeLayout = findViewById(R.id.prescription_products_container);
        relativeLayout.addView(view);
        Glide.with(this).load("https://www.tutorialspoint.com/images/tp-logo-diamond.png").into((ImageView) view);*/

        upload = findViewById(R.id.choose_an_image);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewModel.uploadPrescription(bitmap);
            }
        });
    }
}