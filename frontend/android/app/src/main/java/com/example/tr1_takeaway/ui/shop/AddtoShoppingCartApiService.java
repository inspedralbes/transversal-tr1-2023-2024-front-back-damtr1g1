package com.example.tr1_takeaway.ui.shop;

import com.example.tr1_takeaway.ui.shop.AddtoShoppingCartResponse;

import retrofit2.Call;
import retrofit2.http.POST;

public interface AddtoShoppingCartApiService {
    @POST("/api/addShoppingCartProduct")
    Call<AddtoShoppingCartResponse> addShoppingCartProduct();
}

