package com.example.dawaya.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.dawaya.R;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dawaya.adapters.CategoriesAdapter;
//import com.example.dawaya.adapters.SubProductsAdapter;
import com.example.dawaya.interfaces.HomeItemClickInterface;
import com.example.dawaya.models.CategoryModel;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.utils.StaticAppData;
import com.example.dawaya.viewmodels.CategoryProductsViewModel;

import java.util.ArrayList;

public class SubCategoriesActivity extends AppCompatActivity implements HomeItemClickInterface {



    ArrayList<CategoryModel> subCategories = new ArrayList<>();
    ArrayList<ProductModel> products = new ArrayList<>();

    CategoriesAdapter adapter;

    Intent intent;
    String categoryName;
    RecyclerView subCategoriesRecyclerView;

    CategoryProductsFragment cpFragment = new CategoryProductsFragment();
    CategoryProductsViewModel viewModel;

    Bundle categoryProductsBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categories);

        viewModel = new ViewModelProvider(this).get(CategoryProductsViewModel.class);

        intent = getIntent();
        categoryName = intent.getStringExtra("categoryName");
        subCategories = new ArrayList<>();
        //categoriesNames = intent.getStringArrayListExtra("categoriesNames");

        //Setting the label
        TextView subCategoryLabel = findViewById(R.id.sub_category_label);
        subCategoryLabel.setText(categoryName);

        //ArrayList Static Items

        switch (categoryName){

            case StaticAppData.RESPIRATORY_SYSTEM:

                subCategories.clear();
                subCategories.add(new CategoryModel("Cough&Cold", "url"));
                subCategories.add(new CategoryModel("BREATHING PROBLEMS", "url"));

                break;
            case StaticAppData.DIGESTIVE_SYSTEM:

                subCategories.clear();
                subCategories.add(new CategoryModel("DIABETES", "url"));
                subCategories.add(new CategoryModel("DIGESTION PROBLEMS", "url"));

                break;

            case StaticAppData.SKIN:

                subCategories.clear();
                subCategories.add(new CategoryModel("BURNS", "url"));
                subCategories.add(new CategoryModel("ALLERGY", "url"));

                break;
            case StaticAppData.EYE:

                subCategories.clear();
                subCategories.add(new CategoryModel("EYE INFECTIONS", "url"));
                subCategories.add(new CategoryModel("EYE ALLERGY", "url"));

                break;
            case StaticAppData.EAR:

                //view model get products

                break;
            case StaticAppData.BABY:

                subCategories.clear();
                subCategories.add(new CategoryModel("FOOD", "url"));
                subCategories.add(new CategoryModel("GROOMING", "url"));
                subCategories.add(new CategoryModel("DIAPERS", "url"));

                break;

            case StaticAppData.MOUTH_CARE:

                subCategories.clear();
                subCategories.add(new CategoryModel("MOUTH WASH", "url"));
                subCategories.add(new CategoryModel("TEETH", "url"));

                break;
            case StaticAppData.HAIR_CARE:

                subCategories.clear();
                subCategories.add(new CategoryModel("HAIR WASHING", "url"));
                subCategories.add(new CategoryModel("HAIR FALL", "url"));

                break;

            case StaticAppData.MEDICAL_SUPPLIES:

                subCategories.clear();
                subCategories.add(new CategoryModel("FIRST AID", "url"));
                subCategories.add(new CategoryModel("MEASURING TOOLS", "url"));

                break;
            case StaticAppData.NUTRITION_SUPPLEMENTS:

                subCategories.clear();
                subCategories.add(new CategoryModel("VITAMINS", "url"));
                subCategories.add(new CategoryModel("MINERALS", "url"));

                break;
            case StaticAppData.HOME_PRODUCTS:

                subCategories.clear();
                subCategories.add(new CategoryModel("INSECTS KILLERS", "url"));
                subCategories.add(new CategoryModel("AIR FRESHENERS", "url"));

                break;




        }

        subCategoriesRecyclerView = findViewById(R.id.sub_categories_gird_view);
        adapter = new CategoriesAdapter(subCategories, this);
        subCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        subCategoriesRecyclerView.setAdapter(adapter);


    }

    @Override
    public void onItemClicked(String subCategory) {
        categoryProductsBundle = new Bundle();
        categoryProductsBundle.putString("mainCategory", categoryName);
        categoryProductsBundle.putString("subCategory", subCategory);
        cpFragment.setArguments(categoryProductsBundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.category_products_fragment_placeholder, cpFragment);
        fragmentTransaction.addToBackStack("fragmentTransaction");

        fragmentTransaction.commit();
        //sending the request
        //viewModel.getCategoryProducts(categoryName, subCategory);
        //observing the live data


    }



}