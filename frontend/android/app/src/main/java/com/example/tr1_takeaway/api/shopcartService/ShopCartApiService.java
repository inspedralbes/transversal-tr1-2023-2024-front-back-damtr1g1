package com.example.tr1_takeaway.api.shopcartService;

import com.example.tr1_takeaway.ui.shopcart.ShoppingCart;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ShopCartApiService {
    @GET("/api/getShoppingCart")
    Call<ShoppingCart> getShoppingCart(@Query("usuari") String usuari);
}


