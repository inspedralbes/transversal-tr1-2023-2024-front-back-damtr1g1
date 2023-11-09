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
    public ShoppingCart shoppingCart;
    MainActivity main = new MainActivity();
    public void GetShoppingCart(String nomUsuari, Callback<ShoppingCart> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(main.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            ShopCartApiService api = retrofit.create(ShopCartApiService.class);

            Call<ShoppingCart> call = api.getShoppingCart(nomUsuari);
            call.enqueue(new Callback<ShoppingCart>() {
                @Override
                public void onResponse(@NonNull Call<ShoppingCart> callCart, @NonNull Response<ShoppingCart> response) {
                    if (!response.isSuccessful()) {
                        Log.e("Error", "Error en la repsuesta");
                        Log.d("Error", nomUsuari);
                        return;
                    }
                    shoppingCart = response.body();

                    assert shoppingCart != null;
                    //String username = shoppingCart.getUsuari();
                    Log.d("Va", "La solicitud va");
                }

                @Override
                public void onFailure(@NonNull Call<ShoppingCart> callCart, @NonNull Throwable t) {
                    Log.e("Fail", "Error failed");
                }
            });

        }
    }

