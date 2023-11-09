package com.example.tr1_takeaway.ui.shopcart;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
//import com.example.tr1_takeaway.socketProductes.SocketsConnexion;
import com.example.tr1_takeaway.ui.shop.Adapter;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;

public class ShopcartAdapter extends RecyclerView.Adapter<ShopcartAdapter.ViewHolder> {
    Button Comprar;
    //SocketsConnexion SC = new SocketsConnexion();
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

        public ImageView getProductImage() {
            return ProductImage;
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
        holder.getProductName().setText(String.valueOf(currentItem.getNom()));
        holder.getProductPrice().setText(String.valueOf(currentItem.getPreu()));
        new DownloadImageFromInternet(holder.getProductImage()).execute(currentItem.getImageUrl().toString());
        Log.d("HOLA", currentItem.getImageUrl());
        holder.getProductQuantity().setText("1");
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage=BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

}