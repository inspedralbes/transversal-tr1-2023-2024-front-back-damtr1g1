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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        // Encuentra el botón de compra en la vista inflada y configura un onClickListener
        Button comprar = view.findViewById(R.id.addProductToCart);
        Log.d("TAG", "S'ha clicat");
        comprar.setOnClickListener(v -> {
            // Acciones a realizar al hacer clic en el botón de compra
            SC.GuardaProdCarrito(); // Asegúrate de que SC esté inicializado y tenga la lógica adecuada
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopcartProductDataModel currentItem = data.get(position);
        holder.getProductName().setText(currentItem.getNom());
        holder.getProductPrice().setText(String.valueOf(currentItem.getPreu())); // Usar el precio real del producto
        holder.getProductQuantity().setText(String.valueOf(currentItem.getQuantitat())); // Usar la cantidad real del producto
        //Picasso.get().load(currentItem.getImageUrl()).into(holder.ProductImage); // L'imatge la pasem per picasso
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

}