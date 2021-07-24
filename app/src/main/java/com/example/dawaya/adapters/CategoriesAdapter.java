package com.example.dawaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dawaya.R;
import com.example.dawaya.interfaces.HomeItemClickInterface;
import com.example.dawaya.models.CategoryModel;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    ArrayList<CategoryModel> categories;
    HomeItemClickInterface itemClickInterface;


    public CategoriesAdapter(ArrayList<CategoryModel> categories, HomeItemClickInterface itemClickInterface) {
        this.categories = categories;
        this.itemClickInterface = itemClickInterface;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_grid_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.catName.setText(categories.get(position).getCatName());
        Glide.with(holder.itemView.getContext()).load(categories.get(position).getImageUrl())
                .into(holder.catImageView);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView catName;
        ImageView catImageView;
        LinearLayout categoryContainer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            catName = itemView.findViewById(R.id.cat_name);
            catImageView = itemView.findViewById(R.id.cat_image);
            categoryContainer = itemView.findViewById(R.id.category_container);

            categoryContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickInterface.onItemClicked(categories.get(getAdapterPosition()).getCatName());

                }
            });
        }
    }
}
