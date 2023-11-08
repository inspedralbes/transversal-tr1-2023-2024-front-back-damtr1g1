package com.example.tr1_takeaway.ui.shop;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr1_takeaway.R;
import com.example.tr1_takeaway.api.shopService.ShopApiService;
import com.example.tr1_takeaway.api.shopcartService.addProductToCart;
import com.example.tr1_takeaway.ui.shopcart.ShoppingCartProduct;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShopViewModel extends ViewModel {
    public Button addProduct;
    private RecyclerView recyclerView;
    private Adapter adapter;

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    public void addProductToCart() {
        addProduct.findViewById(R.id.addProductToCart);

        // Retrofit initialization
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.205.249:3001")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        addProductToCart service = retrofit.create(addProductToCart.class);

        addProduct.setOnClickListener(v -> {
            int quantity = // aquí obtienes el valor de cantidad de alguna manera
            int cartId = // aquí obtienes el valor del ID del carrito de alguna manera
            int productId = // aquí obtienes el valor del ID del producto de alguna manera

            ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct(quantity, cartId, productId);

            Call<Void> call = service.crearCarritoProducto(shoppingCartProduct);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Maneja la respuesta exitosa
                        Log.d("TAG", "Carrito Producto insertado con éxito.");
                    } else {
                        // Maneja la respuesta de error
                        Log.e("TAG", "Error al insertar Carrito Producto: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // Maneja el fallo de la solicitud
                    Log.e("TAG", "Error: " + t.getMessage());
                }
            });
        });
    }

    public void fetchDataFromApi() {
        // Retrofit initialization
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.205.249:3001")
                //.baseUrl("http://192.168.205.63:3001") // URL Marti
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ShopApiService apiService = retrofit.create(ShopApiService.class);

        Call<List<ProductDataModel>> call = apiService.getProducts();

        call.enqueue(new Callback<List<ProductDataModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProductDataModel>> call, @NonNull Response<List<ProductDataModel>> response) {
                if (response.isSuccessful()) {
                    List<ProductDataModel> data = response.body();
                    adapter = new Adapter(data);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e("TAG", "Response not successful");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ProductDataModel>> call, @NonNull Throwable t) {
                Log.e("TAG", "Error fetching data from API", t);
            }
        });
    }
}
