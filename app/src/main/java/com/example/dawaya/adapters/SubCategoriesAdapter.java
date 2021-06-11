

/*
package com.example.dawaya.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dawaya.R;
import com.example.dawaya.interfaces.HomeItemClickInterface;
import com.example.dawaya.models.CartAddedModel;

import com.example.dawaya.models.CategoryModel;
import com.example.dawaya.models.ProductModel;

import java.util.ArrayList;
import java.util.List;





public class ChosenCategoryAdapter extends RecyclerView.Adapter<ChosenCategoryAdapter.RecyclerViewViewHolder> {

    ArrayList<CategoryModel> categories;
    HomeItemClickInterface anInterface;
    Context context;

    public ChosenCategoryAdapter(ArrayList<CategoryModel> categories, Context context, HomeItemClickInterface anInterface) {
        this.categories = categories;
        this.context = context;
        this.anInterface = anInterface;
    }



    @NonNull
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_category_list_item, parent, false);
        return new RecyclerViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolder holder, int position) {
        holder.mainCategoryLabel.setText(categories.get(position).getCatName());
        holder.mainCategoryImage.setImageResource(categories.get(position).getImageResourceId());
    }



    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        TextView mainCategoryLabel;
        ImageView mainCategoryImage;
        public RecyclerViewViewHolder(@NonNull View itemView) {

            super(itemView);
            mainCategoryImage = itemView.findViewById(R.id.main_category_image);
            mainCategoryLabel = itemView.findViewById(R.id.main_category_label);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    anInterface.onItemClicked(categories.get(getAdapterPosition()).getCatName());
                }
            });


        }
    }
}

*/
package com.example.dawaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dawaya.R;
import com.example.dawaya.interfaces.HomeItemClickInterface;
import com.example.dawaya.interfaces.ItemClickInterface;
import com.example.dawaya.interfaces.SubCategpryClickInterface;
import com.example.dawaya.models.CategoryModel;

import java.util.ArrayList;

public class SubCategoriesAdapter extends BaseAdapter {
    ArrayList<CategoryModel> subCategories;
    SubCategpryClickInterface itemClickInterface;
    String mainCategory;

    public SubCategoriesAdapter(ArrayList<CategoryModel> subCategories, String mainCategory, SubCategpryClickInterface itemClickInterface) {
        this.subCategories = subCategories;
        this.itemClickInterface = itemClickInterface;
        this.mainCategory = mainCategory;
    }

    @Override
    public int getCount() {
        return subCategories.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categories_grid_item, viewGroup,false);

            TextView catName = (TextView) view.findViewById(R.id.cat_name);
            ImageView catImage = (ImageView) view.findViewById(R.id.cat_image);

            catName.setText(subCategories.get(i).getCatName());
            catImage.setImageResource(subCategories.get(i).getImageResourceId());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickInterface.onItemClicked(mainCategory, subCategories.get(i).getCatName());
                }
            });
        }


        return view;
    }
}
