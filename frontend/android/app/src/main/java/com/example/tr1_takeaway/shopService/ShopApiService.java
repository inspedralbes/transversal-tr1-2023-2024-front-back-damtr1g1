package com.example.tr1_takeaway.shopService;

import android.widget.Button;


import com.example.tr1_takeaway.ui.shop.ProductDataModel;
import com.example.tr1_takeaway.ui.shopcart.ShopcartProductDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ShopApiService {

    @GET("/api/getProducts")
    Call<List<ProductDataModel>> getProducts();

    @GET("/api/getCart")
    Call<Button> getCart();

    @GET("/api/getCartProduct")
    Call<List<ShopcartProductDataModel>> getCartProducts();

    @POST("/api/addShoppingCartProduct")
    Call<List<ShopcartProductDataModel>> addShoppingCartProduct(
            @Query("quantitat") int quantity,
            @Query("id_carret") int shopcartId,
            @Query("id_producte") int productId
    );

    @POST("/api/deleteShoppingCartProduct")
    Call<List<ShopcartProductDataModel>> deleteShoppingCartProduct(
            @Query("idCarritoProductoEliminar") int deletedProductId
    );

    @POST("/api/addComanda")
    Call<ShopResponse> addComanda(
            @Query("id_carret") int shopcartId,
            @Query("usuari") String usuario
    );

}

