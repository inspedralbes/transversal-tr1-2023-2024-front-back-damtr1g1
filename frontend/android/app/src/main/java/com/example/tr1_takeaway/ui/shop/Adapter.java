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

        public TextView getProductID() {
            return ProductID;
        }

        public TextView getProductName() {
            return ProductName;
        }

        public TextView getProductDefinition() {
            return ProductDefinition;
        }

        public TextView getProductPrice() {
            return ProductPrice;
        }

        public TextView getProductCategory() {
            return ProductCategory;
        }

        public TextView getProductQuantity() {
            return ProductQuantity;
        }
    }

    public Adapter(List<ProductDataModel> data) {
        this.data = data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDataModel currentItem = data.get(position);
        holder.getProductID().setText("ID producte");
        holder.getProductName().setText("nom producte");
        holder.getProductDefinition().setText("descripcio producte");
        holder.getProductPrice().setText("preu producte");
        holder.getProductCategory().setText("categoria producte");
        holder.getProductQuantity().setText("quantitat producte");
        //Picasso.get().load(currentItem.getImageUrl()).into(holder.ProductImage); // L'imatge la pasem per picasso
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}