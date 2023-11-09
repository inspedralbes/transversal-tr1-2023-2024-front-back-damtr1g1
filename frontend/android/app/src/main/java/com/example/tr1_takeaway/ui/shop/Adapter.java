package com.example.tr1_takeaway.ui.shop;

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
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final List<ProductDataModel> data;
    AddShoppingCartToNode AS = new AddShoppingCartToNode();
    String ProducIDString;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ProductID, ProductName, ProductDefinition, ProductPrice, ProductCategory, ProductQuantity;
        public ImageView ProductImage;
        public Button button;

        public ViewHolder(View itemView) {
            super(itemView);
            ProductID = itemView.findViewById(R.id.productID);
            ProductName = itemView.findViewById(R.id.productName);
            ProductDefinition = itemView.findViewById(R.id.productDescription);
            ProductPrice = itemView.findViewById(R.id.productPrice);
            ProductCategory = itemView.findViewById(R.id.productCategory);
            ProductQuantity = itemView.findViewById(R.id.productQuantity);
            ProductImage = itemView.findViewById(R.id.productImage);
            button = itemView.findViewById(R.id.addProductToCart);
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
        holder.getProductID().setText(String.valueOf(currentItem.getId())); // Convertir a String si es un ID de recurso
        holder.getProductName().setText(currentItem.getNom());
        holder.getProductDefinition().setText(currentItem.getDefinicio());
        holder.getProductPrice().setText(String.valueOf(currentItem.getPreu())); // Usar el precio real del producto
        holder.getProductCategory().setText(String.valueOf(currentItem.getCategoria_id())); // Usar la categoría real del producto
        holder.getProductQuantity().setText(String.valueOf(currentItem.getQuantitat())); // Usar la cantidad real del producto
        Picasso.get().load(currentItem.getImageUrl()).into(holder.ProductImage); // L'imatge la pasem per picasso
        holder.button.setOnClickListener(v -> {
            String productIDContent = holder.getProductID().getText().toString();
            int productIDIntContent = Integer.parseInt(productIDContent);

            // Retrofit initialization
            MainActivity MA = new MainActivity();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MA.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            addProductToCart service = retrofit.create(addProductToCart.class);
            //int quantity = 1 // aquí obtienes el valor de cantidad de alguna manera
            String userID = MA.getUsernameText();
            AS.GetShoppingCart(userID, new Callback<ShoppingCart>() {
                @Override
                public void onResponse(@NonNull Call<ShoppingCart> callCart, @NonNull Response<ShoppingCart> response) {
                    if (!response.isSuccessful()) {
                        Log.e("Error", "Error en la repsuesta");
                        Log.d("Error", userID);
                        return;
                    }
                    AS.shoppingCart = response.body(); // Guarda el carrito de compras en la variable de instancia
                    assert AS.shoppingCart != null;
                    Log.d("Va", "La solicitud va");

                    if (AS.shoppingCart != null) { // Comprueba si se ha obtenido un carrito de compras
                        int cartId = AS.shoppingCart.getId_carrito();
                        Log.d("Cart id", String.valueOf(cartId));
                        ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct(1, cartId, productIDIntContent);

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
                    } else {
                        Log.e("Error", "No se ha obtenido un carrito de compras");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ShoppingCart> callCart, @NonNull Throwable t) {
                    Log.e("Fail", "Error failed");
                }
            });
        });

    }
    @Override
    public int getItemCount() {
        return data.size();
    }

}