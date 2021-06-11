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
import com.example.dawaya.models.CategoryModel;

import java.util.ArrayList;

public class MainCategoriesAdapter extends BaseAdapter {
    ArrayList<CategoryModel> categories;
    HomeItemClickInterface itemClickInterface;

    public MainCategoriesAdapter(ArrayList<CategoryModel> categories, HomeItemClickInterface itemClickInterface) {
        this.categories = categories;
        this.itemClickInterface = itemClickInterface;
    }

    @Override
    public int getCount() {
        return categories.size();
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

            catName.setText(categories.get(i).getCatName());
            catImage.setImageResource(categories.get(i).getImageResourceId());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickInterface.onItemClicked(categories.get(i).getCatName());
                }
            });
        }


        return view;
    }
}
