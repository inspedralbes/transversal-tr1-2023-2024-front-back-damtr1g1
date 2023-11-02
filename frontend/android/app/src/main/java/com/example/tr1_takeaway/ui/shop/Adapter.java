package com.example.tr1_takeaway.ui.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr1_takeaway.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final List<ProductDataModel> data;
    private Context context; // Agregado el contexto

    public Adapter(List<ProductDataModel> data) {
        this.data = data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_shop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDataModel currentItem = data.get(position);
        holder.ProductID.setText(currentItem.getId());
        holder.ProductName.setText(currentItem.getNom());
        holder.ProductDefinition.setText(currentItem.getDefinicio());
        holder.ProductPrice.setText(String.valueOf(currentItem.getPreu()));
        holder.ProductCategory.setText(String.valueOf(currentItem.getCategoria_id()));
        holder.ProductQuantity.setText(String.valueOf(currentItem.getQuantitat()));
        Picasso.get().load(currentItem.getImageUrl()).into(holder.ProductImage); // L'imatge la pasem per picasso
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ProductID, ProductName, ProductDefinition, ProductPrice, ProductCategory, ProductQuantity;
        public ImageView ProductImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ProductName = itemView.findViewById(R.id.productName);
            ProductDefinition = itemView.findViewById(R.id.productDescription);
            ProductPrice = itemView.findViewById(R.id.productPrice);
            ProductCategory = itemView.findViewById(R.id.productCategory);
            ProductQuantity = itemView.findViewById(R.id.productQuantity);
            ProductImage = itemView.findViewById(R.id.productImage);
        }
    }}