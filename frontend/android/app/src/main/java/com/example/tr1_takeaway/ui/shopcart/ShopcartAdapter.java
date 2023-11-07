package com.example.tr1_takeaway.ui.shopcart;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr1_takeaway.R;
import com.example.tr1_takeaway.socketProductes.SocketsConnexion;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShopcartAdapter extends RecyclerView.Adapter<ShopcartAdapter.ViewHolder> {
    Button Comprar;
    SocketsConnexion SC = new SocketsConnexion();
    private final List<ShopcartProductDataModel> data;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ProductName, ProductPrice, ProductQuantity;
        public ImageView ProductImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ProductName = itemView.findViewById(R.id.shopcartProductName);
            ProductPrice = itemView.findViewById(R.id.shopcartProductPrice);
            ProductQuantity = itemView.findViewById(R.id.shopcartProductQuantity);
            ProductImage = itemView.findViewById(R.id.shopcartProductImage);
        }


        public TextView getProductName() {
            return ProductName;
        }

        public TextView getProductPrice() {
            return ProductPrice;
        }

        public TextView getProductQuantity() {
            return ProductQuantity;
        }
    }

    public ShopcartAdapter(List<ShopcartProductDataModel> data) {
        this.data = data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopcart_grid_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopcartProductDataModel currentItem = data.get(position);
        Log.d("TAG", String.valueOf(currentItem.getNom()));
        holder.getProductName().setText(String.valueOf(currentItem.getNom()));
        holder.getProductPrice().setText(String.valueOf(currentItem.getPreu()));
        holder.getProductQuantity().setText("1");
        Picasso.get().load(currentItem.getImageUrl()).into(holder.ProductImage);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

}