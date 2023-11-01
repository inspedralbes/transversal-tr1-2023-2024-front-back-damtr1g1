package com.example.tr1_takeaway.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr1_takeaway.ApiService;
import com.example.tr1_takeaway.R;
import com.example.tr1_takeaway.databinding.FragmentShopBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShopFragment extends Fragment {

    private FragmentShopBinding binding;

    public class MyFragment extends Fragment {

        private RecyclerView recyclerView;
        private Adapter adapter;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_shop, container, false);

            recyclerView = view.findViewById(R.id.productDisplay);
            recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // 2 columns grid

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

            ApiService apiService = retrofit.create(ApiService.class);

            Call<List<ProductDataModel>> call = apiService.getProducts();

            call.enqueue(new Callback<List<ProductDataModel>>() {
                @Override
                public void onResponse(Call<List<ProductDataModel>> call, Response<List<ProductDataModel>> response) {
                    if (response.isSuccessful()) {
                        List<ProductDataModel> data = response.body();
                        adapter = new Adapter(data);
                        recyclerView.setAdapter(adapter);
                    } else {
                        // Handle API error
                    }
                }

                @Override
                public void onFailure(Call<List<ProductDataModel>> call, Throwable t) {
                    // Handle network failure
                }
            });
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}