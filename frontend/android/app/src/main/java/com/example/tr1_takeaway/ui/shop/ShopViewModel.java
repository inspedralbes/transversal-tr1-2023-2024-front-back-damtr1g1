package com.example.tr1_takeaway.ui.shop;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr1_takeaway.R;
import com.example.tr1_takeaway.shopService.ShopApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShopViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private RecyclerView recyclerView;
    private Adapter adapter;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        Log.e("TAG", "what the fuck is a kilometer");

        recyclerView = view.findViewById(R.id.productDisplay);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2)); // 2 columns grid

        // Call the method to fetch data from Retrofit
        fetchDataFromApi();

        return view;
    }

    private void fetchDataFromApi() {
        // Retrofit initialization
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.56.1:3001")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ShopApiService apiService = retrofit.create(ShopApiService.class);

        Call<List<ProductDataModel>> call = apiService.getProducts();

        call.enqueue(new Callback<List<ProductDataModel>>() {
            @Override
            public void onResponse(Call<List<ProductDataModel>> call, Response<List<ProductDataModel>> response) {
                if (response.isSuccessful()) {
                    List<ProductDataModel> data = response.body();
                    adapter = new Adapter(data);
                    recyclerView.setAdapter(adapter);
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<ProductDataModel>> call, Throwable t) {
                Log.e("TAG", "what the fuck is a kilometer");
            }
        });
    }

    private LayoutInflater productInflater;
    private ViewGroup container;
    private Bundle savedInstanceState;
    private View productView;

    public ShopViewModel() {
        assert productInflater != null;
        productView = onCreateView(productInflater, container,savedInstanceState);
    }


}