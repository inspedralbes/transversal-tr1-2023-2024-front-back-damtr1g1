package com.example.tr1_takeaway.ui.shop;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr1_takeaway.MainActivity;
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
    private RecyclerView recyclerView;
    private Adapter adapter;
    public void addProductToCart(View view) {
        Button addProduct = view.findViewById(R.id.addProductToCart);
        EditText productID = view.findViewById(R.id.productID);

        addProduct.setOnClickListener(v -> {
            String productIDContent = productID.getText().toString();
            int productIDIntContent = Integer.parseInt(productIDContent);

            // Retrofit initialization
            MainActivity MA = new MainActivity();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MA.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            addProductToCart service = retrofit.create(addProductToCart.class);
            //int quantity = // aquí obtienes el valor de cantidad de alguna manera
            int cartId = 0; // aquí obtienes el valor del ID del carrito de alguna manera
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
        });
    }


    public void fetchDataFromApi() {
        MainActivity MA = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MA.URL)
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
