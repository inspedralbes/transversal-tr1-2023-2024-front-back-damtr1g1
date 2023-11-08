package com.example.tr1_takeaway.api.shopcartService;

import com.example.tr1_takeaway.ui.shopcart.ShoppingCartProduct;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface addProductToCart {
    @POST("/api/addShoppingCartProduct") // Aquí debes colocar la URL
    Call<Void> crearCarritoProducto(@Body ShoppingCartProduct shoppingCartProduct);
}

