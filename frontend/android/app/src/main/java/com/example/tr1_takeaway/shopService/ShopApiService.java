package com.example.tr1_takeaway.shopService;

import com.example.tr1_takeaway.loginService.LoginResponse;
import com.example.tr1_takeaway.ui.shop.ProductDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ShopApiService {

    @GET("/api/getProducts")
    Call<List<ProductDataModel>> getProducts();
}

