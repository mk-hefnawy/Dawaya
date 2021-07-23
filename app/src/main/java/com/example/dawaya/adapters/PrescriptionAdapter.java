package com.example.dawaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.dawaya.R;
import com.example.dawaya.interfaces.PrescriptionInterface;
import com.example.dawaya.models.PrescriptionModel;
import com.example.dawaya.models.ProductModel;

import java.util.ArrayList;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.PrescriptionViewHolder> {

    ArrayList<PrescriptionModel> prescriptions;
    PrescriptionInterface prescriptionInterface;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();


    public PrescriptionAdapter(ArrayList<PrescriptionModel> prescriptions, PrescriptionInterface prescriptionInterface) {
        this.prescriptions = prescriptions;
        this.prescriptionInterface = prescriptionInterface;
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
        //If the situation is uploading the image from the device
        if (!prescriptions.get(position).getAreProductsAttached()){
            ((ImageView) holder.itemView.findViewById(R.id.prescription_placeholder)).setImageBitmap(prescriptions.get(position).getBitmap());

            holder.addPrescriptionProductsToCart.setVisibility(View.VISIBLE);
        }

        else {
            //If the situation is getting all the prescriptions
            //

            /*Glide.with(holder.itemView.getContext()).load(holder.itemView.getContext()).load(prescriptions.get(position).getUrl())
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .into(((ImageView) holder.itemView.findViewById(R.id.prescription_placeholder)));*/


        }
        /////
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.prescriptionProductsRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL,false);

        //Making the sub recycler view fit all the products of the first prescription
        if (prescriptions.get(position).getAreProductsAttached()) {
            layoutManager.setInitialPrefetchItemCount(prescriptions.get(position).getProducts().size());
        }else {
            layoutManager.setInitialPrefetchItemCount(0);
        }

        ProductsAdapter productsAdapter = new ProductsAdapter((ArrayList<ProductModel>) prescriptions.get(position).getProducts(),
                true, true);

        holder.prescriptionProductsRecyclerView.setLayoutManager(layoutManager);
        holder.prescriptionProductsRecyclerView.setAdapter(productsAdapter);
        holder.prescriptionProductsRecyclerView.setRecycledViewPool(viewPool);
    }

    class PrescriptionViewHolder extends RecyclerView.ViewHolder{
        ImageView prescriptionImage;
        RecyclerView prescriptionProductsRecyclerView;
        Button addPrescriptionProductsToCart;

        public PrescriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            prescriptionImage = itemView.findViewById(R.id.prescription_placeholder);
            prescriptionProductsRecyclerView = itemView.findViewById(R.id.prescription_products_recyclerview);
            addPrescriptionProductsToCart = itemView.findViewById(R.id.btn_add_prescription_products_to_cart);

            addPrescriptionProductsToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prescriptionInterface.onAddToCartClicked(getAdapterPosition());
                }
            });
        }
    }
}
