package com.example.tr1_takeaway.api.shopcartService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ShopCartApiService {
    @POST("/api/addShoppingCart")
    Call<ResponseBody> crearCarritoCompra(@Query("nomUsuari") String nomUsuari);
}

