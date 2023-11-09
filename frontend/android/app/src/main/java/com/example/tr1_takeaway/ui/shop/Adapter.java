package com.example.tr1_takeaway.ui.shop;

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

import com.example.tr1_takeaway.MainActivity;
import com.example.tr1_takeaway.R;
import com.example.tr1_takeaway.api.shopcartService.addProductToCart;
import com.example.tr1_takeaway.ui.shopcart.AddShoppingCartToNode;
import com.example.tr1_takeaway.ui.shopcart.ShoppingCart;
import com.example.tr1_takeaway.ui.shopcart.ShoppingCartProduct;

import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final List<ProductDataModel> data;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ProductID, ProductName, ProductPrice, ProductCategory, ProductQuantity;
        public ImageView ProductImage;
        public Button button;

        public ViewHolder(View itemView) {
            super(itemView);
            ProductName = itemView.findViewById(R.id.productName);
            ProductPrice = itemView.findViewById(R.id.productPrice);
            ProductQuantity = itemView.findViewById(R.id.productQuantity);
            ProductImage = itemView.findViewById(R.id.productImage);
            button = itemView.findViewById(R.id.addProductToCart);
        }

        public ImageView getProductImage() {
            return ProductImage;
        }

        public void setProductImage(ImageView productImage) {
            ProductImage = productImage;
        }

        public TextView getProductID() {
            return ProductID;
        }

        public TextView getProductName() {
            return ProductName;
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
        AddShoppingCartToNode AS = new AddShoppingCartToNode();
        ProductDataModel currentItem = data.get(position);
        holder.getProductName().setText(currentItem.getNom());
        holder.getProductPrice().setText(String.valueOf(currentItem.getPreu() + "€")); // Usar el precio real del producto
        holder.getProductQuantity().setText(String.valueOf(currentItem.getQuantitat() + " unitats")); // Usar la cantidad real del producto
        new DownloadImageFromInternet(holder.getProductImage()).execute(currentItem.getImageUrl().toString());
        holder.button.setOnClickListener(v -> {
            String productIDContent = holder.getProductID().getText().toString();
            int productIDIntContent = Integer.parseInt(productIDContent);
            ShoppingCart shoppingCart = new ShoppingCart();
            //String id = shoppingCart.getId();
            // Retrofit initialization
            MainActivity main = new MainActivity();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(main.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            addProductToCart service = retrofit.create(addProductToCart.class);
            //int id = Integer.parseInt(AS.cartId);
            //Log.d("id", String.valueOf(id));
            int quantity = 1;
            int cartId = 1;
            ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct(quantity, cartId, productIDIntContent);

            Call<Void> call = service.crearCarritoProducto(shoppingCartProduct);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Maneja la respuesta exitosa
                        Log.d("TAG", "Carrito Producto insertado con éxito.");
                    } else {
                        // Maneja la respuesta de error
                        Log.e("TAG", "Error al insertar Carrito Producto: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    // Maneja el fallo de la solicitud
                    Log.e("TAG", "Error: " + t.getMessage());
                }
            });
        });
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
                bimage= BitmapFactory.decodeStream(in);
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