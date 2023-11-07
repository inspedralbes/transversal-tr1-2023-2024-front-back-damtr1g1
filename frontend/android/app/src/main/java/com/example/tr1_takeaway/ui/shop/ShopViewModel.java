package com.example.tr1_takeaway.ui.shop;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr1_takeaway.shopService.ShopApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShopViewModel extends ViewModel {

    private RecyclerView recyclerView;
    private Adapter adapter;

    public void fetchDataFromApi() {
        // Retrofit initialization
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.205.249:3001")
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
