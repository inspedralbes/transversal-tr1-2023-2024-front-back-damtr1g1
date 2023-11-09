package com.example.tr1_takeaway.ui.shopcart;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tr1_takeaway.MainActivity;
import com.example.tr1_takeaway.api.shopcartService.ShopCartApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddShoppingCartToNode {
    MainActivity main = new MainActivity();
    private String cartId;
    public int id;
    public void GetShoppingCart(String nomUsuari) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(main.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ShopCartApiService api = retrofit.create(ShopCartApiService.class);

        Call<ShoppingCart> call = api.getShoppingCart(nomUsuari);
        call.enqueue(new Callback<ShoppingCart>() {
            @Override
            public void onResponse(@NonNull Call<ShoppingCart> call, @NonNull Response<ShoppingCart> response) {
                if (response.isSuccessful()) {
                    ShoppingCart shoppingCartResponse = response.body();
                    if (shoppingCartResponse != null) {
                        cartId = shoppingCartResponse.getId();
                        Log.d("CartId", "ID del carrito: " + cartId);
                        id = Integer.parseInt(cartId);
                    }
                } else {
                    Log.e("Error", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ShoppingCart> call, @NonNull Throwable t) {
                Log.e("Fail", "Error en la solicitud: " + t.getMessage());
            }
        });
    }

}


