package com.example.dawaya.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.dawaya.R;

import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dawaya.adapters.SubCategoriesAdapter;
//import com.example.dawaya.adapters.SubProductsAdapter;
import com.example.dawaya.interfaces.HomeItemClickInterface;
import com.example.dawaya.interfaces.SubCategpryClickInterface;
import com.example.dawaya.models.CategoryModel;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.viewmodels.CategoryProductsViewModel;

import java.util.ArrayList;

public class SubCategoriesActivity extends AppCompatActivity implements SubCategpryClickInterface {


    ArrayList<String> categoriesNames = new ArrayList<>();
    ArrayList<CategoryModel> categories = new ArrayList<>();
    ArrayList<ProductModel> products = new ArrayList<>();


    SubCategoriesAdapter adapter;
    ImageView cartIcon;

    Intent intent;
    String categoryName;
    GridView gridView;

    CategoryProductsFragment cpFragment = new CategoryProductsFragment();
    CategoryProductsViewModel viewModel;

    Bundle categoryProductsBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categories);

        viewModel = new ViewModelProvider(this).get(CategoryProductsViewModel.class);

        //Cart Icon
        cartIcon = findViewById(R.id.action_cart);
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SubCategoriesActivity.this, CartActivity.class));
            }
        });

        intent = getIntent();
        categoryName = intent.getStringExtra("categoryName");
        categoriesNames = intent.getStringArrayListExtra("categoriesNames");

        //Setting the label
        TextView subCategoryLabel = findViewById(R.id.sub_category_label);
        subCategoryLabel.setText(categoryName);

        //ArrayList Static Items


        //Notice the number of sub categories is not known
        for (int i=0; i<categoriesNames.size(); i++) {
            categories.add(new CategoryModel(categoriesNames.get(i), R.mipmap.drug_img));

        /*categories.add(new CategoryModel(categoriesNames.get(2), R.mipmap.drug_img));
        categories.add(new CategoryModel(categoriesNames.get(3), R.mipmap.man));*/
        }
        //The Grid View
        gridView = findViewById(R.id.sub_categories_gird_view);
        adapter = new SubCategoriesAdapter(categories,categoryName,this);
        gridView.setAdapter(adapter);


    }

    @Override
    public void onItemClicked(String mainCategory, String subCategory) {

        categoryProductsBundle = new Bundle();
        categoryProductsBundle.putString("mainCategory", mainCategory);
        categoryProductsBundle.putString("subCategory", subCategory);
        cpFragment.setArguments(categoryProductsBundle);

        //sending the request
        viewModel.getCategoryProducts(mainCategory, subCategory);
        //observing the live data
        observeViewModelLiveData();

    }

    private void observeViewModelLiveData() {
        viewModel.getProducts().observe(this, new Observer<ArrayList<ProductModel>>() {
            @Override
            public void onChanged(ArrayList<ProductModel> productModels) {
                cpFragment.setProducts(productModels);
                Log.v("From OnChanged", productModels.get(1).getName());
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.category_products_fragment_placeholder, cpFragment);
                fragmentTransaction.addToBackStack("fragmentTransaction");

                fragmentTransaction.commit();
            }
        });
    }

}