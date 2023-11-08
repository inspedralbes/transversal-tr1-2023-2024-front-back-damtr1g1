package com.example.tr1_takeaway.ui.shop;

import android.util.Log;

import androidx.annotation.NonNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddShoppingCartToNode {
    public void CreateShoppingCart(String nomUsuari) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.205.249:3001")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ShopCartApiService apiInterface = retrofit.create(ShopCartApiService.class);
        Call<ResponseBody> call = apiInterface.crearCarritoCompra(nomUsuari);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Operación exitosa
                    Log.d("RESPONSE", "Carrito Creado con éxito.");
                } else {
                    // Error en la operación
                    Log.e("RESPONSE", "Error al insertar Carrito Producto.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                // Manejo de fallos en la solicitud
                Log.e("RESPONSE", "Fallo en la solicitud: " + t.getMessage());
            }
        });
    }
}
