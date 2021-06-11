package com.example.dawaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dawaya.R;
import com.example.dawaya.models.PrescriptionModel;
import com.example.dawaya.models.ProductModel;

import java.util.ArrayList;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.PrescriptionViewHolder> {

    ArrayList<PrescriptionModel> prescriptions;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public PrescriptionAdapter(ArrayList<PrescriptionModel> prescriptions) {
        this.prescriptions = prescriptions;
    }

    @NonNull
    @Override
    public PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_parent_recycler_view_item,parent,false);
        return new PrescriptionViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return prescriptions.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext()).load(prescriptions.get(position).getPrescriptionPath())
                .into(((ImageView)holder.itemView.findViewById(R.id.prescription_placeholder)));

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.prescriptionProductsRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL,false);

        //Making the sub recycler view fit all the products pf the first prescription
        layoutManager.setInitialPrefetchItemCount(prescriptions.get(position).getPrescriptionProducts().size());


        ProductsAdapter productsAdapter = new ProductsAdapter((ArrayList<ProductModel>) prescriptions.get(position).getPrescriptionProducts(), true);

        holder.prescriptionProductsRecyclerView.setLayoutManager(layoutManager);
        holder.prescriptionProductsRecyclerView.setAdapter(productsAdapter);
        holder.prescriptionProductsRecyclerView.setRecycledViewPool(viewPool);
    }

    class PrescriptionViewHolder extends RecyclerView.ViewHolder{
        ImageView prescriptionImage;
        RecyclerView prescriptionProductsRecyclerView;

        public PrescriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            prescriptionImage = itemView.findViewById(R.id.prescription_placeholder);
            prescriptionProductsRecyclerView = itemView.findViewById(R.id.prescription_products_recyclerview);
        }
    }
}
