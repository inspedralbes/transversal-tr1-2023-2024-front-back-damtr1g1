package com.example.tr1_takeaway.ui.shop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr1_takeaway.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<ProductDataModel> data;

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
        ProductDataModel item = data.get(position);
        holder.productName.setText(item.toString());
        holder.productCategory.setText(item.toString());
        holder.productPrice.setText(item.toString());
        holder.productDescription.setText(item.toString());
        holder.productQuantity.setText(item.toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productCategory;
        TextView productPrice;
        TextView productDescription;
        TextView productQuantity;

        // Add other views for your grid item here

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productCategory = itemView.findViewById(R.id.productCategory);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDescription = itemView.findViewById(R.id.productDescription);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            // Initialize other views here
        }
    }
}

